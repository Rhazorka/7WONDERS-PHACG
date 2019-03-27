package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.SocketConfig;
import com.google.gson.Gson;

import commun.Identification;
import commun.Moteur;
import commun.Plateau;
import commun.Ressource;
import commun.Joueur;
import commun.Carte_victoire;
import commun.Carte;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Serveur {
    public final static int NB_JOUEURS = 3;
	private SocketIOServer serveur;
    private Map <String,Joueur> listeJoueur = new HashMap<String,Joueur>();
    private final Object lock = new Object();
    ArrayList<Carte> me1 ; // temporaire : un joueur doit avoir son deck

    public Serveur(Configuration config) { 
        razCompteurNbCoupDuTour();
        serveur = new SocketIOServer(config);
        System.out.println("serveur : préparation du listener");
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("serveur : connexion de "+socketIOClient.getRemoteAddress().toString());
            }
        });

        Moteur mo1 = new Moteur();
        Plateau p1 = mo1.getGizah_a();
        me1 = mo1.getdeckA1();        
        mo1.melangerDeck_A1();
        ArrayList<SocketIOClient> sockettemp = new ArrayList<SocketIOClient>();

        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                synchronized(lock){
                    identification.setNom("Joueur "+listeJoueur.size());
                    System.out.println("serveur : le client est "+identification.getNom());
                    System.out.println("serveur : remote address : "+socketIOClient.getRemoteAddress().toString());
                    listeJoueur.put(socketIOClient.getRemoteAddress().toString(),new Joueur(identification));
                    System.out.println("serveur : il y a maintenant "+listeJoueur.size()+" joueurs");
                    distribPlateau(socketIOClient,p1);
                    listeJoueur.get(socketIOClient.getRemoteAddress().toString()).ajouterPlateau(p1);  // ajoute le plateau dans leJoueur
                }
            }
        });

        serveur.addEventListener("distributionPlateau", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String reponseJSON, AckRequest ackRequest) throws Exception {
                if (tousLesJoueursOntJoue()) {
                    faireUnTourDejeu();
                }
            } 
        });

        serveur.addEventListener("choixCarte", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String carteChoisiJSON, AckRequest ackRequest) throws Exception {
                String jsonstr = carteChoisiJSON;  
                Gson gson = new Gson();
                Carte_victoire carteChoisi = gson.fromJson(jsonstr, Carte_victoire.class);        
                Joueur leJoueur = listeJoueur.get(socketIOClient.getRemoteAddress().toString());
                leJoueur.ajouterCarte(carteChoisi);
                System.out.println("serveur : le "+leJoueur.getId().getNom()+" a maintenant "+leJoueur.getPtsVictoire()+" pts de victoires");
                System.out.println("serveur : carteChoisi = "+carteChoisi);
    
    //            System.out.println("-----------TEST--------");
    //            System.out.println(leJoueur.toString());
    //            System.out.println("---------------------");

                if (tousLesJoueursOntJoue()) {
                    System.out.println("------------------------------------------------------------------------------------");
                    serveur.stop(); // ou on fait un tour de plus ou on change d'age ou on a fini
                    System.exit(0);
                }
            } 
        });
    }

    synchronized boolean tousLesJoueursOntJoue() {
        nbCoupDuTour++;
        return (nbCoupDuTour >= NB_JOUEURS);
    }

    private int nbCoupDuTour = 0;
    synchronized void razCompteurNbCoupDuTour() {
        nbCoupDuTour = 0;
    }

    synchronized boolean tousLesJoueurSontConnecte(){
        if(listeJoueur.size()==NB_JOUEURS)
            return true;
        else
            return false;
    }

    void faireUnTourDejeu() {
        razCompteurNbCoupDuTour();
        for( SocketIOClient s : serveur.getAllClients()) {
            Joueur j = listeJoueur.get(s.getRemoteAddress().toString());
            poserUneQuestion(s, me1); // ou plutot j.getDeck();
        }
    }

    public void demarrer() {
        serveur.start();
        System.out.println("serveur : en attente de connexion");
        /*
        System.out.println("serveur : une connexion est arrivée, on arrête");
        serveur.stop();

        //On tue le programme 
        System.exit(0);
        */
    }

    private void distribPlateau(SocketIOClient socketIOClient, Plateau pl) {
        Gson gson = new Gson();
    	String json = new Gson().toJson(pl);
        socketIOClient.sendEvent("distributionPlateau",json);
    }
    
    private void poserUneQuestion(SocketIOClient socketIOClient, ArrayList<Carte> deck) {
    	Gson gson = new Gson();
        String json = new Gson().toJson(deck);
        socketIOClient.sendEvent("choixCarte",json);
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        // permet de réutiliser l'adresse du port (fix linux)
        SocketConfig sockConfig = new SocketConfig();
        sockConfig.setReuseAddress(true);
        config.setSocketConfig(sockConfig);

        Serveur serveur = new Serveur(config);
        serveur.demarrer();
        System.out.println("serveur : fin du main");
    }
}
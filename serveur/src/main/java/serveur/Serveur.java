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
import java.util.concurrent.Semaphore;

public class Serveur {
	private SocketIOServer serveur;
    private final Object attenteConnexion = new Object();
    private Map <String,Joueur> listeJoueur = new HashMap<String,Joueur>();
    private final Object lock = new Object();

    public Serveur(Configuration config) { 
        serveur = new SocketIOServer(config);
        System.out.println("serveur : préparation du listener");
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("serveur : connexion de "+socketIOClient.getRemoteAddress().toString());
            }
        });

        Moteur mo1 = new Moteur();
        Plateau p1 = mo1.getGizah_a();
        ArrayList<Carte> me1 = mo1.getdeckA1();        

        mo1.melangerDeck_A1();

        //réception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                synchronized(lock){
                    identification.setNom("Joueur "+listeJoueur.size());
                    System.out.println("serveur : le client est "+identification.getNom());
                    /*on créer le joueur*/
    
                    /* un joueur est identifié tant qu'il reste des plateaux dans le deck */
                    //for(Plateau plat : deck_plat){
                        //Joueur joueur = new Joueur(plat,identification);
                        System.out.println("serveur : remote adress : "+socketIOClient.getRemoteAddress().toString());
                        System.out.println("serveur : UIID : "+socketIOClient.getSessionId().toString());
                        System.out.println("serveur : namespace : "+socketIOClient.getNamespace().toString());
                        listeJoueur.put(socketIOClient.getRemoteAddress().toString(),new Joueur(identification));
                        /*for (Map.Entry mapentry : listeJoueur.entrySet()) {
                            System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
                        }*/
                        System.out.println("serveur : il y a maintenant "+listeJoueur.size()+" joueurs");
                        //distribPlateau(socketIOClient, plat);
                        distribPlateau(socketIOClient,p1);
                    //}
                }
            }
        });

        serveur.addEventListener("distributionPlateau", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String reponseJSON, AckRequest ackRequest) throws Exception {
                synchronized(lock){
                    for (Map.Entry mapentry : listeJoueur.entrySet()) {
                        if(mapentry.getKey().equals(socketIOClient.getRemoteAddress().toString())){
                            poserUneQuestion(socketIOClient,me1);
                        }
                    }
                }
            } 
        });

        serveur.addEventListener("choixCarte", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String carteChoisiJSON, AckRequest ackRequest) throws Exception {
                String jsonstr = carteChoisiJSON;  
                Gson gson = new Gson();
                Carte_victoire carteChoisi = gson.fromJson(jsonstr, Carte_victoire.class);
                for (Map.Entry mapentry : listeJoueur.entrySet()) {
                    if(mapentry.getKey().equals(socketIOClient.getRemoteAddress().toString())){
                        Joueur jtemp = (Joueur)mapentry.getValue();
                        System.out.println("serveur : la réponse de  "+jtemp.getId().getNom()+" est "+carteChoisiJSON.toString());
                        jtemp.ajouterCarte(carteChoisi);
                        String keytemp = (String)mapentry.getKey();
                        listeJoueur.remove(mapentry.getKey());
                        listeJoueur.put(keytemp, jtemp);
                        jtemp.ajouterCarte(carteChoisi);                        
                        System.out.println("serveur : le "+jtemp.getId().getNom()+" a maintenant "+jtemp.getPtsVictoire()+" pts de victoires");
                    }
                }
                System.out.println("serveur : carteChoisi = "+carteChoisi);
                synchronized (attenteConnexion) {
                	attenteConnexion.notify();
                }
            } 
        });
    }


    public void demarrer() {
        serveur.start();
        System.out.println("serveur : en attente de connexion");
        synchronized (attenteConnexion) {
            try {
                attenteConnexion.wait();
            } 
            catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("serveur : erreur dans l'attente");
            }
        }
        System.out.println("serveur : une connexion est arrivée, on arrête");
        serveur.stop();

        //On tue le programme 
        System.exit(0);
    }
    private void distribPlateau(SocketIOClient socketIOClient, Plateau pl)
    {
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
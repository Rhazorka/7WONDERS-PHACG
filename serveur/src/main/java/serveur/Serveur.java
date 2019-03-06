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
	private SocketIOServer serveur;
    private final Object attenteConnexion = new Object();
    private Map <Joueur,SocketIOClient> listeJoueur = new HashMap<Joueur,SocketIOClient>();

    public Serveur(Configuration config) {
        serveur = new SocketIOServer(config);
        System.out.println("serveur : préparation du listener");
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("serveur : connexion de "+socketIOClient.getRemoteAddress());
            }
        });
		Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
		Carte_victoire c1 = new Carte_victoire("victoire_3",r1,3);
		Carte_victoire c4 = new Carte_victoire("victoire_5",r1,5);

		Ressource[] r2 = {Ressource.BOIS,Ressource.BOIS,Ressource.BOIS};
		Carte_victoire c2 = new Carte_victoire("victoire_5",r2,5);
		Carte_victoire c5 = new Carte_victoire("victoire_7",r2,3);

		Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
		Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);
        Carte_victoire c6 = new Carte_victoire("victoire_3",r3,3);

		ArrayList<Carte> me1 = new ArrayList<>();
		me1.add(c1);
		me1.add(c2);
		me1.add(c3);

        ArrayList<Carte> me2 = new ArrayList();
        me2.add(c4);
        me2.add(c5);
        me2.add(c6);
		
		Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
        Plateau p2 = new Plateau("Alexandrie", Ressource.BOIS,me2);

        ArrayList<Plateau> deck_plat = new ArrayList();
        deck_plat.add(p1);
        deck_plat.add(p2);

		Moteur mo1 = new Moteur(me1);
        Moteur mo2 = new Moteur(me2);

		mo1.melangerCartes();
        mo2.melangerCartes();

        //réception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                identification.setNom("Joueur "+listeJoueur.size());
                System.out.println("serveur : le client est "+identification.getNom());
                /*on créer le joueur*/

                /* un joueur est identifié tant qu'il reste des plateaux dans le deck */
                for(Plateau plat : deck_plat){
                    Joueur joueur = new Joueur(plat,identification);
                    listeJoueur.put(joueur,socketIOClient);
                    /*for (Map.Entry mapentry : listeJoueur.entrySet()) {
                        System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
                    }*/
                    System.out.println("serveur : il y a maintenant "+listeJoueur.size()+" joueurs");
                    //System.out.println("serveur : me1 = "+me1.toString());
                    //System.out.println("serveur : merv1 = "+p1);
                    distribPlateau(socketIOClient, plat);
                }
            }
        });

        serveur.addEventListener("distributionPlateau", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String reponseJSON, AckRequest ackRequest) throws Exception {
                for (Map.Entry mapentry : listeJoueur.entrySet()) {
                    //System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
                    if(mapentry.getValue().equals(socketIOClient)){
                        poserUneQuestion(socketIOClient,me1);
                    }
                }
            } 
        });
        // on attend une réponse
        serveur.addEventListener("choixCarte", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String carteChoisiJSON, AckRequest ackRequest) throws Exception {
                String jsonstr = carteChoisiJSON;  
                Gson gson = new Gson();
                Carte_victoire carteChoisi = gson.fromJson(jsonstr, Carte_victoire.class);
                for (Map.Entry mapentry : listeJoueur.entrySet()) {
                    //System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
                    if(mapentry.getValue().equals(socketIOClient)){
                        Joueur jtemp = (Joueur)mapentry.getKey();
                        System.out.println("serveur : la réponse de  "+jtemp.getId().getNom()+" est "+carteChoisiJSON.toString());
                        jtemp.ajouterCarte(carteChoisi);
                        SocketIOClient old = listeJoueur.remove(mapentry.getKey());
                        listeJoueur.put(jtemp,old);
                        System.out.println("serveur : le "+jtemp.getId().getNom()+" a maintenant "+jtemp.getPtsVictoire()+" pts de victoires");
                        //listeJoueur.replace((Joueur)mapentry.getValue(),(SocketIOClient)mapentry.getKey(),jtemp);
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
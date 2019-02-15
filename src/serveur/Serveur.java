package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
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
import org.json.JSONArray;

public class Serveur {
	private SocketIOServer serveur;
    final Object attenteConnexion = new Object();
    private Identification leClient ;

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
		
		Ressource[] r2 = {Ressource.BOIS,Ressource.BOIS,Ressource.BOIS};
		Carte_victoire c2 = new Carte_victoire("victoire_5",r2,5);
		
		Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
		Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);

		ArrayList<Carte> me1 = new ArrayList<>();
		me1.add(c1);
		me1.add(c2);
		me1.add(c3);
		
		Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);

		Moteur mo1 = new Moteur(me1);

		mo1.melangerCartes();

        //réception d'une identification
        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest) throws Exception {
                System.out.println("serveur : le client est "+identification.getNom());
                leClient = new Identification(identification.getNom());

        		/*on créer le joueur*/
        		Joueur j1 = new Joueur(p1,leClient);
                System.out.println("serveur : me1 = "+me1.toString());
                poserUneQuestion(socketIOClient,me1);
            }
        });


        // on attend une réponse
        serveur.addEventListener("requete", Integer.class, new DataListener<Integer>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Integer carteChoisi, AckRequest ackRequest) throws Exception {
                System.out.println("serveur : la réponse de  "+leClient.getNom()+" est "+carteChoisi.toString());
        		//j1.ajouterCarte(carteChoisi);
                synchronized (attenteConnexion) {
                	attenteConnexion.notify();
                }
            } 
        });
    }


    private void demarrer() {
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
    }

    private void poserUneQuestion(SocketIOClient socketIOClient, ArrayList<Carte> deck) {
    	JSONArray json = new JSONArray(deck);
        socketIOClient.sendEvent("requete",deck);
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
        Serveur serveur = new Serveur(config);
        serveur.demarrer();
        System.out.println("serveur : fin du main");
    }
}

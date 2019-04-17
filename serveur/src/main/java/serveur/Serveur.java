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
import commun.Joueur;
import commun.Carte_victoire;
import commun.Carte;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Serveur {
    public final static int NB_JOUEURS = 3;
    private SocketIOServer serveur;
    private Map<SocketIOClient, Joueur> listeJoueur = new HashMap<SocketIOClient, Joueur>();
    private Map<SocketIOClient,ArrayList<Carte>> listeMainJoueurs = new HashMap<SocketIOClient,ArrayList<Carte>>();
    private final Object lock = new Object();
    private int nbCoupDuTour = 0;
    private int age = 1;
    private ArrayList<ArrayList<Carte>> listeDecks = new ArrayList<ArrayList<Carte>>();

    private ArrayList<Carte> deck1;
    private ArrayList<Carte> deck2;
    private ArrayList<Carte> deck3;

    public ArrayList<Carte> melangerDeck(ArrayList<Carte> Deck_AgeX) {
        Collections.shuffle(Deck_AgeX);
        return Deck_AgeX;
    }
    
    public String ServeurTest(){
        return "serveur : réplique reçu";
    }

    public Serveur(Configuration config) {
        razCompteurNbCoupDuTour();
        serveur = new SocketIOServer(config);
        serveur.addConnectListener(new ConnectListener() {
            public void onConnect(SocketIOClient socketIOClient) {
                System.out.println("connexion de " + socketIOClient.getRemoteAddress().toString());
            }
        });

        Moteur mo1 = new Moteur();
        Plateau p1 = mo1.getGizah_a();
        deck1 = mo1.getdeckA1();
        deck1 = melangerDeck(deck1);
        deck2 = mo1.getdeckA2();
        deck2 = melangerDeck(deck2);
        deck3 = mo1.getdeckA3();
        deck3 = melangerDeck(deck3);

        ArrayList<SocketIOClient> sockettemp = new ArrayList<SocketIOClient>();

        serveur.addEventListener("identification", Identification.class, new DataListener<Identification>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identification identification, AckRequest ackRequest)
                    throws Exception {
                synchronized (lock) {
                    identification.setNom((""+listeJoueur.size()));
                    System.out.println("le client est Joueur " + identification.getNom());
                    listeJoueur.put(socketIOClient, new Joueur(identification));
                    sockettemp.add(socketIOClient);
                    if (tousLesJoueurSontConnecte()) {
                        System.out.println("\n----------------------Debut partie----------------------");
                        for (SocketIOClient s : sockettemp) {
                            distributionPlateau(s, p1);
                        }
                    }
                    listeJoueur.get(socketIOClient).ajouterPlateau(p1); // ajoute le plateau dans leJoueur
                }
            }
        });

        serveur.addEventListener("test", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String reponseJSON, AckRequest ackRequest) throws Exception {
                    String Jreponse = reponseJSON;
                    Gson gson = new Gson();
                    System.out.println(Jreponse);
                    Jreponse = ServeurTest();
                    socketIOClient.sendEvent("retour_test", gson.toJson(Jreponse)); // retransforme Jreponse en Json
            }
        });

        serveur.addEventListener("distributionPlateau", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String reponseJSON, AckRequest ackRequest)
                    throws Exception {
                if (tousLesJoueursOntJoue()) {
                    faireUnAge(age);
                    serveur.stop();
                    System.exit(0);
                }
            }
        });

        serveur.addEventListener("choixCarte", String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String carteChoisiJSON, AckRequest ackRequest)
                throws Exception {
                String jsonstr = carteChoisiJSON;
                Gson gson = new Gson();
                Carte_victoire carteChoisi = gson.fromJson(jsonstr, Carte_victoire.class);
                //System.out.println("taille du deck : "+listeMainJoueurs.get(socketIOClient).size());
                //System.out.println("carteChoisi    : "+carteChoisi.getNom());
                for(int i=0;i<listeMainJoueurs.get(socketIOClient).size();i++){
                    //System.out.println("carteDuDeck    : "+listeMainJoueurs.get(socketIOClient).get(i).getNom());
                    if(carteChoisi.getNom().equals(listeMainJoueurs.get(socketIOClient).get(i).getNom())){
                        //System.out.println("bah on suppr : "+listeMainJoueurs.get(socketIOClient).get(i).getNom());
                        listeMainJoueurs.get(socketIOClient).remove(i);
                    }
                }
            }
        });
    }

    synchronized boolean tousLesJoueursOntJoue() {
        nbCoupDuTour++;
        return (nbCoupDuTour >= NB_JOUEURS);
    }

    synchronized void razCompteurNbCoupDuTour() {
        nbCoupDuTour = 0;
    }

    synchronized boolean tousLesJoueurSontConnecte() {
        if (listeJoueur.size() == NB_JOUEURS)
            return true;
        else
            return false;
    }

    synchronized void faireUnAge(int age) {
        razCompteurNbCoupDuTour();
        initialiserTours(age);
        System.out.println("\n\t      ~~~~~~~~~~Debut age~~~~~~~~~~");
        int i = 0;
        nbCoupDuTour = NB_JOUEURS;
        ArrayList<Carte> deckAge = new ArrayList<Carte>();
        if(age==1)
            deckAge=deck1;
        else if(age==2)
            deckAge=deck2;
        else
            deckAge=deck3;
        System.out.println("\nNombre de tours pour cet age : "+(deckAge.size()-(deckAge.size()%NB_JOUEURS))/NB_JOUEURS);
        while (i <((deckAge.size()-(deckAge.size()%NB_JOUEURS))/NB_JOUEURS)) {
            if (tousLesJoueursOntJoue()) {
                faireUnTourDejeu();
                i++;
            }
        }
        //System.out.println("listeMainJoueurs : "+listeMainJoueurs.toString());
        System.out.println("\n\t      ~~~~~~~~~~~Fin age~~~~~~~~~~~");
        System.out.println("\n-----------------------Fin partie-----------------------\n");
    }

    synchronized void initialiserTours(int age){
        for(int i=NB_JOUEURS;i>0;i--){
            listeDecks.add(CouperDeck(i,age));
        }
        Set<SocketIOClient> cles = listeJoueur.keySet();
        Iterator<SocketIOClient> it = cles.iterator();
        int i=0;
        while (it.hasNext()){
            SocketIOClient cle = it.next();
            listeMainJoueurs.put(cle,listeDecks.get(i));
            i++;
        }
        //System.out.println("listeMainJoueurs : "+listeMainJoueurs.toString());
    }

    synchronized void faireUnTourDejeu() {
        razCompteurNbCoupDuTour();
        voisin(listeJoueur);
        System.out.println("\n\t\t   ====Debut tour====\n");
        Set<SocketIOClient> cles = listeMainJoueurs.keySet();
        Iterator<SocketIOClient> it = cles.iterator();
        int i=0;
        while (it.hasNext()){
            SocketIOClient cle = it.next();
            choixCarte(cle, listeMainJoueurs.get(cle));
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\t\t   =====Fin tour=====");
    }

    public void demarrer() {
        serveur.start();
    }

    private void distributionPlateau(SocketIOClient socketIOClient, Plateau pl) {
    	String json = new Gson().toJson(pl);
        socketIOClient.sendEvent("distributionPlateau",json);
    }
    
    private void choixCarte(SocketIOClient socketIOClient, ArrayList<Carte> deck) {
        String json = new Gson().toJson(deck);
        socketIOClient.sendEvent("choixCarte",json);
    }

    public ArrayList<Carte> CouperDeck(int joueur, int age) {
        ArrayList<Carte> mainAge = new ArrayList<Carte>();
        ArrayList<Carte> deckAge = new ArrayList<Carte>();
        if(age==1)
            deckAge=deck1;
        else if(age==2)
            deckAge=deck2;
        else
            deckAge=deck3;
        for(int i=0;i<((deckAge.size()-(deckAge.size()%NB_JOUEURS))/NB_JOUEURS)+1;i++)
            mainAge.add(deckAge.get(i+joueur*NB_JOUEURS));
        return mainAge;
    }

    public static void voisin(Map mp) {
        ArrayList<Integer> in = new ArrayList<Integer>();
        int i=0;
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            i+=1;
            in.add(i);
        }

        int k = in.size()-1;
        for(int j=0;j<in.size();j++){
            System.out.println("Joueur :"+j);
            if((j-1)%3==-1){
                System.out.println("Voisin gauche :"+k);
            }else{
                System.out.println("Voisin gauche :"+(j-1)%3);
            }
            System.out.println("Voisin droite :"+(j+1)%3);
        }
    } 

    private void test(SocketIOClient socketIOClient){
        //Gson gson = new Gson();
        //String json = new Gson().toJson(str);
        socketIOClient.sendEvent("test");
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

        SocketConfig sockConfig = new SocketConfig();
        sockConfig.setReuseAddress(true);
        config.setSocketConfig(sockConfig);

        Serveur serveur = new Serveur(config);
        serveur.demarrer();
    }
}
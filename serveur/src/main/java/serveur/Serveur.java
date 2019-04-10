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
    private Map<Joueur,ArrayList<Carte>> listeMainJoueurs = new HashMap<Joueur,ArrayList<Carte>>();
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

                /*for (int i = 0; i < listeDecks.size(); i++) {
                    for (int j = 0; j < listeDecks.get(i).size(); j++) {
                        //System.out.println("carte du deck : "+listeDecks.get(i).get(j).getNom());
                        //System.out.println("carte choisi  : "+carteChoisi.getNom());
                        if(carteChoisi.getNom().equals(listeDecks.get(i).get(j).getNom())){
                            //System.out.println("remove de :"+listeDecks.get(i).get(j).getNom());
                            listeJoueur.get(socketIOClient).ajouterCarte(listeDecks.get(i).get(j));
                            listeDecks.get(i).remove(j);
                        }
                    }
                }*/

                /*cette méthode ne fonctionne que si les decks ne sont pas transmis d'une main à l'autre*/
                //System.out.println("--carteChoisi-- : "+carteChoisi.getNom());
                for(int x=0;x<listeDecks.get(Integer.parseInt(listeJoueur.get(socketIOClient).getId().getNom())).size();x++){
                    //System.out.println("carteDuDeck : "+listeDecks.get(listeJoueur.get(socketIOClient).getId().getNom()).get(x).getNom());
                    if(carteChoisi.getNom().equals(listeDecks.get(Integer.parseInt(listeJoueur.get(socketIOClient).getId().getNom())).get(x).getNom())){
                        listeJoueur.get(socketIOClient).ajouterCarte(listeDecks.get(Integer.parseInt(listeJoueur.get(socketIOClient).getId().getNom())).get(x));
                        listeDecks.get(Integer.parseInt(listeJoueur.get(socketIOClient).getId().getNom())).remove(x);
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
        System.out.println("\n\t      ~~~~~~~~~~~Fin age~~~~~~~~~~~");
        System.out.println("\n-----------------------Fin partie-----------------------\n");
    }

    synchronized void initialiserTours(int age){
        for(int i=NB_JOUEURS;i>0;i--){
            listeDecks.add(CouperDeck(i,age));
        }
    }

    synchronized void faireUnTourDejeu() {
        razCompteurNbCoupDuTour();
        System.out.println("\n\t\t   ====Debut tour====\n");
        Set cles = listeJoueur.keySet();
        Iterator it = cles.iterator();
        int i=0;
        while (it.hasNext()){
            Object cle = it.next();
            choixCarte((SocketIOClient)cle, listeDecks.get(i));
            //System.out.println("Main : "+i+" attribué à Joueur "+listeJoueur.get((SocketIOClient)cle).getId().getNom());
            //i++; commenter car sinon des decks différents sont attribués aux joueurs mais la suppression des cartes de ces mains ne fonctionne pas parfaitement pour le moment
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
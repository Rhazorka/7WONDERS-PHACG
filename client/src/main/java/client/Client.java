package client;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import com.google.gson.Gson;

import commun.Identification;
import commun.Moteur;
import commun.Carte_victoire;
import commun.Carte;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client extends Thread {
    private Identification moi = new Identification();
    private Socket connexion;
    private final Object attenteDeconnexion = new Object();
    private String urlServeur;

    public Client(String urlServeur) {
        this.urlServeur=urlServeur;
        try {
            connexion = IO.socket(urlServeur);
            System.out.println("client : on s'abonne à la connection / déconnection ");;
            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println("client : on est connecté et on s'identifie ");
                    JSONObject id = new JSONObject(moi);
                    connexion.emit("identification", id); //transmet l'objet moi au serveur
                }
            });
            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println("client : on est déconnecté");
                    connexion.disconnect();
                    connexion.close();
                    synchronized (attenteDeconnexion) {
                        attenteDeconnexion.notify();
                    }
                }
            });
            this.start();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void seConnecter() {
        connexion.connect();
        System.out.println("client : en attente de déconnexion");
        synchronized (attenteDeconnexion) {
            try {
                attenteDeconnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("client : erreur dans l'attente");
            }
        }
    }

    public void run(){
        connexion.on("choixCarte", new Emitter.Listener() { // on recoit une requete de la part du serveur
            @Override
            public void call(Object... objects) {
                //System.out.println("client : on a reçu une requête avec "+objects.length+" paramètre(s)");
                ArrayList<Carte> cartes = new ArrayList<Carte>();
                System.out.println("client : recu Deck = "+(String)objects[0]);
                String jsonstr = (String) objects[0];  
                Gson gson = new Gson();
                Carte_victoire[] deck = gson.fromJson(jsonstr, Carte_victoire[].class);
                //System.out.println("client : converti = :"+deck.toString());
                ArrayList<Carte> deckAL = new ArrayList<Carte>(Arrays.asList(deck));
                Moteur motemp = new Moteur(deckAL);
                Carte carteChoisi = motemp.choisirCarte();
                System.out.println("client : carteChoisi = "+carteChoisi.toString());
                String json = new Gson().toJson(carteChoisi);
                connexion.emit("choixCarte",json);
            }
        });
        connexion.on("distributionPlateau", new Emitter.Listener() { // on recoit une requete de la part du serveur avec le plateau
            @Override
            public void call(Object... objects) {
                //System.out.println("client : on a reçu une requête avec "+objects.length+" paramètre(s) ");
                Boolean rep =false;
                if(objects.length>0){
                    System.out.println("client : recu Plateau = "+(String)objects[0]);
                    rep=true;
                }
                String json = new Gson().toJson(rep);
                connexion.emit("distributionPlateau",json);
            }
        });
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8")); //réassigne la sortie sur le flux de système standard
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Client client = new Client("http://127.0.0.1:10101");
        client.seConnecter();
        System.out.println("client : fin du main");
    }
}
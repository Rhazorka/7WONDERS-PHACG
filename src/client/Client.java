package client;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import commun.Identification;
import commun.Ressource;
import commun.Carte_victoire;
import commun.Carte;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Client {
    Identification moi = new Identification("Joueur 1");
    Socket connexion;
    final Object attenteDeconnexion = new Object();

    public Client(String urlServeur) {
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

            connexion.on("requete", new Emitter.Listener() { // on recoit une requete de la part du serveur
                @Override
                public void call(Object... objects) {
                    System.out.println("client : on a reçu une requête avec "+objects.length+" paramètre(s)");
                    ArrayList<Carte> cartes = new ArrayList<Carte>();
                    JSONArray tab = (JSONArray) objects[0];
                    for(int i = 0; i < tab.length(); i++) {
                        try {
                            cartes.add(new Carte_victoire(tab.getJSONObject(i).getString("nom"),(Ressource[])tab.getJSONObject(i).get("cout"),tab.getJSONObject(i).getInt("pts")));
                        } 
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    connexion.emit("requete",1);
                    }
                }
            });
        } 
        catch (URISyntaxException e) {
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

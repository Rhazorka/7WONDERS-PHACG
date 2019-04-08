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

public class Client {
    private Identification moi = new Identification();
    private Socket connexion;
//    private final Object attenteDeconnexion = new Object();
    private final Object lock = new Object();

    public Carte choisirCarte(ArrayList<Carte> cartes) {
		Carte ret;
		System.out.println("Le bot choisi le numéro de la carte qu'il va jouer : ");
		for (Carte c : cartes) {
			 System.out.println(cartes.indexOf(c)+" : "+c.toString());
		}
		int choix_bot = (int) (Math.random() * cartes.size());
		while(true) {
			try {
				ret = cartes.get(choix_bot);
				cartes.remove(choix_bot);
				return ret;
			}catch(NumberFormatException e) {
				System.out.println("Il faut que la selection soit un chiffre");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Il faut que le chiffre soit compris entre 0 et "+(cartes.size()-1));
			}
		}
	}

    public Client(String urlServeur, String nom) {
        try {
            moi.setNom(nom);
            connexion = IO.socket(urlServeur);

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    JSONObject id = new JSONObject(moi);
                    connexion.emit("identification", id); // transmet l'objet moi au serveur
                }
            });
            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    connexion.disconnect();
                    connexion.close();
                    System.exit(0);
                }
            });
            connexion.on("choixCarte", new Emitter.Listener() { // on recoit une requete de la part du serveur
                @Override
                public void call(Object... objects) {
                    synchronized (lock) {
                        ArrayList<Carte> cartes = new ArrayList<Carte>();
                        String jsonstr = (String) objects[0];
                        Gson gson = new Gson();
                        Carte_victoire[] deck = gson.fromJson(jsonstr, Carte_victoire[].class);
                        ArrayList<Carte> deckAL = new ArrayList<Carte>(Arrays.asList(deck));
                        Carte carteChoisi = choisirCarte(deckAL);
                        System.out.println("client : carteChoisi = " + carteChoisi.toString());
                        String json = new Gson().toJson(carteChoisi);
                        connexion.emit("choixCarte", json);
                    }
                }
            });
            connexion.on("distributionPlateau", new Emitter.Listener() { // on recoit une requete de la part du serveur
                                                                         // avec le plateau
                @Override
                public void call(Object... objects) {
                    synchronized (lock) {
                        //System.out.println("client : on a reçu une requête avec "+objects.length+" paramètre(s) ");
                        Boolean rep =false;
                        if(objects.length>0){
                            //System.out.println("client : recu Plateau = "+(String)objects[0]);
                            rep=true;
                        }
                        String json = new Gson().toJson(rep);
                        connexion.emit("distributionPlateau",json);
                    }

                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void seConnecter() {
        connexion.connect();
        /*
        synchronized (attenteDeconnexion) {
            try {
                attenteDeconnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("client : erreur dans l'attente");
            }
        } */
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8")); //réassigne la sortie sur le flux de système standard
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Client client = new Client("http://127.0.0.1:10101", "toto");
        client.seConnecter();
    }
}
package client;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;
import com.google.gson.Gson;



import commun.Identification;
import commun.Carte_victoire;
import commun.Carte;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class Client{
    private Identification moi = new Identification();
    private Socket connexion;


    public Carte choisirCarte(ArrayList<Carte> cartes) {
		Carte ret;
		for (Carte c : cartes) {
			 System.out.println(cartes.indexOf(c)+" : "+c.getNom());
		}
		int choix_bot = (int) (Math.random() * cartes.size());
		while(true) {
			try {
                System.out.println("Le Joueur "+moi.getNom()+" a choisi la carte numéro "+choix_bot+" soit "+cartes.get(choix_bot).getNom());
				ret = cartes.get(choix_bot);
				//cartes.remove(choix_bot);
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
                    String jsonstr = (String) objects[0];
                    Gson gson = new Gson();
                    Carte_victoire[] deck = gson.fromJson(jsonstr, Carte_victoire[].class);
                    ArrayList<Carte> deckAL = new ArrayList<Carte>(Arrays.asList(deck));
                    System.out.println("\nLe Joueur "+moi.getNom()+" choisi le numero de la carte qu'il va jouer : ");
                    Carte carteChoisi = choisirCarte(deckAL);
                    //System.out.println("carteChoisi = " + carteChoisi.getNom()+"\n");
                    String json = new Gson().toJson(carteChoisi);
                    System.out.println("Le Joueur "+moi.getNom()+" nous emit qu'il veut supprimer la carte "+carteChoisi.getNom());
                    connexion.emit("choixCarte", json);
                }
            });
            connexion.on("distributionPlateau", new Emitter.Listener() { // on recoit une requete de la part du serveur
                                                                         // avec le plateau
                @Override
                public void call(Object... objects) {
                    Boolean rep =false;

                    if(objects.length>0){
                        rep=true;
                    }
                    String json = new Gson().toJson(rep);
                    connexion.emit("distributionPlateau",json);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void seConnecter() {
         //   System.out.println("-------- CLIENT ------- "+this.phrase_base+" ------");
        connexion.connect();
      //  System.out.println("-------- CLIENT ------- "+this.phrase_base+" ------");
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
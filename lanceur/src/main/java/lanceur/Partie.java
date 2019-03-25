package lanceur;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import client.Client;
import serveur.Serveur;

public class Partie {
    private static int nbJoueur= Serveur.NB_JOUEURS;

    public final static void main(String [] args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8")); //réassigne la sortie sur le flux de système standard
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        Thread serveur = new Thread(new Runnable() {
            @Override
            public void run() {
                Serveur.main(null);
            }
        });

        serveur.start();
        final String adresse = "http://127.0.0.1:10101";
        Thread[] listeJoueurs=new Thread[nbJoueur];
        for(int i=0;i<nbJoueur;i++){
             String nom = ""+i;            
                   Client c = new Client(adresse, nom);
                   c.seConnecter();          
        }
    }
}
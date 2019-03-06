package lanceur;

import client.Client;
import commun.Joueur;
import serveur.Serveur;

public class Partie {
    private static int nbJoueur=1;

    public final static void main(String [] args) {

        
        Thread serveur = new Thread(new Runnable() {
            @Override
            public void run() {
                Serveur.main(null);
            }
        });

        serveur.start();

        Thread[] listeJoueurs=new Thread[nbJoueur];

        for(int i=0;i<nbJoueur;i++){
            Thread client = new Thread(new Runnable() {
                @Override
                public void run() {
                    Client.main(null);
                }
            });
            listeJoueurs[i]=client;
        }

        for(int i=0;i<nbJoueur;i++){
            listeJoueurs[i].start();
        }
    }
}
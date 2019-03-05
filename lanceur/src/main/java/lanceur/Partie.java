package lanceur;

import client.Client;
import serveur.Serveur;

public class Partie {

    public final static void main(String [] args) {

        Thread serveur = new Thread(new Runnable() {
            @Override
            public void run() {
                Serveur.main(null);
            }
        });

        Thread client1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Client.main(null);
            }
        });

        Thread client2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Client.main(null);
            }
        });

        serveur.start();
        client1.start();
        client2.start();
    }
}

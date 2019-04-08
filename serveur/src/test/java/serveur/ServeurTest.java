package serveur;

import java.util.ArrayList;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import commun.Carte;
import commun.Moteur;


public class ServeurTest {
    

    public static final void main(String[] args) {
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);
        Serveur serveur = new Serveur(config);
        Moteur moteur = new Moteur();

        ArrayList<Carte> deck;
        deck = moteur.getdeckA1();
        deck = serveur.melangerDeck(deck);


        // Faire test sur la distribution des cartes (CouperDeck) pour vérifier que tous les joueurs piochent 3 cartes, et donc ont une main différente
        
        // Faire un sénario entraînant une erreur, du genre : ......
        
    }

}
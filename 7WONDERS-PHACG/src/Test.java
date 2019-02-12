/* ! ceci n'est pas une classe de test mais seulement une classe permettant de tester rapidement ses autres classes et méthodes ! */

import java.util.ArrayList;

public class Test {

	public static void main(String[] args) {
		/*on créer les 3 merveilles du plateau*/
		Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
		Carte_victoire c1 = new Carte_victoire("victoire_3",r1,3);
		
		Ressource[] r2 = {Ressource.BOIS,Ressource.BOIS,Ressource.BOIS};
		Carte_victoire c2 = new Carte_victoire("victoire_5",r2,5);
		
		Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
		Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);
		
		/*on les ajoute a sa liste de merveilles*/
		ArrayList<Carte> me1 = new ArrayList<>();
		me1.add(c1);
		me1.add(c2);
		me1.add(c3);
		
		/*on créer le plateau*/
		Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
		
		/*on créer le joueur*/
		Joueur j1 = new Joueur(p1);

		/*on créer le moteur*/
		Moteur mo1 = new Moteur(me1);
		
		/*on mélange les cartes*/
		mo1.melangerCartes();
		
		/*on choisi une carte*/
		j1.ajouterCarte(mo1.choisirCarte());
		System.out.println(j1.toString());
		j1.ajouterCarte(mo1.choisirCarte());
		System.out.println(j1.toString());
	}

}

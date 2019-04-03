package commun;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import commun.Carte_victoire;
import commun.Ressource;
import commun.Joueur;

public class Carte_victoireTest {

	ArrayList<Carte> deck1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,deck1);
	Identification i1 = new Identification("j1");
	Joueur joueur = new Joueur(p1,i1);
	
	Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
	Carte_victoire c1 = new Carte_victoire("victoire_3",3);
	Carte_victoire c2 = new Carte_victoire("victoire_6",6);
	Carte_victoire c3 = new Carte_victoire("victoire_4",r1,4);

	Carte_ressource cr = new Carte_ressource("res 1",0,r1);
	
	@Test
    public void TestEffet() throws Exception {
		assert(joueur.AcheterCarte(c1)); //On regarde si on peut acheter la première carte
		joueur.ajouterCarte(c1); // On ajoute la première carte
		assertEquals(3, joueur.getPtsVictoire()); // On regarde si le nb de pts victoire du joueur est bien a 3

		assert(joueur.AcheterCarte(c2));
		joueur.ajouterCarte(c2);
		assertEquals(9, joueur.getPtsVictoire()); // On regarde si le nb de pts victoire du joueur est bien a 9 car il a 3 + 6

		assert(!joueur.AcheterCarte(c3)); //On peut pas jouer la carte car il a pas les ressources
		joueur.ajouterCarte(cr); // On lui donne les ressources necessaire
		assert(joueur.AcheterCarte(c3)); //C'est bon il peut maintenant 
		joueur.ajouterCarte(c3);
		assertEquals(13, joueur.getPtsVictoire()); // On regarde si le nb de pts victoire du joueur est bien a 13 car il a 3 + 6 + 4
    }

}

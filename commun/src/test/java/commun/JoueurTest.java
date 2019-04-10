package commun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;
import commun.Carte;
import commun.Carte_victoire;
import commun.Joueur;
import commun.Plateau;
import commun.Ressource;
import commun.Identification;

public class JoueurTest {
	ArrayList<Carte> deck1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,deck1);
	Ressource[] r1 = {Ressource.PIERRE};
	Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
	Ressource[] bois = {Ressource.BOIS};
	Carte_victoire c3 = new Carte_victoire("victoire_7",r3,4);

	Carte_ressource carte_testHA_failMoney = new Carte_ressource("Diamant", 5, r3);
	Carte_victoire carte_testHA_failR = new Carte_victoire("Defeat", bois, 5);
	Carte_victoire carte_testHA_succes = new Carte_victoire("Victory", r1, 5);

	Identification i1 = new Identification("j1");
	Joueur j1 = new Joueur(p1,i1);

	String str = "Joueur [id=j1, plateau=Plateau [nom=gizah_a, ressourcePrincipale=PIERRE, etape=[]], ptsVictoire=4, cartes=[Carte [nom=victoire_7]]]";
	String str2 = "Joueur [id=j1, plateau=Plateau [nom=gizah_a, ressourcePrincipale=PIERRE, etape=[]], ptsVictoire=9, cartes=[Carte [nom=victoire_7], Carte [nom=Victory]]]";
	int p = 4;
	int p2 = 9;

	@Test
	public void Test() throws Exception {
		j1.ajouterCarte(c3);
		assertEquals(p, j1.getPtsVictoire());
		assertTrue(str.equals(j1.toString()));
		if(j1.AcheterCarte(carte_testHA_failMoney)){ // échec
			j1.ajouterCarte(carte_testHA_failMoney); 
		}	
		assertTrue(str.equals(j1.toString())); // test si les données du joueurs n'ont pas bougé

		if(j1.AcheterCarte(carte_testHA_failR)){ // échec
			j1.ajouterCarte(carte_testHA_failR);
		}
		assertTrue(str.equals(j1.toString())); // test si les points de victoires n'ont pas bougé à l'ajout de la carte

		if(j1.AcheterCarte(carte_testHA_succes)){ // succès
			j1.ajouterCarte(carte_testHA_succes); 
		}
		assertEquals(p2, j1.getPtsVictoire()); // tout se met à jour correctement
		assertTrue(str2.equals(j1.toString()));
	}
}

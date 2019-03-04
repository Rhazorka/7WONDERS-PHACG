package commun;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import commun.Carte;
import commun.Carte_victoire;
import commun.Joueur;
import commun.Moteur;
import commun.Plateau;
import commun.Ressource;
import commun.Identification;

public class MoteurTest {
		
	Ressource[] r2 = {Ressource.BOIS,Ressource.BOIS,Ressource.BOIS};
	Carte_victoire c2 = new Carte_victoire("victoire_5",r2,5);
		
	/*on les ajoute a sa liste de merveilles*/
	ArrayList<Carte> me1 = new ArrayList<Carte>();

	/*on créer le plateau*/
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
	/*on créer une identification pour le besoin du test*/
	Identification i1 = new Identification("j1");
	/*on créer le joueur*/
	Joueur j1 = new Joueur(p1,i1);

	/*on créer le moteur*/
	Moteur mo1 = new Moteur(me1);

	String s = "Joueur [id=j1, plateau=Plateau [nom=gizah_a, ressourcePrincipale=PIERRE, etape=[]], ptsVictoire=5, cartes=[Carte [nom=victoire_5]]]";

	@Test
	public void test() throws Exception{

		me1.add(c2);
			
		/*on mélange les cartes*/
		mo1.melangerCartes();
		
		/*on choisi une carte*/
		j1.ajouterCarte(mo1.choisirCarte());
		assertTrue(s.equals(j1.toString()));

	}

}

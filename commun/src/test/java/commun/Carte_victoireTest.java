package commun;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import commun.Carte_victoire;
import commun.Ressource;
import commun.Joueur;

public class Carte_victoireTest {

	ArrayList<Carte> me1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
	Identification i1 = new Identification("j1");
	Joueur joueur = new Joueur(p1,i1);
	
	Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
	Carte_victoire c1 = new Carte_victoire("victoire_3",r1,3);
	int p = 3;
	
	@Test
    public void TestEffet() throws Exception {
		joueur.ajouterCarte(c1);
      	assertEquals(p, joueur.getPtsVictoire());
    }

}

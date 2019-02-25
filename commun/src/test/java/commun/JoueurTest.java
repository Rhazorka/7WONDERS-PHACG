package test;

import java.util.ArrayList;
import org.junit.Test;
import commun.Carte;
import commun.Carte_victoire;
import commun.Joueur;
import commun.Plateau;
import commun.Ressource;
import commun.Identification;

public class JoueurTest {
	ArrayList<Carte> me1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
	Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
	Identification i1 = new Identification("j1");

	@Test
	public void test() {

		Joueur j1 = new Joueur(p1,i1);
	
		Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);
		
		j1.ajouterCarte(c3);
		
		System.out.println(j1.toString());
		
	}

}

package test;

import java.util.ArrayList;
import org.junit.Test;
import sevenwonders.Carte;
import sevenwonders.Carte_victoire;
import sevenwonders.Joueur;
import sevenwonders.Plateau;
import sevenwonders.Ressource;

public class JoueurTest {
	ArrayList<Carte> me1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
	Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};

	@Test
	public void test() {

		Joueur j1 = new Joueur(p1);
	
		Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);
		
		j1.ajouterCarte(c3);
		
		System.out.println(j1.toString());
		
	}

}

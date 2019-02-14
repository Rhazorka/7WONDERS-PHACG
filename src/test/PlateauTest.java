package test;

import java.util.ArrayList;

import org.junit.Test;

import sevenwonders.Carte;
import sevenwonders.Carte_victoire;
import sevenwonders.Plateau;
import sevenwonders.Ressource;

public class PlateauTest {

	@Test
	public void test() {
		Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
		Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);
		
		ArrayList<Carte> me1 = new ArrayList<Carte>();
		me1.add(c3);
		
		Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
		
		System.out.println(p1.toString());
	}

}

package commun;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Test;

import commun.Carte;
import commun.Carte_victoire;
import commun.Plateau;
import commun.Ressource;

public class PlateauTest {
	Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
	Carte_victoire c3 = new Carte_victoire("victoire_7",r3,7);

	ArrayList<Carte> me1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,me1);
	
	String s = "Plateau [nom=gizah_a, ressourcePrincipale=PIERRE, etape=[Carte [nom=victoire_7, cout=[PIERRE, PIERRE, PIERRE, PIERRE]]]]";

	@Test
	public void test() {	
		me1.add(c3);
		assertTrue(s.equals(p1.toString()));
	}
}

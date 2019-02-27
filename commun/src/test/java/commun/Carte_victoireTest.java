package commun;

import static org.junit.Assert.*;

import org.junit.Test;

import commun.Carte_victoire;
import commun.Ressource;

public class Carte_victoireTest {

	Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
	Carte_victoire c1 = new Carte_victoire("victoire_3",r1,3);
	int p = 3;
	
	@Test
    public void TestEffet() throws Exception {
       assertEquals(p, c1.effet());
    }

}

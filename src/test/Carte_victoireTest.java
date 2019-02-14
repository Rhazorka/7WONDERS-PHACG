package test;

import static org.junit.Assert.*;

import org.junit.Test;

import sevenwonders.Carte_victoire;
import sevenwonders.Ressource;

public class Carte_victoireTest {

	Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
	Carte_victoire c1 = new Carte_victoire("victoire_3",r1,3);
	int p = 3;
	
	@Test
    public void testEffet() throws Exception {
       assertEquals(p, c1.effet());
    }

}

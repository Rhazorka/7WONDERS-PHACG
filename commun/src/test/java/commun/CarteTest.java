package commun;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import commun.Carte_victoire;
import commun.Ressource;

public class CarteTest {
	
	Ressource[] r2 = {Ressource.BOIS,Ressource.BOIS,Ressource.BOIS};
    String s = "Carte [nom=Victoire, cout=[BOIS, BOIS, BOIS]]";
    Carte_victoire c2 = new Carte_victoire("Victoire", r2, 3);  
	
	@Test
    public void Test() { 
        assertTrue(s.equals(c2.toString()));
    }

}

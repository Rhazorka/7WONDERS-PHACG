package test;

import org.junit.Test;
import sevenwonders.Carte_victoire;
import sevenwonders.Ressource;

public class CarteTest {
	
	Ressource[] r2 = {Ressource.BOIS,Ressource.BOIS,Ressource.BOIS};
	
	@Test
    public void test() {
        Carte_victoire c2 = new Carte_victoire("Victoire", r2, 5);
        
        System.out.println(c2.toString());
    }

}

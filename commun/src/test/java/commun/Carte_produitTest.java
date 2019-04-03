package commun;

import java.util.ArrayList;

import org.junit.Test;

import commun.Ressource;
import commun.Joueur;

public class Carte_produitTest {

	ArrayList<Carte> deck1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,deck1);
	Identification i1 = new Identification("j1");
	Joueur joueur = new Joueur(p1,i1);
	
	Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
	Carte_ressource c1 = new Carte_ressource("res",0,r1);
	
	@Test
    public void TestAjoutCarte() throws Exception {
		joueur.ajouterCarte(c1);
        assert(!joueur.getCartes().isEmpty());
    }

}
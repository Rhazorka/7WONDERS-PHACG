package commun;

import java.util.ArrayList;

import org.junit.Test;

import commun.Ressource;
import commun.Joueur;

public class Carte_ressourceTest {

	ArrayList<Carte> deck1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,deck1);
	Identification i1 = new Identification("j1");
	Joueur joueur = new Joueur(p1,i1);
	
	Ressource[] r1 = {Ressource.PIERRE,Ressource.PIERRE};
	Carte_ressource c1 = new Carte_ressource("res 1",0,r1);

	Ressource[] r2 = {Ressource.BOIS,Ressource.PIERRE};
	Carte_ressource c2 = new Carte_ressource("res 2",0,r2);

	Ressource[] r3 = {Ressource.BOIS};
	Carte_ressource c3 = new Carte_ressource("res 3",1,r2);
	Carte_ressource c4 = new Carte_ressource("res 4",4,r2);
	
	@Test
    public void TestAjoutCarte() throws Exception {
		assert(joueur.AcheterCarte(c1));
		joueur.ajouterCarte(c1);
		assert(!joueur.getCartes().isEmpty());
		
		assert(joueur.AcheterCarte(c2));
		joueur.ajouterCarte(c2);

		assert(joueur.AcheterCarte(c3));
		joueur.ajouterCarte(c3);
		assert(2==joueur.getPiece());

		assert(!joueur.AcheterCarte(c4));
		joueur.setPiece(4);
		assert(joueur.AcheterCarte(c4));
		joueur.ajouterCarte(c4);
		assert(0==joueur.getPiece());
    }

}
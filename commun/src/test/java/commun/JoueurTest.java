package commun;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import org.junit.Test;
import commun.Carte;
import commun.Carte_victoire;
import commun.Joueur;
import commun.Plateau;
import commun.Ressource;
import commun.Identification;

public class JoueurTest {
	ArrayList<Carte> deck1 = new ArrayList<Carte>();
	Plateau p1 = new Plateau("gizah_a",Ressource.PIERRE,deck1);
	Ressource[] r3 = {Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE,Ressource.PIERRE};
	Carte_victoire c3 = new Carte_victoire("victoire_7",r3,4);

	Identification i1 = new Identification("j1");
	Joueur j1 = new Joueur(p1,i1);

	String s = "Joueur [id=j1, plateau=Plateau [nom=gizah_a, ressourcePrincipale=PIERRE, etape=[]], ptsVictoire=4, cartes=[Carte [nom=victoire_7]]]";
	int p = 4;

	@Test
	public void Test() throws Exception {
		j1.ajouterCarte(c3);
		assertEquals(p, j1.getPtsVictoire());
		assertTrue(s.equals(j1.toString()));
	}
}

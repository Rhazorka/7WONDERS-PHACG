package commun;

//import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import commun.Carte;
import commun.Carte_victoire;
import commun.Joueur;
import commun.Moteur;
import commun.Plateau;
import commun.Ressource;
import commun.Identification;

public class MoteurTest {
	/*on crée le moteur*/
	Moteur moteur = new Moteur();
	Moteur M_temp = new Moteur();
	ArrayList<Carte> DeckAge1_temp = M_temp.getdeckA1();
	
	// Test instance des plateaux
	ArrayList<Plateau> DeckPlateau = moteur.get_deckPlateaux();
	Plateau gizahTest = DeckPlateau.get(0);
	
	Ressource[] pierre2 = {Ressource.PIERRE,Ressource.PIERRE};
	Ressource[] pierre4 = {Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE};
	Ressource[] bois3 = {Ressource.BOIS, Ressource.BOIS, Ressource.BOIS};
	Carte_victoire e1_gizah_a = new Carte_victoire("etape 1", pierre2, 3);
	Carte_victoire e2_gizah_a = new Carte_victoire("etape 2", bois3, 5);
	Carte_victoire e3_gizah_a = new Carte_victoire("etape 3", pierre4, 7);
	ArrayList<Carte> deck_gizah_a = new ArrayList<Carte>();
	Plateau gizah_a = new Plateau("gizah_a", Ressource.PIERRE,deck_gizah_a);

	// Test instance des cartes/decks par age
	ArrayList<Carte> DeckAge1 = moteur.getdeckA1();
	Carte preteur_sur_gageTest = DeckAge1.get(0);

	Carte_victoire preteur_sur_gage = new Carte_victoire("preteur_sur_gage", 3);

	/*on crée une identification pour le besoin du test*/
	Identification i1 = new Identification("j1");
	/*on crée le joueur*/
	Joueur j1 = new Joueur(gizahTest,i1);

	String Sjoueur = "Joueur [id=j1, plateau=Plateau [nom=gizah_a, ressourcePrincipale=PIERRE, etape=[Carte [nom=etape 1], Carte [nom=etape 2], Carte [nom=etape 3]]], ptsVictoire=3, cartes=[Carte [nom=preteur_sur_gage]]]";
	
	
	@Test
	public void test() throws Exception{
		
		deck_gizah_a.add(e1_gizah_a);
		deck_gizah_a.add(e2_gizah_a);
		deck_gizah_a.add(e3_gizah_a);

		assertEquals(gizah_a.toString(),gizahTest.toString()); // test si on récupère correctement le plateaux gizah_a instancié dans Moteur
		
		assertEquals(preteur_sur_gage.toString(), preteur_sur_gageTest.toString()); // test si on récupère correctement la première carte instanciée dans le deck de l Age 1

		j1.ajouterCarte(preteur_sur_gageTest);
		assertTrue(Sjoueur.equals(j1.toString())); // test si le plateau et la carte ajoutés ont bien été ajouté dans la donnée du joueur
		
		M_temp.melangerDeck_A1();
		Carte[] deckA1 = new Carte[DeckAge1.size()];
		Carte[] deckA1_temp = new Carte[DeckAge1_temp.size()];
		
		assertTrue(!Arrays.equals(DeckAge1_temp.toArray(deckA1_temp),DeckAge1.toArray(deckA1) )); //test si les cartes du deck de l'age 1 se mélangent correctement


	}

}

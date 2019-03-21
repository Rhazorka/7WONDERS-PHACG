package commun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner; 

public class Moteur {
	ArrayList<Carte> cartes = new ArrayList<Carte>();
	ArrayList<Carte> deck_age_1 = new ArrayList<Carte>();
	ArrayList<Carte> deck_age_2 = new ArrayList<Carte>();
	ArrayList<Carte> deck_age_3 = new ArrayList<Carte>();

	Plateau gizah_a;

	public Moteur(){
		// Ressources
		
		Ressource[] pierre1 = {Ressource.PIERRE};
		Ressource[] pierre2 = {Ressource.PIERRE,Ressource.PIERRE};
		Ressource[] pierre3 = {Ressource.PIERRE,Ressource.PIERRE, Ressource.PIERRE};
		Ressource[] pierre4 = {Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE};

		Ressource[] bois1 = {Ressource.BOIS};
		Ressource[] bois2 = {Ressource.BOIS, Ressource.BOIS};
		Ressource[] bois3 = {Ressource.BOIS, Ressource.BOIS, Ressource.BOIS};
		
		Ressource[] argile1 = {Ressource.ARGILE};

		Ressource[] minerai1 = {Ressource.MINERAI};

		Ressource[] papyrus2 = {Ressource.PAPYRUS, Ressource.PAPYRUS};

		// Cartes pour les plateaux (correspondantes aux étapes)
		
		Carte_victoire e1_gizah_a = new Carte_victoire("etape 1", pierre2, 3);
		Carte_victoire e2_gizah_a = new Carte_victoire("etape 2", bois3, 5);
		Carte_victoire e3_gizah_a = new Carte_victoire("etape 3", pierre4, 7);

		Carte_victoire e1_artemis_a = new Carte_victoire("etape 1", pierre2, 3);
		Carte_victoire e2_artemis_a = new Carte_victoire("etape 2", pierre3, 5);
		Carte_victoire e3_artemis_a = new Carte_victoire("etape 3", papyrus2, 7);
	

		// Deck cartes pour plateaux 

		ArrayList<Carte> deck_gizah_a = new ArrayList<Carte>();
		ArrayList<Carte> deck_artemis_a = new ArrayList<Carte>();


		// Plateaux 

		Plateau gizah_a = new Plateau("gizah_a", Ressource.PIERRE,deck_gizah_a);
		Plateau artemis_a = new Plateau("artemis_a", Ressource.PAPYRUS, deck_artemis_a);

		// Cartes Age 1 
		
		Carte_victoire preteur_sur_gage = new Carte_victoire("preteur_sur_gage", 3);
		Carte_victoire bains = new Carte_victoire("bains", pierre1, 3);
		Carte_victoire autel = new Carte_victoire("autel", 2);
		Carte_victoire theatre = new Carte_victoire("theatre", 2);

		Carte_ressource chantier = new Carte_ressource("chantier", bois1);
		Carte_ressource cavite = new Carte_ressource("cavite", pierre1);
		Carte_ressource bassin_argileux = new Carte_ressource("bassin argileux", argile1);
		Carte_ressource filon = new Carte_ressource("filon", minerai1);
		
		deck_gizah_a.add(e1_gizah_a);
		deck_gizah_a.add(e2_gizah_a);
		deck_gizah_a.add(e3_gizah_a);

		deck_artemis_a.add(e1_artemis_a);
		deck_artemis_a.add(e2_artemis_a);
		deck_artemis_a.add(e3_artemis_a);

		deck_age_1.add(preteur_sur_gage);
		deck_age_1.add(autel);
		deck_age_1.add(chantier);
		deck_age_1.add(cavite);
		deck_age_1.add(bains);
		deck_age_1.add(theatre);
		deck_age_1.add(bassin_argileux);
		deck_age_1.add(filon);	
	}

	public ArrayList<Carte> getdeckA1(){
		return deck_age_1;
	} 

	public Plateau getGizah_a(){
		return gizah_a;
	}
	public Moteur(ArrayList<Carte> cartes) {
		super();
		this.cartes = cartes;
	}	
	
	public Carte choisirCarte() {
		Carte ret;
		System.out.println("Le bot choisi le numéro de la carte qu'il va jouer : ");
		for (Carte c : cartes) {
			 System.out.println(cartes.indexOf(c)+" : "+c.toString());
		}
		//Scanner sc = new Scanner(System.in);
		
		int choix_bot = (int) (Math.random() * cartes.size());

		while(true) {
			//String str = sc.nextLine();
		
			try {
			//	int selection = Integer.parseInt(str);
			//	ret = cartes.get(selection);
			//	cartes.remove(selection);
				ret = cartes.get(choix_bot);
				cartes.remove(choix_bot);
				return ret;
			}catch(NumberFormatException e) {
				System.out.println("Il faut que la selection soit un chiffre");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Il faut que le chiffre soit compris entre 0 et "+(cartes.size()-1));
			}
		}
	}
	
	public void melangerDeck_A1() {
		Collections.shuffle(deck_age_1);
	}

	public void melangerDeck_A2() {
		Collections.shuffle(deck_age_2);
	}

	public void melangerDeck_A3() {
		Collections.shuffle(deck_age_3);
	}
}

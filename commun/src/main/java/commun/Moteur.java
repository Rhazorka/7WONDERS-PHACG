package commun;

import java.util.ArrayList;

public class Moteur {
	ArrayList<Carte> cartes = new ArrayList<Carte>();
	ArrayList<Carte> deck_age_1 = new ArrayList<Carte>();
	ArrayList<Carte> deck_age_2 = new ArrayList<Carte>();
	ArrayList<Carte> deck_age_3 = new ArrayList<Carte>();

	ArrayList<Plateau> deck_plateaux = new ArrayList<Plateau>();
	Plateau gizah_a;

	public Moteur(){
		// Ressources principales
		
		Ressource[] pierre1 = {Ressource.PIERRE};
		Ressource[] pierre2 = {Ressource.PIERRE,Ressource.PIERRE};
		Ressource[] pierre3 = {Ressource.PIERRE,Ressource.PIERRE, Ressource.PIERRE};
		Ressource[] pierre4 = {Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE};

		Ressource[] bois1 = {Ressource.BOIS};
		Ressource[] bois2 = {Ressource.BOIS, Ressource.BOIS};
		Ressource[] bois3 = {Ressource.BOIS, Ressource.BOIS, Ressource.BOIS};
		
		Ressource[] argile1 = {Ressource.ARGILE};
		Ressource[] argile2 = {Ressource.ARGILE, Ressource.ARGILE};

		Ressource[] minerai1 = {Ressource.MINERAI};
		Ressource[] minerai2 = {Ressource.MINERAI, Ressource.MINERAI};

		Ressource[] papyrus2 = {Ressource.PAPYRUS, Ressource.PAPYRUS};

		Ressource[] tissu1 = {Ressource.TISSU};

		Ressource[] verre1 = {Ressource.VERRE};

		Ressource[] papyrus1 = {Ressource.PAPYRUS};

		// Ressources complexes pour...

		//...carte ressource

		Ressource[] Rfriche = {Ressource.BOIS, Ressource.ARGILE};
		Ressource[] Rexcavation = {Ressource.PIERRE, Ressource.ARGILE};
		Ressource[] Rfosse_argileuse = {Ressource.ARGILE, Ressource.MINERAI};
		Ressource[] Rexploitation_forestiere = {Ressource.PIERRE, Ressource.BOIS};
		Ressource[] Rgisement = {Ressource.BOIS, Ressource.MINERAI};
		Ressource[] Rmine = {Ressource.MINERAI, Ressource.PIERRE};

		//...carte victoire

		Ressource[] Rtemple = {Ressource.BOIS, Ressource.ARGILE, Ressource.VERRE};
		Ressource[] Rstatue = {Ressource.MINERAI, Ressource.MINERAI, Ressource.BOIS};
		Ressource[] Rtribunal = {Ressource.ARGILE, Ressource.ARGILE, Ressource.TISSU};
		Ressource[] Rpantheon = {Ressource.ARGILE, Ressource.ARGILE, Ressource.MINERAI, Ressource.PAPYRUS, Ressource.TISSU, Ressource.VERRE};
		Ressource[] Rjardins = {Ressource.ARGILE, Ressource.ARGILE, Ressource.BOIS};
		Ressource[] Rhotel = {Ressource.PIERRE, Ressource.PIERRE, Ressource.MINERAI, Ressource.VERRE};
		Ressource[] Rpalace = {Ressource.ARGILE, Ressource.BOIS, Ressource.MINERAI, Ressource.PIERRE, Ressource.VERRE, Ressource.PAPYRUS, Ressource.TISSU};
		Ressource[] Rsenat = {Ressource.BOIS, Ressource.BOIS, Ressource.MINERAI, Ressource.PIERRE};

		//...carte scientifique

		Ressource[] Rdispensaire = {Ressource.MINERAI, Ressource.MINERAI, Ressource.VERRE};
		Ressource[] Rlaboratoire = {Ressource.ARGILE, Ressource.ARGILE, Ressource.PAPYRUS};
		Ressource[] Rbibliotheque = {Ressource.PIERRE, Ressource.PIERRE, Ressource.TISSU};
		Ressource[] Recole = {Ressource.BOIS, Ressource.PAPYRUS};
		Ressource[] Rloge = {Ressource.ARGILE, Ressource.ARGILE, Ressource.TISSU, Ressource.PAPYRUS};
		Ressource[] Robservatoire = {Ressource.MINERAI, Ressource.MINERAI, Ressource.VERRE, Ressource.TISSU};
		Ressource[] Runiversite = {Ressource.BOIS, Ressource.BOIS, Ressource.PAPYRUS, Ressource.VERRE};
		Ressource[] Racademie = {Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE, Ressource.VERRE};
		Ressource[] Retude = {Ressource.BOIS, Ressource.PAPYRUS, Ressource.TISSU};

		//...carte militaire

		Ressource[] Rplace_darmes = {Ressource.MINERAI, Ressource.MINERAI, Ressource.BOIS};
		Ressource[] Recuries = {Ressource.MINERAI, Ressource.ARGILE, Ressource.BOIS};
		Ressource[] Rchamps_de_tir = {Ressource.BOIS, Ressource.BOIS, Ressource.MINERAI};
		Ressource[] Rfortifications = {Ressource.MINERAI, Ressource.MINERAI, Ressource.MINERAI, Ressource.PIERRE};
		Ressource[] Rcirque = {Ressource.PIERRE, Ressource.PIERRE, Ressource.PIERRE, Ressource.MINERAI};
		Ressource[] Rarsenal = {Ressource.MINERAI, Ressource.BOIS, Ressource.BOIS, Ressource.TISSU};
		Ressource[] Ratelier_de_siege = {Ressource.BOIS, Ressource.ARGILE, Ressource.ARGILE, Ressource.ARGILE};

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

		gizah_a = new Plateau("gizah_a", Ressource.PIERRE,deck_gizah_a);
		Plateau artemis_a = new Plateau("artemis_a", Ressource.PAPYRUS, deck_artemis_a);

		// Deck Plateaux

		deck_plateaux.add(gizah_a);
		deck_plateaux.add(artemis_a);

		// Cartes Age 1 
		
		Carte_victoire preteur_sur_gage = new Carte_victoire("preteur_sur_gage", 3);
		Carte_victoire bains = new Carte_victoire("bains", pierre1, 3);
		Carte_victoire autel = new Carte_victoire("autel", 2);
		Carte_victoire theatre = new Carte_victoire("theatre", 2);

		Carte_ressource chantier = new Carte_ressource("chantier", bois1);
		Carte_ressource cavite = new Carte_ressource("cavite", pierre1);
		Carte_ressource bassin_argileux = new Carte_ressource("bassin argileux", argile1);
		Carte_ressource filon = new Carte_ressource("filon", minerai1);
		Carte_ressource friche = new Carte_ressource("friche", 1, Rfriche);
		Carte_ressource excavation = new Carte_ressource("excevation", 1, Rexcavation);
		Carte_ressource fosse_argileuse = new Carte_ressource("fosse argileuse", 1, Rfosse_argileuse);
		Carte_ressource exploitation_forestiere = new Carte_ressource("exploitation forestiere", 1, Rexploitation_forestiere);
		Carte_ressource gisement = new Carte_ressource("gisement", 1, Rgisement);
		Carte_ressource mine = new Carte_ressource("mine", 1, Rmine);

		Carte_produit metier_a_tisser = new Carte_produit("metier a tisser",tissu1);
		Carte_produit verrerie = new Carte_produit("verrerie",verre1);
		Carte_produit presse = new Carte_produit("presse",papyrus1);

		Carte_scientifique officine = new Carte_scientifique("officine",tissu1,Famille.COMPAS);
		Carte_scientifique atelier = new Carte_scientifique("atelier",verre1,Famille.ROUE);
		Carte_scientifique scriptorium = new Carte_scientifique("scriptorium",papyrus1,Famille.ROSETTE);

		Carte_militaire palissade = new Carte_militaire("palissade", bois1, 1);
		Carte_militaire caserne = new Carte_militaire("caserne", minerai1, 1);
		Carte_militaire tour_de_garde = new Carte_militaire("tour de garde", argile1, 1);

		// Cartes Age 2

		Carte_ressource scierie = new Carte_ressource("scierie", 1, bois2);
		Carte_ressource carriere = new Carte_ressource("carriere", 1, pierre2);
		Carte_ressource briqueterie = new Carte_ressource("briqueterie", 1, argile2);
		Carte_ressource fonderie = new Carte_ressource("fonderie", 1, minerai2);

		Carte_produit metier_a_tisser2 = new Carte_produit("metier a tisser",tissu1);
		Carte_produit verrerie2 = new Carte_produit("verrerie",verre1);
		Carte_produit presse2 = new Carte_produit("presse",papyrus1);
		
		Carte_victoire aqueduc = new Carte_victoire("aqueduc", pierre3, 5);
		Carte_victoire temple = new Carte_victoire("temple", Rtemple, 3);
		Carte_victoire statue = new Carte_victoire("statue", Rstatue, 4);
		Carte_victoire tribunal = new Carte_victoire("tribunal", Rtribunal, 4);

		Carte_scientifique dispensaire = new Carte_scientifique("dispensaire",Rdispensaire,Famille.COMPAS);
		Carte_scientifique laboratoire = new Carte_scientifique("laboratoire",Rlaboratoire,Famille.ROUE);
		Carte_scientifique bibliotheque = new Carte_scientifique("bibliotheque",Rbibliotheque,Famille.ROSETTE);
		Carte_scientifique ecole = new Carte_scientifique("ecole",Recole,Famille.ROSETTE);

		Carte_militaire muraille = new Carte_militaire("muraille", pierre3, 2);
		Carte_militaire place_darmes = new Carte_militaire("place d'armes", Rplace_darmes, 2);
		Carte_militaire ecuries = new Carte_militaire("ecuries", Recuries, 2);
		Carte_militaire champs_de_tir = new Carte_militaire("champs de tir", Rchamps_de_tir, 2);

		// Carte Age 3

		Carte_victoire pantheon = new Carte_victoire("pantheon", Rpantheon, 7);
		Carte_victoire jardins = new Carte_victoire("jardins", Rjardins, 5);
		Carte_victoire hotel = new Carte_victoire("hotel de ville", Rhotel, 6);
		Carte_victoire palace = new Carte_victoire("palace", Rpalace, 8);
		Carte_victoire senat = new Carte_victoire("senat", Rsenat, 6); 

		Carte_scientifique loge = new Carte_scientifique("loge",Rloge,Famille.COMPAS);
		Carte_scientifique observatoire = new Carte_scientifique("observatoire",Robservatoire,Famille.ROUE);
		Carte_scientifique universite = new Carte_scientifique("universite",Runiversite,Famille.ROSETTE);
		Carte_scientifique academie = new Carte_scientifique("academie",Racademie,Famille.COMPAS);
		Carte_scientifique etude = new Carte_scientifique("etude",Retude,Famille.COMPAS);

		Carte_militaire fortifications = new Carte_militaire("fortifications", Rfortifications, 3);
		Carte_militaire cirque = new Carte_militaire("cirque", Rcirque, 3);
		Carte_militaire arsenal = new Carte_militaire("arsenal", Rarsenal, 3);
		Carte_militaire atelier_de_siege = new Carte_militaire("atelier de siege", Ratelier_de_siege, 3);

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
		deck_age_1.add(metier_a_tisser);
		deck_age_1.add(verrerie);
		deck_age_1.add(presse);
		deck_age_1.add(friche);
		deck_age_1.add(excavation);
		deck_age_1.add(fosse_argileuse);
		deck_age_1.add(exploitation_forestiere);
		deck_age_1.add(gisement);
		deck_age_1.add(mine);

		deck_age_2.add(scierie);
		deck_age_2.add(carriere);
		deck_age_2.add(briqueterie);
		deck_age_2.add(fonderie);
		deck_age_2.add(metier_a_tisser2);
		deck_age_2.add(verrerie2);
		deck_age_2.add(presse2);
		deck_age_2.add(aqueduc);
		deck_age_2.add(temple);
		deck_age_2.add(statue);
		deck_age_2.add(tribunal);

		deck_age_3.add(pantheon);
		deck_age_3.add(jardins);
		deck_age_3.add(hotel);
		deck_age_3.add(palace);
		deck_age_3.add(senat);
	}

	public ArrayList<Carte> getdeckA1(){
		return deck_age_1;
	} 

	public ArrayList<Carte> getdeckA2(){
		return deck_age_2;
	} 

	public ArrayList<Carte> getdeckA3(){
		return deck_age_3;
	} 


	public Plateau getGizah_a(){
		return gizah_a;
	}

	public ArrayList<Plateau> get_deckPlateaux(){
		return deck_plateaux;
	}

	public Moteur(ArrayList<Carte> cartes) {
		super();
		this.cartes = cartes;
	}	

}

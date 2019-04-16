package commun;

public class Carte_militaire extends Carte {
    private int puissanceMilitaire;
	
	public Carte_militaire(String nom, Ressource[] cout,int puiss) {
        super(nom, cout);
        this.puissanceMilitaire = puiss;
	}

	@Override
	public void effet(Joueur j) {
        j.ajouterPuissanceMilitaire(puissanceMilitaire);
	}
}
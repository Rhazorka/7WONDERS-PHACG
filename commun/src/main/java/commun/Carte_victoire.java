package commun;

public class Carte_victoire extends Carte {
	private int pts;
	
	public Carte_victoire(String nom, int pts) {
		super(nom);
		this.pts=pts;
	}
	public Carte_victoire(String nom,int prix, int pts) {
		super(nom, prix);
		this.pts=pts;
	}
	public Carte_victoire(String nom, Ressource[] cout, int pts) {
		super(nom,cout);
		this.pts=pts;
	}

	@Override
	public void effet(Joueur j) {
		j.setPtsVictoire(pts);
	}
}

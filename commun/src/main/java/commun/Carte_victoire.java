package commun;

public class Carte_victoire extends Carte {
	private int pts;
	
	public Carte_victoire(String nom, Ressource[] cout, int pts) {
		super(nom, cout);
		this.pts=pts;
	}

	@Override
	int effet() {
		return this.pts;
	}
}

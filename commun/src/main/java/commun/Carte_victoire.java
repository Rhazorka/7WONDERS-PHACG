package commun;

public class Carte_victoire extends Carte {
	private int pts;
	private Ressource[] cout;								//array de type 'Ressource' afin de pouvoir indiquer le cout de la carte
	
	public Carte_victoire(String nom, Ressource[] cout, int pts) {
		super(nom);
		this.cout = cout;
		this.pts=pts;
	}

	@Override
	public void effet(Joueur j) {
		j.setPtsVictoire(pts);
	}
}

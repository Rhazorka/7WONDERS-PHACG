package commun;

public class Carte_ressource extends Carte {
    private Ressource[] ressources;
	private boolean Ou;

	public Carte_ressource(String nom, Ressource[] ressources) {
		super(nom);
		this.ressources=ressources;
	}
	public Carte_ressource(String nom, int prix, Ressource[] ressources) {
		super(nom,prix);
		this.ressources=ressources;
	}
	public Carte_ressource(String nom, Ressource[] cout, Ressource[] ressources) {
		super(nom, cout);
		this.ressources=ressources;
	}

	public Ressource[] getRessource()
	{
		return ressources;
	}
	public boolean getOu()
	{
		return Ou;
	}

	@Override
	public void effet(Joueur j) {
        //Pas d'effet
	}
}

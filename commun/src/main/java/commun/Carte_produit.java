package commun;

public class Carte_produit extends Carte {
    private Ressource[] ressources;
    private int cout;
	
	public Carte_produit(String nom, int cout, Ressource[] ressources) {
		super(nom);
        this.cout=cout;
        this.ressources = ressources;
	}

	@Override
	public void effet(Joueur j) {
        //Pas d'effet
	}
}
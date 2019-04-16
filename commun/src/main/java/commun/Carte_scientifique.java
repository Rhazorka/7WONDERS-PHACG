package commun;


public class Carte_scientifique extends Carte {
	private Famille famille;
	
	public Carte_scientifique(String nom, Ressource[] cout,Famille famille) {
        super(nom,cout);
        this.famille=famille;
	}

	public Famille getFamille()
	{
		return famille;
	}

	@Override
	public void effet(Joueur j) {
        //Pas d'effet
	}
}
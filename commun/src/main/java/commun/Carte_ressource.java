package commun;

public class Carte_ressource extends Carte {
    private Ressource[] ressources;
	private boolean Ou;

	public Carte_ressource(String nom, Ressource[] ressources) {
		super(nom);
		this.ressources=ressources;
		if(ressources.length==2)
		{
			if(ressources[0]!=ressources[1])
			{
				this.Ou=true;
			}
		}
	}
	public Carte_ressource(String nom, int prix, Ressource[] ressources) {
		super(nom,prix);
		this.ressources=ressources;
		if(ressources.length==2)
		{
			if(ressources[0]!=ressources[1])
			{
				this.Ou=true;
			}
		}
	}
	public Carte_ressource(String nom, Ressource[] cout, Ressource[] ressources) {
		super(nom, cout);
		this.ressources=ressources;
		if(ressources.length==2)
		{
			if(ressources[0]!=ressources[1])
			{
				this.Ou=true;
			}
		}
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

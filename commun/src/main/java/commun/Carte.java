package commun;

import java.util.Arrays;

public abstract class Carte {
	private String nom;
	private int prix;
	private Ressource[] cout; //array de type 'Ressource' afin de pouvoir indiquer le cout de la carte

	public Carte(String nom) {
		this.nom = nom;
		this.prix=0;
		this.cout=null;
	}

	public Carte(String nom, int prix) {
		this.nom = nom;
		this.prix=prix;
		this.cout=null;
	}
	

	public Carte(String nom, Ressource[] cout) {
		this.nom = nom;
		this.prix=0;
		this.cout=cout;
	}
	abstract void effet(Joueur j);							//effet de la carte (ex:donner des pts de victoires, changer les prix, ect...)
													//je pense qu'il faudrait changer le type de la méthode afin qu'elle puisse renvoyer des int quand elle attribue des pts

	public Ressource[] getCout()
	{
		return cout;
	}
	public int getPrix()
	{ 
		return prix;
	}
	@Override
	public String toString() {
		return "Carte [nom=" + nom + "]";
	}
}

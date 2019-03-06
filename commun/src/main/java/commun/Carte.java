package commun;

import java.util.Arrays;

public abstract class Carte {
	private String nom;
	private int prix;
	private Ressource[] ressources; //array de type 'Ressource' afin de pouvoir indiquer le cout de la carte

	public Carte(String nom) {
		this.nom = nom;
		this.prix=0;
		this.ressources=null;
	}

	public Carte(String nom, int prix) {
		this.nom = nom;
		this.prix=prix;
		this.ressources=null;
	}
	

	public Carte(String nom, Ressource[] ressource) {
		this.nom = nom;
		this.prix=0;
		this.ressources=ressource;
	}
	abstract void effet(Joueur j);							//effet de la carte (ex:donner des pts de victoires, changer les prix, ect...)
													//je pense qu'il faudrait changer le type de la méthode afin qu'elle puisse renvoyer des int quand elle attribue des pts

	@Override
	public String toString() {
		return "Carte [nom=" + nom + "]";
	}
}

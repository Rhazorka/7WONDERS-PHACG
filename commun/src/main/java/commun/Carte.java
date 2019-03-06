package commun;

import java.util.Arrays;

public abstract class Carte {
	private String nom;
	

	public Carte(String nom) {
		this.nom = nom;
	}
	
	abstract void effet(Joueur j);							//effet de la carte (ex:donner des pts de victoires, changer les prix, ect...)
													//je pense qu'il faudrait changer le type de la méthode afin qu'elle puisse renvoyer des int quand elle attribue des pts

	@Override
	public String toString() {
		return "Carte [nom=" + nom + "]";
	}
}

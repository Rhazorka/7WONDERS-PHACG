import java.util.Arrays;

public abstract class Carte {
	private String nom;
	private Ressource[] cout;								//array de type 'Ressource' afin de pouvoir indiquer le cout de la carte

	public Carte(String nom, Ressource[] cout) {
		this.nom = nom;
		this.cout = cout;
	}
	
	void effet(){}									//effet de la carte (ex:donner des pts de victoires, changer les prix, ect...)
													//je pense qu'il faudrait changer le type de la méthode afin qu'elle puisse renvoyer des int quand elle attribue des pts

	@Override
	public String toString() {
		return "Carte [nom=" + nom + ", cout=" + Arrays.toString(cout) + "]";
	}
}

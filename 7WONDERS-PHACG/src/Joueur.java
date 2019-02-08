
public class Joueur {
	private Plateau plateau;
	/*int pieces;
	int ptsAttaque;
	int ptsVictoire;
	Ressource ressourcesJoueur;*/

	public Joueur(Plateau plateau) {
		this.plateau = plateau;
	}

	@Override
	public String toString() {
		return "Joueur [plateau=" + plateau + "]";
	}
}

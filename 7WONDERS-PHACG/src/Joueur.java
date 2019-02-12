import java.util.ArrayList;

public class Joueur {
	private Plateau plateau;
	private int ptsVictoire;
	private ArrayList<Carte> cartes = new ArrayList<>();

	public Joueur(Plateau plateau) {
		this.plateau = plateau;
	}
	
	public void ajouterCarte(Carte carte) {
		cartes.add(carte);
		ptsVictoire+=carte.effet();
	}

	@Override
	public String toString() {
		return "Joueur [plateau=" + plateau + ", ptsVictoire=" + ptsVictoire + ", cartes=" + cartes + "]";
	}
}

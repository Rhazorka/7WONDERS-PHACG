package commun;

import java.util.ArrayList;

public class Joueur {
	private Identification id;
	private Plateau plateau;
	private int ptsVictoire;
	private ArrayList<Carte> cartes = new ArrayList<>();

	public Joueur(Plateau plateau, Identification id) {
		this.plateau = plateau;
		this.id = id;
	}
	
	public void ajouterCarte(Carte carte) {
		cartes.add(carte);
		ptsVictoire+=carte.effet();
	}
	
	public int getPtsVictoire() {
		return ptsVictoire;
	}

	@Override
	public String toString() {
		return "Joueur [id=" + id + ", plateau=" + plateau + ", ptsVictoire=" + ptsVictoire + ", cartes=" + cartes
				+ "]";
	}
}

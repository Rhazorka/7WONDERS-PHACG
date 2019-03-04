package commun;

import java.util.ArrayList;

public class Joueur {
	private Identification id;
	private Plateau plateau;
	//Points / Pieces
	private int ptsVictoire;
	private int piece;
	//Sa liste de cartes
	private ArrayList<Carte> cartes = new ArrayList<>();

	public Joueur(Plateau plateau, Identification id) {
		this.plateau = plateau;
		this.id = id;
		this.piece = 3; //Le joueur a trois pièces de base
		this.ptsVictoire = 0;
	}
	
	public void ajouterCarte(Carte carte) {
		cartes.add(carte);
		carte.effet(this);
	}

	public ArrayList<Carte> getCartes(){
		return cartes;
	}

	//Setter et getter

	public void setPtsVictoire(int nb){
		ptsVictoire = nb;
	}
	
	public int getPtsVictoire() {
		return ptsVictoire;
	}

	public void setPiece(int nb){
		piece = nb;
	}

	public int getPiece(){
		return piece;
	}



	@Override
	public String toString() {
		return "Joueur [id=" + id.getNom() + ", plateau=" + plateau + ", ptsVictoire=" + ptsVictoire + ", cartes=" + cartes
				+ "]";
	}
}

import java.util.ArrayList;

public class Plateau {
	private String nom;									
	private Ressource ressourcePrincipale;				//ressource produite par le plateau
	private ArrayList<Carte> etape = new ArrayList<>(); //liste des merveilles du plateau, les merveilles ont un fonctionnement similaire aux carte (1 cout et 1 effet) 
	
	public Plateau(String nom, Ressource ressourcePrincipale, ArrayList<Carte> etape) {
		this.nom = nom;
		this.ressourcePrincipale = ressourcePrincipale;
		this.etape = etape;
	}

	@Override
	public String toString() {
		return "Plateau [nom=" + nom + ", ressourcePrincipale=" + ressourcePrincipale + ", etape=" + etape + "]";
	}
}

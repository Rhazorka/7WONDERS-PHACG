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

	public Joueur(Identification id){
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

	public Identification getId(){
		return id;
	}

	/*public Ressource[] getLesRessDuJ() //fait et retourne une liste de toutes les ressources que le joueur a
	{
		Ressource[] RessDuJ = new Ressource[45];
		int j = 0;
		Carte_ressource CarteResTMP;
		Carte_produit CarteProdTMP;
		
		for(int i=0; i<this.cartes.size(); i++ )
		{
			
			if(cartes.get(i) instanceof Carte_ressource)
			{
				System.out.println("carte r");
				CarteResTMP = (Carte_ressource)cartes.get(i);
				for( Ressource uneRess : CarteResTMP.getRessource())
				{
					RessDuJ[j]=uneRess;
					j+=1;
				}
			}
			else if(cartes.get(i) instanceof Carte_produit)
			{
				CarteProdTMP = (Carte_produit)cartes.get(i);
				for( Ressource uneRess : CarteProdTMP.getRessource())
				{
					RessDuJ[j]=uneRess;
					j+=1;
				}
			}
		}
		RessDuJ[j]=this.plateau.getRessourcePrincipale();
		return RessDuJ;
	}*/

	public boolean AcheterCarte(Carte laCarte)
	{
		Ressource[] RessCoutTMP = laCarte.getCout();
		boolean Trouver = false;
		int CountRFind=0;
		if (laCarte.getPrix()!=0)
		{
			if(this.getPiece()>=laCarte.getPrix())
			{
				this.setPiece(this.getPiece()-laCarte.getPrix());
				return true;
			}
		}
		//cette partie va comparer les ressources produites par les joueur au ressources nécessaire
		else if (laCarte.getCout()!=null)
		{
			//boucle permettant de tester si parmi les ressource nécessaire, la ressource du plateau en produit une
			for( int i =0; i<RessCoutTMP.length;i++)
			{
				if(RessCoutTMP[i]==this.plateau.getRessourcePrincipale())
				{
					CountRFind++;
					RessCoutTMP[i]=null;
					break;
				}
			}
			//dans le cas où il y a besoin que d'une ressource et que le plateau suffit
			if(CountRFind==RessCoutTMP.length)
			{
				return true;
			}
			for(int i=0; i<this.cartes.size();i++)//parcour les cartes du joueur
			{
				if(this.cartes.get(i) instanceof Carte_ressource)//Carte ressource?
				{
					Trouver=false;
					//parcour des ressources produite par la carte ressource
					for( Ressource uneRessCarte : ((Carte_ressource)this.cartes.get(i)).getRessource()) 
					{
						//parcour des ressources nécessaire pour l'achat de la carte
						for( int j=0; j<RessCoutTMP.length;j++)
						{
							System.out.println(RessCoutTMP[j]);
							if(uneRessCarte==RessCoutTMP[j])
							{
								Trouver=true;
								RessCoutTMP[j]=null; // pour éviter de la prendre en compte deux fois 
								break;
							}
						}
						if(Trouver)
						{
							CountRFind++;
							//compteur qui, une fois avoir trouver toutes les ressources nécessaire,
							//sera égale au nombre de ressource nécessaire
							if(CountRFind==RessCoutTMP.length)
							{
								System.out.println(CountRFind);
								return true;
							}
							//se if est utile dans le cas des cartes qui produisent soit une ressource soit une autre
							//si on a trouver une ressource nécessaire et que la carte sur laquel on l'a trouvé est
							//une carte de type "ou" alors on passe à l'autre carte car pour ces cartes on ne peut choisir
							// qu'une des deux ressources qu'il peut produire
							if(((Carte_ressource)this.cartes.get(i)).getOu())
							{
								break;
							}
						}
					}
				}
				else if(this.cartes.get(i) instanceof Carte_produit)
				{
					Trouver=false;
					//parcour des ressources produites par la carte produit
					for( Ressource uneRessCarte : ((Carte_produit)this.cartes.get(i)).getRessource())
					{
						//parcour des ressources nécessaire pour l'achat de la carte
						for( int j=0; j<RessCoutTMP.length;j++)
						{
							System.out.println(RessCoutTMP[j]);
							if(uneRessCarte==RessCoutTMP[j])
							{
								Trouver=true;
								RessCoutTMP[j]=null; // pour éviter de la prendre en compte deux fois 
								break;
							}
						}
						if(Trouver)
						{
							System.out.println(CountRFind+" "+ laCarte.getCout().length);
							CountRFind++;
							//compteur qui, une fois avoir trouver toutes les ressources nécessaires,
							//sera égale au nombre de ressource nécessaires
							if(CountRFind==RessCoutTMP.length)
							{
								return true;
							}
							//se if est utile dans le cas des cartes qui produisent soit une ressource soit une autre
							//si on a trouver une ressource nécessaire et que la carte sur laquel on l'a trouvé est
							//une carte de type "ou" alors on passe à l'autre carte car pour ces cartes on ne peut choisir
							// qu'une des deux ressources qu'ele peut produire
							if(((Carte_produit)this.cartes.get(i)).getOu())
							{
								break;
							}
						}
					}
				}
			}
			if(CountRFind<RessCoutTMP.length)
			{
				return false;
			}
		}
		else
		{
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Joueur [id=" + id.getNom() + ", plateau=" + plateau + ", ptsVictoire=" + ptsVictoire + ", cartes=" + cartes
				+ "]";
	}
}

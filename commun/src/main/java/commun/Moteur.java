package commun;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner; 

public class Moteur {
	private ArrayList<Carte> cartes = new ArrayList<>();

	public Moteur(ArrayList<Carte> cartes) {
		super();
		this.cartes = cartes;
	}	public Carte choisirCarte() {
		Carte ret;
		System.out.println("Bonjour, choissisez une carte en appuyant sur le chiffre correspondant : ");
		for (Carte c : cartes) {
			 System.out.println(cartes.indexOf(c)+" : "+c.toString());
		}
		//Scanner sc = new Scanner(System.in);
		
		int choix_bot = (int) (Math.random() * cartes.size());

		while(true) {
			//String str = sc.nextLine();
		
			try {
			//	int selection = Integer.parseInt(str);
			//	ret = cartes.get(selection);
			//	cartes.remove(selection);
				ret = cartes.get(choix_bot);
				cartes.remove(choix_bot);
				return ret;
			}catch(NumberFormatException e) {
				System.out.println("Il faut que la selection soit un chiffre");
			}catch(IndexOutOfBoundsException e) {
				System.out.println("Il faut que le chiffre soit compris entre 0 et "+(cartes.size()-1));
			}
		}
	}
	
	public void melangerCartes() {
		Collections.shuffle(cartes);
	}
}

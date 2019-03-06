package actions;

import java.util.Random;
/**
 * Cette classe va permettre de generer une action aleatoire pour les voitures. Les actions possibles sont les suivantes : 
 * aller tout droit, tourner a gauche et tourner a droite.
 * @author Barri Mamadou
 *
 */
public class Action {
	//variables
	private String descriptionAction;
	private int numAction;
	public Action() {
		genererActionVoiture();
		switch (numAction)
		{
			case -1:
				descriptionAction = "tourner gauche";
				break;
			case 0:
				descriptionAction = "tout droit";
				break;
			case 1:
				descriptionAction = "tourner droite";
				break;
		}
		
	}

	/**
	 * toString de l'action qui retourne la chaine descriptive
	 * return descriptionAction une chaine decrivant l'action de la voiture
	 */
	public String toString() {
		return(descriptionAction);
	}
	/**
	 * Methode qui genere une direction aleatoire pour la voitre, champ: -1 a 1
	 */
	public void genererActionVoiture() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(3) - 1;
		numAction = aleatoire;
	}
}

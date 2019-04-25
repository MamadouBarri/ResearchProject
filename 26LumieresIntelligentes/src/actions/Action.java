package actions;

import java.util.Random;
/**
 * Cette classe va permettre de generer une action aleatoire pour les voitures. Les actions possibles sont les suivantes : 
 * aller tout droit, tourner a gauche et tourner a droite.
 * @author Barri Mamadou et Reiner Gayta
 *
 */
public class Action {
	//variables
	private String descriptionAction;
	private int numAction;
	//Mamadou
	/**
	 * Constructeur de l'action aléatoire d'une voiture
	 */
	public Action() {
		genererActionVoiture();
		switch (numAction)
		{
			case 2:
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
	//Mamadou
	/**
	 * toString de l'action qui retourne la chaine descriptive
	 * return descriptionAction une chaine decrivant l'action de la voiture
	 */
	public String toString() {
		return(descriptionAction);
	}
	//Mamadou
	/**
	 * Methode qui genere une direction aleatoire pour la voitre, champ: 0 a 2
	 */
	public void genererActionVoiture() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(3);
		numAction = aleatoire;
	}
	//Reiner
	/**Méthode qui retourne un int représentant l'action de la voiture
	 * @return the numAction int représentant l'action de la voiture
	 */
	public int getNumAction() {
		return numAction;
	}
}

package actions;

import java.util.Random;

public class Action {

	//variables
	private String descriptionAction;
	private int numAction;
	public Action() {
		genererActionVoiture();
		switch (numAction)
		{
			case -1:
				//se deplace vers le nord
				descriptionAction = "tourner gauche";
				break;
			case 0:
				//se deplace vers l'ouest
				descriptionAction = "tout droit";
				break;
			case 1:
				//se deplace vers le sud
				descriptionAction = "tourner droite";
				break;
		}
		
	}
	/**
	 * toString de l'action, on retourne la description
	 */
	public String toString() {
		return(descriptionAction);
	}
	/**
	 * Generer une direction aleatoire pour la voitre, champ: -1 a 1
	 */
	//Mamadou
	public void genererActionVoiture() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(3) - 1;
		numAction = aleatoire;
	}
}

package geometrie;

import java.util.Random;

public class Direction extends Ligne {
	
	//varbiales
	private String descriptionDirection="";
	private int numDirection;
	
	public Direction() {
		genererDirectionVoiture();
		switch (numDirection)
		{
			case 1:
				//se deplace vers le nord
				descriptionDirection = "nord";
				break;
			case 2:
				//se deplace vers l'ouest
				descriptionDirection = "ouest";
				break;
			case 3:
				//se deplace vers le sud
				descriptionDirection = "sud";
				break;
			case 4:
				//se deplace vers l'est
				descriptionDirection = "est";
				break;
		}
	}
	public String toString() {
		return(descriptionDirection);
	}
	public void setDirection(int numDirection) {
		this.numDirection = numDirection;
		
	}
	public int getNumDirection() {
		return numDirection;
	}
	/**
	 * Generer un cote aleatoire pour la voitre, champ: 1 a 4
	 */
	//Mamadou
	public void genererDirectionVoiture( ) {
		Random rn = new Random();
		int aleatoire = rn.nextInt(4) + 1;
		numDirection = aleatoire;
	}
}

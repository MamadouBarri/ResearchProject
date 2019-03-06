package geometrie;

import java.util.Random;
/**
 * Cette classe va gerer la generation du parametre Direction de chaque voiture avec des probabilites selon le traffic mis en place
 * @author Mamadou Barri et Reiner
 */
public class Direction extends Ligne {
	//varbiales
	private String descriptionDirection="";
	private int numDirection;
	private int probVoie1 = 25;
	private int probVoie2 = 25;
	private int probVoie3 = 25;
	private int probVoie4 = 25;
	private int probabiliteTotale=100;
	private int nbVoiesNormales=4;
	//Mamadou
	/**
	 * Constructeur de la direction, qui genere une direction aleatoire et associe une description en chaine
	 */
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
	//Mamadou
	/**
	 * Methode qui retourne la chaine descriptif de la direction
	 * @return la direction en chaine
	 */
	public String toString() {
		return(descriptionDirection);
	}
	//Mamadou
	/**
	 * Methode qui permet de specifier la direction d'un objet direction
	 * @param numDirection le numero de la direction
	 */
	public void setDirection(int numDirection) {
		this.numDirection = numDirection;

	}
	//Mamadou
	/**
	 * Methode qui permet de get la direction de l'objet
	 * @return le numero de la direction
	 */
	public int getNumDirection() {
		return numDirection;
	}
	//Mamadou
	/**
	 * Generer un cote aleatoire pour la voitre, champ: 1 a 4
	 */
	public void genererDirectionVoiture( ) {
		Random rn = new Random();
		//génère un nombre entre 1 et la probabilité totale
		int aleatoire = rn.nextInt(probabiliteTotale) + 1;
		//détermine la direction de la voiture selon le chiffre généré
		if(aleatoire<=probVoie1) {
			numDirection = 1;
		} else {
			if(aleatoire<=probVoie1+probVoie2) {
				numDirection = 2;
			} else {
				if(aleatoire<=probVoie1+probVoie2+probVoie3) {
					numDirection = 3;
				} else {
					numDirection = 4;
				}
			}
		}
	}
	//Reiner
	/**
	 * Double la chance qu'une voiture ait la direction choisie
	 * @param numDeVoie Voie qu'on désire doubler la probabilité qu'une voiture apparait dessus
	 */
	public void setAnormal(int numDeVoie) {
		if(nbVoiesNormales>0) {
			switch(numDeVoie) {
			case 1:
				probVoie1 = probVoie1*2;
				break;
			case 2:
				probVoie2 = probVoie2*2;
				break;
			case 3:
				probVoie3 = probVoie3*2;
				break;
			case 4:
				probVoie4 = probVoie4*2;
				break;
			}
			//ajoute à la probabilité totale pour compenser l'augmentation des chances de la voie choisie 
			probabiliteTotale = probabiliteTotale+25;
			//assure qu'on ne peut pas avoir plus que 4 voies anormales
			nbVoiesNormales--;
		}
	}
	//Reiner
	/**
	 * Normalize la chance qu'une voiture ait une certaine direction choisie
	 * @param numDeVoie Voie qu'on désire normaliser la probabilité qu'une voiture apparait dessus
	 */
	public void setNormal(int numDeVoie) {
		if(nbVoiesNormales<=4) {
			switch(numDeVoie) {
			case 1:
				probVoie1 = probVoie1/2;
				break;
			case 2:
				probVoie2 = probVoie2/2;
				break;
			case 3:
				probVoie3 = probVoie3/2;
				break;
			case 4:
				probVoie4 = probVoie4/2;
				break;
			}
			//Soustrait de la probabilité totale pour compenser la diminution des chances de la voie choisie 
			probabiliteTotale = probabiliteTotale-25;
			//assure qu'on ne peut pas avoir plus que 4 voies normales
			nbVoiesNormales++;
		}
	}
	//Reiner
	public void setToutNormal() {
		probVoie1 = 25;
		probVoie2 = 25;
		probVoie3 = 25;
		probVoie4 = 25;
		nbVoiesNormales = 4;
	}
	public int getProbVoie1() {
		return probVoie1;
	}
	public int getProbVoie2() {
		return probVoie2;
	}
	public int getProbVoie3() {
		return probVoie3;
	}
	public int getProbVoie4() {
		return probVoie4;
	}
}

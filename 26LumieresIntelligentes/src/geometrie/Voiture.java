package geometrie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;

import interfaces.Dessinable;

public class Voiture implements Dessinable, Runnable{

	//constantes des positions
	private int coteVoiture;
	private int[] lignes;
	private int trafficAnormale;
	private double ampleurTrafficAnormale;
	/**
	 * Pour la direction -1 represente gauche, 0 tout droit et 1 a droite
	 */
	private int direction;
	private 
	
	Voiture() {
		genererLigneVoiture();
	}
	
	@Override
	public void run() {
		
	}

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Generer un cote aleatoire pour la voitre, champ: 1 a 4
	 */
	//Mamadou
	public void genererLigneVoiture( ) {
		Random rn = new Random();
		int aleatoire = rn.nextInt(4) + 1;
		coteVoiture = aleatoire;
	}
	/**
	 * Generer une direction aleatoire pour la voitre, champ: -1 a 1
	 */
	//Mamadou
	public void genererDirectionVoitre() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(3) -1;
		direction = aleatoire;
	}

}

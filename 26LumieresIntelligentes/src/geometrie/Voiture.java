package geometrie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import interfaces.Dessinable;

public class Voiture implements Dessinable, Runnable{

	
	//image des voitures
	private Image imgVoiture = null;
	//constantes des positions
	private int coteVoiture;
	private int[] lignes;
	private int numImage;
	private int trafficAnormale;
	private double ampleurTrafficAnormale;
	private int direction;
	private int xVoiture;
	//variables 
	private boolean premiereFois = true;
	/**
	 * Pour la direction -1 represente gauche, 0 tout droit et 1 a droite
	 */
	public Voiture() {
		genererLigneVoiture();
		genererDirectionVoitre();
		genererImageVoitre();
		URL fichVoiture = getClass().getClassLoader().getResource(numImage +".jpg");
		
		if (fichVoiture == null) {
			System.out.println("Incapable de lire un fichier d'image");
			return;
		}
		try {
			imgVoiture = ImageIO.read(fichVoiture);
		} 
		catch (IOException e) {
			System.out.println("Erreur de lecture d'images");
		}
	}
	
	@Override
	public void run() {
		
	}

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		//Dessiner la voiture
		//Si premiere fois 
		if (premiereFois) {
			xVoiture = 120;
			premiereFois = false;
		}
		//Dessiner la voiture
		g2d.drawImage(imgVoiture, (int)xVoiture, 60, 20, 20, null);
		
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
		int aleatoire = rn.nextInt(3) - 1;
		direction = aleatoire;
	}
	/**
	 * Gener une image qui va representer la voiture
	 */
	//Mamadou
	public void genererImageVoitre() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(10)+ 1;
		numImage = aleatoire;
	}
}

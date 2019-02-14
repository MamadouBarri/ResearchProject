package geometrie;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.naming.directory.ModificationItem;

import actions.Action;
import interfaces.Dessinable;
/**
 * Cette classe génère l'image, la direction et l'action d'une voiture de façon aléatoire.
 * C'est également un objet dessinable et on le dessine en fonction de sa direction et de l'image.
 * @author Mamadou Barri
 *
 */
public class Voiture implements Dessinable, Runnable {
	//Image de la voiture
	private Image imgVoiture = null;
	private int numImage = 1;

	//Variables
	
	//Trafic anormal
	private int[] trafficAnormale;
	
	//Positions de la voiture
	private int xVoiture;
	private int yVoiture = 120 + 10;
	
	//Les booleans
	private boolean premiereFois = true;
	private boolean enCoursDAnimation = false;
	private boolean voitureActive = true;

	//Objets de la voiture
	Direction direction;
	Action action;
	
	//Description de la voiture
	String descriptionDirection = "";
	
	/**
	 * Constructeur de la voiture qui génère: image, direction et action
	 */
	public Voiture() {
		//Generer l'image aleatoire de la voiture
		genererImageVoitre();
		URL fichVoiture = getClass().getClassLoader().getResource(numImage +".jpg");

		if (fichVoiture == null) {
			affichageAvecTemps("**ERREUR** : Incapable de lire un fichier d'image");
			return;
		}
		try {
			imgVoiture = ImageIO.read(fichVoiture);
		} 
		catch (IOException e) {
			affichageAvecTemps("Erreur de lecture d'images");
		}
		//Afficher les informations sur la voiture generee
		
		//Traduire la direction
		//Objet direction aleatoire
		direction = new Direction();
		descriptionDirection = direction.toString();
		//Traduire l'action
		String descriptionAction = "";
		action = new Action();
		descriptionAction = action.toString();
		affichageAvecTemps("Voiture générée. INFOS: #image : " + numImage + " | direction :  " + direction.toString() +  " | action :  " + action.toString());
		//
		//Ou mettre la voiture?
	}
	public Voiture(int[] trafficAnormale) {
		this.trafficAnormale = trafficAnormale;
		//Generer l'image aleatoire de la voiture
		genererImageVoitre();
		URL fichVoiture = getClass().getClassLoader().getResource(numImage +".jpg");

		if (fichVoiture == null) {
			affichageAvecTemps("**ERREUR** : Incapable de lire un fichier d'image");
			return;
		}
		try {
			imgVoiture = ImageIO.read(fichVoiture);
		} 
		catch (IOException e) {
			affichageAvecTemps("Erreur de lecture d'images");
		}
		//Afficher les informations sur la voiture generee
		
		//Traduire la direction
		//Objet direction aleatoire
		direction = new Direction();
		for(int i=0;i<trafficAnormale.length;i++) {
			direction.setAnormal(trafficAnormale[i]);
		}
		descriptionDirection = direction.toString();
		//Traduire l'action
		String descriptionAction = "";
		action = new Action();
		descriptionAction = action.toString();
		affichageAvecTemps("Voiture générée. INFOS: #image : " + numImage + " | direction :  " + direction.toString() +  " | action :  " + action.toString());
		//
		//Ou mettre la voiture?
	}

	@Override
	public void run() {
		//calculerUneIterationPhysique();
		while (enCoursDAnimation) {
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	public void demarrer() {
		if (!enCoursDAnimation ) { 
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}//fin methode

	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
	}//fin methode

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		//Dessiner la voiture
		//Si premiere fois 
		if (premiereFois) {
			switch (this.direction.getNumDirection())
			{
				case 1:
					//se deplace vers le nord
					xVoiture = 0;yVoiture=130;
					break;
				case 2:
					//se deplace vers l'ouest
					xVoiture=110;yVoiture=0;
					break;
				case 3:
					//se deplace vers le sud
					xVoiture=240;yVoiture = 120;
					break;
				case 4:
					//se deplace vers l'est
					xVoiture=130; yVoiture= 240;
					break;
			}
			premiereFois = false;
		}
		//Dessiner la voiture selon la direction
		g2d.drawImage(imgVoiture, xVoiture, yVoiture , 20, 10, null);

	}
	/**
	 * Gener une image qui va representer la voiture
	 */
	//Mamadou
	public void genererImageVoitre() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(9)+ 1;
		numImage = aleatoire;
	}
	public boolean getVoitureActive() {
		return(voitureActive);
	}
	public void setVoitureActive(boolean voitureActive) {
		this.voitureActive = voitureActive;
	}
	/**
	 * Affichage a la console avec le temps precis
	 * @param affichage Ce qu'on veut afficher a la console
	 */
	public void affichageAvecTemps(String affichage){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date maintenant = new Date();
		String strDate = sdfDate.format(maintenant);
		System.out.println("[" + strDate + "] " + affichage);
	}
	
	/**
	 * getter de la position de la voiture
	 */
	public int getXVoiture() {
		return(xVoiture);
	}
	/**
	 * setter de la position X de la voiture
	 */
	public  void setXVoiture(int xVoiture) {
		this.xVoiture = xVoiture;
	}
	
	/**
	 * getter de la position de la voiture
	 */
	public int getYVoiture() {
		return(yVoiture);
	}
	/**
	 * setter de la position X de la voiture
	 */
	public  void setYVoiture(int xVoiture) {
		this.yVoiture = xVoiture;
	}
	
	public Direction getDirection() {
		return(this.direction);
	}
}

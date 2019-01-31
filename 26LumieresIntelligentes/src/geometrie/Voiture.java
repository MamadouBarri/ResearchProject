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

public class Voiture implements Dessinable, Runnable {


	//image des voitures
	private Image imgVoiture = null;
	//constantes des positions
	private int coteVoiture;
	private int[] lignes;
	private int numImage = 1;
	private int trafficAnormale;
	private double ampleurTrafficAnormale;
	private int xVoiture;
	private int yVoiture = 120 + 10;
	//variables 
	private boolean premiereFois = true;
	private boolean enCoursDAnimation = false;
	private boolean voitureActive = true;
	//compteurs
	
	//Objets
	Direction direction;
	Action action;
	/**
	 * Pour la direction -1 represente gauche, 0 tout droit et 1 a droite
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
		String descriptionDirection ="";
		//Objet direction aleatoire
		direction = new Direction();
		descriptionDirection = direction.toString();
		//Traduire l'action
		String descriptionAction = "";
		action = new Action();
		descriptionAction = action.toString();
		affichageAvecTemps("Voiture générée. INFOS: #image : " + numImage + " | direction :  " + direction.toString() +  " | action :  " + action.toString());
		
		//Ou mettre la voiture?
	}

	@Override
	public void run() {
		//calculerUneIterationPhysique();
		while (enCoursDAnimation) {
			int directionVoiture = direction.getNumDirection();
			xVoiture++;
			//Lorsque voiture va vers l'ouest ->
			if(xVoiture>240 && voitureActive) {
				arreter();
				//Ajouter au nombre de voitures passees
				//Lorsque la voiture a depasse l'intersection on la rend inactive et on l'enleve de la liste dans scene
				voitureActive = false;
				affichageAvecTemps("Thread d'une voiture: mort");
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
		g2d.drawImage(imgVoiture, (int)xVoiture, (int)yVoiture , 20, 10, null);

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

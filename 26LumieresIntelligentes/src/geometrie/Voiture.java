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

import interfaces.Dessinable;

public class Voiture implements Dessinable, Runnable{


	//image des voitures
	private Image imgVoiture = null;
	//constantes des positions
	private int coteVoiture;
	private int[] lignes;
	private int numImage = 1;
	private int trafficAnormale;
	private double ampleurTrafficAnormale;
	private int direction;
	private int action;
	private int xVoiture;
	//variables 
	private boolean premiereFois = true;
	private boolean enCoursDAnimation = false;
	private boolean voitureActive = true;
	//compteurs
	/**
	 * Pour la direction -1 represente gauche, 0 tout droit et 1 a droite
	 */
	public Voiture() {
		genererDirectionVoiture();
		genererActionVoitre();
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
		switch (direction)
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
			default:
				affichageAvecTemps("**ERREUR** : Generation du cote de la voiture");
		}
		String descriptionAction = "";
		switch (action)
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
			default:
				affichageAvecTemps("**ERREUR** : Generation de l'action de la voiture");
		}
		affichageAvecTemps("Voiture générée. INFOS: #image : " + numImage + " | direction :  " + descriptionDirection  +  " | action :  " + descriptionAction);
	}

	@Override
	public void run() {
		//calculerUneIterationPhysique();
		while (enCoursDAnimation) {
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
			xVoiture = 0;
			premiereFois = false;
		}
		//Dessiner la voiture
		g2d.drawImage(imgVoiture, (int)xVoiture, 120 + 10 , 20, 10, null);

	}
	/**
	 * Generer un cote aleatoire pour la voitre, champ: 1 a 4
	 */
	//Mamadou
	public void genererDirectionVoiture( ) {
		Random rn = new Random();
		int aleatoire = rn.nextInt(4) + 1;
		direction = aleatoire;
	}
	/**
	 * Generer une direction aleatoire pour la voitre, champ: -1 a 1
	 */
	//Mamadou
	public void genererActionVoitre() {
		Random rn = new Random();
		int aleatoire = rn.nextInt(3) - 1;
		action = aleatoire;
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
}

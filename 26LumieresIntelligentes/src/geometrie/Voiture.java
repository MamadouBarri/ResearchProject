package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.naming.directory.ModificationItem;

import actions.Action;
import interfaces.Dessinable;
import modele.ModeleAffichage;
/**
 * Cette classe génère l'image, la direction et l'action d'une voiture de façon aléatoire.
 * C'est également un objet dessinable et on le dessine en fonction de sa direction et de l'image.
 * @author Mamadou Barri
 */
public class Voiture implements Dessinable {
	//Image de la voiture
	private Image imgVoiture = null;
	private int numImage = 1;

	//Variables
	
	
	//
	double scaleX;
	double scaleY;
	//Trafic anormal
	private int[] trafficAnormal;

	//Positions de la voiture
	private double xVoiture;
	private double yVoiture;

	//Dimensions de la voiture

	private double longueurVoiturePixels;
	private double largeurVoiturePixels;

	//Les booleans
	private boolean premiereFois = true;
	private boolean enCoursDAnimation = false;
	private boolean voitureActive = true;

	//Objets de la voiture
	Direction direction;
	Action action;
	//Distances relatives des voitures
	private boolean voitureProche=false;
	private boolean voitureArretee = false;
	
	
	//Description de la voiture
	String descriptionDirection = "";
	private double dimensionRoutePixels;
	private double largeurVoie;
	private double xTest;
	//Tester le bug
	public double getxTest() {
		return xTest;
	}

	public void setxTest(double xTest) {
		this.xTest = xTest;
	}

	/**
	 * Constructeur de la voiture qui génère: image, direction et action
	 * @param longueurVoiturePixels longueur d'une voiture en pixels
	 * @param largeurVoiturePixels largeur d'une voiture en pixels
	 * @param dimensionRoutePixels dimension d'une route en pixels
	 * @param largeurVoie la largeur d'une voie en pixels
	 */
	public Voiture(double longueurVoiturePixels, double largeurVoiturePixels, double dimensionRoutePixels, double largeurVoie) {
		//Initialisation des parametres de la voiture
		this.largeurVoie = largeurVoie;
		this.dimensionRoutePixels = dimensionRoutePixels;
		this.largeurVoiturePixels = largeurVoiturePixels;
		this.longueurVoiturePixels = longueurVoiturePixels;
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

	/**
	 * Constructeur d'une voiture 
	 * @param trafficAnormal tableau du traffic anormal
	 */
	public Voiture(double longueurVoiturePixels, double largeurVoiturePixels, double dimensionRoutePixels, double largeurVoie, int[] trafficAnormal) {
		//Initialisation des parametres de la voiture
		this.largeurVoie = largeurVoie;
		this.dimensionRoutePixels = dimensionRoutePixels;
		this.largeurVoiturePixels = largeurVoiturePixels;
		this.longueurVoiturePixels = longueurVoiturePixels;
		this.trafficAnormal = trafficAnormal;
		//Generer l'image aleatoire de la voiture
		genererImageVoitre();
		URL fichVoiture = getClass().getClassLoader().getResource(numImage +".jpg");

		if (fichVoiture == null) {
			affichageAvecTemps("**ERREUR** : Incapable de lire un fichier d'image");
			return;
		}
		try {
			imgVoiture = ImageIO.read(fichVoiture);
			scaleX = (double)this.longueurVoiturePixels / imgVoiture.getWidth(null);
	        scaleY = (double)this.largeurVoiturePixels/imgVoiture.getHeight(null);
		} 
		catch (IOException e) {
			affichageAvecTemps("Erreur de lecture d'images");
		}
		//Afficher les informations sur la voiture generee

		//Traduire la direction
		//Objet direction aleatoire
		direction = new Direction();
		for(int i=0;i<trafficAnormal.length;i++) {
			direction.setAnormal(trafficAnormal[i]);
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

	//	/**
	//	 * 
	//	 */
	//	@Override
	//	public void run() {
	//		//calculerUneIterationPhysique();
	//		while (enCoursDAnimation) {
	//			}
	//			try {
	//				Thread.sleep(10);
	//			} catch (InterruptedException e) {
	//				e.printStackTrace();
	//			}
	//		}
	//	/**
	//	 * Demarre le thread s'il n'est pas deja demarre
	//	 */
	//	public void demarrer() {
	//		if (!enCoursDAnimation ) { 
	//			Thread proc = new Thread(this);
	//			proc.start();
	//			enCoursDAnimation = true;
	//		}
	//	}//fin methode
	//
	//	/**
	//	 * Demande l'arret du thread (prochain tour de boucle)
	//	 */
	//	public void arreter() {
	//		enCoursDAnimation = false;
	//	}//fin methode
//
	/**
	 * 
	 */
	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		//Dessiner la voiture;
		//Si premiere fois 
		AffineTransform matLocale = new AffineTransform(mat);
		AffineTransform matInitial = g2d.getTransform();
		if (premiereFois) {
			switch (this.direction.getNumDirection())
			{
			case 1:
				//se deplace vers l'est
				xVoiture = 0;yVoiture=(int) (dimensionRoutePixels/2.0+ largeurVoie);
				break;
			case 2:
				//se deplace vers le sud
				xVoiture=(int)(dimensionRoutePixels/2.0 - largeurVoie*2.0 - largeurVoiturePixels/2.0 );yVoiture=0;
				break;
			case 3:
				//se deplace vers l'ouest
				//Rotation de l'image
				xVoiture=(int)dimensionRoutePixels;yVoiture = (int)(dimensionRoutePixels/2.0 - largeurVoie * 2 );
				break;
			case 4:
				//se deplace vers le nord
				xVoiture= (int) (dimensionRoutePixels/2.0+ largeurVoie - largeurVoiturePixels/2.0) ;yVoiture=(int)dimensionRoutePixels;
				break;
			}
			premiereFois = false;
		}
		//Rotation des images en fonction de la direction
		switch (this.direction.getNumDirection())
		{
		case 1:
			//se deplace vers l'est
			//aucune rotation d'image
			break;
		case 2:
			//se deplace vers le sud
			AffineTransform rotationMoins90 = AffineTransform.getRotateInstance(Math.PI/2.0, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
			g2d.setTransform(rotationMoins90);
			break;
		case 3:
			//se deplace vers l'ouest
			//Rotation de l'image
			AffineTransform rotation180 = AffineTransform.getRotateInstance(Math.PI, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
			g2d.setTransform(rotation180);
			break;
		case 4:
			//se deplace vers le nord
			AffineTransform rotation90 = AffineTransform.getRotateInstance(-Math.PI/2.0, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
			g2d.setTransform(rotation90);
			break;
		}

		//Dessiner la voiture selon la direction
		AffineTransform t = new AffineTransform();
        t.translate(xVoiture, yVoiture); // = double
        t.scale(scaleX, scaleY); // scale = 1 
        g2d.drawImage(imgVoiture, t, null);
        t.translate(-xVoiture, -yVoiture);
        t.scale(1/scaleX, scaleY);
		g2d.setTransform(matInitial);
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
	public double getXVoiture() {
		return(xVoiture);
	}
	/**
	 * setter de la position X de la voiture
	 * 
	 */
	public  void setXVoiture(double xVoiture) {
		this.xVoiture = xVoiture;
	}

	/**
	 * getter de la position de la voiture
	 */
	public double getYVoiture() {
		return(yVoiture);
	}
	/**
	 * setter de la position X de la voiture
	 */
	public  void setYVoiture(double d) {
		this.yVoiture = d;
	}

	/**
	 * getter de la direction
	 * @return la direction
	 */
	public Direction getDirection() {
		return(this.direction);
	}
	/**
	 * getter du tableau du traffic anormal
	 * @param trafficAnormal
	 */
	public void setAnormale(int[] trafficAnormal) {
		this.trafficAnormal = trafficAnormal;
	}
	/**
	 * Getter de la probabilite de generation pour la premiere voiture
	 * @return
	 */
	public int getProbVoie1() {
		return direction.getProbVoie1();
	}
	/**
	 * Getter du boolean qui dit si la voiture est proche de celle devant
	 * @return
	 */
	public boolean isVoitureProche() {
		return voitureProche;
	}

	/**
	 * Setter de la proximite de la voiture devant
	 * @param voitureProche
	 */
	public void setVoitureProche(boolean voitureProche) {
		this.voitureProche = voitureProche;
	}

	/**
	 * Getter qui retourne vrai si la voiuture est arretee
	 * @return
	 */
	public boolean isVoitureArretee() {
		return voitureArretee;
	}

	/**
	 * Setter de l'arret de la voiture
	 * @param voitureArretee
	 */
	public void setVoitureArretee(boolean voitureArretee) {
		this.voitureArretee = voitureArretee;
	}
}

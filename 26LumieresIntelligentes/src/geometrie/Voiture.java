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
	//Boolean indiquant si la voiture tourne
	private boolean enRotation = false;
	//Integer indiquant si la voiture tourne à gauche ou à droite (0=tout droit; 1=tourne droite; 2=tourne gauche)
	private int directionDeVirage;
	//Angle de rotation de la voiture en radians
	private double rotation;

	//valeur de deplacement temporaire pour la rotation
	private double deplacementTemp = 0;
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
		//génère un nombre entre 0 et 2 pour déterminer si la voiture tourne ou non et, si oui, dans quelle direction
		this.directionDeVirage=((int)(Math.random()*3));
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
		//génère un nombre entre 0 et 2 pour déterminer si la voiture tourne ou non et, si oui, dans quelle direction
		this.directionDeVirage=((int)(Math.random()*3));
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
			if(!enRotation) {
				//aucune rotation d'image
				rotation = 0;
			}else {
				switch(this.directionDeVirage) {
				//aucune rotation d'image, car la voiture ne tourne pas
				case 0:
					rotation = 0;
					break;
				//Voiture tourne droite, alors on augmente la valeur de rotation graduellement
				case 1:
					rotation = rotation + Math.toRadians(3);
					if(rotation>=Math.PI/2.0) {
						rotation = Math.PI/2.0;
					}
					break;
				//Voiture tourne gauche, alors on diminue la valeur de rotation graduellement
				case 2:
					rotation = rotation - Math.toRadians(1.5);
				}
				if(rotation<=-Math.PI/2.0) {
					rotation = -Math.PI/2.0;
				}
			}
			AffineTransform rotationMoins0 = AffineTransform.getRotateInstance(rotation, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
			g2d.setTransform(rotationMoins0);
			break;
		case 2:
			//se deplace vers le sud
			if(!enRotation) {
				rotation = Math.PI/2.0;
			}else {
				switch(this.directionDeVirage) {
				//aucune rotation d'image, car la voiture ne tourne pas
				case 0:
					rotation = Math.PI/2.0;
					break;
				//Voiture tourne droite, alors on augmente la valeur de rotation graduellement
				case 1:
					rotation = rotation + Math.toRadians(3);
					//lorsqu'on atteint une certain valeur de rotation, on fixe la rotation à cette valeur ce qui veut dire que la voiture a fini de tourner
					if(rotation>=Math.PI) {
						rotation = Math.PI;
					}
					break;
				//Voiture tourne gauche, alors on diminue la valeur de rotation graduellement
				case 2:
					rotation = rotation - Math.toRadians(1.5);
					//lorsqu'on atteint une certain valeur de rotation, on fixe la rotation à cette valeur ce qui veut dire que la voiture a fini de tourner
					if(rotation<=0) {
						rotation = 0;
					}
				}
			}
			AffineTransform rotationMoins90 = AffineTransform.getRotateInstance(rotation, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
			g2d.setTransform(rotationMoins90);
			break;
		case 3:
			//se deplace vers l'ouest
			if(!enRotation) {
				rotation = Math.PI;
			}else {
				switch(this.directionDeVirage) {
				//aucune rotation d'image, car la voiture ne tourne pas
				case 0:
					rotation = 0;
					break;
				//Voiture tourne droite, alors on augmente la valeur de rotation graduellement
				case 1:
					rotation = rotation + Math.toRadians(3);
					//lorsqu'on atteint une certain valeur de rotation, on fixe la rotation à cette valeur ce qui veut dire que la voiture a fini de tourner
					if(rotation>=3*Math.PI/2.0) {
						rotation = 3*Math.PI/2.0;
					}
					break;
				//Voiture tourne gauche, alors on diminue la valeur de rotation graduellement
				case 2:
					rotation = rotation - Math.toRadians(1.5);
					//lorsqu'on atteint une certain valeur de rotation, on fixe la rotation à cette valeur ce qui veut dire que la voiture a fini de tourner
				}
				if(rotation<=Math.PI/2.0) {
					rotation = Math.PI/2.0;
				}
			}
			AffineTransform rotation180 = AffineTransform.getRotateInstance(rotation, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
			g2d.setTransform(rotation180);
			break;
		case 4:
			//se deplace vers le nord
			if(!enRotation) {
				rotation = -Math.PI/2.0;
			}else {
				switch(this.directionDeVirage) {
				//aucune rotation d'image, car la voiture ne tourne pas
				case 0:
					rotation = -Math.PI/2.0;
					break;
				//Voiture tourne droite, alors on augmente la valeur de rotation graduellement
				case 1:
					rotation = rotation + Math.toRadians(4);
					//lorsqu'on atteint une certain valeur de rotation, on fixe la rotation à cette valeur ce qui veut dire que la voiture a fini de tourner
					if(rotation>=0) {
						rotation = 0;
					}
					break;
				//Voiture tourne gauche, alors on diminue la valeur de rotation graduellement
				case 2:
					rotation = rotation - Math.toRadians(2);
					//lorsqu'on atteint une certain valeur de rotation, on fixe la rotation à cette valeur ce qui veut dire que la voiture a fini de tourner
					if(rotation<=-Math.PI) {
						rotation = -Math.PI;
					}
				}
			}
			AffineTransform rotation90 = AffineTransform.getRotateInstance(rotation, xVoiture+((int)this.longueurVoiturePixels)/2.0,yVoiture+((int)this.largeurVoiturePixels)/2.0);
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
	 * @param trafficAnormal tableau contenant les direction ayant du trafic anormal
	 */
	public void setAnormale(int[] trafficAnormal) {
		this.trafficAnormal = trafficAnormal;
	}
	/**
	 * Getter de la probabilite de generation pour la premiere voiture
	 * @return direction.getProbVoie1 int representant la probabilité qu'une voiture se déplace vers le sud
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
	/**
	 * Setter pour le boolean indiquant si la voiture tourne ou non
	 * @param enRotation boolean indiquant si la voiture tourne
	 * @author Gayta
	 */
	public void setEnRotation(boolean enRotation) {
		this.enRotation = enRotation;
	}
	/**
	 * Getter qui retourne vrai si la voiture tourne
	 * @return enRotation boolean qui indique si la voiture tourne ou non
	 * @author Gayta
	 */
	public boolean getEnRotation() {
		return this.enRotation;
	}
	/**
	 * Getter qui retourne la valeur de déplacement d'une voiture
	 * @return deplacementTemp la valeur de déplacement d'une voiture
	 * @author Gayta
	 */
	public double getDeplacement() {
		return this.deplacementTemp;
	}
	/**
	 * Setter qui modifie la valeur de déplacement d'une voiture
	 * @param deplacement la valeur de déplacement d'une voiture
	 * @author Gayta
	 */
	public void setDeplacement(double deplacement) {
		this.deplacementTemp = deplacement;
	}
	/**
	 * Getter qui retourne la direction de virage d'une voiture en valeur int (0=tout droit; 1=tourne droite; 2=tourne gauche)
	 * @return directionDeVirage int représentant la direction de virage d'une voiture
	 * @author Gayta
	 */
	public int getDirectionDeVirage() {
		return this.directionDeVirage;
	}
}

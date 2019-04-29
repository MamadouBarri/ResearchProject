package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

/**
 * Cette classe est une classe dessinable et dessine une lumiere de circulation en fonction
 * de son emplacement dans une scène et de sa couleur
 * @author Reiner Gayta
 *
 */

public class Lumiere implements Dessinable{

	Rectangle2D.Double cadre;
	Ellipse2D.Double lumiereVerte;
	Ellipse2D.Double lumiereJaune;
	Ellipse2D.Double lumiereRouge;
	double x,y;
	int direction,couleur;//Code de couleur : 0=vert; 1=jaune; 2=rouge
	Color rouge, jaune, vert;
	double largeur, longueur, diametre, rayon;
	boolean isInverse, isMaitre;
	Lumiere maitre;
	/**
	 * Constructeur de la lumière
	 * @param x position de la lumière en x
	 * @param y position de la lumière en y
	 * @param largeur largeur de la lumière en pixels
	 * @param couleur int indiquant la couleur de la lumière allumée (voir code de couleur en haut)
	 * @param direction int indiquant la direction de la voie qu'elle dirige
	 */
	public Lumiere(double x, double y, double largeur, int couleur, int direction){
		this.direction = direction;
		this.couleur = couleur;
		this.largeur = largeur;
		this.longueur = this.largeur * (6.0/19.0);
		this.x = x;
		this.y = y;
		this.diametre = this.longueur * 6.0/8.0;
		this.rayon = this.diametre/2.0;
		rouge = new Color(255,0,0);
		jaune = new Color(75,75,0);
		vert = new Color(0,75,0);
	}
	/**
	 * Permet de dessiner une lumière ayant des couleurs et directions différentes selon ses paramètres
	 * @param g2d contexte graphique
	 * @param matMC matrice de transformation monde-vers-composant
	 */
	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		AffineTransform matInitial = g2d.getTransform();
		switch(this.couleur) {
		case 2:
			setCouleurRouge();
			break;
		case 1:
			setCouleurJaune();
			break;
		case 0:
			setCouleurVert();
			break;
		}

		switch (direction)
		{
		case 1:
			//Lumiere de la voie sud
			//aucune rotation d'image
			break;
		case 2:
			//Lumiere de la voie ouest
			g2d.rotate(-Math.toRadians(90), x+longueur/2.0,y+largeur/2.0);
			break;
		case 3:
			//Lumiere de la voie est
			//Rotation de l'image
			g2d.rotate(-Math.toRadians(-90), x+longueur/2.0,y+largeur/2.0);
			break;
		case 4:
			//Lumiere de la voie nord
			g2d.rotate(-Math.toRadians(180), x+longueur/2.0,y+largeur/2.0);
			break;

		}

		cadre = new Rectangle2D.Double(x, y, longueur, largeur);
		g2d.setColor(Color.black);
		g2d.fill(cadre);
		lumiereRouge = new Ellipse2D.Double(x+longueur/2.0-rayon, y+rayon/2.0, diametre, diametre);
		g2d.setColor(rouge);
		g2d.fill(lumiereRouge);
		lumiereJaune = new Ellipse2D.Double(x+longueur/2.0-rayon, y+largeur*2.0/4.0-rayon, diametre, diametre);
		g2d.setColor(jaune);
		g2d.fill(lumiereJaune);
		lumiereVerte = new Ellipse2D.Double(x+longueur/2.0-rayon, y+largeur-diametre-rayon/2, diametre, diametre);
		g2d.setColor(vert);
		g2d.fill(lumiereVerte);
		g2d.setTransform(matInitial);
	}
	//Reiner
	/**
	 * Change la couleur des cercles représentant les lumières pour donner l'impression
	 * que la lumière rouge est allumée et que les autres sont éteintes
	 */
	public void setCouleurRouge() {
		rouge = new Color(255,0,0);
		jaune = new Color(75,75,0);
		vert = new Color(0,75,0);
	}
	//Reiner
		/**
		 * Change la couleur des cercles représentant les lumières pour donner l'impression
		 * que la lumière jaune est allumée et que les autres sont éteintes
		 */
	public void setCouleurJaune() {
		rouge = new Color(75,0,0);
		jaune = new Color(255,255,0);
		vert = new Color(0,75,0);
	}
	//Reiner
		/**
		 * Change la couleur des cercles représentant les lumières pour donner l'impression
		 * que la lumière verte est allumée et que les autres sont éteintes
		 */
	public void setCouleurVert() {
		rouge = new Color(75,0,0);
		jaune = new Color(75,75,0);
		vert = new Color(0,255,0);
	}
	//Reiner
	/**
	 * Setter qui change la paramètre couleur de la lumière
	 * @param couleur int indiquant la couleur de la lumière (voir code de couleur en haut)
	 */
	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}
	//Reiner
	/**
	 * Getter qui retourne la couleur de lumière en forme de int (voir code de couleur en haut)
	 * @return coueleur int indiquant al couleur de la lumière (voir code de couleur en haut)
	 */
	public int getCouleur() {
		return this.couleur;
	}
	//Reiner
	/**
	 * Getter qui retourne la longueur de la lumière en pixels
	 * @return longueur de la lumière en pixels
	 */
	public double getLongueur() {
		return this.longueur;
	}
	//Reiner
	/**
	 * Getter qui retourne la largeur de la lumière en pixels
	 * @return largeur de la lumière en pixels
	 */
	public double getLargeur() {
		return this.largeur;
	}
	//Reiner
	/**
	 * Setter qui permet de modifier l'emplacement X de la lumière
	 * @param x position de la lumière en x
	 */
	public void setX(double x) {
		this.x = x;
	}
	//Reiner
	/**
	 * Setter qui permet de modifier l'emplacement Y de la lumière
	 * @param y position de la lumière en y
	 */
	public void setY(double y) {
		this.y = y;
	}
	//Reiner
	/**
	 * Setter qui permet de modifier l'emplacement de la lumière
	 * @param x position de la lumière en x
	 * @param y position de la lumière en y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

}

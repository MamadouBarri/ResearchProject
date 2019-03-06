package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import interfaces.Dessinable;

/**
 * Cette classe va dessiner l'intersection c'est a dire : les voies et les rues.
 * @author Mamadou Barri
 *
 */
public class Intersection implements Dessinable {
	
	//Variables a initialiser au debut
	private double moitieRouteReelle;
	
	private int nbVoiesHorizontale =1;
	//CONSTANTES
	private final double DIMENSION_DIRECTION_REELLE = 10;
	private final double DIMENSION_VOIE_REELLE = DIMENSION_DIRECTION_REELLE/2.0;
	private final double largeurReelle; //En metres
	//Geometrie
	private Line2D.Double ligne;
	//Mamadou
	/**
	 * Constructeur ou la position, la vitesse et l'acceleration  initiales sont spécifiés
	 * @param position Vecteur incluant les positions en x et y du coin superieur-gauche
	 * @param vitesse Vecteur incluant les vitesses en x et y
	 * @param accel Vecteur incluant les accelerations en x et y  
	 * @param diametre diametre (unites du monde reel)
	 */
	public Intersection(double largeurReelle) {
		this.largeurReelle = largeurReelle;
		moitieRouteReelle = largeurReelle / 2.0;
	}
	//Mamadou
	/**
	 * Permet de dessiner la balle, sur le contexte graphique passe en parametre.
	 * @param g2d contexte graphique
	 * @param matMC matrice de transformation monde-vers-composant
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		g2d.setColor(Color.white);
		//Matrice de transformation
		AffineTransform matLocale = new AffineTransform();
		matLocale = new AffineTransform(mat);
		//Dessiner toutes les lignes pour l'intersection
		ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0 , 0, moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0,moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , 0, moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0,moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(0 , moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0,moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0,moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0,largeurReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0,largeurReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0, largeurReelle,moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, largeurReelle,moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//dessiner les lignes jaunes
		g2d.setColor(Color.yellow);
		ligne =new Line2D.Double(moitieRouteReelle, 0, moitieRouteReelle, moitieRouteReelle-DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne =new Line2D.Double(moitieRouteReelle,moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle, largeurReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne= new Line2D.Double(0,moitieRouteReelle , moitieRouteReelle-DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0,moitieRouteReelle , largeurReelle, moitieRouteReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//dessiner les voies si il y a lieu
		if(nbVoiesHorizontale == 2) {
			g2d.setColor(Color.black);
			ligne = new Line2D.Double(0,moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0, moitieRouteReelle - DIMENSION_VOIE_REELLE, moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
			ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE,moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0, largeurReelle, moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
			//Deuxieme
			ligne = new Line2D.Double(0,moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0, moitieRouteReelle - DIMENSION_VOIE_REELLE, moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
			ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE,moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0, largeurReelle, moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
		}
		
	}//fin methode//

	//Mamadou
	/**
	 * Methode qui permet de creer les axes de la route
	 */
	public void creerAxes() {
		ligne =new Line2D.Double(moitieRouteReelle, 0, moitieRouteReelle, moitieRouteReelle-DIMENSION_DIRECTION_REELLE/2.0);
	}
	//Mamadou
	/**
	 * Methode qui permet de get la moitie de la largeur reelle de la route
	 * @return la moitie de la largeur reelle de la route
	 */
	public double getMoitieLargeurReelle() {
		return(moitieRouteReelle);
	}
	//Getters et setters
	//Mamadou
	/**
	 * Methode qui permet de get le nombre de voies horizontales
	 * @return nombre de voies horizontales
	 */
	public int getNbVoiesHorizontale() {
		return nbVoiesHorizontale;
	}
	//Mamadou
	/**
	 * Methode qui permet de get le nombre de voies verticales
	 * @return nombre de voies verticales
	 */
	public void setNbVoiesHorizontale(int nbVoiesHorizontale) {
		this.nbVoiesHorizontale = nbVoiesHorizontale;
	}
	
	
}

package geometrie;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import interfaces.Dessinable;
import modele.ModeleAffichage;

/**
 * Cette classe va dessiner le bloc + ressort + sol + ligne de zéro + l'affichage de l'échelle
 * @author Mamadou Barri et Caroline Houle
 *
 */
//
public class Intersection implements Dessinable {
	
	//Variables a initialiser au debut 
	
	
	//Constantes modifiables

	private double moitieRouteReelle;
	
	private int nbTraits;
	
	//Variables monde des pixels
	private double moitieRouteReellePixels;
	//
	private int nbVoiesHorizontale =1;
	
	//CONSTANTES
	private final double DIMENSION_DIRECTION_REELLE = 10;
	private final double DIMENSION_VOIE_REELLE = DIMENSION_DIRECTION_REELLE/2.0;
	private final int LARGEUR_TRAITS = 1;
		//Modele
	private final double LARGEUR_REELLE; //En metres
	//Geometrie
	private Path2D.Double axe; 
	private Line2D.Double ligne;
	/**
	 * Constructeur ou la position, la vitesse et l'acceleration  initiales sont spécifiés
	 * @param position Vecteur incluant les positions en x et y du coin superieur-gauche
	 * @param vitesse Vecteur incluant les vitesses en x et y
	 * @param accel Vecteur incluant les accelerations en x et y  
	 * @param diametre diametre (unites du monde reel)
	 */
	public Intersection(double largeurReelle) {
		this.LARGEUR_REELLE = largeurReelle;
		moitieRouteReelle = LARGEUR_REELLE / 2.0;
		nbTraits = (int)(moitieRouteReelle/2.0-DIMENSION_DIRECTION_REELLE)/LARGEUR_TRAITS;
	}
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
		ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0,LARGEUR_REELLE);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0,LARGEUR_REELLE);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0, LARGEUR_REELLE,moitieRouteReelle - DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0, LARGEUR_REELLE,moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//dessiner les lignes jaunes
		g2d.setColor(Color.yellow);
		ligne =new Line2D.Double(moitieRouteReelle, 0, moitieRouteReelle, moitieRouteReelle-DIMENSION_DIRECTION_REELLE/2.0);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne =new Line2D.Double(moitieRouteReelle,moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0 , moitieRouteReelle, LARGEUR_REELLE);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne= new Line2D.Double(0,moitieRouteReelle , moitieRouteReelle-DIMENSION_DIRECTION_REELLE/2.0, moitieRouteReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_DIRECTION_REELLE/2.0,moitieRouteReelle , LARGEUR_REELLE, moitieRouteReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//dessiner les voies si il y a lieu
		if(nbVoiesHorizontale == 2) {
			g2d.setColor(Color.black);
			ligne = new Line2D.Double(0,moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0, moitieRouteReelle - DIMENSION_VOIE_REELLE, moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
			ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE,moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0, LARGEUR_REELLE, moitieRouteReelle + DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
			//Deuxieme
			ligne = new Line2D.Double(0,moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0, moitieRouteReelle - DIMENSION_VOIE_REELLE, moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
			ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE,moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0, LARGEUR_REELLE, moitieRouteReelle - DIMENSION_VOIE_REELLE/2.0);
			g2d.draw(matLocale.createTransformedShape(ligne));
		}
		
	}//fin methode//
	
	
	public void creerAxes() {
		ligne =new Line2D.Double(moitieRouteReelle, 0, moitieRouteReelle, moitieRouteReelle-DIMENSION_DIRECTION_REELLE/2.0);
		
	}
	public double getMoitieLargeurReelle() {
		return(moitieRouteReelle);
	}
	//Getters et setters
	public int getNbVoiesHorizontale() {
		return nbVoiesHorizontale;
	}
	public void setNbVoiesHorizontale(int nbVoiesHorizontale) {
		this.nbVoiesHorizontale = nbVoiesHorizontale;
	}
	
	
}

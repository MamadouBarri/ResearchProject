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

/**
 * Cette classe va dessiner le bloc + ressort + sol + ligne de zéro + l'affichage de l'échelle
 * @author Mamadou Barri et Caroline Houle
 *
 */
//
public class Intersection implements Dessinable {
	
	//Variables a initialiser au debut 
	
	
	//Constantes modifiables
	private int largeurRoute =240;
	private double moitieRoute;
	private int nbTraits;
	
	
	//CONSTANTES
	private final double DIMENSION_CARRE_INTERSECTION = 50;
	private final int LARGEUR_TRAITS = 5;
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
	public Intersection(int longueur, int hauteur) {
		System.out.println("Longueur reele: " + longueur);
		moitieRoute = largeurRoute/2.0;
		nbTraits = (largeurRoute/2-(int)DIMENSION_CARRE_INTERSECTION)/LARGEUR_TRAITS;
	}
	/**
	 * Permet de dessiner la balle, sur le contexte graphique passe en parametre.
	 * @param g2d contexte graphique
	 * @param matMC matrice de transformation monde-vers-composant
	 */
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		g2d.setColor(Color.white);
		//Dessiner toutes les lignes pour l'intersection
		ligne = new Line2D.Double(moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0 , 0, moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0,moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		ligne = new Line2D.Double(moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0 , 0, moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0,moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		ligne = new Line2D.Double(0 , moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0, moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0,moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		ligne = new Line2D.Double(0 , moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0, moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0,moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		ligne = new Line2D.Double(moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0 , moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0, moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0,largeurRoute);
		g2d.draw(ligne);
		ligne = new Line2D.Double(moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0 , moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0, moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0,largeurRoute);
		g2d.draw(ligne);
		ligne = new Line2D.Double(moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0 , moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0, largeurRoute,moitieRoute - DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		ligne = new Line2D.Double(moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0 , moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0, largeurRoute,moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		//dessiner les lignes blanches
		g2d.setColor(Color.yellow);
		ligne =new Line2D.Double(moitieRoute, 0, moitieRoute, moitieRoute-DIMENSION_CARRE_INTERSECTION/2.0);
		g2d.draw(ligne);
		ligne =new Line2D.Double(moitieRoute,moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0 , moitieRoute, largeurRoute);
		g2d.draw(ligne);
		ligne= new Line2D.Double(0,moitieRoute , moitieRoute-DIMENSION_CARRE_INTERSECTION/2.0, moitieRoute);
		g2d.draw(ligne);
		ligne = new Line2D.Double(moitieRoute + DIMENSION_CARRE_INTERSECTION/2.0,moitieRoute , largeurRoute, moitieRoute);
		g2d.draw(ligne);
		
	}//fin methode//
	
	
	public void creerAxes() {
		ligne =new Line2D.Double(moitieRoute, 0, moitieRoute, moitieRoute-DIMENSION_CARRE_INTERSECTION/2.0);
		
	}
	public double getMoitieLargeur() {
		return(moitieRoute);
	}
	
}

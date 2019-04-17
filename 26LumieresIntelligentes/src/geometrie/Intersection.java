package geometrie;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
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
	private int nbVoiesEst;
	private int nbVoiesOuest;
	private int nbVoiesNord;
	private int nbVoiesSud;
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
	public Intersection(double largeurReelle, int nbVoiesEst, int nbVoiesOuest, int nbVoiesSud, int nbVoiesNord) {
		this.largeurReelle = largeurReelle;
		moitieRouteReelle = largeurReelle / 2.0;
		this.nbVoiesEst = nbVoiesEst;
		this.nbVoiesOuest = nbVoiesOuest;
		this.nbVoiesSud = nbVoiesSud;
		this.nbVoiesNord = nbVoiesNord;
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
		//dessiner les lignes blanches
		DessinerVoiesEst(g2d, matLocale);
		DessinerVoiesOuest(g2d, matLocale);
		DessinerVoiesSud(g2d, matLocale);
		DessinerVoiesNord(g2d, matLocale);
		//dessiner les lignes jaunes
		g2d.setColor(Color.yellow);
		//Nord
		ligne =new Line2D.Double(moitieRouteReelle, 0, moitieRouteReelle, moitieRouteReelle-DIMENSION_VOIE_REELLE*this.nbVoiesOuest);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//Sud
		ligne =new Line2D.Double(moitieRouteReelle,moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesEst, moitieRouteReelle, largeurReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//Ouest
		ligne= new Line2D.Double(0,moitieRouteReelle , moitieRouteReelle-DIMENSION_VOIE_REELLE*this.nbVoiesSud, moitieRouteReelle);
		g2d.draw(matLocale.createTransformedShape(ligne));
		//Est
		ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesNord,moitieRouteReelle , largeurReelle, moitieRouteReelle);
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
	
	
	private void DessinerVoiesEst(Graphics2D g2d, AffineTransform matLocale) {
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		Stroke normal = g2d.getStroke();
		//Voies Est gauche
		g2d.setStroke(dashed);
		for(int i = 1; i<=this.nbVoiesEst;i++) {
			if(i!=this.nbVoiesEst) {
				ligne = new Line2D.Double(0 , moitieRouteReelle + DIMENSION_VOIE_REELLE*i, moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesSud,moitieRouteReelle + DIMENSION_VOIE_REELLE*i);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(0 , moitieRouteReelle + DIMENSION_VOIE_REELLE*i, moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesSud,moitieRouteReelle + DIMENSION_VOIE_REELLE*i);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
		//Voies Est droite
		g2d.setStroke(dashed);
		for(int k = 1; k<=this.nbVoiesEst;k++) {
			if(k!=this.nbVoiesEst) {
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesNord , moitieRouteReelle + DIMENSION_VOIE_REELLE*k, largeurReelle,moitieRouteReelle + DIMENSION_VOIE_REELLE*k);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesNord , moitieRouteReelle + DIMENSION_VOIE_REELLE*k, largeurReelle,moitieRouteReelle + DIMENSION_VOIE_REELLE*k);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
	}
	private void DessinerVoiesOuest(Graphics2D g2d, AffineTransform matLocale) {
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		Stroke normal = g2d.getStroke();
		//Voies Ouest gauche
		g2d.setStroke(dashed);
		for(int i = 1; i<=this.nbVoiesOuest;i++) {
			if(i!=this.nbVoiesOuest) {
				ligne = new Line2D.Double(0 , moitieRouteReelle - DIMENSION_VOIE_REELLE*i, moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesSud,moitieRouteReelle - DIMENSION_VOIE_REELLE*i);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(0 , moitieRouteReelle - DIMENSION_VOIE_REELLE*i, moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesSud,moitieRouteReelle - DIMENSION_VOIE_REELLE*i);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
		//Voies Ouest droite
		g2d.setStroke(dashed);
		for(int k = 1; k<=this.nbVoiesOuest;k++) {
			if(k!=this.nbVoiesOuest) {
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesNord , moitieRouteReelle - DIMENSION_VOIE_REELLE*k, largeurReelle,moitieRouteReelle - DIMENSION_VOIE_REELLE*k);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesNord , moitieRouteReelle - DIMENSION_VOIE_REELLE*k, largeurReelle,moitieRouteReelle - DIMENSION_VOIE_REELLE*k);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
	}
	private void DessinerVoiesSud(Graphics2D g2d, AffineTransform matLocale) {
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		Stroke normal = g2d.getStroke();
		//Voies Sud superieur
		g2d.setStroke(dashed);
		for(int i = 1; i<=this.nbVoiesSud;i++) {
			if(i!=this.nbVoiesSud) {
				ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_VOIE_REELLE*i , 0, moitieRouteReelle - DIMENSION_VOIE_REELLE*i,moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesOuest);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_VOIE_REELLE*i , 0, moitieRouteReelle - DIMENSION_VOIE_REELLE*i,moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesOuest);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
		//Voies Sud inferieur
		g2d.setStroke(dashed);
		for(int k = 1; k<=this.nbVoiesSud;k++) {
			if(k!=this.nbVoiesSud) {
				ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_VOIE_REELLE*k , moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesEst, moitieRouteReelle - DIMENSION_VOIE_REELLE*k,largeurReelle);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(moitieRouteReelle - DIMENSION_VOIE_REELLE*k , moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesEst, moitieRouteReelle - DIMENSION_VOIE_REELLE*k,largeurReelle);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
	}
	private void DessinerVoiesNord(Graphics2D g2d, AffineTransform matLocale) {
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		Stroke normal = g2d.getStroke();
		//Voies Nord superieur
		g2d.setStroke(dashed);
		for(int i = 1; i<=this.nbVoiesNord;i++) {
			if(i!=this.nbVoiesNord) {
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*i, 0, moitieRouteReelle + DIMENSION_VOIE_REELLE*i,moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesOuest);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*i, 0, moitieRouteReelle + DIMENSION_VOIE_REELLE*i,moitieRouteReelle - DIMENSION_VOIE_REELLE*this.nbVoiesOuest);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
		//Voies Nord inferieur
		g2d.setStroke(dashed);
		for(int k = 1; k<=this.nbVoiesNord;k++) {
			if(k!=this.nbVoiesNord) {
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*k , moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesEst, moitieRouteReelle + DIMENSION_VOIE_REELLE*k,largeurReelle);
				g2d.draw(matLocale.createTransformedShape(ligne));
			} else {
				g2d.setStroke(normal);
				ligne = new Line2D.Double(moitieRouteReelle + DIMENSION_VOIE_REELLE*k , moitieRouteReelle + DIMENSION_VOIE_REELLE*this.nbVoiesEst, moitieRouteReelle + DIMENSION_VOIE_REELLE*k,largeurReelle);
				g2d.draw(matLocale.createTransformedShape(ligne));
			}
		}
	}
	


}

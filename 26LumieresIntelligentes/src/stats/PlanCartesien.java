package stats;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Collections;

import sceneAnimee.SceneAnimee;
import sceneAnimee.SceneAnimeeAvecAlgo;

import javax.swing.JPanel;

/**
 * Cette classe permet de dessiner une fonctoin sur un plan cartésien
 * @author Mamadou Barri et Caroline Houle (pour la squelette de la classe)
 *
 */
public class PlanCartesien extends JPanel {
	private static final long serialVersionUID = 1L;

	private Path2D.Double axes, ligneBriseeSansAlgo,ligneBriseeAvecAlgo;
	private double xMin, xMax;
	private double yMin, yMax;
	private double pixelsXParUnite;
	private double pixelsYParUnite;
	private ArrayList<Integer> nbVoituresEnAttenteParSecSansAlgo;
	private ArrayList<Integer> nbVoituresEnAttenteParSecAvecAlgo;
	private final int VOITURE_MAX = 10;
	private final float DISTANCE_GRADUATION = 5;
	//Avec algo
	private int yMaxAlgo;

	private int nbSegmentsPourApproximerAvecAlgo;

	private int nbSegmentsPourApproximerSansAlgo;
	
	/**
	 * Constructeur : cree le composant et fixe la couleur de fond
	 * @param arrayList les statistiques à afficher
	 */
	public PlanCartesien() {	
		setBackground(Color.white);
		xMin = 0; //Temps commence a 0
		yMin = 0;
	}//fin du constructeur
	
	/**
	 * Redefinit la methode de dessin
	 * @param g Le conexte graphique
	 */
	@Override
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;	
		//Initialisation des donne
		nbVoituresEnAttenteParSecSansAlgo = new ArrayList<Integer>(SceneAnimee.nbVoituresEnAttente); //Creer un arrayList local pour les stats
		nbVoituresEnAttenteParSecAvecAlgo = new ArrayList<Integer>(SceneAnimeeAvecAlgo.nbVoituresEnAttente);
		xMax = Math.max(nbVoituresEnAttenteParSecSansAlgo.size(), nbVoituresEnAttenteParSecAvecAlgo.size());//Le nombre de donnees
		int nbMaxEnAttenteSansAlgo = Collections.max(nbVoituresEnAttenteParSecSansAlgo);
		int nbMaxEnAttenteAvecAlgo = Collections.max(nbVoituresEnAttenteParSecAvecAlgo);
		yMax = Math.max(nbMaxEnAttenteSansAlgo, nbMaxEnAttenteAvecAlgo);
		nbSegmentsPourApproximerSansAlgo = nbVoituresEnAttenteParSecSansAlgo.size();
		nbSegmentsPourApproximerAvecAlgo = nbVoituresEnAttenteParSecAvecAlgo.size();
		pixelsXParUnite = getWidth() / (xMax-xMin);
		pixelsYParUnite = getHeight()/(yMax - yMin);
		
		AffineTransform mat = new AffineTransform();
		mat.scale(pixelsXParUnite, pixelsYParUnite);
		creerAxes();
		
		creerApproxCourbeSansAlgo();
		creerApproxCourbeAvecAlgo();
		
		creerGraduations(g2d);
		//Translation
		g2d.translate(1, getHeight()- DISTANCE_GRADUATION);
		g2d.scale(1, -1);
		//on dessine les axes
		g2d.setColor(Color.blue);
		g2d.draw(mat.createTransformedShape(axes)); 
	    
		
		//on dessine la courbe 
		g2d.setColor(Color.red);
		g2d.draw(mat.createTransformedShape(ligneBriseeSansAlgo));  
		g2d.setColor(Color.green);
		g2d.draw(mat.createTransformedShape(ligneBriseeAvecAlgo));  
	    
	}//fin paintComponent
	
	/**
	 * Méthode pour créer la grille
	 * @param g l'objet Graphics2D qui sera utilisé pour dessiner les graduations
	 */
	private void creerGraduations(Graphics2D g) {
		//variables
		float posX = 0;
		float posY = 0;
		//création des graduation en X
		int valeurX = (int)xMin;
		for(int k=0; k<=(int)xMax-(int)xMin;k++) {
			//dessine la dernière graduation en X
			g.setFont(new Font("TimesRoman",Font.PLAIN,8));
			g.drawString(valeurX+"", (float)posX, (float) (getHeight()) - DISTANCE_GRADUATION);
			valeurX++;
			posX+=pixelsXParUnite;
		}
		//création des graduations en Y
		int valeurY = (int)yMax;
		for(int i=0; i<=(int)yMax - (int)yMin;i++) {
			g.drawString(valeurY+"", DISTANCE_GRADUATION, (float)posY);
			valeurY--;
			posY+=pixelsYParUnite;
		}
		
	}

	/**
	 * Creation des axes du plan
	 * Le resustat est place dans le Path2D "axes"
	 */
	private void creerAxes() {
	    axes = new Path2D.Double ();
	    axes.moveTo( xMin, 0 );
	    axes.lineTo( xMax, 0 );
	    axes.moveTo( 0,  yMin );
	    axes.lineTo( 0,  yMax );

	}
	
	/**
	 * Creation d'une approximation de la courbe
	 * Le resultat est place dans le Path2D "ligneBrisee"
	 */
	private void creerApproxCourbeSansAlgo() {
	    double x, y;

	    ligneBriseeSansAlgo = new Path2D.Double();
	    x = xMin;  //on commence a l'extreme gauche
	    y = 0;
	  	ligneBriseeSansAlgo.moveTo( x, y );
	
	    for (int k=1; k<=nbSegmentsPourApproximerSansAlgo; k++) {
	    	
	   		x++;  //on ajoute un intervalle fixe en x, une seconde dans notre cas
	    	y = this.nbVoituresEnAttenteParSecSansAlgo.get((int) x-1); //modifier cette ligne!!!
	    	ligneBriseeSansAlgo.lineTo( x, y);
	    }//fin for
	}
	/**
	 * Creation d'une approximation de la courbe
	 * Le resultat est place dans le Path2D "ligneBrisee"
	 */
	private void creerApproxCourbeAvecAlgo() {
	    double x, y;

	    ligneBriseeAvecAlgo = new Path2D.Double();
	    x = xMin;  //on commence a l'extreme gauche
	    y = 0;
	  	ligneBriseeAvecAlgo.moveTo( x, y );
	
	    for (int k=1; k<=nbSegmentsPourApproximerAvecAlgo; k++) {
	    	
	   		x++;  //on ajoute un intervalle fixe en x, une seconde dans notre cas
	    	y = this.nbVoituresEnAttenteParSecAvecAlgo.get((int) x-1); //modifier cette ligne!!!
	    	ligneBriseeAvecAlgo.lineTo( x, y);
	    }//fin for
	}
	
}//fin classe
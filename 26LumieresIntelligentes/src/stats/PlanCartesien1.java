package stats;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.util.Collections;

import sceneAnimee.SceneAnimee;
import sceneAnimee.SceneAnimeeAvecAlgo;

import javax.swing.JPanel;


public class PlanCartesien1 extends JPanel {

	//Les lignes
	private Path2D.Double axes, ligneBriseeSansAlgo,ligneBriseeAvecAlgo;
	//Les listes
	private ArrayList<Integer> nbVoituresEnAttenteParSecSansAlgo;
	private ArrayList<Integer> nbVoituresEnAttenteParSecAvecAlgo;

	//coordonnes de l'axe des X
	private final int COORD_INITIAL_X_AXE_X = 50;
	private final int COORD_FINALE_X_AXE_X = 600;
	private final int COORD_Y_AXE_X = 600;

	//coordonnes de l'axe des Y
	private  final int COORD_INITIAL_Y_AXE_Y = 50;
	private  final int COORD_FINALE_Y_AXE_Y= 600;
	private  final int COORD_X_AXE_Y = 50;

	//Largeur des points
	private final int LARGEUR_POINT = 10;

	//Distance entre axe et numéraux
	public final int DISTANCE_AXE_ECRITURE = 20;
	private int xNbCoord;
	private int yNbCoord;
	private int largeurEntreY;
	private int largeurEntreX;

	//Pour les fleches
	public static final int SECOND_LENGHT = 5;

	//Approximation
	private int nbSegmentsPourApproximerAvecAlgo;
	private int nbSegmentsPourApproximerSansAlgo;
	/**
	 * Constructeur : cree le composant et fixe la couleur de fond
	 * @param arrayList les statistiques à afficher
	 */
	public PlanCartesien1() {
		// numerate axis
		xNbCoord = 10;
		yNbCoord = 10;
		setBackground(Color.white);
		largeurEntreX = (COORD_FINALE_X_AXE_X - COORD_INITIAL_X_AXE_X)
				/ xNbCoord;
		largeurEntreY = (COORD_FINALE_Y_AXE_Y - COORD_INITIAL_Y_AXE_Y)
				/ yNbCoord;
	}//fin du constructeur


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		//Initialisation des donne
		nbVoituresEnAttenteParSecSansAlgo = new ArrayList<Integer>(SceneAnimee.nbVoituresEnAttente); //Creer un arrayList local pour les stats
		nbVoituresEnAttenteParSecAvecAlgo = new ArrayList<Integer>(SceneAnimeeAvecAlgo.nbVoituresEnAttente);
		//Determiner le nombre de X max
		xNbCoord = Math.max(nbVoituresEnAttenteParSecSansAlgo.size(), nbVoituresEnAttenteParSecAvecAlgo.size());//Le nombre de donnees
		//Determiner le nombre de Y max
		int nbMaxEnAttenteSansAlgo = Collections.max(nbVoituresEnAttenteParSecSansAlgo);
		int nbMaxEnAttenteAvecAlgo = Collections.max(nbVoituresEnAttenteParSecAvecAlgo);
		yNbCoord= Math.max(nbMaxEnAttenteSansAlgo, nbMaxEnAttenteAvecAlgo);
		//Nombre de segements pour approximation
		nbSegmentsPourApproximerSansAlgo = nbVoituresEnAttenteParSecSansAlgo.size();
		nbSegmentsPourApproximerAvecAlgo = nbVoituresEnAttenteParSecAvecAlgo.size();

		//Améliorer la qualité des dessins
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

		// AXE DES X
		g2d.drawLine(COORD_INITIAL_X_AXE_X, COORD_Y_AXE_X,
				COORD_FINALE_X_AXE_X, COORD_Y_AXE_X);
		// AXE DES Y
		g2d.drawLine(COORD_X_AXE_Y, COORD_INITIAL_Y_AXE_Y,
				COORD_X_AXE_Y, COORD_FINALE_Y_AXE_Y);

		// draw origin Point
		g2d.fillOval(
				COORD_INITIAL_X_AXE_X - (LARGEUR_POINT / 2), 
				COORD_FINALE_Y_AXE_Y - (LARGEUR_POINT / 2),
				LARGEUR_POINT, LARGEUR_POINT);

		// draw text "X" and draw text "Y"
		g2d.drawString("X", COORD_FINALE_X_AXE_X - DISTANCE_AXE_ECRITURE / 2,
				COORD_Y_AXE_X + DISTANCE_AXE_ECRITURE);
		g2d.drawString("Y", COORD_X_AXE_Y - DISTANCE_AXE_ECRITURE,
				COORD_INITIAL_Y_AXE_Y + DISTANCE_AXE_ECRITURE / 2);
		g2d.drawString("(0, 0)", COORD_INITIAL_X_AXE_X - DISTANCE_AXE_ECRITURE,
				COORD_FINALE_Y_AXE_Y + DISTANCE_AXE_ECRITURE);

		// draw x-axis numbers
		for(int i = 1; i < xNbCoord; i++) {
			g2d.drawLine(COORD_INITIAL_X_AXE_X + (i * largeurEntreX),
					COORD_Y_AXE_X - SECOND_LENGHT,
					COORD_INITIAL_X_AXE_X + (i * largeurEntreX),
					COORD_Y_AXE_X + SECOND_LENGHT);
			g2d.drawString(Integer.toString(i), 
					COORD_INITIAL_X_AXE_X + (i * largeurEntreX) - 3,
					COORD_Y_AXE_X + DISTANCE_AXE_ECRITURE);
		}
		this.numeroterAxeX(g2d);
		this.numeroterAxeY(g2d);
		//On cree les courbes
		creerApproxCourbeSansAlgo();
		creerApproxCourbeAvecAlgo();

		//on dessine la courbe 
		g2d.setColor(Color.red);
		g2d.draw(ligneBriseeSansAlgo);  
		g2d.setColor(Color.green);
		g2d.draw(ligneBriseeAvecAlgo);  


	}

	/**
	 * Méthode pour numeroter les axes
	 * @param g l'objet Graphics2D qui sera utilisé pour dessiner les graduations
	 */
	private void numeroterAxeX(Graphics2D g) {
		// draw x-axis numbers
		for(int i = 1; i < xNbCoord; i++) {
			g.drawLine(COORD_INITIAL_X_AXE_X + (i * largeurEntreX),
					COORD_Y_AXE_X - SECOND_LENGHT,
					COORD_INITIAL_X_AXE_X + (i * largeurEntreX),
					COORD_Y_AXE_X + SECOND_LENGHT);
			g.drawString(Integer.toString(i), 
					COORD_INITIAL_X_AXE_X + (i * largeurEntreX) - 3,
					COORD_Y_AXE_X + DISTANCE_AXE_ECRITURE);
		}

	}
	/**
	 * Méthode pour numeroter les axes
	 * @param g l'objet Graphics2D qui sera utilisé pour dessiner les graduations
	 */
	private void numeroterAxeY(Graphics2D g) {
		for(int i = 1; i < yNbCoord; i++) {
			g.drawLine(COORD_X_AXE_Y - SECOND_LENGHT,
					COORD_FINALE_Y_AXE_Y - (i * largeurEntreY), 
					COORD_X_AXE_Y + SECOND_LENGHT,
					COORD_FINALE_Y_AXE_Y - (i * largeurEntreY));
			g.drawString(Integer.toString(i), 
					COORD_X_AXE_Y - DISTANCE_AXE_ECRITURE, 
					COORD_FINALE_Y_AXE_Y - (i * largeurEntreY));
		}
	}

	/**
	 * Creation d'une approximation de la courbe
	 * Le resultat est place dans le Path2D "ligneBrisee"
	 */
	private void creerApproxCourbeSansAlgo() {
		double x, y;

		ligneBriseeSansAlgo = new Path2D.Double();
		x = 0;  //on commence a l'extreme gauche
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
		x = 0;  //on commence a l'extreme gauche
		y = 0;
		ligneBriseeAvecAlgo.moveTo( x, y );

		for (int k=1; k<=nbSegmentsPourApproximerAvecAlgo; k++) {

			x++;  //on ajoute un intervalle fixe en x, une seconde dans notre cas
			y = this.nbVoituresEnAttenteParSecAvecAlgo.get((int) x-1); //modifier cette ligne!!!
			ligneBriseeAvecAlgo.lineTo( x, y);
		}//fin for
	}
}


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
	private ArrayList<Integer> donneesSansAlgo;
	private ArrayList<Integer> donneesAvecAlgo;

	//
	//62, 209,400, 400
	//coordonnes de l'axe des X
	private final int COORD_INITIAL_X_AXE_X = 50;
	private final int COORD_FINALE_X_AXE_X = 350;
	private final int COORD_Y_AXE_X = 350;

	//coordonnes de l'axe des Y
	private  final int COORD_INITIAL_Y_AXE_Y = 50;
	private  final int COORD_FINALE_Y_AXE_Y= 350;
	private  final int COORD_X_AXE_Y = 50;



	//Largeur des points
	private final int LARGEUR_POINT = 5;

	//Distance entre axe et numéraux
	public final int DISTANCE_AXE_ECRITURE = 20;
	private int xNbCoord;
	private int yNbCoord;
	private int largeurEntreY;
	private int largeurEntreX;

	//Pour les fleches
	// now we are define length of cathetas of that triangle
	public static final int FIRST_LENGHT = 10;
	public static final int SECOND_LENGHT = 5;

	//Approximation
	private int nbSegmentsPourApproximerAvecAlgo;
	private int nbSegmentsPourApproximerSansAlgo;
	//Le type de donnees
	private int typeDonnees;
	/**
	 * Constructeur : cree le composant et fixe la couleur de fond
	 * @param arrayList les statistiques à afficher
	 */
	public PlanCartesien1(int typeDonnees) {
		//Les donnees a afficher sur le plan cartesien
		this.typeDonnees = typeDonnees; 
		// numerate axis
		setBackground(Color.white);

	}//fin du constructeur


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		//Initialisation des donnees
		//Creer un arrayList local pour les stats
		switch(typeDonnees) {
			case 0 : //le nombre de voitures arretees en fonction du temps
				donneesSansAlgo = new ArrayList<Integer>(SceneAnimee.nbVoituresEnAttente); 
				donneesAvecAlgo = new ArrayList<Integer>(SceneAnimeeAvecAlgo.nbVoituresEnAttente);
				System.out.println("TYPE DONEEES 1 ");
				break;
			case 1 ://la vitesse moyenne en fonction du temps
				donneesSansAlgo = new ArrayList<Integer>(SceneAnimee.moyenneDesVitesse); 
				donneesAvecAlgo = new ArrayList<Integer>(SceneAnimeeAvecAlgo.moyenneDesVitesse);
				System.out.println("TYPE DONEEES 2 ");
				break;
			case 2 :
				donneesSansAlgo = new ArrayList<Integer>();
				donneesAvecAlgo = new ArrayList<Integer>();
				for(int k =0;k<10;k++) {
					donneesSansAlgo.add(k);
					donneesAvecAlgo.add(k*2);
				}
				break;
			case 3:
				break;
			default:
				System.out.println("LES DONNEES N'ONT PAS ETE LUES");
		}
		
		//Determiner le nombre de X max
		xNbCoord = Math.max(donneesSansAlgo.size(), donneesAvecAlgo.size());//Le nombre de donnees
		//Determiner le nombre de Y max
		int valeurMaxSansAlgo = Collections.max(donneesSansAlgo);
		int valeurMaxAvecAlgo = Collections.max(donneesAvecAlgo);

		yNbCoord= Math.max(valeurMaxSansAlgo, valeurMaxAvecAlgo) + 1 ; // On se garde une unité pour la visibilité
		System.out.println("LE NOMBRE Y MAX" +yNbCoord );
		//Calculer la largeur entre les x et y
		largeurEntreX = (COORD_FINALE_X_AXE_X - COORD_INITIAL_X_AXE_X)
				/ xNbCoord;
		largeurEntreY = (COORD_FINALE_Y_AXE_Y - COORD_INITIAL_Y_AXE_Y)
				/ yNbCoord;
		//Nombre de segements pour approximation
		nbSegmentsPourApproximerSansAlgo = donneesSansAlgo.size();
		nbSegmentsPourApproximerAvecAlgo = donneesAvecAlgo.size();

		//Améliorer la qualité des dessins
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);


		//les fleches
		// x-axis arrow
		g2d.drawLine(COORD_FINALE_X_AXE_X - FIRST_LENGHT,
				COORD_Y_AXE_X - SECOND_LENGHT,
				COORD_FINALE_X_AXE_X, COORD_Y_AXE_X);
		g2d.drawLine(COORD_FINALE_X_AXE_X - FIRST_LENGHT,
				COORD_Y_AXE_X + SECOND_LENGHT,
				COORD_FINALE_X_AXE_X, COORD_Y_AXE_X);

		// y-axis arrow
		g2d.drawLine(COORD_X_AXE_Y - SECOND_LENGHT,
				COORD_INITIAL_Y_AXE_Y + FIRST_LENGHT,
				COORD_X_AXE_Y, COORD_INITIAL_Y_AXE_Y);
		g2d.drawLine(COORD_X_AXE_Y + SECOND_LENGHT, 
				COORD_INITIAL_Y_AXE_Y + FIRST_LENGHT,
				COORD_X_AXE_Y, COORD_INITIAL_Y_AXE_Y);
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

		//Changer le font
		g2d.setFont(new Font("TimesRoman",Font.PLAIN,15));
		// draw text "X" and draw text "Y"
		g2d.drawString("t", COORD_FINALE_X_AXE_X - DISTANCE_AXE_ECRITURE / 2,
				COORD_Y_AXE_X + DISTANCE_AXE_ECRITURE);
		g2d.drawString("f(t)", COORD_X_AXE_Y - DISTANCE_AXE_ECRITURE*2,
				COORD_INITIAL_Y_AXE_Y + DISTANCE_AXE_ECRITURE / 2);
		g2d.setFont(new Font("TimesRoman",Font.PLAIN,8));
		g2d.drawString("(0, 0)", COORD_INITIAL_X_AXE_X - DISTANCE_AXE_ECRITURE,
				COORD_FINALE_Y_AXE_Y + DISTANCE_AXE_ECRITURE);
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
		x = this.COORD_INITIAL_X_AXE_X;  //on commence a l'extreme gauche
		y = this.COORD_Y_AXE_X;
		ligneBriseeSansAlgo.moveTo( x, y );

		for (int k=0; k<nbSegmentsPourApproximerSansAlgo; k++) {

			x = x +this.largeurEntreX;  //on ajoute un intervalle fixe en x, une seconde dans notre cas
			y = this.getHeight() - this.COORD_X_AXE_Y- this.donneesSansAlgo.get(k)* this.largeurEntreY;
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
		x = this.COORD_INITIAL_X_AXE_X;  //on commence a l'extreme gauche
		y = this.COORD_Y_AXE_X;
		ligneBriseeAvecAlgo.moveTo( x, y );

		for (int k=0; k<nbSegmentsPourApproximerAvecAlgo; k++) {

			x = x +this.largeurEntreX;  //on ajoute un intervalle fixe en x, une seconde dans notre cas
			y = this.getHeight() - this.COORD_X_AXE_Y- this.donneesAvecAlgo.get(k) * this.largeurEntreY;
			ligneBriseeAvecAlgo.lineTo( x, y);
		}//fin for
	}
}


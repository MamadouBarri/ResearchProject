package stats;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import java.util.Collections;
//
import sceneAnimee.SceneAnimee;
import sceneAnimee.SceneAnimeeAvecAlgo;
import sceneAnimee.SceneAnimeeAvecAlgoTempsDArret;
import javax.swing.JPanel;


public class PlanCartesien1 extends JPanel {

	/**
	 * Numéro par défaut
	 */
	private static final long serialVersionUID = 1L;
	//Les lignes
	private Path2D.Double ligneBrisee;
	//Les listes
	private ArrayList<Integer> donneesSansAlgo;
	private ArrayList<Integer> donneesAvecAlgoDensite;
	private ArrayList<Integer> donneesAvecAlgoDensiteTempsArret;
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
	private int nbSegmentsPourApproximerAvecAlgoDensite;
	private int nbSegmentsPourApproximerAvecAlgoDensiteTempsArret;
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

	/**
	 * Dessine le graphique avec les données passées en paramètres
	 * @param g Le conexte graphique
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		//Initialisation des donnees
		//Creer un arrayList local pour les stats
		switch(typeDonnees) {
			case 0 : //le nombre de voitures arretees en fonction du temps
				donneesSansAlgo = new ArrayList<Integer>(SceneAnimee.nbVoituresEnAttente); 
				donneesAvecAlgoDensite = new ArrayList<Integer>(SceneAnimeeAvecAlgo.nbVoituresEnAttente);
				donneesAvecAlgoDensiteTempsArret = new ArrayList<Integer>(SceneAnimeeAvecAlgoTempsDArret.nbVoituresEnAttente);
				System.out.println("TYPE DONEEES 1 ");
				break;
			case 1 ://la vitesse moyenne en fonction du temps
				donneesSansAlgo = new ArrayList<Integer>(SceneAnimee.moyenneDesVitesse); 
				donneesAvecAlgoDensite = new ArrayList<Integer>(SceneAnimeeAvecAlgo.moyenneDesVitesse);
				donneesAvecAlgoDensiteTempsArret = new ArrayList<Integer>(SceneAnimeeAvecAlgoTempsDArret.moyenneDesVitesse);
				System.out.println("TYPE DONEEES 2 ");
				break;
			case 2 :
				donneesSansAlgo = new ArrayList<Integer>();
				donneesAvecAlgoDensite = new ArrayList<Integer>();
				donneesAvecAlgoDensiteTempsArret = new ArrayList<Integer>();
				for(double d : SceneAnimee.tempsDArretMoyen) {
					donneesSansAlgo.add((int)d);
				}
				for(double d : SceneAnimeeAvecAlgo.tempsDArretMoyen) {
					donneesAvecAlgoDensite.add((int)d);
				}
				for(double d : SceneAnimeeAvecAlgoTempsDArret.tempsDArretMoyen) {
					donneesAvecAlgoDensiteTempsArret.add((int)d);
				}
				break;
			case 3:
				donneesSansAlgo = new ArrayList<Integer>();
				donneesAvecAlgoDensite = new ArrayList<Integer>();
				donneesAvecAlgoDensiteTempsArret = new ArrayList<Integer>();
				for(double d : SceneAnimee.densiteVoitures) {
					donneesSansAlgo.add((int)d);
				}
				for(double d : SceneAnimeeAvecAlgo.densiteVoitures) {
					donneesAvecAlgoDensite.add((int)d);
				}
				for(double d : SceneAnimeeAvecAlgoTempsDArret.densiteVoitures) {
					donneesAvecAlgoDensiteTempsArret.add((int)d);
				}
				break;
			default:
				System.out.println("LES DONNEES N'ONT PAS ETE LUES");
		}
		
		//Determiner le nombre de X max
		xNbCoord = Math.max(Math.max(donneesSansAlgo.size(), donneesAvecAlgoDensite.size()), donneesAvecAlgoDensiteTempsArret.size()) + 1;//Le nombre de donnees
		//Determiner le nombre de Y max
		int valeurMaxSansAlgo = Collections.max(donneesSansAlgo);
		int valeurMaxAvecAlgoDensite = Collections.max(donneesAvecAlgoDensite);
		int valeurMaxAvecAlgoDensiteTempsArret = Collections.max(donneesAvecAlgoDensiteTempsArret);

		yNbCoord= Math.max(Math.max(valeurMaxSansAlgo, valeurMaxAvecAlgoDensite), valeurMaxAvecAlgoDensiteTempsArret) + 1 ; // On se garde une unité pour la visibilité
		System.out.println("LE NOMBRE Y MAX" +yNbCoord );
		//Calculer la largeur entre les x et y
		largeurEntreX = (COORD_FINALE_X_AXE_X - COORD_INITIAL_X_AXE_X)
				/ xNbCoord;
		largeurEntreY = (COORD_FINALE_Y_AXE_Y - COORD_INITIAL_Y_AXE_Y)
				/ yNbCoord;
		//Nombre de segements pour approximation
		nbSegmentsPourApproximerSansAlgo = donneesSansAlgo.size();
		nbSegmentsPourApproximerAvecAlgoDensite = donneesAvecAlgoDensite.size();
		nbSegmentsPourApproximerAvecAlgoDensiteTempsArret = donneesAvecAlgoDensiteTempsArret.size();

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
		//On cree les courbes et on les dessine
		//Sans algo
		int numCourbe =0;
		g2d.setColor(Color.red);
		creerApproxCourbe(nbSegmentsPourApproximerSansAlgo,donneesSansAlgo,g2d,numCourbe);
		g2d.draw(ligneBrisee); 
		//Densite
		numCourbe =1;
		g2d.setColor(Color.green);
		creerApproxCourbe(nbSegmentsPourApproximerAvecAlgoDensite,donneesAvecAlgoDensite,g2d,numCourbe);
		g2d.draw(ligneBrisee);
		//Densite et temps d'arret
		numCourbe=2;
		g2d.setColor(Color.cyan);
		creerApproxCourbe(nbSegmentsPourApproximerAvecAlgoDensiteTempsArret,donneesAvecAlgoDensiteTempsArret,g2d,numCourbe);
		g2d.draw(ligneBrisee);
	}
	//

	/**
	 * Méthode pour numeroter les axes
	 * @param g l'objet Graphics2D qui sera utilisé pour dessiner les graduations
	 */
	private void numeroterAxeX(Graphics2D g) {
		// draw x-axis numbers
		for(int i = 0; i < xNbCoord; i++) {
			g.drawLine(COORD_INITIAL_X_AXE_X + (i * largeurEntreX),
					COORD_Y_AXE_X - SECOND_LENGHT,
					COORD_INITIAL_X_AXE_X + (i * largeurEntreX),
					COORD_Y_AXE_X + SECOND_LENGHT);
			
			if(i%5==0||xNbCoord<25) {
				g.drawString(Integer.toString(i), 
						COORD_INITIAL_X_AXE_X + (i * largeurEntreX) - 3,
						COORD_Y_AXE_X + DISTANCE_AXE_ECRITURE);
			}
		}

	}
	/**
	 * Méthode pour numeroter les axes
	 * @param g l'objet Graphics2D qui sera utilisé pour dessiner les graduations
	 */
	private void numeroterAxeY(Graphics2D g) {

		for(int i = 0; i < yNbCoord; i++) {
			g.drawLine(COORD_X_AXE_Y - SECOND_LENGHT,
					COORD_FINALE_Y_AXE_Y - (i * largeurEntreY), 
					COORD_X_AXE_Y + SECOND_LENGHT,
					COORD_FINALE_Y_AXE_Y - (i * largeurEntreY));
			if(i%5==0||yNbCoord<25) {
			g.drawString(Integer.toString(i), 
					COORD_X_AXE_Y - DISTANCE_AXE_ECRITURE, 
					COORD_FINALE_Y_AXE_Y - (i * largeurEntreY));
			}
		}
	}

	/**
	 * Creation d'une approximation de la courbe
	 * Le resultat est place dans le Path2D "ligneBrisee"
	 */
	private void creerApproxCourbe(int nbSegments, ArrayList<Integer> donnees,Graphics2D g,int numCourbe) {
		double x, y;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		ligneBrisee = new Path2D.Double();
		x = this.COORD_INITIAL_X_AXE_X;  //on commence a l'extreme gauche
		y = this.COORD_Y_AXE_X;
		ligneBrisee.moveTo( x, y );

		for (int k=0; k<nbSegments; k++) {
			//Ajouter des formes pour l'esthétique
			x = x +this.largeurEntreX;  //on ajoute un intervalle fixe en x, une seconde dans notre cas
			y = this.getHeight() - this.COORD_X_AXE_Y- donnees.get(k)* this.largeurEntreY;
			ligneBrisee.lineTo( x, y);
			switch(numCourbe) {
			case 0 : //lumieres ordinaires
				//aucune géométrie
				break;
			case 1 ://algorithme avec densite seulement
				g2d.fill(new Rectangle2D.Double(x-LARGEUR_POINT/2.0, y-LARGEUR_POINT/2.0, LARGEUR_POINT, LARGEUR_POINT));
				Color couleurTemp = g2d.getColor();
				g2d.setColor(Color.BLACK);
				g2d.draw(new Rectangle2D.Double(x-LARGEUR_POINT/2.0, y-LARGEUR_POINT/2.0, LARGEUR_POINT, LARGEUR_POINT));
				g2d.setColor(couleurTemp);
				break;
			case 2 : //algorithme avec temps d'attente et densite
				g2d.fill(new Ellipse2D.Double(x-LARGEUR_POINT/2.0, y-LARGEUR_POINT/2.0, LARGEUR_POINT, LARGEUR_POINT));
				couleurTemp = g2d.getColor();
				g2d.setColor(Color.BLACK);
				g2d.draw(new Ellipse2D.Double(x-LARGEUR_POINT/2.0, y-LARGEUR_POINT/2.0, LARGEUR_POINT, LARGEUR_POINT));
				g2d.setColor(couleurTemp);
				break;
			case 3:
				break;
			default:
				System.out.println("LES DONNEES N'ONT PAS ETE LUES");
		}
		}//fin for
	}
	//Reiner 
	/**
	 * Méthode qui efface les graphiques lorsqu'on réinitialise la fenêtre de simulations
	 */
	public void reinitisaliser() {
		this.donneesAvecAlgoDensite.clear();
		this.donneesAvecAlgoDensiteTempsArret.clear();
		this.donneesSansAlgo.clear();
		this.repaint();
	}
}


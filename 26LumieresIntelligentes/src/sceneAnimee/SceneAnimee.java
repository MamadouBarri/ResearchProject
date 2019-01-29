package sceneAnimee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import geometrie.Intersection;
import modele.ModeleAffichage;

public class SceneAnimee extends JPanel implements Runnable{

	/**
	 * Numero par defaut
	 */
	
	private final double DEPLACEMENT = 10;
	/**
	 * Variables
	 */
	
	
	//Varibales animation
	private boolean enCoursDAnimation = false;
	private long tempsDuSleep = 100;
	private double deltaT;
	
	//Objets
	Intersection inter;
	private double largeurRouteReelle;
	private double xVoiture;
	private int largeurVoiture = 10;

	/**
	 * Create the panel.
	 */
	public SceneAnimee() {
		setBackground(Color.gray);
		//On cree le bloc et ressort
		//this.setPreferredSize(preferredSize);
		//setPrefferedBounds(1110,406);
		inter = new Intersection(this.getHeight(),this.getWidth());
	}
	
	public void ecouteursDeSouris() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				//debut
				
				//fin
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				//debut
				
				//fin
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//debut
				
				//fin
			}
			
		});	
	}
	
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		ModeleAffichage modele = new ModeleAffichage(getWidth(), getHeight(), largeurRouteReelle);
		AffineTransform mat = modele.getMatMC();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//peut etre modifier lorigine
		inter.dessiner(g2d,mat);
		
		g2d.setColor(Color.yellow);
		//g2d.fill( new Ellipse2D.Double (xVoiture, xVoiture, largeurVoiture, largeurVoiture) );
		System.out.println(xVoiture);
		
	}//fin paintComponent
	
	
	/**
	 * position de la voiture
	 */
	
	public double positionVoiture () {
		return 0;
	}
	/**
	 * Animation de la balle
	 */
	@Override
	public void run() {
		while (enCoursDAnimation) {	
			System.out.println("un tour de run..");
			//calculerUneIterationPhysique();
			xVoiture++;
			repaint();
			System.out.println(xVoiture);
			try {
				Thread.sleep(tempsDuSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}//fin while
		System.out.println("Le thread est mort...");
	}
	
	/**
	 * Calcul des nouvelles positions pour
	 * tous les objets de la scène
	 */
	private void calculerUneIterationPhysique() {
		//tempsTotalEcoule += deltaT;
		//System.out.println("\nTemps total écoulé: "  + String.format("%.3f",tempsTotalEcoule) + "sec (en temps simulé)");
		//blocEtRessort.unPasEuler( deltaT );
	///forceDeFriction = MoteurPhysique.calculerForceFriction(blocEtRessort.getMasseBlocEnKg(), blocEtRessort.getVitesse(), coefficientDeFriction);
		//forceDeRappel = MoteurPhysique.calculerForceRappel(blocEtRessort.getPosition(), constanteDeRappel);
		//blocEtRessort.setSommeDesForces(MoteurPhysique.sommeDesForces(forceDeFriction, forceDeRappel) );
		//System.out.println(forceDeFriction);
		
	}


	/**
	 * Demarre le thread s'il n'est pas deja demarre
	 */
	public void demarrer() {
		if (!enCoursDAnimation) { 
			Thread proc = new Thread(this);
			proc.start();
			enCoursDAnimation = true;
		}
	}//fin methode
	
	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
	}//fin methode

	/**
	 * Arrête l'animation et reinitialise tout comme au début
	 */
	public void reinitialiser() {
		arreter();
		//blocEtRessort.setPosition(new Vecteur(positionXInitaleBloc,0));
		//blocEtRessort.setVitesse(new Vecteur());
		//tempsTotalEcoule = 0;
		repaint();
	}


	/**
	 * Avance la simulation d'une unique image 
	 */
	public void prochaineImage() {
		
		//à completer....
		
		calculerUneIterationPhysique();
		repaint();
		
	}
	
	public boolean getEnCoursDAnimation() {
		return enCoursDAnimation;
	}
	

	
	/**
	 * Change le temps pour le sleep du thread
	 * @param tempsDuSleep Nouveua temps a appliquer au sleep
	 */
	public void setTempsDuSleep(int tempsDuSleep) {
		this.tempsDuSleep = tempsDuSleep;
	}
	
	/**
	 * Retourne le temps de sleep actuel
	 * @return temps du sleep actuel
	 */
	public int getTempsDuSleep() {
		return (int) tempsDuSleep;
	}


	/**
	 * Modifie le pas (intervalle) de la simulation
	 * @param deltaT le pas (intervalle) de la simulation, exprime en secondes
	 */
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;
		
	}
	
	/**
	 * Retourne le pas intervalle) de la simulation
	 * @return le pas intervalle) de la simulation, exprime en secondes
	 */
	public double getDeltaT() {
		return (deltaT);
	}
	
}

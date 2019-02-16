package sceneAnimee;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Queue;

import javax.swing.JPanel;

import geometrie.Direction;
import geometrie.Intersection;
import geometrie.Lumiere;
import geometrie.Voiture;
import modele.ModeleAffichage;
/**
 * Classe de la scène d'animation de l'intersection
 * @author Mamadou
 *
 */
public class SceneAnimee extends JPanel implements Runnable{
//
	/**
	 * Numero par defaut
	 */

	private final double DEPLACEMENT = 10;
	/**
	 * Variables
	 */
	private ArrayList<Queue<Voiture>> traffic = new ArrayList<Queue<Voiture>>();
	
	
	//Largeur reelle
	private final double LARGEUR_REELLE = 100; //En metres
	
	
	//Voitures
	private final int LARGEUR_VOITURE = 2;
	private final int LONGUEUR_VOITURE = 4;
	private final double DIMENSION_VOIE_REELLE = 10;
	//Modele
	private ModeleAffichage modele;

	
	//Liste de cotes
	ArrayList<Direction> direction = new ArrayList<Direction>();
	//Liste de voitures
	ArrayList<Voiture> voitures = new ArrayList<Voiture>();
	
	//Les listes des directions 
	ArrayList<Voiture> est = new ArrayList<Voiture>();
	ArrayList<Voiture> sud = new ArrayList<Voiture>();
	ArrayList<Voiture>  ouest = new ArrayList<Voiture>();
	ArrayList<Voiture> nord = new ArrayList<Voiture>();
	//Varibales animation
	private boolean enCoursDAnimation = false;
	private long tempsDuSleep = 10; 
	private double deltaT;

	//Objets
	Intersection inter;
	private double largeurRouteReelle;
	private double xVoiture;
	private int largeurVoiture = 10;
	//Voitures
	private double nbBouclesAvantNouvelleVoiture = 100;
	private double nbBouclesAvantLumiereJaune = 1500;
	private double nbBouclesAvantLumiereVerte = 1900;
	private double nbBouclesAvantLumiereRouge = 3400;
	private final double UNE_SECONDE_EN_MILLISECONDE = 1000;
	private int couleur=1;
	Lumiere lum1,lum2,lum3;
	

	/**
	 * Create the panel.
	 */
	public SceneAnimee() {
		setBackground(Color.gray);
		//On cree le bloc et ressort
		//this.setPreferredSize(preferredSize);
		//setPrefferedBounds(1110,406);
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
		modele = new ModeleAffichage(getWidth(), getHeight(), LARGEUR_REELLE);
		AffineTransform mat = modele.getMatMC();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//On passe les dimensions du JPanel a l'intersection
		inter = new Intersection(this.getHeight(),this.getWidth());
		inter.dessiner(g2d,mat);
		
		
		lum2 = new Lumiere(105,10,75,2);
		//lum2.setCouleurJaune();
		lum2.dessiner(g2d, mat);
		
		lum3 = new Lumiere(205,10,75,3);
		//lum3.setCouleurVert();
		lum3.dessiner(g2d, mat);
		
		lum1 = new Lumiere(10,10,75,couleur);
		//lum1.setCouleurRouge();
		lum1.dessiner(g2d, mat);

		//g2d.setColor(Color.yellow);
		//g2d.fill( new Ellipse2D.Double (xVoiture, xVoiture, largeurVoiture, largeurVoiture) );
		for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
			Voiture v = i.next();
			v.dessiner(g2d, mat);
		}	
		//if(!v.getVoitureActive()) {
		//Utiliser le remove sur l'iterrateur pour eviter les erreurs concurrentModification
		//	i.remove();
		//}
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
	double nbRepetitionsPourVoitures = 0;
	double nbRepetitionsPourLumieres = 0;
	while (enCoursDAnimation) {	
		//Commencer le thread de voiture pour chaque voiture de la liste
		//DIRECTION : EST
		for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
			Voiture v = i.next();
			//Essaie pour voir si l'animation marche avec un seul thread
			//v.demarrer();
			v.setXVoiture(v.getXVoiture()+1);
			if(v.getXVoiture()>240 && v.getVoitureActive()) {
				//v.arreter();
				v.setVoitureActive(false);
			}
		}
		//DIRECTION : 
		for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
			Voiture v = i.next();
			//Essaie pour voir si l'animation marche avec un seul thread
			//v.demarrer();
			v.setYVoiture(v.getYVoiture()+1);
			if(v.getYVoiture()>240 && v.getVoitureActive()) {
				//v.arreter();
				v.setVoitureActive(false);
			}
		}
		for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
			Voiture v = i.next();
			//Essaie pour voir si l'animation marche avec un seul thread
			//v.demarrer();
			v.setXVoiture(v.getXVoiture()-1);
			if(v.getXVoiture()<-20 && v.getVoitureActive()) {
				//v.arreter();
				v.setVoitureActive(false);
			}
		}
		for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
			Voiture v = i.next();
			//Essaie pour voir si l'animation marche avec un seul thread
			//v.demarrer();
			v.setYVoiture(v.getYVoiture()-1);
			if(v.getXVoiture()>240 && v.getVoitureActive()) {
				//v.arreter();
				v.setVoitureActive(false);
			}
		}
		try {
			Thread.sleep(tempsDuSleep);
			nbRepetitionsPourVoitures++;
			nbRepetitionsPourLumieres++;
			System.out.println(nbRepetitionsPourLumieres);
			//Lorsque le thread a sleep 10 fois (intervale 10 x tempsSleep)
			
			if(nbRepetitionsPourVoitures == nbBouclesAvantNouvelleVoiture ) {
				ajouterNouvelleVoiture();
				nbRepetitionsPourVoitures=0;
				//for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
					//Voiture v = i.next();
					//if(!v.getVoitureActive()) {
						//Utiliser le remove sur l'iterrateur pour eviter les erreurs concurrentModification
					//	i.remove();
					//	affichageAvecTemps("1 voiture sortie de l'intersection");
					//}
				//}//
			}
			if(nbRepetitionsPourLumieres == nbBouclesAvantLumiereJaune) {
				this.couleur = 2;
				repaint();
			}
			if(nbRepetitionsPourLumieres == nbBouclesAvantLumiereVerte) {
				this.couleur = 3;
				repaint();
			}
			if(nbRepetitionsPourLumieres == nbBouclesAvantLumiereRouge) {
				this.couleur = 1;
				repaint();
				nbRepetitionsPourLumieres =0;
			}
			

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		repaint();
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
 * Affichage a la console avec le temps precis
 * @param affichage Ce qu'on veut afficher a la console
 */
public void affichageAvecTemps(String affichage){
	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Date maintenant = new Date();
	String strDate = sdfDate.format(maintenant);
	System.out.println("[" + strDate + "] " + affichage);
}

/**
 * Rajouter une autre voiture dans l'intersection
 */
public void ajouterNouvelleVoiture() {
	Voiture voiture = new Voiture(modele.getPixelsParUniteX() * LONGUEUR_VOITURE, modele.getPixelsParUniteY() * LARGEUR_VOITURE, modele.getLargPixels(), DIMENSION_VOIE_REELLE );
	//Quelle direction?
	int direction = voiture.getDirection().getNumDirection();
	switch (direction)
	{
		case 1:
			//se deplace vers le est
			est.add(voiture);
			break;
		case 2:
			//se deplace vers le sud
			sud.add(voiture);
			break;
		case 3:
			//se deplace vers louest
			ouest.add(voiture);
			break;
		case 4:
			//se deplace vers le nord
			nord.add(voiture);
			break;
	}
	//pour linstants jajoute dans voitures general
	voitures.add(voiture);
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
	for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
		Voiture v = i.next();
		v.arreter();
	}
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
/**
 * Modifie le nombre de boucles nécessaires avant de créer une voiture
 * @param taux Le taux d'apparition des voitures en voitures/secondes.
 */
public void setTauxDApparition(double taux) {
	this.nbBouclesAvantNouvelleVoiture = this.UNE_SECONDE_EN_MILLISECONDE/this.tempsDuSleep/taux;
}

}

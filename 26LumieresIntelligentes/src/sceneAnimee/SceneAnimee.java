package sceneAnimee;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JPanel;

import geometrie.Direction;
import geometrie.Intersection;
import geometrie.Lumiere;
import geometrie.Voiture;
import modele.ModeleAffichage;
/**
 * Classe de la scène d'animation d'une intersection simple et isolée sur laquelle des voitures avec
 * des directions aleatoires et des actions aleatoires sont generees.
 * @author Mamadou
 */
public class SceneAnimee extends JPanel implements Runnable{
	/**
	 * Numero par defaut
	 */
	private static final long serialVersionUID = 1L;
	private double deplacement = 30; //km/h pour convertir en m/s diviser par 3.
	/**
	 * Variables
	 */
	//Largeur reelle
	private final double LARGEUR_REELLE = 100; //En metres

	//Voitures constantes
	private final int LARGEUR_VOITURE = 2;
	private final int LONGUEUR_VOITURE = 4;
	private final double DIMENSION_VOIE_REELLE = 10;
	private final double DISTANCE_LIGNE_ARRET = 2; 
	//Constante de conversion
	//private final double CONVERSION_KMH_MS = 3.6; //Constante de conversion à utiliser plus tard

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
	//Varibales de l'animation
	private boolean enCoursDAnimation = false;
	private long tempsDuSleep = 10; 
	private double deltaT;
	//Objets
	Intersection inter;
	//Variables pour génération des voitures
	private int nbBouclesAvantNouvelleVoiture = 100;
	private int nbVoituresGenerees =0;
	private int nbVoituresMax ;

	//Lumieres 
	//nombres de tours de run faits pour déterminer quand faire avancer le cycle de lumieres
	private double nbBouclesAvantChangement1 = 2400;
	private double nbBouclesAvantChangement2 = 2700;
	private double nbBouclesAvantChangement3 = 5100;
	//À cette derniere boucle, on retourne le compteur à 0
	private double nbBouclesAvantChangement4 = 5400;
	private final double UNE_SECONDE_EN_MILLISECONDE = 1000;
	private final double DISTANCE_BORDURE = 5; ///En pixels pour le drawString 

	//Les couleurs des lumieres sont determines par des valeurs int : 0=vert; 1=jaune; 2=rouge
	//couleur des lumieres pour les voies nord et sud
	private int couleur=0;
	//couleur des lumieres pour les voies est et ouest
	private int couleurInv=2; 
	Lumiere lumSud,lumNord,lumOuest,lumEst;
	private int nbVoiesHorizontale = 1;
	private int[] trafficAnormale = new int[1];
	private int[] trafficAnormaleTemp= new int[1];
	private boolean enTrafficAnormale;
	private double vitesse;
	//Code des lumiere
	private final int VERTE = 0;
	private final int ROUGE = 2;

	/**
	 * Constructeur de la scène d'animation qui met le background en gris
	 */
	public SceneAnimee() {
		this.vitesse = 20;
		setBackground(Color.gray);
		trafficAnormale = new int[1];
	}
	/**
	 * Dessine l'intersection avec les voitures, les lumièrs et la route 
	 * @param g Le conexte graphique
	 */
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		modele = new ModeleAffichage(getWidth(), getHeight(), LARGEUR_REELLE);
		AffineTransform mat = modele.getMatMC();
		//On recalcule la vitesse des voitures a chaque paint
		calculerVitesse();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//On passe les dimensions du JPanel a l'intersection
		inter = new Intersection(this.LARGEUR_REELLE);
		inter.setNbVoiesHorizontale(nbVoiesHorizontale);
		inter.dessiner(g2d,mat);

		lumSud = new Lumiere(0,0,75,couleurInv,4);
		lumSud.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0-lumSud.getLongueur()-5,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0-lumSud.getLargeur()-5);
		lumSud.dessiner(g2d, mat);

		lumNord = new Lumiere(0,0,75,couleurInv,1);
		lumNord.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0+5,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0+5);
		lumNord.dessiner(g2d, mat);

		lumOuest = new Lumiere(0,0,75,couleur,2);
		lumOuest.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0+lumOuest.getLargeur()/2.0-lumOuest.getLongueur()/2.0+this.DISTANCE_BORDURE, this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0-lumOuest.getLongueur()/2.0-lumOuest.getLargeur()/2.0-5);
		lumOuest.dessiner(g2d, mat);	

		lumEst = new Lumiere(0,0,75,couleur,3);
		lumEst.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0-lumEst.getLongueur()/2.0-lumEst.getLargeur()/2.0-this.DISTANCE_BORDURE,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0-lumEst.getLargeur()/2.0+lumEst.getLongueur()/2.0+this.DISTANCE_BORDURE);
		lumEst.dessiner(g2d, mat);



		//Dessiner l'échelle
		g2d.setColor(Color.cyan);
		g2d.drawString("Échelle: " + LARGEUR_REELLE + " m", (float)DISTANCE_BORDURE, (float)(LARGEUR_REELLE * modele.getPixelsParUniteY() - DISTANCE_BORDURE));
		
		//Parcourir et dessiner chaque voiture
		for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
			Voiture v = i.next();
			v.dessiner(g2d, mat);
		}	
	}//fin paintComponent

	/**
	 * Animation de la balle
	 */
	@Override
	public void run() {
		//compteur pour savoir quand on génère une nouvelle voiture selon le nombre de boucles de run faits
		double nbRepetitionsPourVoitures = 0;
		//compteur pour savoir quand on faire avancer le cycle de lumiere selon le nombre de boucles de run faits
		double nbRepetitionsPourLumieres = 0;
		while (enCoursDAnimation) {	
			//Commencer le thread de voiture pour chaque voiture de la liste

			//Convention des lumieres : 0 - > vert 1 | - > jaune | 2 -> rouge

			//DIRECTION : EST
			for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
				Voiture v = i.next();//pour chaque voiture qui s'en vers l'EST
				switch(v.getDirectionDeVirage()){
				case 0:
					//La voiture continue tout droite, car elle n'effectue pas de virage
					if(!v.isVoitureArretee()) {
						v.setXVoiture((v.getXVoiture()+deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()-1) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setYVoiture(v.getYVoiture()+v.getDeplacement());
						}
					}
					break;
				case 2:
					//La voiture tourne à gauche
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()-1) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setYVoiture(v.getYVoiture()-v.getDeplacement());
						}
						break;
					}
				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumEst.getCouleur() == ROUGE&&v.getEnRotation()==false) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Voiture devant trop proche
				if(est.indexOf(v)!=0) {
					Voiture voitureDevant = est.get(est.indexOf(v)-1);
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE) {
						v.setVoitureArretee(true);
					}
				}

				//Verifier l'etat de la voiture devant
				//Si la liste contient plus qu'une voiture

				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Lorsque la lumiere redevient verte 
				if(lumEst.getCouleur() == VERTE) {
					v.setVoitureArretee(false);
				}
			}//fin DIRECTION EST


			//DIRECTION : SUD
			for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
				Voiture v = i.next();
				if(v.getYVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteY() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture en mouvement
				switch(v.getDirectionDeVirage()){
				//La voiture continue tout droite, car elle n'effectue aucun virage
				case 0:
					if(!v.isVoitureArretee()) {
						v.setYVoiture((v.getYVoiture()+deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0+DISTANCE_BORDURE/4.0)*modele.getPixelsParUniteY()) {
							v.setYVoiture((v.getYVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setXVoiture(v.getXVoiture()-v.getDeplacement());
						}
					}
					break;
				case 2:
					//La voiture tourne à gauche
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+DISTANCE_BORDURE/4.0)*modele.getPixelsParUniteY()) {
							v.setYVoiture((v.getYVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.03);
							}
							v.setXVoiture(v.getXVoiture()+v.getDeplacement());
						}
					}
					break;
				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumSud.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Voiture devant trop proche
				if(sud.indexOf(v)!=0) {
					Voiture voitureDevant = sud.get(sud.indexOf(v)-1);
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE) {
						v.setVoitureArretee(true);
					}
				}

				//Lorsque la lumiere redevient verte 
				if(lumSud.getCouleur() == VERTE) {
					v.setVoitureArretee(false);
				}
			}

			//DIRECTION : OUEST
			for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
				Voiture v = i.next();
				if(v.getXVoiture()<-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}
				//Voiture en mouvement

				switch(v.getDirectionDeVirage()){
				case 0:
					//La voiture continue tout droite, car elle n'effectue aucun virage
					if(!v.isVoitureArretee()) {
						v.setXVoiture((v.getXVoiture()-deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0+this.LARGEUR_VOITURE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setYVoiture(v.getYVoiture()-v.getDeplacement());
						}
					}
					break;
				case 2:
					//La voiture tourne à gauche
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setYVoiture(v.getYVoiture()+v.getDeplacement());
						}
					}
					break;
				}

				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumOuest.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Voiture devant trop proche
				if(ouest.indexOf(v)!=0) {
					Voiture voitureDevant = ouest.get(ouest.indexOf(v)-1);
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE) {
						v.setVoitureArretee(true);
					}
				}

				//Lorsque la lumiere redevient verte 
				if(lumOuest.getCouleur() == VERTE) {
					v.setVoitureArretee(false);
				}

			}

			//DIRECTION : NORD
			for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
				Voiture v = i.next();
				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture en mouvement

				switch(v.getDirectionDeVirage()){
				case 0:
					//La voiture continue tout droite, car elle n'effectue aucun virage
					if(!v.isVoitureArretee()) {
						v.setYVoiture((v.getYVoiture()-deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/4.0)*modele.getPixelsParUniteY()) {
							v.setYVoiture((v.getYVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/3.0)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
							//System.out.println("I WANNA TURN");
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setXVoiture(v.getXVoiture()+v.getDeplacement());
						}
					}
					break;
				case 2:
					//La voiture tourne à gauche
					if(!v.isVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-DISTANCE_BORDURE/2.0)*modele.getPixelsParUniteY()) {
							v.setYVoiture((v.getYVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE/3.0)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.05);
							}
							v.setXVoiture(v.getXVoiture()-v.getDeplacement());
						}
					}
					break;
				}

				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE - LARGEUR_VOITURE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumNord.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Voiture devant trop proche
				if(nord.indexOf(v)!=0) {
					Voiture voitureDevant = nord.get(nord.indexOf(v)-1);
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE) {
						v.setVoitureArretee(true);
					}
				}

				//Lorsque la lumiere redevient verte 
				if(lumNord.getCouleur() == VERTE) {
					v.setVoitureArretee(false);
				}
			}
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
				nbRepetitionsPourVoitures++;
				nbRepetitionsPourLumieres++;
				//System.out.println(nbRepetitionsPourLumieres);
				//Lorsque le thread a sleep 10 fois (intervale 10 x tempsSleep)
				if(nbRepetitionsPourVoitures == nbBouclesAvantNouvelleVoiture && nbVoituresGenerees < nbVoituresMax ) {
					ajouterNouvelleVoiture();
					nbRepetitionsPourVoitures=0;
				}
				if(nbRepetitionsPourLumieres == nbBouclesAvantChangement1) {
					changeCouleurLumieres();
					repaint();
				}
				if(nbRepetitionsPourLumieres == nbBouclesAvantChangement2) {
					changeCouleurLumieres();
					repaint();
				}
				if(nbRepetitionsPourLumieres == nbBouclesAvantChangement3) {
					changeCouleurLumieres();
					repaint();
				}
				if(nbRepetitionsPourLumieres == nbBouclesAvantChangement4) {
					changeCouleurLumieres();
					repaint();
					nbRepetitionsPourLumieres =0;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}//fin while
		System.out.println("Le thread est mort...");
	}
	/**
	 * Méthode qui fait l'affichage a la console avec le temps precisé
	 * @param affichage Ce qu'on veut afficher a la console
	 */
	public void affichageAvecTemps(String affichage){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date maintenant = new Date();
		String strDate = sdfDate.format(maintenant);
		System.out.println("[" + strDate + "] " + affichage);
	}

	/**
	 * Méthode qui ajoute une nouvelle voiture dans l'intersection
	 */
	public void ajouterNouvelleVoiture() {
		//On ajoute au nombre de voitures generees
		nbVoituresGenerees++;
		Voiture voiture = new Voiture(modele.getPixelsParUniteX() * LONGUEUR_VOITURE, modele.getPixelsParUniteY() * LARGEUR_VOITURE, modele.getLargPixels(), DIMENSION_VOIE_REELLE, trafficAnormale );
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
	}

	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
	}

	/**
	 * Arrête l'animation et reinitialise tout comme au début
	 */
	public void reinitialiser() {
		arreter();
		repaint();
	}
	/**
	 * Getter vrai si en cours d'animation et faux sinon
	 * @return enCoursDAnimation boul qui spécifie si on est en cours d'animation
	 */
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
	public void setTauxDApparition(double tauxParMinute) {
		double tauxParSeconde = tauxParMinute/60.0;
		double periodeApparition = 1.0/tauxParSeconde * this.UNE_SECONDE_EN_MILLISECONDE; //On passe de la fréquence d'apparition au temps (période)
		this.nbBouclesAvantNouvelleVoiture = (int)(periodeApparition/tempsDuSleep); //On calcule le nombre de boucle avant une nouvelle voiture avecle tempsDuSleep
		System.out.println("Nombre de boucle sleep avant une nouvelle voiture : " + this.nbBouclesAvantNouvelleVoiture); //Test

	}
	/**
	 * Getter de la largeur reelle
	 * @return LARGEUR_REELLE retourne la constante de la largeur reelle
	 */
	public double getLARGEUR_REELLE() {
		return LARGEUR_REELLE;
	}
	/**
	 * Méthode qui change la couleur des lumières
	 */
	private void changeCouleurLumieres(){
		if(couleur==0) {
			couleur = (couleur+1)%3;
			lumSud.setCouleur(couleur);
			lumNord.setCouleur(couleur);
		} else {
			if(couleur==1) {
				couleur = (couleur+1)%3;
				couleurInv = (couleurInv+1)%3;
				lumSud.setCouleur(couleur);
				lumNord.setCouleur(couleur);
				lumOuest.setCouleur(couleurInv);
				lumEst.setCouleur(couleurInv);
			} else {
				if(couleur==2&&couleurInv==0) {
					couleurInv = (couleurInv+1)%3;
					lumOuest.setCouleur(couleurInv);
					lumEst.setCouleur(couleurInv);
				} else {
					couleur = (couleur+1)%3;
					couleurInv = (couleurInv+1)%3;
					lumSud.setCouleur(couleur);
					lumNord.setCouleur(couleur);
					lumOuest.setCouleur(couleurInv);
					lumEst.setCouleur(couleurInv);
				}
			}
		}
	}
	/**
	 * Setter qui change le nombre de voies horizontalemnt
	 * @param nbVoiesHorizontale le nombre de voie a l'horizontale
	 */
	public void setNbVoiesHorizontale(int nbVoiesHorizontale) {
		this.nbVoiesHorizontale= nbVoiesHorizontale; 
	}
	/**
	 * Méthode qui converti les vitesses en m/s à des vitesses en pixels/boucle de run
	 * @param vitesse
	 */
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	public void calculerVitesse() {
		this.deplacement = vitesse*modele.getPixelsParUniteX()/(this.UNE_SECONDE_EN_MILLISECONDE/this.tempsDuSleep);
	}
	public void addTrafficAnormale(int numDeVoie) {
		if(this.enTrafficAnormale&&numDeVoie>0) {
			int[] tabTemporaire = this.trafficAnormale;
			this.trafficAnormale = new int[this.trafficAnormale.length+1];
			for(int i = 0;i<tabTemporaire.length;i++) {
				this.trafficAnormale[i] = tabTemporaire[i]; 
			}
			this.trafficAnormale[tabTemporaire.length] = numDeVoie;
		}
	}
	/**
	 * Méthode qui permet d'ajouter du traffic anormal a une voie
	 * @param numDeVoie la voie qui aura  du traffic anormal
	 */
	public void addTrafficNormale(int numDeVoie) {
		if(this.enTrafficAnormale&&numDeVoie>0) {
			int[] tabTemporaire = this.trafficAnormale;
			this.trafficAnormale = new int[this.trafficAnormale.length-1];
			for(int i = 0;i<tabTemporaire.length;i++) {
				if(tabTemporaire[i]!=numDeVoie) {
					this.trafficAnormale[i] = tabTemporaire[i];
				}
			}
		}
	}
	/**
	 * Méthode qui permet de spécifier si il y a du traffic anormal
	 * @param enTrafficAnormale boolean qui indique s'il y a du traffic anormal
	 */
	public void setTrafficAnormale(boolean enTrafficAnormale) {
		if(enTrafficAnormale) {
			this.trafficAnormale = this.trafficAnormaleTemp;
			this.enTrafficAnormale = true;
		} else {
			this.trafficAnormaleTemp = this.trafficAnormale;
			this.trafficAnormale = new int[1];
			this.enTrafficAnormale = false;
		}
	}
	/**
	 * Methode qui retourne les voitures qui ont deja ete generees
	 * @return le nombre de voitures deja generees
	 */
	public int getNbVoituresGenerees() {
		return nbVoituresGenerees;
	}
	/**
	 * Methode qui permet de specifier le nombre de voitures generees
	 * @param nbVoituresGenerees le nombre de voitures generees
	 */
	public void setNbVoituresGenerees(int nbVoituresGenerees) {
		this.nbVoituresGenerees = nbVoituresGenerees;
	}
	/**
	 * Methode qui permet de get le nombre de voitures max a generer pendant 
	 * la simulation
	 * @return le nombre de voitures max a generer
	 */
	public int getNbVoituresMax() {
		return nbVoituresMax;
	}
	/**
	 * Methode qui permet de specifier le nombre de voitures max a generer pendant
	 * @param nbVoituresMax le nombre de voitures max quon desire generer
	 */
	public void setNbVoituresMax(int nbVoituresMax) {
		this.nbVoituresMax = nbVoituresMax;
	}
}

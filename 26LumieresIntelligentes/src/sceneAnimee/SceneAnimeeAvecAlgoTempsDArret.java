package sceneAnimee;
//
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JPanel;

import geometrie.Direction;
import geometrie.Intersection;
import geometrie.Lumiere;
import geometrie.Voiture;
import modele.ModeleAffichage;
/**
 * Classe de la scène d'animation d'une intersection simple et isolée sur laquelle des voitures avec
 * des directions aleatoires et des actions aleatoires sont generees.
 * @author Mamadou & Reiner
 */
public class SceneAnimeeAvecAlgoTempsDArret extends JPanel implements Runnable{
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
	//Distribution gaussienne
	private final double DEVIATION_GAUSSIENNE = 0.3 ;
	private double tauxDApparitionMoyen = 0.789; // Par seconde
	private static final double TAUX_GAUSS_MAX = 1.8;
	private static final double TAUX_GAUSS_MIN= 0.1;
	//Constante de conversion
	//private final double CONVERSION_KMH_MS = 3.6; //Constante de conversion à utiliser plus tard
	//Nombre de voies
	private int nbVoiesEst=1;
	private int nbVoiesOuest=1;
	private int nbVoiesSud=1;
	private int nbVoiesNord=1;
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
	private boolean veutProchainImage = false;
	//Objets
	Intersection inter;
	//Variables pour génération des voitures
	private int nbBouclesAvantNouvelleVoiture = 100;
	private int nbVoituresGenerees =0;
	private int nbVoituresMax = 60 ;

	//Lumieres 
	//nombres de tours de run faits pour déterminer quand faire avancer le cycle de lumieres après que l'algorithme à décider d'inverser le "flow"
	private double nbBouclesAvantChangement2 = 300;
	private final double UNE_SECONDE_EN_MILLISECONDE = 1000;
	private final double DISTANCE_BORDURE = 8; ///En pixels pour le drawString 

	//Les couleurs des lumieres sont determines par des valeurs int : 0=vert; 1=jaune; 2=rouge
	//couleur des lumieres pour les voies nord et sud
	private int couleur=0;
	//couleur des lumieres pour les voies est et ouest
	private int couleurInv=2; 
	Lumiere lumSud,lumNord,lumOuest,lumEst;
	private int nbVoiesHorizontale = 1;
	private int[] trafficAnormale = new int[0];
	private int[] trafficAnormaleTemp= new int[0];
	private boolean enTrafficAnormale;
	private double vitesse;
	//Code des lumiere
	private final int VERTE = 0;
	private final int JAUNE = 1;
	private final int ROUGE = 2;
	//Voiture
	private boolean ilYAVoitureQuiBloque = false;
	private int typeImages = 0;
	private int nbRepetitionsStats = 0;
	private int nbRepetitionsMaxStats = 100;
	private int nbVoituresActives;
	private int vitessesTotales;
	//Pour les statistiques
	public static ArrayList<Integer> nbVoituresEnAttente = new ArrayList<Integer>();
	public static ArrayList<Integer> moyenneDesVitesse = new ArrayList<Integer>();
	public static ArrayList<Double> tempsDArretMoyen = new ArrayList<Double>();
	public static ArrayList<Double> densiteVoitures = new ArrayList<Double>();
	//Mamadou
	/**
	 * Constructeur de la scène d'animation qui met le background en gris
	 */
	public SceneAnimeeAvecAlgoTempsDArret() {
		this.vitesse = 20;
		setBackground(Color.gray);
		trafficAnormale = new int[1];
	}
	//Mamadou
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
		inter = new Intersection(this.LARGEUR_REELLE,this.nbVoiesEst,this.nbVoiesOuest,this.nbVoiesSud,this.nbVoiesNord);
		inter.setNbVoiesHorizontale(nbVoiesHorizontale);
		inter.dessiner(g2d,mat);

		dessinerLumieres(g2d,mat);

		//Dessiner l'échelle
		g2d.setColor(Color.cyan);
		g2d.drawString("Échelle: " + LARGEUR_REELLE + " m", (float)DISTANCE_BORDURE, (float)(LARGEUR_REELLE * modele.getPixelsParUniteY() - DISTANCE_BORDURE));

		//Parcourir et dessiner chaque voiture
		for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
			Voiture v = i.next();
			v.setNbVoies(nbVoiesEst, nbVoiesOuest, nbVoiesSud, nbVoiesNord);
			v.dessiner(g2d, mat);
		}	
	}//fin paintComponent
	//Reiner
	/**
	 * Méthode qui dessine les lumières sur l'intersection
	 * @param g2d contexte graphique
	 * @param mat matrice de transformation monde-vers-composant
	 */
	public void dessinerLumieres(Graphics2D g2d, AffineTransform mat) {
		lumSud = new Lumiere(0,0,75,couleur,4);
		lumSud.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0-(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0)*this.nbVoiesSud-lumSud.getLongueur()-5,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0-(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0)*this.nbVoiesOuest-lumSud.getLargeur()-5);
		lumSud.dessiner(g2d, mat);

		lumNord = new Lumiere(0,0,75,couleur,1);
		lumNord.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0+(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0)*this.nbVoiesNord+5,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0+(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0)*this.nbVoiesEst+5);
		lumNord.dessiner(g2d, mat);

		lumOuest = new Lumiere(0,0,75,couleurInv,2);
		lumOuest.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0+(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0)*this.nbVoiesNord+lumOuest.getLargeur()/2.0-lumOuest.getLongueur()/2.0+this.DISTANCE_BORDURE, this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0-(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0)*this.nbVoiesOuest-lumOuest.getLongueur()/2.0-lumOuest.getLargeur()/2.0-5);
		lumOuest.dessiner(g2d, mat);	

		lumEst = new Lumiere(0,0,75,couleurInv,3);
		lumEst.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0-(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0)*this.nbVoiesSud-lumEst.getLongueur()/2.0-lumEst.getLargeur()/2.0-this.DISTANCE_BORDURE,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0+(this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0)*this.nbVoiesEst-lumEst.getLargeur()/2.0+lumEst.getLongueur()/2.0+this.DISTANCE_BORDURE);
		lumEst.dessiner(g2d, mat);
	}
	//Mamadou 
	/**
	 * Animation de l'intersection avec les voitures se deplacant dans les quatres directions differentes : NORD,SUD,OUEST,EST
	 */
	@Override
	public void run() {
		//compteur pour savoir quand on génère une nouvelle voiture selon le nombre de boucles de run faits
		double nbRepetitionsPourVoitures = 0;
		//compteur pour savoir quand on faire avancer le cycle de lumiere selon le nombre de boucles de run faits
		double nbRepetitionsPourChangement = 0;
		double nbRepetitionsPourLumieres = 0;
		double densiteHorizontale = 0;
		double densiteVerticale = 0;
		while (enCoursDAnimation) {	
			//Commencer le thread de voiture pour chaque voiture de la liste

			//Convention des lumieres : 0 - > vert 1 | - > jaune | 2 -> rouge

			//DIRECTION : EST
			for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
				Voiture v = i.next();//pour chaque voiture qui s'en vers l'EST
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
					verifierVoitureOppose('e', v);
				}
				//fin de la verification
				//Voiture en mouvement
				v = animationVirages(v,'e');

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumEst.getCouleur() == VERTE||lumEst.getCouleur() == JAUNE) {
					v.setVoitureArretee(false);
					v.setVoitureAccelere(true);
				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getXVoiture() - ((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX() - DIMENSION_VOIE_REELLE/2.0*nbVoiesSud*modele.getPixelsParUniteX()-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX())) < DISTANCE_LIGNE_ARRET && lumEst.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}


				//Verifier l'etat de la voiture devant
				//Si la liste contient plus qu'une voiture

				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture devant trop proche
				if(est.indexOf(v)!=0) {
					Voiture voitureDevant = verifierVoitureDevant(est,v, nbVoiesEst);
					//La voiture est trop proche donc s'arrête
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3) {
						v.setVoitureArretee(true);
						v.setVoitureAccelere(false);
						v.setCompteurTemp(100);
						v.setVoitureRalentit(false);
					}
					//La voiture est assez proche donc doit ralentir /**CHENGMENT ICI
					//
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*7.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3 && !v.getVoitureArretee() && voitureDevant.getVoitureArretee() && lumEst.getCouleur() == ROUGE) {
						v.setVoitureRalentit(true);
					}
					/**
					 * CHANGEMENTS
					 */

					//La voiture devant est assez loin pour accelerer
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) > LARGEUR_VOITURE*4.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3 &&
							!voitureDevant.getVoitureArretee() && !voitureDevant.getVoitureAccelere() && lumEst.getCouleur() == VERTE && voitureDevant.getVoitureActive()) {
						v.setVoitureAccelere(true);
						/**
						 * CHANGEMMENT && !voitureDevant.getVoitureArretee() && Math.abs(voitureDevant.getXVoiture()) < LARGEUR_VOITURE*5.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE
						 */
						v.setVoitureArretee(false);
						//
					}

					//if(!voitureDevant.getVoitureAccelere() && !voitureDevant.getVoitureArretee() && (lumEst.getCouleur() == VERTE||lumEst.getCouleur() == JAUNE)) { 
					//v.setVoitureAccelere(true);
					//}
					/**
					 * CHANGEMENTS
					 */
					//if(!voitureDevant.getVoitureArretee( ) && Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3) {
					//v.setVoitureAccelere(false);
					//}
					/**
					 * CHANGEMENT
					 */
					//if(voitureDevant.getVoitureAccelere()) {
					//v.setVoitureArretee(true);
					//}else {
					//	v.setVoitureArretee(false);
					//}


				}else {
					if(lumEst.getCouleur() == VERTE||lumEst.getCouleur() == JAUNE) {
						v.setVoitureArretee(false);
					}
				}

			}//fin DIRECTION EST


			//DIRECTION : SUD
			for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
				Voiture v = i.next();
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
					verifierVoitureOppose('s', v);
				}
				//fin de la verification
				if(v.getYVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteY() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture en mouvement
				v = animationVirages(v,'s');
				//Lorsque la lumiere redevient verte ou est jaune
				if(lumSud.getCouleur() == VERTE||lumSud.getCouleur() == JAUNE) {

					v.setVoitureArretee(false);

				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE/2.0*nbVoiesOuest-this.LONGUEUR_VOITURE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumSud.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}


				//Voiture devant trop proche
				if(sud.indexOf(v)!=0) {
					Voiture voitureDevant = verifierVoitureDevant(sud,v, nbVoiesSud);
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getXVoiture()-voitureDevant.getXVoiture())<3) {
						v.setVoitureArretee(true);
						v.setCompteurTemp(100);
						v.setVoitureRalentit(false);
					}

					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*7.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getXVoiture()-voitureDevant.getXVoiture())<3 && !v.getVoitureArretee() && voitureDevant.getVoitureArretee()) {
						v.setVoitureRalentit(true);
					}


				}
			}

			//DIRECTION : OUEST
			for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
				Voiture v = i.next();
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
					verifierVoitureOppose('o', v);
				}
				//fin de la verification
				if(v.getXVoiture()<-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}
				//Voiture en mouvement
				v = animationVirages(v,'o');

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumOuest.getCouleur() == VERTE||lumOuest.getCouleur()==JAUNE) {
					v.setVoitureArretee(false);
				}

				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0*nbVoiesNord)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumOuest.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Voiture devant trop proche
				if(ouest.indexOf(v)!=0) {
					Voiture voitureDevant = verifierVoitureDevant(ouest,v, nbVoiesOuest);
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3) {
						v.setVoitureArretee(true);
						v.setCompteurTemp(100);
						v.setVoitureRalentit(false);
					}
					//La voiture est assez proche donc doit ralentir
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*7.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3 && !v.getVoitureArretee() && voitureDevant.getVoitureArretee()) {
						v.setVoitureRalentit(true);
					}
					//La voiture devant est assez loin pour accelerer
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) > LARGEUR_VOITURE*4.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getYVoiture()-voitureDevant.getYVoiture())<3 &&
							!voitureDevant.getVoitureArretee() && !voitureDevant.getVoitureAccelere() && lumOuest.getCouleur() == VERTE && voitureDevant.getVoitureActive()) {
						v.setVoitureAccelere(true);
						/**
						 * CHANGEMMENT && !voitureDevant.getVoitureArretee() && Math.abs(voitureDevant.getXVoiture()) < LARGEUR_VOITURE*5.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE
						 */
						v.setVoitureArretee(false);
					}

				}else {
					if(lumOuest.getCouleur() == VERTE||lumOuest.getCouleur() == JAUNE) {
						v.setVoitureArretee(false);
					}
				}
			}

			//DIRECTION : NORD
			for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
				Voiture v = i.next();
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
					verifierVoitureOppose('n', v);
				}
				//fin de la verification
				if(v.getYVoiture()<-this.LARGEUR_VOITURE*modele.getPixelsParUniteY() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture en mouvement
				v = animationVirages(v,'n'); 

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumNord.getCouleur() == VERTE||lumNord.getCouleur() == JAUNE) {
					v.setVoitureArretee(false);
				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0*(nbVoiesEst))*modele.getPixelsParUniteY()-this.DISTANCE_BORDURE) < DISTANCE_LIGNE_ARRET && lumNord.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Voiture devant trop proche
				if(nord.indexOf(v)!=0) {
					Voiture voitureDevant = verifierVoitureDevant(nord,v, nbVoiesNord);
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getXVoiture()-voitureDevant.getXVoiture())<3) {
						v.setVoitureArretee(true);
						v.setCompteurTemp(100);
						v.setVoitureRalentit(false);
					}

					//La voiture est assez proche donc doit ralentir
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*7.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&Math.abs(v.getXVoiture()-voitureDevant.getXVoiture())<3 && !v.getVoitureArretee() && voitureDevant.getVoitureArretee()) {
						v.setVoitureRalentit(true);
					}
				}
			}
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
				nbRepetitionsPourVoitures++;
				nbRepetitionsPourLumieres++;
				nbRepetitionsStats++;
				double vitesse;
				//booleans pour déterminer si on a des voitures d'urgences sur l'intersection
				boolean ilYAVoitureDUrgenceVert = false;
				boolean ilYAVoitureDUrgenceHoriz = false;
				//Le temps d'arrêt de la voiture qui a attendu le plus
				double tempsDArretMaxHoriz = 0;
				double tempsDArretMaxVert = 0;
				//Lorsque le thread a sleep 10 fois (intervale 10 x tempsSleep)
				if(nbRepetitionsPourVoitures == nbBouclesAvantNouvelleVoiture && nbVoituresGenerees < nbVoituresMax ) {
					ajouterNouvelleVoiture();

					nbRepetitionsPourVoitures=0;
				}
				//Lorsque une seconde passe, donc on prend une nouvelle valeur pour les statistiques
				if(nbRepetitionsStats  == nbRepetitionsMaxStats ) {
					//On calcule les voitures en attente
					int voituresEnAttenteTotal = 0;
					//On remet le nombre de voitures actives a 0
					nbVoituresActives = 0;
					//On remet le total des vitesse a 0
					vitessesTotales = 0;
					for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
						Voiture v = i.next();

						//  1 )   Calculons le TEMPS ACTIF SUR L'INTERSECTION
						if(v.getVoitureActive()) {
							nbVoituresActives++; // On a le nombre de voitures actives
							if(!v.getVoitureArretee()) {
								vitesse = this.vitesse;
								vitessesTotales+=vitesse; //Pour plus de 
							}
							v.setTempsSurIntersection(v.getTempsSurIntersection()+1);//On augmente le temps passe sur intersection de 1 sec
						}						
						//  2 )   Calculons le NOMBRE DE VOITURES EN ARRET
						if(v.getVoitureArretee()) {
							voituresEnAttenteTotal++;
						}
					}
					//On divise le total des vitesses par le nombre de voitures actives
					double moyenneDesVitesses;
					if(nbVoituresActives!=0) {
						moyenneDesVitesses = vitessesTotales * 1.0/(double)nbVoituresActives;
					}else {
						moyenneDesVitesses = 0;
					}
					//On met dans la liste à chaque seconde
					moyenneDesVitesse.add((int)moyenneDesVitesses);
					//On ajoute la valeur dans la liste
					nbVoituresEnAttente.add(voituresEnAttenteTotal);
					tempsDArretMoyen.add(this.calculeTempsDArretMoyen());
					densiteVoitures.add(calculeDensite(voitures, this.LARGEUR_REELLE*4));//On fait LARGEUR_REELLE*4, car il y a 4 voies dans l'intersection
					nbRepetitionsStats=0; //On remet le compteur a 0
				}
				//on vérifie le temps d'arrêt des voies à chaque 5 secondes
				if(nbRepetitionsPourLumieres>=500&&(nbRepetitionsPourChangement==nbBouclesAvantChangement2||nbRepetitionsPourChangement==0)) {
					//on réinitialise nos valeurs
					densiteHorizontale = 0;
					densiteVerticale = 0;
					tempsDArretMaxHoriz = 0;
					tempsDArretMaxVert = 0;
					//On vérifie les voitures ayant est comme direction
					for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getXVoiture()<(LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0*nbVoiesSud)*modele.getPixelsParUniteX()) {
							//si oui, on l'ajoute à la densité de voitures des voies horizontales
							densiteHorizontale++;
							if(v.getTempsDArret()>tempsDArretMaxHoriz) {
								tempsDArretMaxHoriz = v.getTempsDArret();
							}
							if(v.estVoitureDUrgence()) {
								ilYAVoitureDUrgenceHoriz = true;
							}
						}
					}
					//On vérifie les voitures ayant ouest comme direction
					for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getXVoiture()>(LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0*nbVoiesNord)*modele.getPixelsParUniteX()) {
							//si oui, on l'ajoute à la densité de voitures des voies horizontales
							densiteHorizontale++;
							if(v.getTempsDArret()>tempsDArretMaxHoriz) {
								tempsDArretMaxHoriz = v.getTempsDArret();
							}
							if(v.estVoitureDUrgence()) {
								ilYAVoitureDUrgenceHoriz = true;
							}
						}
					}
					//On vérifie les voitures ayant nord comme direction
					for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getYVoiture()>(LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0*nbVoiesEst)*modele.getPixelsParUniteY()) {
							//si oui, on l'ajoute à la densité de voitures des voies verticales
							densiteVerticale++;
							if(v.getTempsDArret()>tempsDArretMaxVert) {
								tempsDArretMaxVert = v.getTempsDArret();
							}
							if(v.estVoitureDUrgence()) {
								ilYAVoitureDUrgenceVert = true;
							}
						}
						//On vérifie les voitures ayant sud comme direction
					}for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getYVoiture()<(LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0*nbVoiesOuest)*modele.getPixelsParUniteY()) {
							//si oui, on l'ajoute à la densité de voitures des voies verticales
							densiteVerticale++;
							if(v.getTempsDArret()>tempsDArretMaxVert) {
								tempsDArretMaxVert = v.getTempsDArret();
							}
							if(v.estVoitureDUrgence()) {
								ilYAVoitureDUrgenceVert = true;
							}
						}
					}
					if(tempsDArretMaxHoriz>7) {
						//si une voiture sur les voies horizontales a attendu pour plus de 10 secondes, on modifie les densités pour la permettre de passer
						densiteHorizontale = 1;
						densiteVerticale = 0;
					} else {
						//si une voiture sur les voies verticales a attendu pour plus de 10 secondes, on modifie les densités pour la permettre de passer
						if(tempsDArretMaxVert>7) {
							densiteHorizontale = 0;
							densiteVerticale = 1;
						}
					}
					if(ilYAVoitureDUrgenceHoriz){
						//s'il y a une voiture d'urgence sur une des voies horizontales, on fait comme si ces voies avait une plus grande densité
						densiteHorizontale = 1;
						densiteVerticale = 0;
					} else {
						if(ilYAVoitureDUrgenceVert) {
							//s'il y a une voiture d'urgence sur une des voies verticales, on fait comme si ces voies avait une plus grande densité
							densiteHorizontale = 0;
							densiteVerticale = 1;
						}
					}
					nbRepetitionsPourLumieres=0;
				} else {
					nbRepetitionsPourLumieres++;
				}
				//on vérifie si les voies avec les temps d'arrêt supérieurs ont la lumière verte
				if(densiteHorizontale>densiteVerticale&&lumEst.getCouleur()==ROUGE) {
					//Sinon, on change la lumière opposée de vert à jaune
					if(lumNord.getCouleur()==VERTE) {
						changeCouleurLumieres();
					} else {
						//petite pause avant de changer la lumière opposée de jaune à rouge
						if(nbRepetitionsPourChangement!=nbBouclesAvantChangement2) {
							nbRepetitionsPourChangement++;
						} else {
							changeCouleurLumieres();
							nbRepetitionsPourChangement = 0;
						}
					}
				} else {
					if(densiteVerticale>densiteHorizontale&&lumNord.getCouleur()==ROUGE) {
						//Changement de vert à jaune
						if(lumEst.getCouleur()==VERTE) {
							changeCouleurLumieres();
						} else {
							//petite pause avant de changer de jaune à rouge
							if(nbRepetitionsPourChangement!=nbBouclesAvantChangement2) {
								nbRepetitionsPourChangement++;
							} else {
								changeCouleurLumieres();
								nbRepetitionsPourChangement = 0;
							}
						}
					} else {
						if(densiteVerticale==densiteHorizontale) {
							//On ne change rien
						}
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//arrete l'animation apres un tour de run si l'utilisateur veut faire avancer l'animation pas a pas
			if(veutProchainImage) {
				enCoursDAnimation = false;
				veutProchainImage = false;
			}

		}//fin while
	}
	//Mamadou
	/**
	 * Methode qui gere l'acceleration horizontale des voitures
	 */
	public void voitureAccelereHorizontalement(Voiture v, char direction) {
		int iTemp = -1;
		if(direction=='e') {
			iTemp = 1;
		}
		v.setXVoiture((v.getXVoiture() + iTemp *deplacement - iTemp *deplacement*v.getCompteurTemp()*0.009 ));
		v.setCompteurTemp(v.getCompteurTemp()-1);
		if(v.getCompteurTemp()==0) {
			v.setAccelTerminee(true);
			v.setVoitureArretee(false);
			v.setVoitureAccelere(false);
			v.setCompteurTemp(110);
		}
	}
	//Mamadou
	/**
	 * Methode qui gere l'acceleration verticale des voitures
	 */
	public void voitureAccelereVerticalement(Voiture v,char direction) {
		int iTemp =-1;
		if(direction=='s') {
			iTemp = 1;
		}
		v.setYVoiture((v.getYVoiture() + iTemp * deplacement - iTemp * deplacement*v.getCompteurTemp()*0.009 ));
		v.setCompteurTemp(v.getCompteurTemp()-1);
		if(v.getCompteurTemp()==0) {
			v.setAccelTerminee(true);
			v.setVoitureArretee(false);
			v.setVoitureAccelere(false);
			v.setCompteurTemp(110);
		}
	}
	//Mamadou
	/**
	 * Methode qui gere la deceleration horizontale des voitures
	 * @param v
	 * @param direction
	 */
	public void voitureDecelereHorizontalement(Voiture v, char direction) {
		int iTemp =-1;
		if(direction=='e') {
			iTemp = 1;
		}
		v.setXVoiture((v.getXVoiture() + iTemp *deplacement*v.getCompteurTemp()*0.009 ));
		v.setCompteurTemp(v.getCompteurTemp()-1);
		if(v.getCompteurTemp()==0) {
			v.setVoitureArretee(true);
			v.setVoitureRalentit(false);
			v.setCompteurTemp(110);
		}
	}
	//Mamadou
	/**
	 * Methode qui gere la deceleration verticale des voitures
	 * @param v
	 * @param direction
	 */
	public void voitureDecelereVerticalement(Voiture v, char direction) {
		int iTemp =-1;
		if(direction=='s') {
			iTemp = 1;
		}
		v.setYVoiture((v.getYVoiture() + iTemp*deplacement*v.getCompteurTemp()*0.009 ));
		v.setCompteurTemp(v.getCompteurTemp()-1);
		if(v.getCompteurTemp()==0) {
			v.setVoitureArretee(true);
			v.setVoitureRalentit(false);
			v.setCompteurTemp(110);
		}
	}

	//Reiner
	/**
	 * Méthode contenant tout le code qui permet la voiture de se déplacer sur l'intersection 
	 * @param v voiture qui se déplace
	 * @param direction direction de conduite de la voiture
	 * @return la voiture qui se déplace avec ses nouveaux coordonnées
	 */
	//Reiner 
	public Voiture animationVirages(Voiture v, char direction) {
		switch (direction) {
		case'e':
			switch(v.getDirectionDeVirage()){
			case 0:
				//La voiture continue tout droite, car elle n'effectue pas de virage
				if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
					v.setXVoiture((v.getXVoiture()+deplacement));
				}
				/**
				 * CHANGEMENTS
				 */
				//La voiture accelere
				if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
					voitureAccelereHorizontalement(v, direction);
				}
				//Lorsque la voiture ralenti
				if(v.getVoitureRalentit()) {
					voitureDecelereHorizontalement(v, direction);
				}
				break;
			case 1:
				//La voiture tourne à droite
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
					if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud*modele.getPixelsParUniteX()) {
						if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
							v.setXVoiture((v.getXVoiture()+deplacement));
						}
						/**
						 * VOITURE ACCELERE
						 */
						//La voiture accelere
						if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
							voitureAccelereHorizontalement(v, direction);
						}
						if(v.getVoitureRalentit()) {
							voitureDecelereHorizontalement(v, direction);
						}

						v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0-this.LONGUEUR_VOITURE)*this.modele.getPixelsParUniteX()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()-this.DIMENSION_VOIE_REELLE/2.0*modele.getPixelsParUniteX())));
					}


					//La voiture commence sa rotation après avoir dépassé sa lumiere
					if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud*modele.getPixelsParUniteX()){
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
				if(v.getYVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteY()&&v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
				break;
			case 2:
				//La voiture tourne à gauche
				if((!v.getVoitureArretee()||v.getEnRotation() == true)) {
					if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()) {
							if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
								v.setXVoiture((v.getXVoiture()+deplacement));
							}
							//Voiture accelere 
							/**
							 * CHANGMENET
							 */
							if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
								voitureAccelereHorizontalement(v, direction);
							}
							if(v.getVoitureRalentit()) {
								voitureDecelereHorizontalement(v, direction);
							}
							v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud)*this.modele.getPixelsParUniteX()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX())));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud*modele.getPixelsParUniteX()){
							v.setEnRotation(true);
						}
						//La voiture commence graduellement à avancer vers sa nouvelle direction 
						if(v.getEnRotation()) {
							if(v.getDeplacement() < deplacement) {
								v.setDeplacement(v.getDeplacement()+0.050);
							}
							v.setYVoiture(v.getYVoiture()-v.getDeplacement());
						}
					}
					if(v.getYVoiture()<=0&&v.getVoitureActive()) {
						v.setVoitureActive(false);
					}
					break;
				}
			}
			break;
		case'o':

			switch(v.getDirectionDeVirage()){
			case 0:

				//La voiture continue tout droite, car elle n'effectue aucun virage
				if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
					v.setXVoiture((v.getXVoiture()-deplacement));
				}
				//Lorsque la voiture ralenti
				if(v.getVoitureRalentit()) {
					voitureDecelereHorizontalement(v, direction);
				}
				//La voiture accelere
				if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
					voitureAccelereHorizontalement(v, direction);
				}
				break;
			case 1:
				//La voiture tourne à droite
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
					if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*(nbVoiesNord-1))*modele.getPixelsParUniteX()+this.DISTANCE_BORDURE) {
						if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
							v.setXVoiture((v.getXVoiture()+deplacement));
						}
						//Lorsque la voiture ralenti
						if(v.getVoitureRalentit()) {
							voitureDecelereHorizontalement(v, direction);
						}
						//La voiture accelere
						if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
							voitureAccelereHorizontalement(v, direction);
						}
						v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0-this.LONGUEUR_VOITURE)*this.modele.getPixelsParUniteX()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()-this.DIMENSION_VOIE_REELLE/2.0*modele.getPixelsParUniteX())));
					}
					//La voiture commence sa rotation après avoir dépassé sa lumiere
					if(lumOuest.getCouleur()!=ROUGE&&v.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesNord)*modele.getPixelsParUniteX()&&v.getXVoiture()>0){
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
				if(v.getYVoiture()<=0&&v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
				break;
			case 2:
				//La voiture tourne à gauche
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
							if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
								v.setXVoiture((v.getXVoiture()-deplacement));
							}
							//Lorsque la voiture ralenti
							if(v.getVoitureRalentit()) {
								voitureDecelereHorizontalement(v, direction);
							}
							//La voiture accelere
							if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
								voitureAccelereHorizontalement(v, direction);
							}
							v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesNord)*this.modele.getPixelsParUniteX()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX())));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(lumOuest.getCouleur()!=ROUGE&&v.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesNord)*modele.getPixelsParUniteX()&&v.getXVoiture()>0){
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
				}
				if(v.getYVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteY()&&v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
			}
			break;
		case's':

			switch(v.getDirectionDeVirage()){
			//La voiture continue tout droite, car elle n'effectue aucun virage
			case 0:
				if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
					v.setYVoiture((v.getYVoiture()+deplacement));
				}
				//Voiture accelere 
				/**
				 * CHANGMENET
				 */
				if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
					voitureAccelereVerticalement(v, direction);
				}
				if(v.getVoitureRalentit()) {
					voitureDecelereVerticalement(v, direction);
				}

				break;
			case 1:
				//La voiture tourne à droite
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
					if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0*this.nbVoiesOuest)*modele.getPixelsParUniteY()+this.DISTANCE_BORDURE/2.0) {

						if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
							v.setYVoiture((v.getYVoiture()+deplacement));
						}
						//Voiture accelere 
						/**
						 * CHANGMENET
						 */
						if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
							voitureAccelereVerticalement(v, direction);
						}
						if(v.getVoitureRalentit()) {
							voitureDecelereVerticalement(v, direction);
						}

						v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0-this.LONGUEUR_VOITURE)*this.modele.getPixelsParUniteX()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteY()-this.DIMENSION_VOIE_REELLE/2.0*modele.getPixelsParUniteY())));
					}
					//La voiture commence sa rotation après avoir dépassé sa lumiere
					if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*this.nbVoiesOuest-this.LONGUEUR_VOITURE)*modele.getPixelsParUniteY()){
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
				if(v.getXVoiture()<-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
				break;
			case 2:
				//La voiture tourne à gauche
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0-this.LARGEUR_VOITURE)*modele.getPixelsParUniteY()-this.DISTANCE_BORDURE) {

							if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
								v.setYVoiture((v.getYVoiture()+deplacement));
							}

							//Voiture accelere 
							/**
							 * CHANGMENET
							 */
							if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
								voitureAccelereVerticalement(v, direction);
							}
							if(v.getVoitureRalentit()) {
								voitureDecelereVerticalement(v, direction);
							}


							v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesOuest)*this.modele.getPixelsParUniteY()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteY())));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesOuest)*modele.getPixelsParUniteY()){
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
				}
				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
				break;
			}
			break;
		case'n':

			switch(v.getDirectionDeVirage()){
			case 0:
				//La voiture continue tout droite, car elle n'effectue aucun virage
				if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
					v.setYVoiture((v.getYVoiture()-deplacement));
				}
				//Voiture accelere 
				/**
				 * CHANGMENET
				 */
				if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
					voitureAccelereVerticalement(v, direction);
				}
				if(v.getVoitureRalentit()) {
					voitureDecelereVerticalement(v, direction);
				}
				break;
			case 1:
				//La voiture tourne à droite
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
					if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*(nbVoiesEst-1))*modele.getPixelsParUniteY()+this.DISTANCE_BORDURE) {

						if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
							v.setYVoiture((v.getYVoiture()-deplacement));
						}
						//Voiture accelere 
						/**
						 * CHANGMENET
						 */
						if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
							voitureAccelereVerticalement(v, direction);
						}
						if(v.getVoitureRalentit()) {
							voitureDecelereVerticalement(v, direction);
						}


						v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0-this.LONGUEUR_VOITURE)*this.modele.getPixelsParUniteX()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteY()-this.DIMENSION_VOIE_REELLE/2.0*modele.getPixelsParUniteY())));
					}
					//La voiture commence sa rotation après avoir dépassé sa lumiere
					if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesEst)*modele.getPixelsParUniteY()&&v.getYVoiture()>0){
						v.setEnRotation(true);
					}
					//La voiture commence graduellement à avancer vers sa nouvelle direction 
					if(v.getEnRotation()) {
						if(v.getDeplacement() < deplacement) {
							v.setDeplacement(v.getDeplacement()+0.05);
						}
						v.setXVoiture(v.getXVoiture()+v.getDeplacement());
					}
				}
				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
				break;
			case 2:
				//La voiture tourne à gauche
				if(!v.getVoitureArretee()||v.getEnRotation() == true) {
					if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()+this.DISTANCE_BORDURE) {

							if(!v.getVoitureArretee() && !v.getVoitureRalentit()&&(!v.getVoitureAccelere() || v.getAccelTerminee())) {
								v.setYVoiture((v.getYVoiture()-deplacement));
							}

							//Voiture accelere 
							/**
							 * CHANGMENET
							 */
							if(v.getVoitureAccelere() && !v.getAccelTerminee()) {
								voitureAccelereVerticalement(v, direction);
							}
							if(v.getVoitureRalentit()) {
								voitureDecelereVerticalement(v, direction);
							}


							v.setVitesseDeRotation(deplacement, Math.abs((this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesEst)*this.modele.getPixelsParUniteY()-((this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteY())));

						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesEst)*modele.getPixelsParUniteY()&&v.getYVoiture()>0){
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
				}
				if(v.getXVoiture()<-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					v.setVoitureActive(false);
				}
				break;
			}
		}
		return v;
	}
	//Reiner
	/**
	 * Méthode qui permet une voiture de connaitre la position de la voiture opposée à elle pour assurer un virage sans collision
	 * @param direction direction de la voiture
	 * @param v la voiture qui veut tourner à gauche
	 */
	public void verifierVoitureOppose(char direction, Voiture v) {
		switch (direction) {
		case 'e':
			//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
			for(Iterator<Voiture> iOppose = ouest.iterator();iOppose.hasNext();) {
				Voiture vOppose = iOppose.next();
				//entre si la voiture est pret à tourner à gauche
				if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()&&v.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud)*modele.getPixelsParUniteX()&&v.getYVoiture()>(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteY()) {
					//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
					switch(vOppose.getDirectionDeVirage()) {
					case 0:
						if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*(nbVoiesNord+0.5))*modele.getPixelsParUniteX()) {
							ilYAVoitureQuiBloque = true;
						}
						break;
					case 1:
						if(vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesOuest)*modele.getPixelsParUniteY()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0*(nbVoiesNord+0.5))*modele.getPixelsParUniteX()) {
							ilYAVoitureQuiBloque = true;
						}
						break;
					case 2:
						if(nbVoiesSud==1&&nbVoiesNord==1) {
							if(vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*(nbVoiesNord+0.5))*modele.getPixelsParUniteX()) {
								ilYAVoitureQuiBloque = true;
							}
						}
					}
				}
			}
			//Si il y a voiture qui bloque, la voiture ne peut pas tourner à gauche, sinon, elle peut
			if(ilYAVoitureQuiBloque) {
				v.setPeutTournerGauche(false);
			} else {
				v.setPeutTournerGauche(true);
			}
			//On remet le boolean à sa valeur initiale
			this.ilYAVoitureQuiBloque = false;
			break;
		case 'o' :
			//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
			for(Iterator<Voiture> iOppose = est.iterator();iOppose.hasNext();) {
				Voiture vOppose = iOppose.next();
				//entre si la voiture est pret à tourner à gauche
				if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*(nbVoiesNord+0.5))*modele.getPixelsParUniteX()&&v.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud)*modele.getPixelsParUniteX()&&v.getYVoiture()<(this.LARGEUR_REELLE/2.0)*this.modele.getPixelsParUniteY()) {
					//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
					switch(vOppose.getDirectionDeVirage()) {
					case 0:
						if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesNord)*modele.getPixelsParUniteX()) {
							ilYAVoitureQuiBloque = true;
						}
						break;
					case 1:
						if(vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesEst)*modele.getPixelsParUniteY()&&vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0*(nbVoiesNord+0.5))*modele.getPixelsParUniteX()) {
							ilYAVoitureQuiBloque = true;
						}
					}
				}
			}
			//Si il y a voiture qui bloque, la voiture ne peut pas tourner à gauche, sinon, elle peut
			if(ilYAVoitureQuiBloque) {
				v.setPeutTournerGauche(false);
			} else {
				v.setPeutTournerGauche(true);
			}
			//On remet le boolean à sa valeur initiale
			this.ilYAVoitureQuiBloque = false;
			break;
		case 's' :
			//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
			for(Iterator<Voiture> iOppose = nord.iterator();iOppose.hasNext();) {
				Voiture vOppose = iOppose.next();
				//entre si la voiture est pret à tourner à gauche
				if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*(this.nbVoiesOuest+0.5))*modele.getPixelsParUniteY()&&v.getYVoiture()<this.LARGEUR_REELLE/2.0*modele.getPixelsParUniteY()&&v.getXVoiture()<(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()) {
					//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
					switch(vOppose.getDirectionDeVirage()) {
					case 0:
						if(vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE*(this.nbVoiesOuest+0.5))*modele.getPixelsParUniteY()) {
							ilYAVoitureQuiBloque = true;
						}
						break;
					case 1:
						if(vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*this.nbVoiesNord)*modele.getPixelsParUniteX()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0*(this.nbVoiesEst+0.5))*modele.getPixelsParUniteY()) {
							ilYAVoitureQuiBloque = true;
						}
						break;
					case 2:
						if(nbVoiesEst==1&&nbVoiesOuest==1) {
							if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE*(this.nbVoiesEst+0.5))*modele.getPixelsParUniteY()) {
								ilYAVoitureQuiBloque = true;
							}
						}
					}
				}
			}
			//Si il y a voiture qui bloque, la voiture ne peut pas tourner à gauche, sinon, elle peut
			if(ilYAVoitureQuiBloque) {
				v.setPeutTournerGauche(false);
			} else {
				v.setPeutTournerGauche(true);
			}
			//On remet le boolean à sa valeur initiale
			this.ilYAVoitureQuiBloque = false;
			break;
		case 'n':
			//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
			for(Iterator<Voiture> iOppose = sud.iterator();iOppose.hasNext();) {
				Voiture vOppose = iOppose.next();
				//entre si la voiture est pret à tourner à gauche
				if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*(nbVoiesEst+0.5))*modele.getPixelsParUniteY()&&v.getYVoiture()>this.LARGEUR_REELLE/2.0*modele.getPixelsParUniteY()&&v.getXVoiture()>(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()) {
					//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
					switch(vOppose.getDirectionDeVirage()) {
					case 0:
						if(vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0*nbVoiesEst)*modele.getPixelsParUniteY()) {
							ilYAVoitureQuiBloque = true;
						}
						break;
					case 1:
						if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0*nbVoiesSud)*modele.getPixelsParUniteX()&&vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0*(nbVoiesOuest+0.5))*modele.getPixelsParUniteY()) {
							ilYAVoitureQuiBloque = true;
						}
					}
				}
			}
			//Si il y a voiture qui bloque, la voiture ne peut pas tourner à gauche, sinon, elle peut
			if(ilYAVoitureQuiBloque) {
				v.setPeutTournerGauche(false);
			} else {
				v.setPeutTournerGauche(true);
			}
			//On remet le boolean à sa valeur initiale
			this.ilYAVoitureQuiBloque = false;
		}
	}
	//Reiner
	/**
	 * Méthode qui permet d'itentifier la voiture devant 
	 * @param list array liste contenant la voiture qu'on examine
	 * @param v voiture examinée dont on cherhce la voiture qui est devant elle
	 * @param nbVoies nombre de voie de la direction sur laquelle la voiture examinée se retrouve
	 */
	public Voiture verifierVoitureDevant(ArrayList<Voiture> list, Voiture v, int nbVoies) {
		Voiture voitureDevant = list.get(list.indexOf(v)-1);
		int f = 1;
		switch (nbVoies) {
		case 1:
			voitureDevant = list.get(list.indexOf(v)-f);
			break;
		case 2:
			if(v.getDirectionDeVirage()==0||v.getDirectionDeVirage()==2) {
				while(voitureDevant.getDirectionDeVirage()!=0&&voitureDevant.getDirectionDeVirage()!=2&&list.indexOf(v)-f!=0) {
					voitureDevant = list.get(list.indexOf(v)-f);
					f++;
				}
			} else {
				while(voitureDevant.getDirectionDeVirage()!=1&&list.indexOf(v)-f!=0) {
					voitureDevant = list.get(list.indexOf(v)-f);
					f++;
				}
			}
			break;
		case 3:
			switch (v.getDirectionDeVirage()) {
			case 0:
				while(voitureDevant.getDirectionDeVirage()!=0&&list.indexOf(v)-f!=0) {
					voitureDevant = list.get(list.indexOf(v)-f);
					f++;
				}
				break;
			case 1:
				while(voitureDevant.getDirectionDeVirage()!=1&&list.indexOf(v)-f!=0) {
					voitureDevant = list.get(list.indexOf(v)-f);
					f++;
				}
				break;
			case 2:
				while(voitureDevant.getDirectionDeVirage()!=2&&list.indexOf(v)-f!=0) {
					voitureDevant = list.get(list.indexOf(v)-f);
					f++;
				}
			}
		}
		return voitureDevant;
	}
	//Mamadou
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
	//Mamadou
	/**
	 * Méthode qui ajoute une nouvelle voiture dans l'intersection
	 */
	public void ajouterNouvelleVoiture() {
		//On ajoute au nombre de voitures generees
		nbVoituresGenerees++;
		Voiture voiture = new Voiture(modele.getPixelsParUniteX() * LONGUEUR_VOITURE, modele.getPixelsParUniteY() * LARGEUR_VOITURE, modele.getLargPixels(), DIMENSION_VOIE_REELLE,DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX(), trafficAnormale, typeImages,false );
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
		normalisationGaussienneTauxApparition();
	}
	//Mamadou
	/**
	 * Cette methode permet de changer la valeur du taux d'apparition de voitures
	 * selon la loi de Gauss
	 */
	private void normalisationGaussienneTauxApparition() {
		double tauxGauss; //Une valeur initiale quelconque < 0
		do{
			// Un objet Random
			Random ran = new Random(); 
			// ON genere un double selon la distribution gaussienne
			tauxGauss = ran.nextGaussian(); 
			tauxGauss = tauxGauss*DEVIATION_GAUSSIENNE+ tauxDApparitionMoyen;
		}while(tauxGauss<=TAUX_GAUSS_MIN || tauxGauss >TAUX_GAUSS_MAX); // On ne veut pas un taux négatif, contrainte car fonction Gaussienne n'a pas de limite théorique
		//On change le taux d'apparition et ainsi le nombre de boucles pour chaque apparition
		this.calculerTauxDApparition(tauxGauss*60); // La methode prend le taux d'apparition par minute
	}
	//Reiner 
	/**
	 * Méthode pour convertir le taux de voitures/secondes à nbBoucles/voitures
	 * @param tauxParMinute le taux d'apparition en voitures/minutes
	 */
	private void calculerTauxDApparition(double tauxParMinute) {
		double tauxParSeconde = tauxParMinute/60.0;
		double periodeApparition = 1.0/tauxParSeconde * this.UNE_SECONDE_EN_MILLISECONDE; //On passe de la fréquence d'apparition au temps (période)
		this.nbBouclesAvantNouvelleVoiture = (int)(periodeApparition/tempsDuSleep); //On calcule le nombre de boucle avant une nouvelle voiture avecle tempsDuSleep
	}
	//Mamadou
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
	//Mamadou
	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
	}
	//Mamadou
	/**
	 * Arrête l'animation et reinitialise tout comme au début
	 */
	public void reinitialiser() {
		arreter();
		voitures.removeAll(est);
		voitures.removeAll(ouest);
		voitures.removeAll(nord);
		voitures.removeAll(sud);
		this.nbVoituresGenerees = 0;
		lumNord.setCouleur(couleur);
		lumSud.setCouleur(couleur);
		lumEst.setCouleur(couleurInv);
		lumOuest.setCouleur(couleurInv);
		nbVoituresEnAttente.clear();
		moyenneDesVitesse.clear();
		tempsDArretMoyen.clear();
		densiteVoitures.clear();
		repaint();
	}
	//Mamadou
	/**
	 * Getter vrai si en cours d'animation et faux sinon
	 * @return enCoursDAnimation boul qui spécifie si on est en cours d'animation
	 */
	public boolean getEnCoursDAnimation() {
		return enCoursDAnimation;
	}
	//Mamdou
	/**
	 * Change le temps pour le sleep du thread
	 * @param tempsDuSleep Nouveua temps a appliquer au sleep
	 */
	public void setTempsDuSleep(int tempsDuSleep) {
		this.tempsDuSleep = tempsDuSleep;
	}
	//Mamadou
	/**
	 * Retourne le temps de sleep actuel
	 * @return temps du sleep actuel
	 */
	public int getTempsDuSleep() {
		return (int) tempsDuSleep;
	}
	//Mamadou
	/**
	 * Modifie le pas (intervalle) de la simulation
	 * @param deltaT le pas (intervalle) de la simulation, exprime en secondes
	 */
	public void setDeltaT(double deltaT) {
		this.deltaT = deltaT;

	}
	//Mamadou
	/**
	 * Retourne le pas intervalle) de la simulation
	 * @return le pas intervalle) de la simulation, exprime en secondes
	 */
	public double getDeltaT() {
		return (deltaT);
	}
	//Mamadou
	/**
	 * Modifie le nombre de boucles nécessaires avant de créer une voiture
	 * @param taux Le taux d'apparition des voitures en voitures/secondes.
	 */
	public void setTauxDApparition(double tauxParMinute) {
		double tauxParSeconde = tauxParMinute/60.0;
		this.tauxDApparitionMoyen = tauxParSeconde;
	}
	//Mamadou
	/**
	 * Getter de la largeur reelle
	 * @return LARGEUR_REELLE retourne la constante de la largeur reelle
	 */
	public double getLARGEUR_REELLE() {
		return LARGEUR_REELLE;
	}
	//Reiner
	/**
	 * Méthode qui change la couleur des lumières en faisant avancer le cycle
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
	//Mamadou
	/**
	 * Setter qui change le nombre de voies horizontalemnt
	 * @param nbVoiesHorizontale le nombre de voie a l'horizontale
	 */
	public void setNbVoiesHorizontale(int nbVoiesHorizontale) {
		this.nbVoiesHorizontale= nbVoiesHorizontale; 
	}
	//Reiner
	/**
	 * Setter qui modifie la paramètre de vitesse de la scène
	 * @param vitesse
	 */
	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}
	//Reiner
	/**
	 * Methode qui permet de calculer le deplacement avec la vitesse donnee
	 */
	public void calculerVitesse() {
		this.deplacement = vitesse*modele.getPixelsParUniteX()/(this.UNE_SECONDE_EN_MILLISECONDE/this.tempsDuSleep);
	}
	//Reiner
	/**
	 * Méthode qui rend le traffic d'une voie normale
	 * @param numDeVoie int indiquant la voie qu'on veut remettre à normal
	 */
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
	//Reiner
	/**
	 * Méthode qui permet d'ajouter du traffic anormal a une voie
	 * @param numDeVoie int indiquant la voie qui aura  du traffic anormal
	 */
	public void addTrafficNormale(int numDeVoie) {
		if(this.enTrafficAnormale&&numDeVoie>0&&this.trafficAnormale.length>1) {
			int[] tabTemporaire = this.trafficAnormale;
			this.trafficAnormale = new int[this.trafficAnormale.length-1];
			for(int i = 0;i<this.trafficAnormale.length;i++) {
				if(tabTemporaire[i]!=numDeVoie) {
					this.trafficAnormale[i] = tabTemporaire[i];
				}
			}
		}
	}
	//Reiner
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
	//Mamadou
	/**
	 * Methode qui retourne les voitures qui ont deja ete generees
	 * @return le nombre de voitures deja generees
	 */
	public int getNbVoituresGenerees() {
		return nbVoituresGenerees;
	}
	//Mamadou
	/**
	 * Methode qui permet de specifier le nombre de voitures generees
	 * @param nbVoituresGenerees le nombre de voitures generees
	 */
	public void setNbVoituresGenerees(int nbVoituresGenerees) {
		this.nbVoituresGenerees = nbVoituresGenerees;
	}
	//Mamadou
	/**
	 * Methode qui permet de get le nombre de voitures max a generer pendant 
	 * la simulation
	 * @return le nombre de voitures max a generer
	 */
	public int getNbVoituresMax() {
		return nbVoituresMax;
	}
	//Mamadou
	/**
	 * Methode qui permet de specifier le nombre de voitures max a generer pendant
	 * @param nbVoituresMax le nombre de voitures max quon desire generer
	 */
	public void setNbVoituresMax(int nbVoituresMax) {
		this.nbVoituresMax = nbVoituresMax;
	}
	//Reiner
	/**
	 * Methode qui fait avancer l'animaiton d'un pas
	 */
	public void prochainImage() {
		if (!enCoursDAnimation) { 
			Thread proc = new Thread(this);
			proc.start();
			veutProchainImage = true;
			enCoursDAnimation = true;
		}
	}
	/**
	 * Methode qui set le type d'images a generer
	 * @param typeImages le type d'images
	 */
	//Mamadou
	public void setTypeImages(int typeImages) {
		this.typeImages  = typeImages;
	}
	//Reiner 
	/**
	 * Méthode qui calcule le temps d'arrêt moyen de tous les voitures générées
	 * @return tempsDArretMoyen temps d'arrêt moyen de tous les voitures générées en secondes
	 */
	public double calculeTempsDArretMoyen() {
		double tempsDArretTotale = 0;
		double tempsDArretMoyen;
		for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
			Voiture v = i.next();
			tempsDArretTotale += v.getTempsDArret();
		}
		tempsDArretMoyen = tempsDArretTotale/(double)nbVoituresGenerees;
		return tempsDArretMoyen;
	}
	//Reiner
	/**
	 * Méthode qui calcule le temps d'arrêt moyen d'un certain groupe de voitures spécifié
	 * @param list liste contenant le groupe de voiture spécifié
	 * @return temps d'arrêt moyen d'un certain groupe de voitures spécifié
	 */
	public double calculerTempsDArretMoyen(ArrayList<Voiture> list) {
		double tempsDArretTotale = 0;
		double tempsDArretMoyen;
		for(Iterator<Voiture> i = list.iterator();i.hasNext();) {
			Voiture v = i.next();
			//différence de l'autre méthode, on ignore les voitures qui ne sont pas présentes dans l'intersection
			if(v.getVoitureActive()) {
				tempsDArretTotale += v.getTempsDArret();
			}
		}
		tempsDArretMoyen = tempsDArretTotale/(double)nbVoituresGenerees;
		return tempsDArretMoyen;
	}
	//Reiner
	/**
	 * Méthode qui calcule la densité de voitures sur une certaine longeur de route
	 * @param list Collection de voitures 
	 * @param longueurDeRoute Longeur de la route où les voitures se trouvent en metres
	 * @return densité de voitures sur une longeur donnée en voiture/kilometre
	 */
	public double calculeDensite(ArrayList<Voiture> list, double longeurDeRoute) {
		double nbVoituresTotales = 0;
		for(Iterator<Voiture> i = list.iterator();i.hasNext();) {
			Voiture v = i.next();
			//on vérifie si la voiture est encore sur l'intersection
			if(v.getVoitureActive()) {
				//si oui, on l'ajoute au nombre de voitures totales
				nbVoituresTotales++;
			}
		}
		return nbVoituresTotales/(longeurDeRoute/1000.0);//on converti les metres en kilometres pour avoir une densité en voitures par kilometre

	}
	//Reiner 
	/**
	 * Getter qui retourne la densité de voitures sur toute l'intersection
	 * @return La densité de voitures sur toute l'intersection en voitures/kilometre;
	 */
	public double getDensiteTotale() {
		return calculeDensite(this.voitures,this.LARGEUR_REELLE*4);//On calcule la densité avec toutes les voitures générés et la longeur totale des voies horizontales et verticales 
	}
	//Reiner 
	/**Getter qui retourne le nombre de voies de direction est
	 * @return nbVoiesEst int le nombre de voies de direction est
	 */
	public int getNbVoiesEst() {
		return nbVoiesEst;
	}
	//Reiner 
	/**Setter qui permet de déterminer le nombre de voies de direction est
	 * @param nbVoiesEst int le nombre de voies de direction est
	 */
	public void setNbVoiesEst(int nbVoiesEst) {
		this.nbVoiesEst = nbVoiesEst;
		repaint();
	}
	//Reiner 
	/**Getter qui retourne le nombre de voies de direction ouest
	 * @return the nbVoiesOuest int le nombre de voies de direction ouest
	 */
	public int getNbVoiesOuest() {
		return nbVoiesOuest;
	}
	//Reiner 
	/**Setter qui permet de déterminer le nombre de voies de direction ouest
	 * @param nbVoiesOuest int le nombre de voies de direction ouest
	 */
	public void setNbVoiesOuest(int nbVoiesOuest) {
		this.nbVoiesOuest = nbVoiesOuest;
		repaint();
	}
	//Reiner 
	/**Getter qui retourne le nombre de voies de direction sud
	 * @return the nbVoiesSud le nombre de voies de direction sud
	 */
	public int getNbVoiesSud() {
		return nbVoiesSud;
	}
	//Reiner 
	/**Setter qui permet de déterminer le nombre de voies de direction sud
	 * @param nbVoiesSud le nombre de voies de direction sud
	 */
	public void setNbVoiesSud(int nbVoiesSud) {
		this.nbVoiesSud = nbVoiesSud;
		repaint();
	}
	//Reiner 
	/**Getter qui retourne le nombre de voies de direction nord
	 * @return the nbVoiesNord le nombre de voies de direction nord
	 */
	public int getNbVoiesNord() {
		return nbVoiesNord;
	}
	//Reiner 
	/**Setter qui permet de déterminer le nombre de voies de direction nord
	 * @param nbVoiesNord le nombre de voies de direction nord
	 */
	public void setNbVoiesNord(int nbVoiesNord) {
		this.nbVoiesNord = nbVoiesNord;
		repaint();
	}
	//Mamadou
	/**
	 * Retourne le nombre de voitures generees
	 */
	public int getNombreVoituresGeneree() {
		return(this.nbVoituresGenerees);
	}
	//Reiner
	/**
	 * Retoune le temps d'arrêt moyen de toutes les voitures sur l'intersection
	 * @return le temps d'arrêt moyen de toutes les voitures sur l'intersection
	 */
	public double getTempsDArretMoyen() {
		return this.calculeTempsDArretMoyen();
	}
}
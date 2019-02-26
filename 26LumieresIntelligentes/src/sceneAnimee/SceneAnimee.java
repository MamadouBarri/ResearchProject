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

import javax.print.attribute.standard.NumberOfInterveningJobs;
import javax.swing.JPanel;

import geometrie.Direction;
import geometrie.Intersection;
import geometrie.Lumiere;
import geometrie.Voiture;
import interfaces.LumiereListener;
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

	private double deplacement = 30; //km/h pour convertir en m/s diviser par 3.6
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
	//Constante de conversion
	private final double CONVERSION_KMH_MS = 3.6;
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
	//Voitures
	private int nbBouclesAvantNouvelleVoiture = 100;
	private int nbVoituresGenerees =0;
	private int nbVoituresMax = 100;
	//Lumieres 
	private double nbBouclesAvantLumiereJaune = 2400;
	

	private double nbBouclesAvantLumiereVerte = 2700;
	private double nbBouclesAvantLumiereRouge = 5100;
	private final double UNE_SECONDE_EN_MILLISECONDE = 1000;
	private final double DISTANCE_BORDURE = 5; ///En pixels pour le drawString 
	private  final double TAUX_DECELERATION = 0.1;
	//Les couleurs des lumieres sont determines par des valeurs int : 0=vert; 1=jaune; 2=rouge
	//couleur des lumieres pour les voies nord et sud
	private int couleur=0;
	//couleur des lumieres pour les voies est et ouest
	private int couleurInv=2; 
	Lumiere lum1,lum2,lum3,lum4;
	private boolean voituresEnArret;
	private int nbVoiesHorizontale = 1;


	private int[] trafficAnormale = new int[1];
	private int[] trafficAnormaleTemp= new int[1];
	private boolean enTrafficAnormale;
	private boolean premiereFois = true;
	
	private double vitesse;



	/**
	 * Constructeur de la scène d'animation qui met le background en gris
	 */
	public SceneAnimee() {
		setBackground(Color.gray);
		trafficAnormale = new int[1];
		//On cree le bloc et ressort
		//this.setPreferredSize(preferredSize);
		//setPrefferedBounds(1110,406);
	}

	/**
	 * Écouteur de souris qui sera utilisé plus tard
	 */
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
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				//debut

				//fin
			}

		});	
	}

	/**
	 * Dessine le systeme bloc-ressort avec axe et le vecteur graphique de la force de rappel
	 * @param g Le conexte graphique
	 */
	public void paintComponent(Graphics g) {		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		modele = new ModeleAffichage(getWidth(), getHeight(), LARGEUR_REELLE);
		AffineTransform mat = modele.getMatMC();
		//Convertir la vitesse en pixels
		if(premiereFois) {
			//deplacement /=CONVERSION_KMH_MS;
			//deplacement /= UNE_SECONDE_EN_MILLISECONDE ; //metre/miliseconde
			//premiereFois= false;
			calculerVitesse(); //A changer
		}
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//On passe les dimensions du JPanel a l'intersection
		inter = new Intersection(this.LARGEUR_REELLE);
		inter.setNbVoiesHorizontale(nbVoiesHorizontale);
		inter.dessiner(g2d,mat);

		lum1 = new Lumiere(0,0,75,couleur,4);
		lum1.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0-lum1.getLongueur()-5,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0-lum1.getLargeur()-5);
		lum1.dessiner(g2d, mat);

		lum2 = new Lumiere(0,0,75,couleur,1);
		lum2.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0+5,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0+5);
		lum2.dessiner(g2d, mat);

		lum3 = new Lumiere(0,0,75,couleurInv,2);
		lum3.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0+lum3.getLargeur()/2.0-lum3.getLongueur()/2.0+this.DISTANCE_BORDURE, this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0-lum3.getLongueur()/2.0-lum3.getLargeur()/2.0-5);
		lum3.dessiner(g2d, mat);	

		lum4 = new Lumiere(0,0,75,couleurInv,3);
		lum4.setPosition(this.LARGEUR_REELLE*modele.getPixelsParUniteX()/2.0-this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteX()/2.0-lum4.getLongueur()/2.0-lum4.getLargeur()/2.0-this.DISTANCE_BORDURE,this.LARGEUR_REELLE*modele.getPixelsParUniteY()/2.0+this.DIMENSION_VOIE_REELLE*modele.getPixelsParUniteY()/2.0-lum4.getLargeur()/2.0+lum4.getLongueur()/2.0+this.DISTANCE_BORDURE);
		lum4.dessiner(g2d, mat);
		


		//Dessiner l'échelle
		g2d.setColor(Color.cyan);
		g2d.drawString("Échelle: " + LARGEUR_REELLE + " m", (float)DISTANCE_BORDURE, (float)(LARGEUR_REELLE * modele.getPixelsParUniteY() - DISTANCE_BORDURE));


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
	 * Animation de la balle
	 */
	@Override
	public void run() {
		double nbRepetitionsPourVoitures = 0;
		double nbRepetitionsPourLumieres = 0;
		while (enCoursDAnimation) {	
			if(voituresEnArret && deplacement>=0) {
				deplacement = deplacement-TAUX_DECELERATION;
				if(deplacement<0) {
					deplacement =0;
				}
			}
			//Commencer le thread de voiture pour chaque voiture de la liste
			//DIRECTION : EST
			for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
				Voiture v = i.next();
				//Essaie pour voir si l'animation marche avec un seul thread
				//v.demarrer();
				v.setXVoiture((int)(v.getXVoiture()+deplacement));
				
				//Lorsque la voiture doit s'arreter
				
				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}
			}//
			//DIRECTION : 
			for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
				Voiture v = i.next();
				//Essaie pour voir si l'animation marche avec un seul thread
				//v.demarrer();
				v.setYVoiture((int)(v.getYVoiture()+deplacement));
				if(v.getYVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteY() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}
			}
			for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
				Voiture v = i.next();
				//Essaie pour voir si l'animation marche avec un seul thread
				//v.demarrer();
				v.setXVoiture((int)(v.getXVoiture()-deplacement));
				if(v.getXVoiture()<-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
				}
			}
			for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
				Voiture v = i.next();
				v.setYVoiture((int)(v.getYVoiture()-deplacement));
				System.out.println(deplacement);
				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					voitures.remove(v);
					v.setVoitureActive(false);
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
				if(nbRepetitionsPourLumieres == nbBouclesAvantLumiereJaune) {
					changeCouleurLumieres();
					repaint();
				}
				if(nbRepetitionsPourLumieres == nbBouclesAvantLumiereVerte) {
					changeCouleurLumieres();
					repaint();
				}
				if(nbRepetitionsPourLumieres == nbBouclesAvantLumiereRouge) {
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
	}//fin methode

	/**
	 * Demande l'arret du thread (prochain tour de boucle)
	 */
	public void arreter() {
		enCoursDAnimation = false;
		for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
			Voiture v = i.next();
			//v.arreter();
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
			lum1.setCouleur(couleur);
			lum2.setCouleur(couleur);
		} else {
			if(couleur==1) {
				couleur = (couleur+1)%3;
				couleurInv = (couleurInv+1)%3;
				lum1.setCouleur(couleur);
				lum2.setCouleur(couleur);
				lum3.setCouleur(couleurInv);
				lum4.setCouleur(couleurInv);
			} else {
				if(couleur==2&&couleurInv==0) {
					couleurInv = (couleurInv+1)%3;
					lum3.setCouleur(couleurInv);
					lum4.setCouleur(couleurInv);
				} else {
					couleur = (couleur+1)%3;
					couleurInv = (couleurInv+1)%3;
					lum1.setCouleur(couleur);
					lum2.setCouleur(couleur);
					lum3.setCouleur(couleurInv);
					lum4.setCouleur(couleurInv);
				}
			}
		}
		/*lum1.setCouleur(couleur);
	lum2.setCouleur(couleur);
	lum3.setCouleur(couleurInv);
	lum4.setCouleur(couleurInv);
	couleur = (couleur+1)%3;
	couleurInv = (couleurInv+1)%3;*/
	}
	/**
	 * La méthode qui set l'arret des voitures a vrai
	 */
	public void arreterVoitures() {
		voituresEnArret = true;
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
	public int getNbVoituresGenerees() {
		return nbVoituresGenerees;
	}

	public void setNbVoituresGenerees(int nbVoituresGenerees) {
		this.nbVoituresGenerees = nbVoituresGenerees;
	}
	public int getNbVoituresMax() {
		return nbVoituresMax;
	}

	public void setNbVoituresMax(int nbVoituresMax) {
		this.nbVoituresMax = nbVoituresMax;
	}
}

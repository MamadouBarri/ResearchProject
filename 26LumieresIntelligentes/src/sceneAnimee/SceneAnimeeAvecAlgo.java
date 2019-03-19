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
 * @author Mamadou & Reiner
 */
public class SceneAnimeeAvecAlgo extends JPanel implements Runnable{
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
	private int[] trafficAnormale = new int[1];
	private int[] trafficAnormaleTemp= new int[1];
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
	//Pour les statistiques
	public static ArrayList<Integer> nbVoituresEnAttente = new ArrayList<Integer>();
	//Mamadou
	/**
	 * Constructeur de la scène d'animation qui met le background en gris
	 */
	public SceneAnimeeAvecAlgo() {
		this.vitesse = 20;
		setBackground(Color.gray);
		trafficAnormale = new int[1];
	}
	//Mamadou et Reiner
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
	//Mamadou et Reiner
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
		int densiteHorizontale = 0;
		int densiteVerticale = 0;
		while (enCoursDAnimation) {	
			//Commencer le thread de voiture pour chaque voiture de la liste

			//Convention des lumieres : 0 - > vert 1 | - > jaune | 2 -> rouge

			//DIRECTION : EST
			for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
				Voiture v = i.next();//pour chaque voiture qui s'en vers l'EST
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
				//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
				for(Iterator<Voiture> iOppose = ouest.iterator();iOppose.hasNext();) {
					Voiture vOppose = iOppose.next();
					//entre si la voiture est pret à tourner à gauche
					if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()&&v.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()&&v.getYVoiture()>(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteY()) {
						//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
						switch(vOppose.getDirectionDeVirage()) {
						case 0:
							if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) {
								ilYAVoitureQuiBloque = true;
							}
							break;
						case 1:
							if(vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) {
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
				//fin de la verification
				switch(v.getDirectionDeVirage()){
				case 0:
					//La voiture continue tout droite, car elle n'effectue pas de virage
					if(!v.getVoitureArretee()) {
						v.setXVoiture((v.getXVoiture()+deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
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
					if((!v.getVoitureArretee()||v.getEnRotation() == true)) {
						if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()+1) < DISTANCE_LIGNE_ARRET){
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
					}
				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumEst.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
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

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumEst.getCouleur() == VERTE||lumEst.getCouleur() == JAUNE) {
					v.setVoitureArretee(false);
				}
				//Voiture devant trop proche
				if(est.indexOf(v)!=0) {
					Voiture voitureDevant = est.get(est.indexOf(v)-1);
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&v.getYVoiture()==voitureDevant.getYVoiture()) {
						v.setVoitureArretee(true);
					}
				}
			}//fin DIRECTION EST


			//DIRECTION : SUD
			for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
				Voiture v = i.next();
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
				//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
				for(Iterator<Voiture> iOppose = nord.iterator();iOppose.hasNext();) {
					Voiture vOppose = iOppose.next();
					//entre si la voiture est pret à tourner à gauche
					if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()&&v.getYVoiture()<this.LARGEUR_REELLE/2.0*modele.getPixelsParUniteY()) {
						//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
						switch(vOppose.getDirectionDeVirage()) {
						case 0:
							if(vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()) {
								ilYAVoitureQuiBloque = true;
							}
							break;
						case 1:
							if(vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()) {
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
				//fin de la verification
				if(v.getYVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteY() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture en mouvement
				switch(v.getDirectionDeVirage()){
				//La voiture continue tout droite, car elle n'effectue aucun virage
				case 0:
					if(!v.getVoitureArretee()) {
						v.setYVoiture((v.getYVoiture()+deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0+DISTANCE_BORDURE/4.0)*modele.getPixelsParUniteY()) {
							v.setYVoiture((v.getYVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
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
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
						if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+DISTANCE_BORDURE/4.0)*modele.getPixelsParUniteY()) {
							v.setYVoiture((v.getYVoiture()+deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
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
					break;
				}
				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 - DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumSud.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumSud.getCouleur() == VERTE||lumSud.getCouleur() == JAUNE) {
					v.setVoitureArretee(false);
				}
				//Voiture devant trop proche
				if(sud.indexOf(v)!=0) {
					Voiture voitureDevant = sud.get(sud.indexOf(v)-1);
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&v.getXVoiture()==voitureDevant.getXVoiture()) {
						v.setVoitureArretee(true);
					}
				}
			}

			//DIRECTION : OUEST
			for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
				Voiture v = i.next();
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
				//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
				for(Iterator<Voiture> iOppose = est.iterator();iOppose.hasNext();) {
					Voiture vOppose = iOppose.next();
					//entre si la voiture est pret à tourner à gauche
					if(v.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()&&v.getXVoiture()>(this.LARGEUR_REELLE/2.0)*modele.getPixelsParUniteX()) {
						//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
						switch(vOppose.getDirectionDeVirage()) {
						case 0:
							if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()&&vOppose.getXVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
								ilYAVoitureQuiBloque = true;
							}
							break;
						case 1:
							if(vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()&&vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteX()) {
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
				//fin de la verification
				if(v.getXVoiture()<-this.LONGUEUR_VOITURE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}
				//Voiture en mouvement

				switch(v.getDirectionDeVirage()){
				case 0:
					//La voiture continue tout droite, car elle n'effectue aucun virage
					if(!v.getVoitureArretee()) {
						v.setXVoiture((v.getXVoiture()-deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0+this.LARGEUR_VOITURE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX())+1 < DISTANCE_LIGNE_ARRET){
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
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
						if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getXVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
							v.setXVoiture((v.getXVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX())+1 < DISTANCE_LIGNE_ARRET){
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
				}

				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getXVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumOuest.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumOuest.getCouleur() == VERTE||lumOuest.getCouleur() == JAUNE) {
					v.setVoitureArretee(false);
				}
				//Voiture devant trop proche
				if(ouest.indexOf(v)!=0) {
					Voiture voitureDevant = ouest.get(ouest.indexOf(v)-1);
					if(Math.abs((v.getXVoiture() - voitureDevant.getXVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&v.getYVoiture()==voitureDevant.getYVoiture()) {
						v.setVoitureArretee(true);
					}
				}
			}

			//DIRECTION : NORD
			for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
				Voiture v = i.next();
				//On vérifie seulement si la voiture tourne à gauche
				if(v.getDirectionDeVirage()==2) {
				//On vérifie si il y a une voiture qui bloque le chemin de la voiture qui veut tourner à gauche
				for(Iterator<Voiture> iOppose = sud.iterator();iOppose.hasNext();) {
					Voiture vOppose = iOppose.next();
					//entre si la voiture est pret à tourner à gauche
					if(v.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()&&v.getYVoiture()>this.LARGEUR_REELLE/2.0*modele.getPixelsParUniteY()) {
						//Conditions différents dépendant si la voiture qui bloque le chemin va tout droit ou tourne à droite
						switch(vOppose.getDirectionDeVirage()) {
						case 0:
							if(vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()&&vOppose.getYVoiture()<(this.LARGEUR_REELLE/2.0+this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()) {
								ilYAVoitureQuiBloque = true;
							}
							break;
						case 1:
							if(vOppose.getXVoiture()>(this.LARGEUR_REELLE/2.0-this.DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()&&vOppose.getYVoiture()>(this.LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE)*modele.getPixelsParUniteY()) {
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
				//fin de la verification
				if(v.getXVoiture()>this.LARGEUR_REELLE*modele.getPixelsParUniteX() && v.getVoitureActive()) {
					//v.arreter();
					affichageAvecTemps("voiture enlevée");
					//voitures.remove(v);
					v.setVoitureActive(false);
				}

				//Voiture en mouvement

				switch(v.getDirectionDeVirage()){
				case 0:
					//La voiture continue tout droite, car elle n'effectue aucun virage
					if(!v.getVoitureArretee()) {
						v.setYVoiture((v.getYVoiture()-deplacement));
					}
					break;
				case 1:
					//La voiture tourne à droite
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
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
					if(!v.getVoitureArretee()||v.getEnRotation() == true) {
						if(v.getPeutTournerGauche()) {
						//La voiture continue à aller tout droit jusqu'au point où elle finit tourner
						if(v.getYVoiture()>(this.LARGEUR_REELLE/2.0-DISTANCE_BORDURE/2.0)*modele.getPixelsParUniteY()+LARGEUR_VOITURE/2.0) {
							v.setYVoiture((v.getYVoiture()-deplacement));
						}
						//La voiture commence sa rotation après avoir dépassé sa lumiere
						if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE/3.0)*modele.getPixelsParUniteY()-1) < DISTANCE_LIGNE_ARRET){
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
					break;
				}

				//Lumiere est rouge 
				//Lorsque la voiture doit s'arreter (lumiere est rouge ou voiture devant est trop proche)
				if(Math.abs(v.getYVoiture() - (this.LARGEUR_REELLE/2.0 + DIMENSION_VOIE_REELLE - LARGEUR_VOITURE)*modele.getPixelsParUniteX()) < DISTANCE_LIGNE_ARRET && lumNord.getCouleur() == ROUGE) { // Lorsque voiture est devant l'intersection
					v.setVoitureArretee(true);
				}

				//Lorsque la lumiere redevient verte ou est jaune
				if(lumNord.getCouleur() == VERTE||lumNord.getCouleur() == JAUNE) {
					v.setVoitureArretee(false);
				}

				//Voiture devant trop proche
				if(nord.indexOf(v)!=0) {
					Voiture voitureDevant = nord.get(nord.indexOf(v)-1);
					if(Math.abs((v.getYVoiture() - voitureDevant.getYVoiture())) < LARGEUR_VOITURE*2.0 * modele.getPixelsParUniteX() + DISTANCE_BORDURE&&v.getXVoiture()==voitureDevant.getXVoiture()) {
						v.setVoitureArretee(true);
					}
				}

			}
			repaint();
			try {
				Thread.sleep(tempsDuSleep);
				nbRepetitionsPourVoitures++;
				nbRepetitionsStats ++;
				//Lorsque le thread a sleep 10 fois (intervale 10 x tempsSleep)
				if(nbRepetitionsPourVoitures == nbBouclesAvantNouvelleVoiture && nbVoituresGenerees < nbVoituresMax ) {
					ajouterNouvelleVoiture();
					nbRepetitionsPourVoitures=0;
				}
				//Lorsque une seconde passe, donc on prend une nouvelle valeur pour les statistiques
				if(nbRepetitionsStats  == nbRepetitionsMaxStats ) {
					//On calcule les voitures en attente
					int voituresEnAttenteTotal = 0;
					for(Iterator<Voiture> i = voitures.iterator();i.hasNext();) {
						Voiture v = i.next();
						if(v.getVoitureArretee()) {
							voituresEnAttenteTotal++;
						}
					}						//On ajoute la valeur dans la liste
					System.out.println("voitures en attente : " + voituresEnAttenteTotal);
					nbVoituresEnAttente.add(voituresEnAttenteTotal);
					nbRepetitionsStats=0; //On remet le compteur a 0
				}
				//on vérifie la densité des voies à chaque 5 secondes
				if(nbRepetitionsPourLumieres>=500) {
					//on remet les densités à zéro quand on décide de recalculer la densité
					densiteHorizontale = 0;
					densiteVerticale = 0;
					//On vérifie les voitures ayant est comme direction
					for(Iterator<Voiture> i = est.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getXVoiture()<(LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
							//si oui, on l'ajoute à la densité de voitures des voies horizontales
							densiteHorizontale++;
						}
					}
					//On vérifie les voitures ayant ouest comme direction
					for(Iterator<Voiture> i = ouest.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getXVoiture()>(LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteX()) {
							//si oui, on l'ajoute à la densité de voitures des voies horizontales
							densiteHorizontale++;
						}
					}
					//On vérifie les voitures ayant nord comme direction
					for(Iterator<Voiture> i = nord.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getYVoiture()>(LARGEUR_REELLE/2.0+DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()) {
							//si oui, on l'ajoute à la densité de voitures des voies verticales
							densiteVerticale++;
						}
					//On vérifie les voitures ayant sud comme direction
					}for(Iterator<Voiture> i = sud.iterator();i.hasNext();) {
						Voiture v = i.next();
						//on vérifie si la voiture n'a pas encore dépassé la lumière
						if(v.getYVoiture()<(LARGEUR_REELLE/2.0-DIMENSION_VOIE_REELLE/2.0)*modele.getPixelsParUniteY()) {
							//si oui, on l'ajoute à la densité de voitures des voies verticales
							densiteVerticale++;
						}
					}
					nbRepetitionsPourLumieres=0;
					System.out.println("nbRep = " + nbRepetitionsPourLumieres);
					System.out.println("DensHor = " + densiteHorizontale);
					System.out.println("DensVer = " + densiteVerticale);
				} else {
					nbRepetitionsPourLumieres++;
				}
				//on vérifie si les voies avec les densités supérieurs ont la lumière verte
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
		System.out.println("Le thread est mort...");
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
		Voiture voiture = new Voiture(modele.getPixelsParUniteX() * LONGUEUR_VOITURE, modele.getPixelsParUniteY() * LARGEUR_VOITURE, modele.getLargPixels(), DIMENSION_VOIE_REELLE, trafficAnormale, typeImages );
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
	//Reiner
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
}

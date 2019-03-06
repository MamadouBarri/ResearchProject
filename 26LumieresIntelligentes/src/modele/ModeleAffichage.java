package modele;



import java.awt.geom.AffineTransform;

/**
 * Un objet ModeleAffichage permet de mémoriser un ensemble de valeurs pour passer du monde réel vers un composant de dessin dont les 
 * coordonnées sont en pixels. On peut interroger l'objet pour connaitre la matrice de transformation, le nombre de pixels par unité, 
 * les dimensions dans le monde réel, etc.
 * 
 * @author Caroline Houle et Mamadou Barri
 */
 

public class ModeleAffichage {
	private double hautUnitesReelles;
	private double largUnitesReelles = 3;
	private double largPixels;
	private double hautPixels;
	private double xOrigUnitesReelle;
	private double yOrigUnitesReelle;
	private double pixelsParUniteX;
	private double pixelsParUniteY;
	private AffineTransform matMC;
	

	//Mamadou
	/**
	 * Permet de créer un objet ModelAffichage, pouvant mémoriser la matrice (et autres valeurs) de transformation pour passer du monde vers le composant. les dimensions du monde 
	 * réel sont passées en paramètre (largeur et hauteur). Si le rapport entre les dimensions en pixels n'est pas identique au rapport entre les dimensions réelles, il y aura distortion.
	 * 
	 * @param largPixels La largeur du composant, en pixels
	 * @param hautPixels La hauteur du composant, en pixels
	 * @param xOrigUnitesReelle L'origine en x de la portion du monde réel que l'on veut montrer 
	 * @param yOrigUnitesReelle L'origine en y de la portion du monde réel que l'on veut montrer 
	 * @param largUnitesReelles La largeur considérée dans le monde, en unité réelles
	 * @param hautUnitesReelles La hauteur considérée dans le monde, en unité réelles  
	 * @param typeLargeur Booléen qui vaut vrai si la dimension fournie est la largeur du monde, vaut faux si la dimension fournie est la hauteur du monde
	 */
	public ModeleAffichage(double largPixels, double hautPixels, double xOrigUnitesReelle, double yOrigUnitesReelle, double largUnitesReelles, double hautUnitesReelles) {
		
		this.largPixels = largPixels;
		this.hautPixels = hautPixels;
		this.xOrigUnitesReelle = xOrigUnitesReelle;
		this.yOrigUnitesReelle = yOrigUnitesReelle;
		this.largUnitesReelles = largUnitesReelles;
		this.hautUnitesReelles = hautUnitesReelles;                            
		
		this.pixelsParUniteX = largPixels / largUnitesReelles;
		this.pixelsParUniteY = hautPixels / hautUnitesReelles ;

		//calcul de la matrice monde-vers-composant
		AffineTransform mat = new AffineTransform();  //donne une matrice identite
		mat.scale( pixelsParUniteX, pixelsParUniteY ); 
		mat.translate(-xOrigUnitesReelle, -yOrigUnitesReelle);
		this.matMC = mat; //on mémorise la matrice (qui pourra être retournée via le getter associé)
		
	}

	//Mamadou
	/**
	 * Permet de créer un objet ModeleAffichage, pouvant mémoriser la matrice (et autres valeurs) de transformation pour passer du monde vers le composant. Une des dimensions du monde 
	 * réel est passée en paramètre (largeur ou hauteur). L'autre dimension sera  calculée de façon à n'introduire aucune distortion.
	 * 
	 * @param largPixels La largeur du composant, en pixels
	 * @param hautPixels La hauteur du composant, en pixels
	 * @param xOrigUnitesReelle L'origine en x de la portion du monde réel que l'on veut montrer 
	 * @param yOrigUnitesReelle L'origine en y de la portion du monde réel que l'on veut montrer 
	 * @param dimensionEnUnitesReelles La dimensions considérée dans le monde, en unité réelles (il peut s'agir d'une largeur ou d'une hauteur, dépendant du dernier paramètre)
	 * @param typeLargeur Booléen qui vaut vrai si la dimension fournie est la largeur du monde, vaut faux si la dimension fournie est la hauteur du monde
	 */
	public ModeleAffichage(double largPixels, double hautPixels, double xOrigUnitesReelle, double yOrigUnitesReelle, double dimensionEnUnitesReelles, boolean typeLargeur) {
		
		this.largPixels = largPixels;
		this.hautPixels = hautPixels;
		this.xOrigUnitesReelle = xOrigUnitesReelle;
		this.yOrigUnitesReelle = yOrigUnitesReelle;
	
		if (typeLargeur) {
			//le parametre "dimensionEnUnitesReelles" represente la LARGEUR voulue
			this.largUnitesReelles = dimensionEnUnitesReelles;
			
			//on calcule la hauteur correspondante pour éviter toute distortion
			this.hautUnitesReelles = largUnitesReelles * hautPixels/largPixels;
			
		} else {
			//le parametre "dimensionEnUnitesReelles" ne represente PAS la largeur, il représente plutôt la HAUTEUR voulue
			this.hautUnitesReelles = dimensionEnUnitesReelles;
			
			//on calcule la largeur correspondante pour éviter toute distortion
			this.largUnitesReelles = hautUnitesReelles * largPixels/hautPixels;
		}
		        
		//pour ce constructeur il n'y a pas de distortion, donc les deux valeurs suivantes seront identiques
		this.pixelsParUniteX = largPixels / largUnitesReelles;
		this.pixelsParUniteY = hautPixels / hautUnitesReelles ;

		//calcul de la matrice monde-vers-composant
		AffineTransform mat = new AffineTransform();  //donne une matrice identite
		mat.scale( pixelsParUniteX, pixelsParUniteY ); 
		mat.translate(-xOrigUnitesReelle, -yOrigUnitesReelle);
		this.matMC = mat; //on mémorise la matrice (qui pourra être retournée via le getter associé)
		
	}//fin constructeur


	//Mamadou
	/**
	 * Création d'un objet ModeleAffichage où la dimension passée en paramètre est forcément la largeur du monde
	 * 
	 * @param largPixels La largeur du composant, en pixels
	 * @param hautPixels La hauteur du composant, en pixels
	 * @param xOrigUnitesReelle L'origine en x de la portion du monde réel que l'on veut montrer 
	 * @param yOrigUnitesReelle L'origine en y de la portion du monde réel que l'on veut montrer 
	 * @param dimensionEnUnitesReelles La dimensions considérée dans le monde, en unité réelles (il peut s'agir d'une largeur ou d'une hauteur, dépendant du dernier paramètre)
	 */
	public ModeleAffichage(double largPixels, double hautPixels, double xOrigUnitesReelle, double yOrigUnitesReelle, double dimensionEnUnitesReelles) {
		this( largPixels, hautPixels, xOrigUnitesReelle, yOrigUnitesReelle, dimensionEnUnitesReelles, true);
	}
	

	//Mamadou
	/**
	 * Création d'un objet ModeleAffichage où l'origine du monde réel est à 0,0
	 * 
	 * @param largPixels La largeur du composant, en pixels
	 * @param hautPixels La hauteur du composant, en pixels
	 * @param dimensionEnUnitesReelles La dimensions considérée dans le monde, en unité réelles (il peut s'agir d'une largeur ou d'une hauteur, dépendant du dernier paramètre)
	 * @param typeLargeur Booléen qui vaut vrai si la dimension fournie est la largeur du monde, qui vaut faux si la dimension fournie est la hauteur du monde
	 */
	public ModeleAffichage(double largPixels, double hautPixels, double dimensionEnUnitesReelles, boolean typeLargeur) {
		this(largPixels, hautPixels, 0, 0, dimensionEnUnitesReelles, typeLargeur);
	}

	//Mamadou
	/**
	 *  Création d'un objet ModeleAffichage où l'origine du monde réel est à 0,0, et où la dimension passée en paramètre est forcément la largeur du monde
	 *  
	 * @param largPixels La largeur du composant, en pixels
	 * @param hautPixels La hauteur du composant, en pixels
	 * @param xOrigUnitesReelle L'origine en x de la portion du monde réel que l'on veut montrer 
	 * @param yOrigUnitesReelle L'origine en y de la portion du monde réel que l'on veut montrer 
	 * @param dimensionEnUnitesReelles La dimensions considérée dans le monde, en unité réelles (il peut s'agir d'une largeur ou d'une hauteur, dépendant du dernier paramètre)
	 */
	public ModeleAffichage(double largPixels, double hautPixels, double dimensionEnUnitesReelles) {
		this(largPixels, hautPixels, 0, 0, dimensionEnUnitesReelles, true);
	}

	//Mamadou
	/**
	 * Retourne une copie de la matrice monde-vers-composant qui a été calculée dans le constructeur
	 * @return La matrice monde-vers-composant
	 */
	public AffineTransform getMatMC() {
		//on décide de retourner une copie de celle qui est mémorisée, pour éviter qu'elle soit modifiée
		return new AffineTransform (matMC);
	}

	//Mamadou
	/**
	 * Retourne la hauteur du monde, en unités réelles
	 * @return La hauteur du monde, en unités réelles
	 */
	public double getHautUnitesReelles() {
		return hautUnitesReelles;
	}
	//Mamadou

	/**
	 * Retourne la largeur du monde, en unités réelles
	 * @return La largeur du monde, en unités réelles
	 */
	public double getLargUnitesReelles() {
		return largUnitesReelles;
	}

	//Mamadou
	/**
	 * Retourne le nombre de pixels contenus dans une unité du monde réel, en x
	 * @return Le nombre de pixels contenus dans une unité du monde réel, en x
	 */
	public double getPixelsParUniteX() {
		return pixelsParUniteX;
	}
	//Mamadou

	/**
	 * Retourne le nombre de pixels contenus dans une unité du monde réel, en y
	 * @return Le nombre de pixels contenus dans une unité du monde réel, en y
	 */
	public double getPixelsParUniteY() {
		return pixelsParUniteY;
	}
	//Mamadou

	/**
	 * Retourne la largeur en pixels du composant auquel s'appliquera la transformation 
	 * @return La largeur en pixels 
	 */
	public double getLargPixels() {
		return largPixels;
	}
	//Mamadou

	/**
	 * Retourne la hauteur en pixels du composant auquel s'appliquera la transformation 
	 * @return La hauteur en pixels 
	 */
	public double getHautPixels() {
		return hautPixels;
	}
	//Mamadou

	/**
	 * Retourne l'origine, en x, de la portion du monde réel considérée
	 * @return L'origine en x, de la portion du monde réel considérée
	 */
	public double getxOrigUnitesReelle() {
		return xOrigUnitesReelle;
	}
	//Mamadou

	/**
	 * Retourne l'origine, en y, de la portion du monde réel considérée
	 * @return L'origine en y, de la portion du monde réel considérée
	 */
	public double getyOrigUnitesReelle() {
		return yOrigUnitesReelle;
	}


}//fin classe

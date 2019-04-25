package ecouteursperso;

import java.util.EventListener;

/**
 * Écouteur personnel qui permet à la fenêtre de paramètres de communiquer les paramètres de la simulation avec la fenêtre de départ
 * @author Gayta
 */

public interface ResultatsFenParamListener extends EventListener{
	/**
	 * Méthode qui communique la vitesse des voitures mis par l'utilisateur
	 * @param vitesse la vitesse des voitures mis par l'utilisateur
	 */
	public void VitesseDesVoitures(int vitesse);
	/**
	 * Méthode qui communique le taux d'apparition des voitures mis par l'utilisateur
	 * @param taux le taux d'apparition des voitures mis par l'utilisateur
	 */
	public void TauxDApparitionDesVoitures(int taux);
	/**
	 * Méthode qui communique le nombre de voitures à générer mis par l'utilisateur
	 * @param nbVoitures le nombre de voitures à générer mis par l'utilisateur
	 */
	public void NombreDeVoituresAGenerer(int nbVoitures);
	/**
	 * Méthode qui indique si oui ou non il y aura du trafic anormal
	 * @param anormale boolean qui indique si oui ou non il y aura du trafic anormal
	 */
	public void isTraficAnormal(boolean anormale);
	/**
	 * Méthode qui communique les voies qui auront du trafic anormal
	 * @param listeVoiesAnormales liste des voies avec du trafic anormal
	 */
	public void setVoiesAvecTraficAnormal(int[] listeVoiesAnormales);
	/**
	 * Méthode qui indique le type d'images utilisées pour représenter les voitures sur l'interesection
	 * @param typeImages le type d'images utilisées pour représenter les voitures sur l'interesection
	 */
	public void typeImages(int typeImages);
	/**
	 * Méthode qui communique le nombre de voies à dessiner par direction
	 * @param nbVoiesEst nombre de voies allant vers la direction est
	 * @param nbVoiesOuest nombre de voies allant vers la direction ouest
	 * @param nbVoiesSud nombre de voies allant vers la direction sud
	 * @param nbVoiesNord nombre de voies allant vers la direction nord
	 */
	public void setNbVoies(int nbVoiesEst, int nbVoiesOuest, int nbVoiesSud, int nbVoiesNord);

}

package ecouteursperso;

import java.util.EventListener;

/**
 * �couteur personnel qui permet � la fen�tre de param�tres de communiquer les param�tres de la simulation avec la fen�tre de d�part
 * @author Gayta
 */

public interface ResultatsFenParamListener extends EventListener{
	/**
	 * M�thode qui communique la vitesse des voitures mis par l'utilisateur
	 * @param vitesse la vitesse des voitures mis par l'utilisateur
	 */
	public void VitesseDesVoitures(int vitesse);
	/**
	 * M�thode qui communique le taux d'apparition des voitures mis par l'utilisateur
	 * @param taux le taux d'apparition des voitures mis par l'utilisateur
	 */
	public void TauxDApparitionDesVoitures(int taux);
	/**
	 * M�thode qui communique le nombre de voitures � g�n�rer mis par l'utilisateur
	 * @param nbVoitures le nombre de voitures � g�n�rer mis par l'utilisateur
	 */
	public void NombreDeVoituresAGenerer(int nbVoitures);
	/**
	 * M�thode qui indique si oui ou non il y aura du trafic anormal
	 * @param anormale boolean qui indique si oui ou non il y aura du trafic anormal
	 */
	public void isTraficAnormal(boolean anormale);
	/**
	 * M�thode qui communique les voies qui auront du trafic anormal
	 * @param listeVoiesAnormales liste des voies avec du trafic anormal
	 */
	public void setVoiesAvecTraficAnormal(int[] listeVoiesAnormales);
	/**
	 * M�thode qui indique le type d'images utilis�es pour repr�senter les voitures sur l'interesection
	 * @param typeImages le type d'images utilis�es pour repr�senter les voitures sur l'interesection
	 */
	public void typeImages(int typeImages);
	/**
	 * M�thode qui communique le nombre de voies � dessiner par direction
	 * @param nbVoiesEst nombre de voies allant vers la direction est
	 * @param nbVoiesOuest nombre de voies allant vers la direction ouest
	 * @param nbVoiesSud nombre de voies allant vers la direction sud
	 * @param nbVoiesNord nombre de voies allant vers la direction nord
	 */
	public void setNbVoies(int nbVoiesEst, int nbVoiesOuest, int nbVoiesSud, int nbVoiesNord);

}

package ecouteursperso;

import java.util.EventListener;

/**
 * Écouteur personnel qui permet à la fenêtre de paramètres de communiquer les paramètres de la simulation avec la fenêtre de départ
 * @author Gayta
 */

public interface ResultatsFenParamListener extends EventListener{
	
	public void VitesseDesVoitures(int vitesse);
	
	public void TauxDApparitionDesVoitures(int taux);
	
	public void NombreDeVoituresAGenerer(int nbVoitures);
	
	public void isTraficAnormal(boolean anormale);
	
	public void setVoiesAvecTraficAnormal(int[] listeVoiesAnormales);

	public void typeImages(int typeImages);

}

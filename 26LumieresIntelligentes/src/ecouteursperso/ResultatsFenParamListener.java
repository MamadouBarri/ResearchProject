package ecouteursperso;

import java.util.EventListener;

/**
 * �couteur personnel qui permet � la fen�tre de param�tres de communiquer les param�tres de la simulation avec la fen�tre de d�part
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

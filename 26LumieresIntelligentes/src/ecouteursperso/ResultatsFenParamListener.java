package ecouteursperso;

import java.util.EventListener;

public interface ResultatsFenParamListener extends EventListener{
	
	public void VitesseDesVoitures(int vitesse);
	
	public void TauxDApparitionDesVoitures(int taux);
	
	public void NombreDeVoituresAGenerer(int nbVoitures);
	
	public void isTraficAnormal(boolean anormale);
	
	public void setVoiesAvecTraficAnormal(int[] listeVoiesAnormales);

}

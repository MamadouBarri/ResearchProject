package ecouteursperso;
import java.util.EventListener;

/**
 * �couteur qui permet de faire la mise � jour des statistiques
 * @author Mamadou
 **/
public interface EvenFenetreStatistiquesMiseAJour extends EventListener{
	/**
	 * M�thode qui permet la mise � jour de toute les statistiques
	 * de la FenetreStatistique
	 */
	public void miseAJourDesStats();
}

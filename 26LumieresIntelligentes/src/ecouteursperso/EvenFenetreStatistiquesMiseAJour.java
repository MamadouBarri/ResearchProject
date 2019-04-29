package ecouteursperso;
import java.util.EventListener;

/**
 * Écouteur qui permet de faire la mise à jour des statistiques
 * @author Mamadou
 **/
public interface EvenFenetreStatistiquesMiseAJour extends EventListener{
	/**
	 * Méthode qui permet la mise à jour de toute les statistiques
	 * de la FenetreStatistique
	 */
	public void miseAJourDesStats();
}

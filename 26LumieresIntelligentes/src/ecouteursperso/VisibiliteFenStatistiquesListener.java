package ecouteursperso;
import java.util.EventListener;
/**
 * Écouteur qui permet la fenêtre de simulation d'ouvrir la fenêtre de statistiques
 * @author Mamadou
 **/
public interface VisibiliteFenStatistiquesListener extends EventListener{
	/**
	 * Méthode qui permet de faire la mise à jour des graphiques des statistiques
	 */
	public void faireLaMiseAJour();
	/**
	 * Méthode qui permet de rendre la fenêtre des statistiques visibles
	 */
	public void rendreFenetreStatistiquesVisible();
}

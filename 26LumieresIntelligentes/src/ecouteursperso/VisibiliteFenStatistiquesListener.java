package ecouteursperso;
import java.util.EventListener;
/**
 * �couteur qui permet la fen�tre de simulation d'ouvrir la fen�tre de statistiques
 * @author Mamadou
 **/
public interface VisibiliteFenStatistiquesListener extends EventListener{
	/**
	 * M�thode qui permet de faire la mise � jour des graphiques des statistiques
	 */
	public void faireLaMiseAJour();
	/**
	 * M�thode qui permet de rendre la fen�tre des statistiques visibles
	 */
	public void rendreFenetreStatistiquesVisible();
}

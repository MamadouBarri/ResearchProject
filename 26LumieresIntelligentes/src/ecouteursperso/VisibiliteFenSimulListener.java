package ecouteursperso;

import java.util.EventListener;
/**
 * Écouteur qui permet la fenêtre de paramètres d'ouvrir la fenêtre de simulation sans video
 * @author Gayta
 *
 */
public interface VisibiliteFenSimulListener extends EventListener {
	/**
	 * Méthode qui indique quand l'utilisateur veut ouvrir la fenêtre de simulations
	 */
	public void rendreFenetreSimulationsSansVideoVisible();
}

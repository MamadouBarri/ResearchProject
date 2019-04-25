package ecouteursperso;

import java.util.EventListener;
/**
 * Écouteur personnel qui permet aux fenêtres d'instructions et de concepts d'ouvrir la fenêtre de paramètres
 * @author Gayta
 *
 */
public interface VisibiliteFenParamListener extends EventListener{
	/**
	 * Méthode qui indique quand l'utilisateur veut ouvrir la fenêtre de paramètres
	 */
	public void rendreFenetreParamVisible();
}

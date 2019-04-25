package ecouteursperso;

import java.util.EventListener;
/**
 * Écouteur personnel qui permet une fenetre d'ouvrir la fenêtre de depart
 * @author Gayta
 *
 */
	public interface VisibiliteFenDepartListener extends EventListener {
		/**
		 * Méthode qui indique quand l'utilisateur veut ouvrir la fenêtre de départ
		 */
		public void rendreFenetreDepartVisible();
}

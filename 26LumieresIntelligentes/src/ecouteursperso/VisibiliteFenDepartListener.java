package ecouteursperso;

import java.util.EventListener;
/**
 * �couteur personnel qui permet une fenetre d'ouvrir la fen�tre de depart
 * @author Gayta
 *
 */
	public interface VisibiliteFenDepartListener extends EventListener {
		/**
		 * M�thode qui indique quand l'utilisateur veut ouvrir la fen�tre de d�part
		 */
		public void rendreFenetreDepartVisible();
}

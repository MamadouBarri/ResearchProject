package ecouteursperso;

import java.util.EventListener;
/**
 * �couteur personnel qui permet aux fen�tres d'instructions et de concepts d'ouvrir la fen�tre de param�tres
 * @author Gayta
 *
 */
public interface VisibiliteFenParamListener extends EventListener{
	/**
	 * M�thode qui indique quand l'utilisateur veut ouvrir la fen�tre de param�tres
	 */
	public void rendreFenetreParamVisible();
}

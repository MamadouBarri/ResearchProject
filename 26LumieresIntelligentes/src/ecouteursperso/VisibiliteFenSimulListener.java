package ecouteursperso;

import java.util.EventListener;
/**
 * �couteur qui permet la fen�tre de param�tres d'ouvrir la fen�tre de simulation sans video
 * @author Gayta
 *
 */
public interface VisibiliteFenSimulListener extends EventListener {
	public void rendreFenetreSimulationsSansVideoVisible();
}

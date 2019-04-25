package interfaces;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
/**
 * Interface dessinable qui regroupe la methode dessiner utilis�e dans les objets dessinables de notre application
 * @author Mamadou Barri
 */
public interface Dessinable {
	/**
	 * M�thode qui permet de dessiner 
	 * @param g2d	Le contexte graphique
	 * @param mat	La matrice de transformation
	 */
	public void dessiner( Graphics2D g2d, AffineTransform mat);

}

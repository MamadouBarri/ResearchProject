package geometrie;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import interfaces.Dessinable;

public class Lumiere implements Dessinable{
	
	Rectangle2D.Double cadre;
	Ellipse2D.Double lumiereVerte;
	Ellipse2D.Double lumiereJaune;
	Ellipse2D.Double lumiereRouge;
	int x,y;
	double hauteur;
	
	Lumiere(double hauteur){
		this.hauteur = hauteur;
	}

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		// TODO Auto-generated method stub
		cadre = new Rectangle2D.Double(0, 0, hauteur, hauteur*(6/19));
		//lumiereVerte = new Ellipse2D.Double(arg0, arg1, arg2, arg3)
		
	}

}

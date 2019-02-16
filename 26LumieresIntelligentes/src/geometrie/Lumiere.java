package geometrie;

import java.awt.Color;
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
	int x,y,couleur;
	Color rouge, jaune, vert;
	double hauteur, longueur, diametre, rayon;
	
	public Lumiere(int x, int y, double hauteur, int couleur){
		this.couleur = couleur;
		this.hauteur = hauteur;
		this.longueur = this.hauteur * (6.0/19.0);
		this.x = x;
		this.y = y;
		this.diametre = this.longueur * 6.0/8.0;
		this.rayon = this.diametre/2.0;
		rouge = new Color(255,0,0);
		jaune = new Color(75,75,0);
		vert = new Color(0,75,0);
	}

	@Override
	public void dessiner(Graphics2D g2d, AffineTransform mat) {
		switch(this.couleur) {
		case 1:
			setCouleurRouge();
			break;
		case 2:
			setCouleurJaune();
			break;
		case 3:
			setCouleurVert();
			break;
		}
		cadre = new Rectangle2D.Double(x, y, longueur, hauteur);
		g2d.setColor(Color.black);
		g2d.fill(cadre);
		lumiereRouge = new Ellipse2D.Double(x+longueur/2-rayon, y+rayon/2, diametre, diametre);
		g2d.setColor(rouge);
		g2d.fill(lumiereRouge);
		lumiereJaune = new Ellipse2D.Double(x+longueur/2-rayon, y+hauteur*2/4-rayon, diametre, diametre);
		g2d.setColor(jaune);
		g2d.fill(lumiereJaune);
		lumiereVerte = new Ellipse2D.Double(x+longueur/2-rayon, y+hauteur-diametre-rayon/2, diametre, diametre);
		g2d.setColor(vert);
		g2d.fill(lumiereVerte);
	}
	
	public void setCouleurRouge() {
		rouge = new Color(255,0,0);
		jaune = new Color(75,75,0);
		vert = new Color(0,75,0);
	}
	
	public void setCouleurJaune() {
		rouge = new Color(75,0,0);
		jaune = new Color(255,255,0);
		vert = new Color(0,75,0);
	}

	public void setCouleurVert() {
		rouge = new Color(75,0,0);
		jaune = new Color(75,75,0);
		vert = new Color(0,255,0);
	}


}

package aaplication;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * Ce composant personnalise permet d'afficher une image accompagnee d'une barre de defilement vertical. Le tout est placee dans un jpanel.
 * 
 * Pour utiliser ce composant: le placer sur l'interface (avec ou sans WindowBuilder). Ensuite, executer sa methode setFichierImage pour
 * associer l'image desiree. Cette derniere methode peut etre appelee a tout moment pour modifier le contenu du panel sur demande.
 * 
 * L'image est redimensionnee de facon a cadrer exactement dans le jpanel.
 * 
 * Caracteristiques modifiables:
 * - la couleur du fond, c'est a dire du cadre derriere le texte (setBackground)
 * - la largeur de ce cadre en pixels (setLargeurCadre)
 * - le nom du fichier associe (setFichierImage)
 * 
 * @author Caroline Houle
 */

public class ImageAvecDefilementConcepts extends JPanel {
	private static final long serialVersionUID = 1L;
	private final int LARG_BARRE_DEFILEMENT = 20; 	//largeur en pixels qu'occupe la barre de defilement vertical
	private int largeurCadre = 6; 			        //nombre de pixels laisses vides autour du scrollpane (prendra la couleur du fond du jpanel)
	private String nomFichierCourant = null;
	private JScrollPane sp;
	private JLabel lblPourContenirImage;

	public ImageAvecDefilementConcepts() {
		super();
		lblPourContenirImage = new JLabel(); //l'image sera chargee dans une etiquette
		// on met un scrollpane autour de l'etiquette, pour avoir les barres de defilement
		sp = new JScrollPane(lblPourContenirImage);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		setLayout(null);
		add(sp); //ajout du scrollpane au jpanel
	}
	
	/**
	 * Place l'image dans le composant et lui associe une barre de defilement.
	 * L'image est redimensionnee de maniere a ce qu'elle occupe toute la largeur du composant (a part un petit cadre).
	 *  
	 * @param nomFichierImage Le nom du fichier d'image a charger
	 */
	public void setFichierImage(String nomFichierImage) {
		URL urlImage = getClass().getClassLoader().getResource(nomFichierImage);
		if (urlImage == null) {
			JOptionPane.showMessageDialog(null, "ImageAvecBarre / setImage : Fichier " + nomFichierImage + " introuvable!");
			return;
		}
		this.nomFichierCourant = nomFichierImage; //au cas ou on veut modifier par la suite la largeur du cadre
		ImageIcon imgIcon = new ImageIcon(urlImage);

		int largeurImageDesiree = this.getWidth()-LARG_BARRE_DEFILEMENT-2*largeurCadre;
		double ratio = imgIcon.getIconWidth() / (double) (largeurImageDesiree);
		int hauteurImageCalulee = (int) (imgIcon.getIconHeight() / ratio); //ainsi l'image reste proportionnelle, aucune distortion
		Image imgRedim = imgIcon.getImage().getScaledInstance(largeurImageDesiree, hauteurImageCalulee, Image.SCALE_SMOOTH);
		lblPourContenirImage.setIcon( new ImageIcon(imgRedim) ); //on place l'image dans l'etiquette
		imgRedim.flush();

		//on fixe les dimensions du scrollpane pour qu'il occupe la largeur du composant (moins un petit cadre)
		sp.setBounds(largeurCadre, largeurCadre, getWidth()-2*largeurCadre, getHeight()-2*largeurCadre);
	}
	
	
	/**
	 * Modifie la largeur courante du cadre autour du scrollpane
	 * Cet espace permet de voir la couleur de l'arriere plan du panel
	 * @param largeurCadre La largeur desiree, en pixels
	 */
	public void setLargeurCadre(int largeurCadre) {
		this.largeurCadre = largeurCadre;
		if (nomFichierCourant != null) {
			setFichierImage(nomFichierCourant); //car il faut refaire l'image et le scrollpane selon la taille cadre
		}
	}
	
	
	/**
	 * Retourne la largeur courante du cadre autour du scrollpane
	 * Cet espace permet de voir la couleur de l'arriere plan du panel
	 * @return La laregur du cadre, en pixels
	 */
	public int getLargeurCadre() {
		return largeurCadre;
	}
	
	
} //fin classe

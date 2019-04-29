package aaplication;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import stats.PlanCartesien1;
import java.awt.Font;
import java.net.URL;
/**
 * JFrame contenant des statistiques qui comparent notre algorithme à des feux de circulations normals
 * @author Gayta et Mamadou
 *
 */
public class FenetreStatistiques extends JFrame {
	//Graphiques
	PlanCartesien1 planCartesien1;
	PlanCartesien1 planCartesien2;
	PlanCartesien1 planCartesien3;
	PlanCartesien1 planCartesienDensite;
	/**
	 * numero par defaut
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreStatistiques frame = new FenetreStatistiques();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
//
	//Reiner
	/**
	 * Constructeur de la fenêtre
	 */
	public FenetreStatistiques() {
		setTitle("Statistiques\r\n");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(1920/2-1443/2, 0, 1443, 1080);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblTempsArretTitre = new JLabel("Temps d'arr\u00EAt moyen en fonction du temps");
		lblTempsArretTitre.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTempsArretTitre.setBounds(722, 11, 290, 14);
		contentPane.add(lblTempsArretTitre);

		JLabel lblNbVoituresEnAttenteTitre = new JLabel("Nombre de voitures arr\u00EAt\u00E9es \r\nen fonction du temps");
		lblNbVoituresEnAttenteTitre.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblNbVoituresEnAttenteTitre.setBounds(95, 11, 344, 14);
		contentPane.add(lblNbVoituresEnAttenteTitre);
		
		JLabel lblTempsSimule1 = new JLabel("Le temps simul\u00E9  t (s)");
		lblTempsSimule1.setBounds(358, 474, 126, 31);
		contentPane.add(lblTempsSimule1);
		
		JLabel lblNombreDeVoituresArretees = new JLabel("N : Voitures arr\u00EAt\u00E9es F(t)\r\n");
		lblNombreDeVoituresArretees.setBounds(10, 36, 141, 14);
		contentPane.add(lblNombreDeVoituresArretees);
		
		JLabel lblVitesseMoyenne = new JLabel("F(t) : Vitesse moyenne des voitures\r\n\r\n");
		lblVitesseMoyenne.setBounds(10, 561, 200, 14);
		contentPane.add(lblVitesseMoyenne);
		
		JLabel lblTempsSimlule2 = new JLabel("Le temps simul\u00E9  t (s)");
		lblTempsSimlule2.setBounds(316, 987, 123, 31);
		contentPane.add(lblTempsSimlule2);
		
		JLabel lblTempsArretTt = new JLabel("F(t) : Temps d'arr\u00EAt moyen\r\n\r\n");
		lblTempsArretTt.setBounds(615, 36, 200, 14);
		contentPane.add(lblTempsArretTt);
		
		JLabel label_1 = new JLabel("Le temps simul\u00E9  t (s)");
		label_1.setBounds(947, 474, 123, 31);
		contentPane.add(label_1);
		
		JLabel lblVitesseMoyenneTitre = new JLabel("La vitesse moyenne en fonction du temps");
		lblVitesseMoyenneTitre.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblVitesseMoyenneTitre.setBounds(95, 537, 344, 14);
		contentPane.add(lblVitesseMoyenneTitre);
		
		JLabel label = new JLabel("Temps d'arr\u00EAt moyen en fonction du temps");
		label.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		label.setBounds(722, 536, 290, 14);
		contentPane.add(label);
		
		JLabel label_2 = new JLabel("F(t) : Temps d'arr\u00EAt moyen\r\n\r\n");
		label_2.setBounds(615, 561, 200, 14);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("Le temps simul\u00E9  t (s)");
		label_3.setBounds(947, 987, 123, 31);
		contentPane.add(label_3);
		
		URL urlImage = getClass().getClassLoader().getResource("legende.png");
		if (urlImage == null) {
			JOptionPane.showMessageDialog(null, "ImageAvecBarre / setImage : Fichier " + "legende.png" + " introuvable!");
			return;
		}
		ImageIcon imgIcon = new ImageIcon(urlImage);
		
		JLabel lblLegende = new JLabel(imgIcon);
		lblLegende.setBounds(1128, 474, 263, 114);
		contentPane.add(lblLegende);
		
	}
	//Mamadou
	/**
	 * Methode qui cree les graphiques avec les statistiques
	 */
	public void creerLesGraphiques() {
		// Graphique 0 : Nombre de voitures en fonction du temps
		planCartesien1 = new PlanCartesien1(0); 
		planCartesien1.setBounds(62, 69,400, 400);
		contentPane.add(planCartesien1);
		
		//Graphique 1 : La vitesse moyenne en fonction du temps
		//520 209 400 400
		planCartesien2 = new PlanCartesien1(1); 
		planCartesien2.setBounds(62, 595,400, 400);
		contentPane.add(planCartesien2);
		
		//Gtaphique 2 : Le temps d'arrêt moyen
		//1022 209 400 400
		planCartesien3 = new PlanCartesien1(2); 
		planCartesien3.setBounds(689, 69,400, 400);
		contentPane.add(planCartesien3);
		
		//Graphique 3 : la densité
		//520 619 400 400
		planCartesienDensite = new PlanCartesien1(3);
		planCartesienDensite.setBounds(689,595,400,400);
		contentPane.add(planCartesienDensite);
	}
	//Mamadou
	/**
	 * Methode qui met ajour les graphiques
	 */
	public void miseAJour() {
		creerLesGraphiques();
		this.repaint();
	}
}

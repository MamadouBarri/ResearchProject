package aaplication;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextPane;

import stats.PlanCartesien1;
import java.awt.Font;
/**
 * JFrame contenant des statistiques qui comparent notre algorithme à des feux de circulations normals
 * @author Gayta et Mamadou
 *
 */
public class FenetreStatistiques extends JFrame {

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
		setBounds(100, 100, 1443, 1000);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane txtpnDensitDeVoitures = new JTextPane();
		txtpnDensitDeVoitures.setText("Densit\u00E9 de voitures\r\n   Temps d'arr\u00EAt\r\n    Nombre de voitures pass\u00E9es par intervale de temps\r\n    Vitesse moyenne de tous les voiture\r\n\r\n");
		txtpnDensitDeVoitures.setBounds(442, 49, 312, 70);
		contentPane.add(txtpnDensitDeVoitures);

		JLabel lblTempsArretTitre = new JLabel("Temps d'arr\u00EAt moyen en fonction du temps");
		lblTempsArretTitre.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTempsArretTitre.setBounds(1069, 157, 290, 14);
		contentPane.add(lblTempsArretTitre);

		//PlanCartesien graphiqueTempsArret = new PlanCartesien();
		//graphiqueTempsArret.setBounds(62, 209,400, 400);
		//contentPane.add(graphiqueTempsArret);

		JLabel lblNbVoituresEnAttenteTitre = new JLabel("Nombre de voitures arr\u00EAt\u00E9es \r\nen fonction du temps");
		lblNbVoituresEnAttenteTitre.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblNbVoituresEnAttenteTitre.setBounds(95, 157, 344, 14);
		contentPane.add(lblNbVoituresEnAttenteTitre);
		
		JTextPane txtpnCourbeVerte = new JTextPane();
		txtpnCourbeVerte.setText("COURBE VERTE : avec notre algorithme\r\n\r\nCOURBE ROUGE : sans l'algorithme");
		txtpnCourbeVerte.setBounds(932, 49, 158, 87);
		contentPane.add(txtpnCourbeVerte);
		
		JLabel lblTempsSimule1 = new JLabel("Le temps simul\u00E9  t (s)");
		lblTempsSimule1.setBounds(358, 620, 126, 31);
		contentPane.add(lblTempsSimule1);
		
		JLabel lblNombreDeVoituresArretees = new JLabel("N : Voitures arr\u00EAt\u00E9es F(t)\r\n");
		lblNombreDeVoituresArretees.setBounds(10, 182, 141, 14);
		contentPane.add(lblNombreDeVoituresArretees);
		
		JLabel lblVitesseMoyenne = new JLabel("F(t) : Vitesse moyenne des voitures\r\n\r\n");
		lblVitesseMoyenne.setBounds(509, 182, 200, 14);
		contentPane.add(lblVitesseMoyenne);
		
		JLabel lblTempsSimlule2 = new JLabel("Le temps simul\u00E9  t (s)");
		lblTempsSimlule2.setBounds(815, 620, 123, 31);
		contentPane.add(lblTempsSimlule2);
		
		JLabel lblTempsArretTt = new JLabel("F(t) : Temps d'arr\u00EAt moyen\r\n\r\n");
		lblTempsArretTt.setBounds(962, 182, 200, 14);
		contentPane.add(lblTempsArretTt);
		
		JLabel label_1 = new JLabel("Le temps simul\u00E9  t (s)");
		label_1.setBounds(1294, 620, 123, 31);
		contentPane.add(label_1);
		
		JLabel lblVitesseMoyenneTitre = new JLabel("La vitesse moyenne en fonction du temps");
		lblVitesseMoyenneTitre.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblVitesseMoyenneTitre.setBounds(548, 158, 344, 14);
		contentPane.add(lblVitesseMoyenneTitre);
		
	}
	//Mamadou
	/**
	 * Methode qui cree les graphiques avec les statistiques
	 */
	public void creerLesGraphiques() {
		// Graphique 0 : Nombre de voitures en fonction du temps
		PlanCartesien1 planCartesien1 = new PlanCartesien1(0); 
		planCartesien1.setBounds(62, 209,400, 400);
		contentPane.add(planCartesien1);
		
		//Graphique 1 : La vitesse moyenne en fonction du temps
		//520 209 400 400
		PlanCartesien1 planCartesien2 = new PlanCartesien1(1); 
		planCartesien2.setBounds(520, 209,400, 400);
		contentPane.add(planCartesien2);
		
		//Gtaphique 2 : 
		//1022 209 400 400
		PlanCartesien1 planCartesien3 = new PlanCartesien1(2); 
		planCartesien3.setBounds(980, 209,400, 400);
		contentPane.add(planCartesien3);
	}
}

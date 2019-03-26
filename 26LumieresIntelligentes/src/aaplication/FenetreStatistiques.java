package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import stats.PlanCartesien;
import stats.PlanCartesien1;
import javax.swing.SwingConstants;
import java.awt.Font;
/**
 * JFrame contenant des statistiques qui comparent notre algorithme à des feux de circulations normals
 * @author Gayta
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
		setBounds(100, 100, 1080, 908);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane txtpnDensitDeVoitures = new JTextPane();
		txtpnDensitDeVoitures.setText("Densit\u00E9 de voitures\r\n   Temps d'arr\u00EAt\r\n    Nombre de voitures pass\u00E9es par intervale de temps\r\n    Vitesse moyenne de tous les voiture\r\n\r\n");
		txtpnDensitDeVoitures.setBounds(183, 57, 312, 70);
		contentPane.add(txtpnDensitDeVoitures);

		JLabel lblTempsArret = new JLabel("Temps d'arr\u00EAt en fonction du temps");
		lblTempsArret.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblTempsArret.setBounds(706, 157, 231, 14);
		contentPane.add(lblTempsArret);

		//PlanCartesien graphiqueTempsArret = new PlanCartesien();
		//graphiqueTempsArret.setBounds(62, 209,400, 400);
		//contentPane.add(graphiqueTempsArret);

		JLabel lblNbVoituresEnAttente = new JLabel("Nombre de voitures arr\u00EAt\u00E9es \r\nen fonction du temps");
		lblNbVoituresEnAttente.setFont(new Font("Tw Cen MT", Font.BOLD, 15));
		lblNbVoituresEnAttente.setBounds(95, 157, 344, 14);
		contentPane.add(lblNbVoituresEnAttente);
		
		JTextPane txtpnCourbeVerte = new JTextPane();
		txtpnCourbeVerte.setText("COURBE VERTE : avec notre algorithme\r\n\r\nCOURBE ROUGE : sans l'algorithme");
		txtpnCourbeVerte.setBounds(548, 57, 158, 87);
		contentPane.add(txtpnCourbeVerte);
		
		JLabel lblLeTempsSimuls = new JLabel("Le temps simul\u00E9  t (s)");
		lblLeTempsSimuls.setBounds(354, 615, 141, 31);
		contentPane.add(lblLeTempsSimuls);
		
		JLabel lblNombreDeVoituresArretees = new JLabel("N : Voitures arr\u00EAt\u00E9es F(t)\r\n");
		lblNombreDeVoituresArretees.setBounds(10, 182, 135, 14);
		contentPane.add(lblNombreDeVoituresArretees);
		
		PlanCartesien1 planCartesien1 = new PlanCartesien1();
		planCartesien1.setBounds(62, 209,400, 400);
		contentPane.add(planCartesien1);

	}
}

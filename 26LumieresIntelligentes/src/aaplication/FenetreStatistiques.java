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

	//Reiner
	/**
	 * Constructeur de la fenêtre
	 */
	public FenetreStatistiques() {
		setTitle("Statistiques\r\n");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 909, 676);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane txtpnDensitDeVoitures = new JTextPane();
		txtpnDensitDeVoitures.setText("Densit\u00E9 de voitures\r\n   Temps d'arr\u00EAt\r\n    Nombre de voitures pass\u00E9es par intervale de temps\r\n    Vitesse moyenne de tous les voiture\r\n\r\n");
		txtpnDensitDeVoitures.setBounds(306, 59, 312, 70);
		contentPane.add(txtpnDensitDeVoitures);

		JLabel lblTempsArret = new JLabel("temps d'arr\u00EAt en fonction du temps");
		lblTempsArret.setBounds(570, 184, 195, 14);
		contentPane.add(lblTempsArret);

		PlanCartesien graphiqueTempsArret = new PlanCartesien();
		graphiqueTempsArret.setBounds(62, 209,400, 400);
		contentPane.add(graphiqueTempsArret);

		JLabel lblNbVoituresEnAttente = new JLabel("Nombre de voitures arr\u00EAt\u00E9es \r\nen fonction du temps");
		lblNbVoituresEnAttente.setBounds(134, 184, 312, 14);
		contentPane.add(lblNbVoituresEnAttente);
		
		JTextPane txtpnCourbeVerte = new JTextPane();
		txtpnCourbeVerte.setText("COURBE VERTE : avec notre algorithme\r\n\r\nCOURBE ROUGE : sans l'algorithme");
		txtpnCourbeVerte.setBounds(570, 313, 158, 87);
		contentPane.add(txtpnCourbeVerte);

	}
}

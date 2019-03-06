package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JTextPane;
/**
 * JFrame contenant des statistiques qui comparent notre algorithme à des feux de circulations normals
 * @author Gayta
 *
 */
public class FenetreStatistiques extends JFrame {

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
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 454, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txtpnDensitDeVoitures = new JTextPane();
		txtpnDensitDeVoitures.setText("Densit\u00E9 de voitures\r\n   Temps d'arr\u00EAt\r\n    Nombre de voitures pass\u00E9es par intervale de temps\r\n    Vitesse moyenne de tous les voiture\r\n\r\n");
		txtpnDensitDeVoitures.setBounds(50, 106, 312, 328);
		contentPane.add(txtpnDensitDeVoitures);
	}
}

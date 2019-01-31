package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class FenetreDeDepart extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreDeDepart frame = new FenetreDeDepart();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FenetreDeDepart() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitre = new JLabel("Lumi\u00E8res intelligentes");
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(39, 26, 568, 110);
		contentPane.add(lblTitre);
		
		JButton btnDepart = new JButton("Commencer");
		btnDepart.setBounds(248, 171, 171, 46);
		contentPane.add(btnDepart);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.setBounds(248, 246, 171, 46);
		contentPane.add(btnInstructions);
		
		JButton btnConceptsScientifiques = new JButton("Concepts Scientifiques");
		btnConceptsScientifiques.setBounds(248, 325, 171, 46);
		contentPane.add(btnConceptsScientifiques);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.setBounds(248, 400, 171, 46);
		contentPane.add(btnQuitter);
	}
}

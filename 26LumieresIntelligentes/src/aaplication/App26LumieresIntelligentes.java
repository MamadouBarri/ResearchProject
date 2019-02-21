package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class App26LumieresIntelligentes extends JFrame {

	private JPanel contentPane;
	private java.net.URL  urlPhotoDeDepart = getClass().getClassLoader().getResource("photoDeDepart.jpg");
	FenetreConcepts concepts;
	FenetreInstructions instructions;
	FenetreParametres parametres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App26LumieresIntelligentes frame = new App26LumieresIntelligentes();
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
	public App26LumieresIntelligentes() {
		concepts = new FenetreConcepts();
		instructions = new FenetreInstructions();
		parametres = new FenetreParametres();
		
		setTitle("Lumi\u00E8res Intelligentes - Mamadou Barri, Reiner Luis Gayta\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitre = new JLabel("Lumi\u00E8res intelligentes");
		lblTitre.setForeground(Color.YELLOW);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(39, 26, 568, 110);
		contentPane.add(lblTitre);
		
		JButton btnDepart = new JButton("Commencer");
		btnDepart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parametres.setVisible(true);
				setVisible(false);
			}
		});
		btnDepart.setBounds(248, 171, 171, 46);
		contentPane.add(btnDepart);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructions.setVisible(true);
				setVisible(false);
			}
		});
		btnInstructions.setBounds(248, 246, 171, 46);
		contentPane.add(btnInstructions);
		
		JButton btnConceptsScientifiques = new JButton("Concepts Scientifiques");
		btnConceptsScientifiques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				concepts.setVisible(true);
				setVisible(false);
			}
		});
		btnConceptsScientifiques.setBounds(248, 325, 171, 46);
		contentPane.add(btnConceptsScientifiques);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnQuitter.setBounds(248, 400, 171, 46);
		contentPane.add(btnQuitter);
		
		JLabel lblphoto = new JLabel((new ImageIcon(urlPhotoDeDepart)));
		lblphoto.setBounds(0, 0, 659, 504);
		contentPane.add(lblphoto);
	}
}

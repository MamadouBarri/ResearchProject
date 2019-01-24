package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class App26LumieresIntelligentes extends JFrame {

	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 720);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnSimulations = new JPanel();
		pnSimulations.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Simulations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnSimulations.setBounds(0, 0, 704, 430);
		contentPane.add(pnSimulations);
		pnSimulations.setLayout(null);
		
		JPanel pnSimulNormal = new JPanel();
		pnSimulNormal.setBackground(Color.GREEN);
		pnSimulNormal.setBounds(65, 86, 238, 218);
		pnSimulations.add(pnSimulNormal);
		
		JPanel pnSimulAlgo = new JPanel();
		pnSimulAlgo.setBackground(Color.ORANGE);
		pnSimulAlgo.setBounds(398, 86, 238, 218);
		pnSimulations.add(pnSimulAlgo);
		
		JPanel pnEmplacementsDesBoutons = new JPanel();
		pnEmplacementsDesBoutons.setBackground(Color.DARK_GRAY);
		pnEmplacementsDesBoutons.setBorder(new TitledBorder(null, "Boutons (play,pause,pas,etc)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnEmplacementsDesBoutons.setBounds(65, 343, 573, 76);
		pnSimulations.add(pnEmplacementsDesBoutons);
		
		JLabel lblSimulationAvecLalgorithme = new JLabel("Simulation avec l'algorithme");
		lblSimulationAvecLalgorithme.setBounds(454, 61, 140, 14);
		pnSimulations.add(lblSimulationAvecLalgorithme);
		
		JPanel pnStatistiques = new JPanel();
		pnStatistiques.setBackground(Color.LIGHT_GRAY);
		pnStatistiques.setBorder(new TitledBorder(null, "Statistiques", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnStatistiques.setBounds(0, 427, 409, 241);
		contentPane.add(pnStatistiques);
		
		JPanel pnParametres = new JPanel();
		pnParametres.setBackground(Color.GRAY);
		pnParametres.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Param\u00E8tres", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnParametres.setBounds(411, 427, 293, 241);
		contentPane.add(pnParametres);
		pnParametres.setLayout(null);
		
		JRadioButton rdbtnSimulation = new JRadioButton("Simuler");
		rdbtnSimulation.setBounds(21, 19, 109, 23);
		pnParametres.add(rdbtnSimulation);
		
		JRadioButton rdbtnVideo = new JRadioButton("Choisir une vid\u00E9o");
		rdbtnVideo.setBounds(159, 19, 109, 23);
		pnParametres.add(rdbtnVideo);
	}
}

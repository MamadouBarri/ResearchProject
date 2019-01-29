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
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import sceneAnimee.SceneAnimee;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class App26LumieresIntelligentes extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup btngrpChoixDeComparaison = new ButtonGroup();
	private JTextField txtfldNombreDeVoitures;
	private JLabel lblVitesseMoyenne;
	private JLabel lblTauxDapparition;
	private JCheckBox chckbxTraficAnormal;
	private JCheckBox chckbxVoie1;
	private JCheckBox chckbxVoie2;
	private JCheckBox chckbxVoie3;
	private JCheckBox chckbxVoie4;
	private JSpinner spnVitesseMoyenne;
	private JSpinner spnTauxDApparition;
	private JButton btnChoisirUneVideo;
	private FenetreFileChooser popup;
	private java.net.URL  urlAnimer = getClass().getClassLoader().getResource("play.png");
	private java.net.URL  urlPause = getClass().getClassLoader().getResource("pause.png");
	private java.net.URL  urlProchaineImage = getClass().getClassLoader().getResource("pas.png");
	private java.net.URL  urlRecommencer = getClass().getClassLoader().getResource("replay.png");

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
		setBounds(100, 100, 716, 857);
		
		popup = new FenetreFileChooser();
		
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
		
		JPanel pnEmplacementsDesBoutons = new JPanel();
		pnEmplacementsDesBoutons.setBackground(Color.DARK_GRAY);
		pnEmplacementsDesBoutons.setBorder(new TitledBorder(null, "Boutons (play,pause,pas,etc)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnEmplacementsDesBoutons.setBounds(65, 333, 573, 86);
		pnSimulations.add(pnEmplacementsDesBoutons);
		pnEmplacementsDesBoutons.setLayout(null);
		
		JButton button = new JButton("New button");
		button.setBounds(10, 11, 76, 76);
		pnEmplacementsDesBoutons.add(button);
		
		JButton button_1 = new JButton("New button");
		button_1.setBounds(107, 11, 76, 76);
		pnEmplacementsDesBoutons.add(button_1);
		
		JButton button_2 = new JButton("New button");
		button_2.setBounds(225, 11, 76, 76);
		pnEmplacementsDesBoutons.add(button_2);
		
		JButton button_3 = new JButton(new ImageIcon(urlRecommencer));
		button_3.setBounds(356, 11, 76, 76);
		pnEmplacementsDesBoutons.add(button_3);
		
		JLabel lblSimulationAvecLAlgorithme = new JLabel("Simulation avec l'algorithme");
		lblSimulationAvecLAlgorithme.setBounds(400, 61, 187, 14);
		pnSimulations.add(lblSimulationAvecLAlgorithme);
		
		JLabel lblSimulationSansLAlgorithme = new JLabel("Simulation sans l'algorithme");
		lblSimulationSansLAlgorithme.setBounds(120, 61, 214, 14);
		pnSimulations.add(lblSimulationSansLAlgorithme);
		
		SceneAnimee sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(83, 84, 240, 240);
		pnSimulations.add(sceneAnimee);
		
		SceneAnimee sceneAnimee2 = new SceneAnimee();
		sceneAnimee2.setBounds(381, 84, 240, 240);
		pnSimulations.add(sceneAnimee2);
	
		JButton btnNewButton = new JButton("debutAnimation\r\n");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee.demarrer();
				sceneAnimee2.demarrer();
			}
		});
		btnNewButton.setBounds(127, 31, 126, 23);
		pnSimulations.add(btnNewButton);
		
		JPanel pnStatistiques = new JPanel();
		pnStatistiques.setBackground(Color.LIGHT_GRAY);
		pnStatistiques.setBorder(new TitledBorder(null, "Statistiques", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnStatistiques.setBounds(0, 427, 409, 395);
		contentPane.add(pnStatistiques);
		
		JPanel pnParametres = new JPanel();
		pnParametres.setBackground(Color.GRAY);
		pnParametres.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Param\u00E8tres", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnParametres.setBounds(411, 427, 293, 395);
		contentPane.add(pnParametres);
		pnParametres.setLayout(null);
		
		JRadioButton rdbtnSimulation = new JRadioButton("Simuler l'intersection");
		rdbtnSimulation.setSelected(true);
		rdbtnSimulation.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(rdbtnSimulation.isSelected()){
					activerParamSimul(true);
					activerBoutonVideo(false);
					if(chckbxTraficAnormal.isSelected()){
						activerVoies(true);
					}
				} else {
					activerParamSimul(false);
					activerBoutonVideo(true);
					activerVoies(false);
				}
			}
		});
		btngrpChoixDeComparaison.add(rdbtnSimulation);
		rdbtnSimulation.setBounds(6, 19, 154, 23);
		pnParametres.add(rdbtnSimulation);
		
		JRadioButton rdbtnVideo = new JRadioButton("Vid\u00E9o");
		btngrpChoixDeComparaison.add(rdbtnVideo);
		rdbtnVideo.setBounds(6, 319, 139, 23);
		pnParametres.add(rdbtnVideo);
		
		lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setBounds(21, 69, 123, 14);
		pnParametres.add(lblVitesseMoyenne);
		
		JLabel lblNombreDeVoitures = new JLabel("Nombre de voitures :");
		lblNombreDeVoitures.setBounds(21, 116, 123, 14);
		pnParametres.add(lblNombreDeVoitures);
		
		lblTauxDapparition = new JLabel("Taux d'apparition :");
		lblTauxDapparition.setBounds(21, 167, 123, 14);
		pnParametres.add(lblTauxDapparition);
		
		chckbxTraficAnormal = new JCheckBox("Trafic anormal");
		chckbxTraficAnormal.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chckbxTraficAnormal.isSelected()) {
					activerVoies(true);
				} else {
					activerVoies(false);
				}
			}
		});
		chckbxTraficAnormal.setBounds(21, 219, 112, 23);
		pnParametres.add(chckbxTraficAnormal);
		
		chckbxVoie1 = new JCheckBox("Voie 1");
		chckbxVoie1.setBounds(38, 254, 97, 23);
		pnParametres.add(chckbxVoie1);
		
		chckbxVoie2 = new JCheckBox("Voie 2");
		chckbxVoie2.setBounds(148, 254, 97, 23);
		pnParametres.add(chckbxVoie2);
		
		chckbxVoie3 = new JCheckBox("Voie 3");
		chckbxVoie3.setBounds(38, 280, 97, 23);
		pnParametres.add(chckbxVoie3);
		
		chckbxVoie4 = new JCheckBox("Voie 4");
		chckbxVoie4.setBounds(148, 280, 97, 23);
		pnParametres.add(chckbxVoie4);
		
		txtfldNombreDeVoitures = new JTextField();
		txtfldNombreDeVoitures.setBounds(151, 113, 48, 20);
		pnParametres.add(txtfldNombreDeVoitures);
		txtfldNombreDeVoitures.setColumns(10);
		
		JLabel lblUniteTauxDApparition = new JLabel("Voitures/Minute");
		lblUniteTauxDApparition.setBounds(186, 167, 126, 14);
		pnParametres.add(lblUniteTauxDApparition);
		
		JLabel lblUniteDeVitesse = new JLabel("km/h");
		lblUniteDeVitesse.setBounds(186, 69, 46, 14);
		pnParametres.add(lblUniteDeVitesse);
		
		spnVitesseMoyenne = new JSpinner();
		spnVitesseMoyenne.setBounds(128, 66, 48, 20);
		pnParametres.add(spnVitesseMoyenne);
		
		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.setBounds(128, 164, 48, 20);
		pnParametres.add(spnTauxDApparition);
		
		btnChoisirUneVideo = new JButton("Choisir une vid\u00E9o");
		btnChoisirUneVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!popup.isVisible()) {
					popup.setVisible(true);
				}
			}
		});
		btnChoisirUneVideo.setBounds(74, 363, 143, 23);
		pnParametres.add(btnChoisirUneVideo);
	}
	
	public void activerVoies(boolean b){
		chckbxVoie1.setEnabled(b);
		chckbxVoie2.setEnabled(b);
		chckbxVoie3.setEnabled(b);
		chckbxVoie4.setEnabled(b);
	}
	public void activerParamSimul(boolean b){
		spnTauxDApparition.setEnabled(b);
		spnVitesseMoyenne.setEnabled(b);
		chckbxTraficAnormal.setEnabled(b);
		txtfldNombreDeVoitures.setEnabled(b);
	}
	public void activerBoutonVideo(boolean b){
		btnChoisirUneVideo.setEnabled(b);
	}
}

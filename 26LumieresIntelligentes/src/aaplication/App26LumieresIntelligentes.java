package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.imageio.ImageIO;
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
	private java.net.URL  urlAnimer = getClass().getClassLoader().getResource("play.jpg");
	private java.net.URL  urlPause = getClass().getClassLoader().getResource("pause.jpg");
	private java.net.URL  urlProchaineImage = getClass().getClassLoader().getResource("pas.jpg");
	private java.net.URL  urlRecommencer = getClass().getClassLoader().getResource("replay.png");
	private java.net.URL  urlStats = getClass().getClassLoader().getResource("statistiques.jpg");
	private SceneAnimee sceneAnimee;
	private SceneAnimee sceneAnimee2;

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
		pnEmplacementsDesBoutons.setForeground(Color.BLACK);
		pnEmplacementsDesBoutons.setBackground(Color.WHITE);
		pnEmplacementsDesBoutons.setBorder(new TitledBorder(null, "Boutons (play,pause,pas,etc)", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnEmplacementsDesBoutons.setBounds(65, 316, 573, 103);
		pnSimulations.add(pnEmplacementsDesBoutons);
		pnEmplacementsDesBoutons.setLayout(null);
		
		JButton btnAnimer = new JButton(new ImageIcon(urlAnimer));
		btnAnimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee.demarrer();;
				sceneAnimee2.demarrer();;
			}
		});
		btnAnimer.setBounds(34, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnAnimer);
		
		JButton btnPause = new JButton(new ImageIcon(urlPause));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee.arreter();
				sceneAnimee2.arreter();
			}
		});
		btnPause.setBounds(175, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnPause);
		
		JButton btnProchainImage = new JButton(new ImageIcon(urlProchaineImage));
		btnProchainImage.setBounds(324, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnProchainImage);
		
		JButton btnRecommencer = new JButton(new ImageIcon(urlRecommencer));
		btnRecommencer.setBounds(459, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnRecommencer);
		
		JLabel lblSimulationAvecLAlgorithme = new JLabel("Simulation avec l'algorithme");
		lblSimulationAvecLAlgorithme.setBounds(398, 33, 187, 14);
		pnSimulations.add(lblSimulationAvecLAlgorithme);
		
		JLabel lblSimulationSansLAlgorithme = new JLabel("Simulation sans l'algorithme");
		lblSimulationSansLAlgorithme.setBounds(118, 33, 214, 14);
		pnSimulations.add(lblSimulationSansLAlgorithme);
		
		sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(81, 56, 240, 240);
		pnSimulations.add(sceneAnimee);
		
		sceneAnimee2 = new SceneAnimee();
		sceneAnimee2.setBounds(379, 56, 240, 240);
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
		pnStatistiques.setLayout(null);
		
		JLabel lblStats = new JLabel(new ImageIcon(urlStats));
		lblStats.setBounds(10, 45, 385, 274);
		pnStatistiques.add(lblStats);
		
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

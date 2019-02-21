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
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SpinnerNumberModel;


public class App26LumieresIntelligentes extends JFrame {
	////
	private JPanel contentPane;
	private final ButtonGroup btngrpChoixDeComparaison = new ButtonGroup();
	private FenetreFileChooser popup;
	private java.net.URL  urlAnimer = getClass().getClassLoader().getResource("play.jpg");
	private java.net.URL  urlPause = getClass().getClassLoader().getResource("pause.jpg");
	private java.net.URL  urlProchaineImage = getClass().getClassLoader().getResource("pas.jpg");
	private java.net.URL  urlRecommencer = getClass().getClassLoader().getResource("replay.png");
	private java.net.URL  urlStats = getClass().getClassLoader().getResource("statistiques.jpg");
	private SceneAnimee sceneAnimee1;
	private SceneAnimee sceneAnimee2;
	private JCheckBox chkbxVoie4;
	private JCheckBox chkbxVoie3;
	private JCheckBox chkbxVoie2;
	private JCheckBox chkbxVoie1;
	private JSpinner spnTauxDApparition;
	private JSpinner spnVitesse;

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
		setTitle("Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 788, 683);

		/*popup = new FenetreFileChooser();
		popup.setVisible(false);*/

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem mntmMenuDeDpart = new JMenuItem("Menu de D\u00E9part");
		mnMenu.add(mntmMenuDeDpart);

		JMenuItem mntmChangerParametres = new JMenuItem("Changer les param\u00E8tres de simulation");
		mnMenu.add(mntmChangerParametres);

		JMenuItem mntmStatisitques = new JMenuItem("Statisitques");
		mnMenu.add(mntmStatisitques);

		JMenuItem mntmQuitter = new JMenuItem("Quitter");
		mntmQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnMenu.add(mntmQuitter);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);



		JPanel pnSimulations = new JPanel();
		pnSimulations.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Simulations", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnSimulations.setBounds(0, 0, 472, 619);
		contentPane.add(pnSimulations);
		pnSimulations.setLayout(null);

		JPanel pnEmplacementsDesBoutons = new JPanel();
		pnEmplacementsDesBoutons.setForeground(Color.BLACK);
		pnEmplacementsDesBoutons.setBackground(Color.WHITE);
		pnEmplacementsDesBoutons.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Boutons ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnEmplacementsDesBoutons.setBounds(10, 113, 142, 414);
		pnSimulations.add(pnEmplacementsDesBoutons);
		pnEmplacementsDesBoutons.setLayout(null);

		JButton btnAnimer = new JButton(new ImageIcon(urlAnimer));
		btnAnimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//On fait juste une scene pour l'instant
				sceneAnimee1.demarrer();
				sceneAnimee2.demarrer();				
				//sceneAnimee2.demarrer();
			}
		});
		btnAnimer.setBounds(34, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnAnimer);

		JButton btnPause = new JButton(new ImageIcon(urlPause));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee1.arreter();
				sceneAnimee2.arreter();
			}
		});
		btnPause.setBounds(34, 117, 76, 76);
		pnEmplacementsDesBoutons.add(btnPause);

		JButton btnProchainImage = new JButton(new ImageIcon(urlProchaineImage));
		btnProchainImage.setBounds(34, 213, 76, 76);
		pnEmplacementsDesBoutons.add(btnProchainImage);

		JButton btnRecommencer = new JButton(new ImageIcon(urlRecommencer));
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee1.arreter();
				sceneAnimee2.arreter();
				sceneAnimee1 = new SceneAnimee();
				sceneAnimee1.setBounds(174, 51, 260, 260);
				pnSimulations.add(sceneAnimee1);
				sceneAnimee2 = new SceneAnimee();
				sceneAnimee2.setBounds(174, 371, 260, 260);
				pnSimulations.add(sceneAnimee2);
				sceneAnimee1.repaint();
				sceneAnimee2.repaint();

			}
		});//À CHANGER 
		btnRecommencer.setBounds(34, 311, 76, 76);
		pnEmplacementsDesBoutons.add(btnRecommencer);

		JLabel lblSimulationAvecLAlgorithme = new JLabel("Simulation avec l'algorithme");
		lblSimulationAvecLAlgorithme.setBounds(211, 329, 187, 14);
		pnSimulations.add(lblSimulationAvecLAlgorithme);

		JLabel lblSimulationSansLAlgorithme = new JLabel("Simulation sans l'algorithme");
		lblSimulationSansLAlgorithme.setBounds(229, 25, 214, 14);
		pnSimulations.add(lblSimulationSansLAlgorithme);

		sceneAnimee1 = new SceneAnimee();
		sceneAnimee1.setBounds(174, 51, 260, 260);
		pnSimulations.add(sceneAnimee1);

		sceneAnimee2 = new SceneAnimee();
		sceneAnimee2.setBounds(174, 348, 260, 260);
		pnSimulations.add(sceneAnimee2);

		JLabel lblVideo = new JLabel("Param\u00E8tres");
		lblVideo.setBounds(568, 130, 123, 14);
		contentPane.add(lblVideo);

		JLabel lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setBounds(480, 176, 123, 14);
		contentPane.add(lblVitesseMoyenne);

		spnVitesse = new JSpinner();
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				double VitesseKmParHeure = (int)spnVitesse.getValue();
				//convertir les km/h en m/s (1m/s = (1km/h)*(1000m/km)/(3600s/h)
				double VitesseMParSeconde = VitesseKmParHeure*1000.0/3600.0;
				sceneAnimee1.setVitesse(VitesseMParSeconde);
			}
		});
		spnVitesse.setModel(new SpinnerNumberModel(new Integer(60), new Integer(0), null, new Integer(1)));
		spnVitesse.setBounds(587, 173, 48, 20);
		contentPane.add(spnVitesse);

		JLabel lblKmParHeure = new JLabel("km/h");
		lblKmParHeure.setBounds(645, 176, 46, 14);
		contentPane.add(lblKmParHeure);

		JLabel lblTauxDApparition = new JLabel("Taux d'apparition :");
		lblTauxDApparition.setBounds(481, 225, 123, 14);
		contentPane.add(lblTauxDApparition);

		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//On modifie le taux pour les deux fenêtres d'animation
				sceneAnimee1.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				sceneAnimee2.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				System.out.println("Nouveau taux d'apparition : " + (Integer)spnTauxDApparition.getValue());
			}
		});
		spnTauxDApparition.setModel(new SpinnerNumberModel(new Integer(60), new Integer(1), null, new Integer(1)));
		spnTauxDApparition.setBounds(588, 222, 48, 20);
		contentPane.add(spnTauxDApparition);

		JLabel lblVoituresParMinute = new JLabel("Voitures/Minute");
		lblVoituresParMinute.setBounds(646, 225, 126, 14);
		contentPane.add(lblVoituresParMinute);

		chkbxVoie3 = new JCheckBox("Voie 3");
		chkbxVoie3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie3.isSelected()) {
					sceneAnimee1.addTrafficAnormale(3);
				}else {
					sceneAnimee1.addTrafficNormale(3);
				}
			}
		});
		chkbxVoie3.setEnabled(false);
		chkbxVoie3.setBounds(497, 325, 97, 23);
		contentPane.add(chkbxVoie3);

		chkbxVoie4 = new JCheckBox("Voie 4");
		chkbxVoie4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie4.isSelected()) {
					sceneAnimee1.addTrafficAnormale(4);
				}else {
					sceneAnimee1.addTrafficNormale(4);
				}
			}
		});
		chkbxVoie4.setEnabled(false);
		chkbxVoie4.setBounds(607, 325, 97, 23);
		contentPane.add(chkbxVoie4);

		chkbxVoie2 = new JCheckBox("Voie 2");
		chkbxVoie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie2.isSelected()) {
					sceneAnimee1.addTrafficAnormale(2);
				}else {
					sceneAnimee1.addTrafficNormale(2);
				}
			}
		});
		chkbxVoie2.setEnabled(false);
		chkbxVoie2.setBounds(607, 299, 97, 23);
		contentPane.add(chkbxVoie2);

		chkbxVoie1 = new JCheckBox("Voie 1");
		chkbxVoie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie1.isSelected()) {
					sceneAnimee1.addTrafficAnormale(1);
				} else {
					sceneAnimee1.addTrafficNormale(1);
				}
			}
		});
		chkbxVoie1.setEnabled(false);
		chkbxVoie1.setBounds(497, 299, 97, 23);
		contentPane.add(chkbxVoie1);

		JCheckBox checkBox_4 = new JCheckBox("Trafic anormal");
		checkBox_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(checkBox_4.isSelected()) {
					sceneAnimee1.setTrafficAnormale(true);
					sceneAnimee2.setTrafficAnormale(true);
					chkbxVoie3.setEnabled(true);
					chkbxVoie4.setEnabled(true);
					chkbxVoie2.setEnabled(true);
					chkbxVoie1.setEnabled(true);
				} else {
					sceneAnimee1.setTrafficAnormale(false);
					sceneAnimee2.setTrafficAnormale(false);
					chkbxVoie3.setEnabled(false);
					chkbxVoie4.setEnabled(false);
					chkbxVoie2.setEnabled(false);
					chkbxVoie1.setEnabled(false);
				}
			}
		});
		checkBox_4.setBounds(480, 264, 112, 23);
		contentPane.add(checkBox_4);

		JButton btnArret = new JButton("arret");
		btnArret.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee1.arreterVoitures();
				sceneAnimee2.arreterVoitures();
			}
		});
		btnArret.setBounds(559, 425, 89, 23);
		contentPane.add(btnArret);
	}
}

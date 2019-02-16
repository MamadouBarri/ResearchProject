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
		btnRecommencer.setBounds(34, 311, 76, 76);
		pnEmplacementsDesBoutons.add(btnRecommencer);
		
		JLabel lblSimulationAvecLAlgorithme = new JLabel("Simulation avec l'algorithme");
		lblSimulationAvecLAlgorithme.setBounds(211, 329, 187, 14);
		pnSimulations.add(lblSimulationAvecLAlgorithme);
		
		JLabel lblSimulationSansLAlgorithme = new JLabel("Simulation sans l'algorithme");
		lblSimulationSansLAlgorithme.setBounds(229, 25, 214, 14);
		pnSimulations.add(lblSimulationSansLAlgorithme);
		
		sceneAnimee1 = new SceneAnimee();
		sceneAnimee1.setBounds(192, 48, 240, 240);
		pnSimulations.add(sceneAnimee1);
		
		sceneAnimee2 = new SceneAnimee();
		sceneAnimee2.setBounds(192, 352, 240, 240);
		pnSimulations.add(sceneAnimee2);
		
		JLabel lblVideo = new JLabel("Param\u00E8tres");
		lblVideo.setBounds(568, 130, 123, 14);
		contentPane.add(lblVideo);
		
		JLabel label = new JLabel("Vitesse moyenne :");
		label.setBounds(480, 176, 123, 14);
		contentPane.add(label);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(587, 173, 48, 20);
		contentPane.add(spinner);
		
		JLabel label_4 = new JLabel("km/h");
		label_4.setBounds(645, 176, 46, 14);
		contentPane.add(label_4);
		
		JLabel label_2 = new JLabel("Taux d'apparition :");
		label_2.setBounds(481, 225, 123, 14);
		contentPane.add(label_2);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(588, 222, 48, 20);
		contentPane.add(spinner_1);
		
		JLabel label_3 = new JLabel("Voitures/Minute");
		label_3.setBounds(646, 225, 126, 14);
		contentPane.add(label_3);
		
		JCheckBox checkBox = new JCheckBox("Voie 3");
		checkBox.setEnabled(false);
		checkBox.setBounds(497, 325, 97, 23);
		contentPane.add(checkBox);
		
		JCheckBox checkBox_1 = new JCheckBox("Voie 4");
		checkBox_1.setEnabled(false);
		checkBox_1.setBounds(607, 325, 97, 23);
		contentPane.add(checkBox_1);
		
		JCheckBox checkBox_2 = new JCheckBox("Voie 2");
		checkBox_2.setEnabled(false);
		checkBox_2.setBounds(607, 299, 97, 23);
		contentPane.add(checkBox_2);
		
		JCheckBox checkBox_3 = new JCheckBox("Voie 1");
		checkBox_3.setEnabled(false);
		checkBox_3.setBounds(497, 299, 97, 23);
		contentPane.add(checkBox_3);
		
		JCheckBox checkBox_4 = new JCheckBox("Trafic anormal");
		checkBox_4.setBounds(480, 264, 112, 23);
		contentPane.add(checkBox_4);
	}
}

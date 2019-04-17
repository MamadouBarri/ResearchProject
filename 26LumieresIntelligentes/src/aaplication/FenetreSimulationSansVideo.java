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

import ecouteursperso.VisibiliteFenDepartListener;
import ecouteursperso.VisibiliteFenParamListener;
import ecouteursperso.VisibiliteFenStatistiquesListener;
import ecouteursperso.VisibiliteFenStatistiquesListener;

import javax.swing.event.ChangeEvent;
import sceneAnimee.SceneAnimee;
import sceneAnimee.SceneAnimeeAvecAlgo;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SpinnerNumberModel;
import sceneAnimee.SceneAnimeeAvecAlgoTempsDArret;
/**
 * JFrame qui compare notre algorithme à une intersection normale dans la vraie vie
 * avec une video d'une intersection et des simulations
 * @author Gayta
 *
 */
public class FenetreSimulationSansVideo extends JFrame {
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
	private SceneAnimeeAvecAlgo sceneAnimee2;
	private SceneAnimeeAvecAlgoTempsDArret sceneAnimee3; 
	private JCheckBox chkbxVoie4;
	private JCheckBox chkbxVoie3;
	private JCheckBox chkbxVoie2;
	private JCheckBox chkbxVoie1;
	private JSpinner spnTauxDApparition;
	private JSpinner spnVitesse;
	private ArrayList<VisibiliteFenDepartListener> listeEcouteurs = new ArrayList<VisibiliteFenDepartListener>();
	private ArrayList<VisibiliteFenParamListener> listeEcouteursFenParam = new ArrayList<VisibiliteFenParamListener>();
	private FenetreStatistiques fenStats;
	private JCheckBox chkbxTraficAnormal;
	private int nbVoituresMax;
	private int typeImages;
	private JLabel nbVoituresAGenerer;
	private JLabel lblNombreDeVoitures;
	private JLabel lblNombreDeVoitures2;
	private JLabel lblNombreDeVoitures3;
	private JLabel lblVitesseMoyenne1;
	private JLabel lblNombreVoituresGenerees1;
	private JLabel lblVitesseMoyenne2;
	private JLabel lblNombreVoituresGenerees2;
	private JLabel lblVitesseMoyenne3;
	private JLabel lblNombreVoituresGenerees3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreSimulationSansVideo frame = new FenetreSimulationSansVideo();
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
	public FenetreSimulationSansVideo(){
		setTitle("Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1920, 1080);

		

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);

		JMenuItem mntmMenuDeDpart = new JMenuItem("Menu de D\u00E9part");
		mntmMenuDeDpart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leverEvenFenetreDepartVisible();
			}
		});
		mnMenu.add(mntmMenuDeDpart);

		JMenuItem mntmChangerParametres = new JMenuItem("Changer les param\u00E8tres de simulation");
		mntmChangerParametres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leverEvenFenetreParametresListener();
			}
		});
		mnMenu.add(mntmChangerParametres);

		JMenuItem mntmStatisitques = new JMenuItem("Statisitques");
		mntmStatisitques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fenStats.setVisible(true);
				fenStats.miseAJour();
			}
		});
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
		pnSimulations.setBounds(0, 0, 1904, 730);
		contentPane.add(pnSimulations);
		pnSimulations.setLayout(null);

		JLabel lblSimulationSansLAlgorithme = new JLabel("Simulation sans l'algorithme");
		lblSimulationSansLAlgorithme.setBounds(224, 70, 214, 14);
		pnSimulations.add(lblSimulationSansLAlgorithme);

		sceneAnimee2 = new SceneAnimeeAvecAlgo();
		sceneAnimee2.setBounds(639, 109, 600, 600);
		pnSimulations.add(sceneAnimee2);

		sceneAnimee1 = new SceneAnimee();
		sceneAnimee1.addVisibiliteFenStatistiquesListener(new VisibiliteFenStatistiquesListener() {
			//Mamadou
			public void rendreFenetreStatistiquesVisible() {
				//creation de la fenetre de stats
				fenStats.setVisible(true);
				fenStats.creerLesGraphiques();
				fenStats.repaint();	
				sceneAnimee1.arreter();
				sceneAnimee2.arreter();
				sceneAnimee3.arreter();
			}

			@Override
			public void faireLaMiseAJour() {
				//Mise a jour des stats sur la fenêtre de la simulation
				//Voitures arretees
				lblNombreDeVoitures.setText("Nombre de voitures arrêtées: "  + SceneAnimee.nbVoituresEnAttente.get(SceneAnimee.nbVoituresEnAttente.size()-1));
				lblNombreDeVoitures2.setText("Nombre de voitures arrêtées: "  + SceneAnimeeAvecAlgo.nbVoituresEnAttente.get(SceneAnimeeAvecAlgo.nbVoituresEnAttente.size()-1));
				lblNombreDeVoitures3.setText("Nombre de voitures arrêtées: "  + SceneAnimeeAvecAlgoTempsDArret.nbVoituresEnAttente.get(SceneAnimeeAvecAlgoTempsDArret.nbVoituresEnAttente.size()-1));
				//Nombre de voitures generees
				lblNombreVoituresGenerees1.setText("Nombre de voitures générées: " + sceneAnimee1.getNombreVoituresGeneree());
				lblNombreVoituresGenerees2.setText("Nombre de voitures générées: " + sceneAnimee2.getNombreVoituresGeneree());
				lblNombreVoituresGenerees3.setText("Nombre de voitures générées: " + sceneAnimee3.getNombreVoituresGeneree());
				//Vitesse moyenne
				lblVitesseMoyenne1.setText("Vitesse moyenne: "  + SceneAnimee.moyenneDesVitesse.get(SceneAnimee.moyenneDesVitesse.size()-1));
				lblVitesseMoyenne2.setText("Vitesse moyenne: "  + SceneAnimeeAvecAlgo.moyenneDesVitesse.get(SceneAnimeeAvecAlgo.moyenneDesVitesse.size()-1));
				lblVitesseMoyenne3.setText("Vitesse moyenne: "  + SceneAnimeeAvecAlgoTempsDArret.moyenneDesVitesse.get(SceneAnimeeAvecAlgoTempsDArret.moyenneDesVitesse.size()-1));
				//La fenetres des stats
				fenStats.creerLesGraphiques();
				fenStats.miseAJour();
			}
		});
		sceneAnimee1.setBounds(10, 109, 600, 600);
		pnSimulations.add(sceneAnimee1);
		
		
		fenStats = new FenetreStatistiques();
		fenStats.setVisible(false);
		
		
		JLabel lblSimulationAvecLalgorithme = new JLabel("Simulation avec l'algorithme bas\u00E9e sur la densit\u00E9 de voitures\r\n");
		lblSimulationAvecLalgorithme.setBounds(742, 70, 386, 14);
		pnSimulations.add(lblSimulationAvecLalgorithme);
		
		sceneAnimee3 = new SceneAnimeeAvecAlgoTempsDArret();
		sceneAnimee3.setBounds(1282, 109, 600, 600);
		pnSimulations.add(sceneAnimee3);
		
		JLabel lblSimulationAvecLalgorithmeTempsDArret = new JLabel("Simulation avec l'algorithme bas\u00E9e sur le temps d'arr\u00EAt des voitures\r\n\r\n");
		lblSimulationAvecLalgorithmeTempsDArret.setBounds(1366, 70, 386, 14);
		pnSimulations.add(lblSimulationAvecLalgorithmeTempsDArret);

		JLabel lblVideo = new JLabel("Param\u00E8tres");
		lblVideo.setBounds(762, 855, 123, 14);
		contentPane.add(lblVideo);

		JLabel lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setBounds(762, 904, 123, 14);
		contentPane.add(lblVitesseMoyenne);

		spnVitesse = new JSpinner();
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(!spnVitesse.getValue().equals(null)) {
					double VitesseKmParHeure = (Integer)spnVitesse.getValue();
					//convertir les km/h en m/s (1m/s = (1km/h)*(1000m/km)/(3600s/h)
					double VitesseMParSeconde = VitesseKmParHeure*1000.0/3600.0;
					sceneAnimee1.setVitesse((int)VitesseMParSeconde);
					sceneAnimee2.setVitesse((int)VitesseMParSeconde);
					sceneAnimee3.setVitesse((int)VitesseMParSeconde);
				}
			}
		});
		spnVitesse.setModel(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(1)));
		spnVitesse.setBounds(869, 901, 48, 20);
		contentPane.add(spnVitesse);

		JLabel lblKmParHeure = new JLabel("km/h");
		lblKmParHeure.setBounds(927, 904, 46, 14);
		contentPane.add(lblKmParHeure);

		JLabel lblTauxDApparition = new JLabel("Taux d'apparition :");
		lblTauxDApparition.setBounds(763, 953, 123, 14);
		contentPane.add(lblTauxDApparition);

		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//On modifie le taux pour les deux fenêtres d'animation ainsi que le nombre de voitures dans le même écouteur
				sceneAnimee1.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				sceneAnimee2.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				sceneAnimee3.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				//System.out.println("Nouveau taux d'apparition : " + (Integer)spnTauxDApparition.getValue());
			}
		});
		spnTauxDApparition.setModel(new SpinnerNumberModel(new Integer(60), new Integer(1), null, new Integer(1)));
		spnTauxDApparition.setBounds(870, 950, 48, 20);
		contentPane.add(spnTauxDApparition);

		JLabel lblVoituresParMinute = new JLabel("Voitures/Minute");
		lblVoituresParMinute.setBounds(928, 953, 126, 14);
		contentPane.add(lblVoituresParMinute);

		chkbxVoie3 = new JCheckBox("Voie SUD");
		chkbxVoie3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie3.isSelected()) {
					setTrafficAnormal(3);
				}else {
					setTrafficNormal(3);
				}
			}
		});
		chkbxVoie3.setEnabled(false);
		chkbxVoie3.setBounds(1081, 916, 97, 23);
		contentPane.add(chkbxVoie3);

		chkbxVoie4 = new JCheckBox("Voie EST");
		chkbxVoie4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie4.isSelected()) {
					setTrafficAnormal(4);
				}else {
					setTrafficNormal(4);
				}
			}
		});
		chkbxVoie4.setEnabled(false);
		chkbxVoie4.setBounds(1191, 916, 97, 23);
		contentPane.add(chkbxVoie4);

		chkbxVoie2 = new JCheckBox("Voie OUEST");
		chkbxVoie2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie2.isSelected()) {
					setTrafficAnormal(2);
				}else {
					setTrafficNormal(2);
				}
			}
		});
		chkbxVoie2.setEnabled(false);
		chkbxVoie2.setBounds(1191, 890, 97, 23);
		contentPane.add(chkbxVoie2);

		chkbxVoie1 = new JCheckBox("Voie NORD");
		chkbxVoie1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxVoie1.isSelected()) {
					setTrafficAnormal(1);
				} else {
					setTrafficNormal(1);
				}
			}
		});
		chkbxVoie1.setEnabled(false);
		chkbxVoie1.setBounds(1081, 890, 97, 23);
		contentPane.add(chkbxVoie1);

		chkbxTraficAnormal = new JCheckBox("Trafic anormal");
		chkbxTraficAnormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxTraficAnormal.isSelected()) {
					sceneAnimee1.setTrafficAnormale(true);
					sceneAnimee2.setTrafficAnormale(true);
					sceneAnimee3.setTrafficAnormale(true);
					chkbxVoie3.setEnabled(true);
					if(chkbxVoie3.isSelected()) {
						setTrafficAnormal(3);
					}
					chkbxVoie4.setEnabled(true);
					if(chkbxVoie4.isSelected()) {
						setTrafficAnormal(4);
					}
					chkbxVoie2.setEnabled(true);
					if(chkbxVoie2.isSelected()) {
						setTrafficAnormal(2);
					}
					chkbxVoie1.setEnabled(true);
					if(chkbxVoie1.isSelected()) {
						setTrafficAnormal(1);
					}
				} else {
					sceneAnimee1.setTrafficAnormale(false);
					sceneAnimee2.setTrafficAnormale(false);
					sceneAnimee3.setTrafficAnormale(false);
					chkbxVoie3.setEnabled(false);
					if(chkbxVoie3.isSelected()) {
						setTrafficNormal(3);
					}
					chkbxVoie4.setEnabled(false);
					if(chkbxVoie4.isSelected()) {
						setTrafficNormal(4);
					}
					chkbxVoie2.setEnabled(false);
					if(chkbxVoie2.isSelected()) {
						setTrafficNormal(2);
					}
					chkbxVoie1.setEnabled(false);
					if(chkbxVoie1.isSelected()) {
						setTrafficNormal(1);
					}
				}
			}
		});
		chkbxTraficAnormal.setBounds(1064, 855, 112, 23);
		contentPane.add(chkbxTraficAnormal);

		JPanel pnEmplacementsDesBoutons = new JPanel();
		pnEmplacementsDesBoutons.setBounds(290, 894, 350, 103);
		contentPane.add(pnEmplacementsDesBoutons);
		pnEmplacementsDesBoutons.setForeground(Color.BLACK);
		pnEmplacementsDesBoutons.setBackground(Color.WHITE);
		pnEmplacementsDesBoutons.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Boutons ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		pnEmplacementsDesBoutons.setLayout(null);

		JButton btnAnimer = new JButton(new ImageIcon(urlAnimer));
		btnAnimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//On fait juste une scene pour l'instant
				sceneAnimee1.demarrer();
				sceneAnimee2.demarrer();
				sceneAnimee3.demarrer();
				//sceneAnimee2.demarrer();
			}
		});
		btnAnimer.setBounds(10, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnAnimer);

		JButton btnPause = new JButton(new ImageIcon(urlPause));
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sceneAnimee1.getEnCoursDAnimation()) {
				sceneAnimee1.arreter();
				sceneAnimee2.arreter();
				sceneAnimee3.arreter();
				}
			}
		});
		btnPause.setBounds(96, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnPause);

		JButton btnProchainImage = new JButton(new ImageIcon(urlProchaineImage));
		btnProchainImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sceneAnimee1.getEnCoursDAnimation()) {
					sceneAnimee1.arreter();
					sceneAnimee2.arreter();
					sceneAnimee3.arreter();
					} else {
						sceneAnimee1.prochainImage();
						sceneAnimee2.prochainImage();
						sceneAnimee3.prochainImage();
					}
				}
		});
		btnProchainImage.setBounds(182, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnProchainImage);

		JButton btnRecommencer = new JButton(new ImageIcon(urlRecommencer));
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sceneAnimee1.reinitialiser();
				sceneAnimee2.reinitialiser();
				sceneAnimee3.reinitialiser();
			}
		});//À CHANGER 
		btnRecommencer.setBounds(268, 16, 76, 76);
		pnEmplacementsDesBoutons.add(btnRecommencer);
		JLabel lblNombreDeVoituresAGenerer = new JLabel("NOMBRE DE VOITURES \u00C0 G\u00C9N\u00C9RER : ");
		lblNombreDeVoituresAGenerer.setBounds(1064, 966, 214, 14);
		contentPane.add(lblNombreDeVoituresAGenerer);

		nbVoituresAGenerer = new JLabel("");
		nbVoituresAGenerer.setBounds(1280, 966, 48, 14);
		contentPane.add(nbVoituresAGenerer);
		
		lblNombreDeVoitures = new JLabel("Nombre de voitures arr\u00EAt\u00E9es:\r\n");
		lblNombreDeVoitures.setBounds(88, 741, 281, 28);
		contentPane.add(lblNombreDeVoitures);
		
		lblNombreDeVoitures2 = new JLabel("Nombre de voitures arr\u00EAt\u00E9es:\r\n");
		lblNombreDeVoitures2.setBounds(687, 741, 286, 28);
		contentPane.add(lblNombreDeVoitures2);
		
		lblNombreDeVoitures3 = new JLabel("Nombre de voitures arr\u00EAt\u00E9es:\r\n");
		lblNombreDeVoitures3.setBounds(1323, 741, 275, 28);
		contentPane.add(lblNombreDeVoitures3);
		
		lblVitesseMoyenne2 = new JLabel("Vitesse moyenne:\r\n");
		lblVitesseMoyenne2.setBounds(687, 770, 286, 28);
		contentPane.add(lblVitesseMoyenne2);
		
		lblVitesseMoyenne3 = new JLabel("Vitesse moyenne:");
		lblVitesseMoyenne3.setBounds(1323, 770, 286, 28);
		contentPane.add(lblVitesseMoyenne3);
		
		lblVitesseMoyenne1 = new JLabel("Vitesse moyenne:\r\n");
		lblVitesseMoyenne1.setBounds(88, 770, 286, 28);
		contentPane.add(lblVitesseMoyenne1);
		
		lblNombreVoituresGenerees1 = new JLabel("Nombre de voitures pass\u00E9es:");
		lblNombreVoituresGenerees1.setBounds(88, 798, 286, 28);
		contentPane.add(lblNombreVoituresGenerees1);
		
		lblNombreVoituresGenerees2 = new JLabel("Nombre de voitures pass\u00E9es:");
		lblNombreVoituresGenerees2.setBounds(687, 798, 286, 28);
		contentPane.add(lblNombreVoituresGenerees2);
		
		lblNombreVoituresGenerees3 = new JLabel("Nombre de voitures pass\u00E9es:");
		lblNombreVoituresGenerees3.setBounds(1323, 798, 286, 28);
		contentPane.add(lblNombreVoituresGenerees3);
	}

	//Reiner
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut rouvrir la fenetre de depart
	 * @param objEcouteur objet qui desire savoir si on veut rouvrir la fenetre de depart
	 */
	public void addVisibiliteFenDepartListener(VisibiliteFenDepartListener objEcouteur) {
		listeEcouteurs.add(objEcouteur);
	}
	//Reiner
	/**
	 * indique aux objets ecouteurs qu'on desire rouvrir la fenetre de depart
	 */
	private void leverEvenFenetreDepartVisible() {	
		for(VisibiliteFenDepartListener ecout : listeEcouteurs ) {
			ecout.rendreFenetreDepartVisible();
		}
	}
	//Reiner
	/**
	 * ajoute un objet à la liste d'objet qui desirent savoir quand on veut rouvrir la fenetre de parametres
	 * @param visibiliteFenParamListener objet qui desire savoir quand on veut rouvrir la fenetre de parametres
	 */
	public void addVisibiliteFenParamListener(VisibiliteFenParamListener visibiliteFenParamListener) {
		listeEcouteursFenParam.add(visibiliteFenParamListener);
	}
	
	//Reiner
	/**
	 * indique aux objets ecouteurs qu'on desire rouvrir la fenetre de parametres
	 */
	private void leverEvenFenetreParametresListener() {
		for(VisibiliteFenParamListener ecout : listeEcouteursFenParam) {
			ecout.rendreFenetreParamVisible();
		}
	}
	//Reiner
	/**
	 * Setter qui indique à la scène qu'il y a du trafic anormal
	 * @param anom boolean qui indique à la scène qu'il y a du trafic anormal
	 */
	public void setTraficAnormal(boolean anom) {
		sceneAnimee1.setTrafficAnormale(anom);
		sceneAnimee2.setTrafficAnormale(anom);
		sceneAnimee3.setTrafficAnormale(anom);
		chkbxVoie3.setEnabled(anom);
		chkbxVoie4.setEnabled(anom);
		chkbxVoie2.setEnabled(anom);
		chkbxVoie1.setEnabled(anom);
		chkbxTraficAnormal.setSelected(anom);
	}
	//Reiner
	/**
	 * Setter qui donne du trafic anormale au liste contenant des numéros qui représentes une voie
	 * @param tabVoiesAnom liste contenant des numéros qui représentes une voie 
	 */
	public void setVoieAnormale(int[] tabVoiesAnom) {
		for(int i=0; i<tabVoiesAnom.length;i++) {
			switch(tabVoiesAnom[i]) {
			//indique qu'une voie a du trafic normal
			case 0:
				//indique à la scene la voie normale selon l'indice(ex: si la valeur de tab[1] = 0, on sait que la voie 2 (ou ouest) a du trafic normal
				switch(i) {
				case 0:
					chkbxVoie1.setSelected(false);
					sceneAnimee1.addTrafficNormale(1);
					sceneAnimee2.addTrafficNormale(1);
					sceneAnimee3.addTrafficNormale(1);
					break;
				case 1:
					chkbxVoie2.setSelected(false);
					sceneAnimee1.addTrafficNormale(2);
					sceneAnimee2.addTrafficNormale(2);
					sceneAnimee3.addTrafficNormale(2);
					break;
				case 2:
					chkbxVoie3.setSelected(false);
					sceneAnimee1.addTrafficNormale(3);
					sceneAnimee2.addTrafficNormale(3);
					sceneAnimee3.addTrafficNormale(3);
					break;
				case 3:
					chkbxVoie4.setSelected(false);
					sceneAnimee1.addTrafficNormale(4);
					sceneAnimee2.addTrafficNormale(4);
					sceneAnimee3.addTrafficNormale(4);
					break;
				}
				break;
				//si tab[i] vaut une valeur, cela veut dire qu'il y a une voie avec du traffic anormale
				//on identifie cette voie avec la valeur de tab[i]
			case 1:
				chkbxVoie1.setSelected(true);
				sceneAnimee1.addTrafficAnormale(1);
				sceneAnimee2.addTrafficAnormale(1);
				sceneAnimee3.addTrafficAnormale(1);
				break;
			case 2:
				chkbxVoie2.setSelected(true);
				sceneAnimee1.addTrafficAnormale(2);
				sceneAnimee2.addTrafficAnormale(2);
				sceneAnimee3.addTrafficAnormale(2);
				break;
			case 3:
				chkbxVoie3.setSelected(true);
				sceneAnimee1.addTrafficAnormale(3);
				sceneAnimee2.addTrafficAnormale(3);
				sceneAnimee3.addTrafficAnormale(3);
				break;
			case 4:
				chkbxVoie4.setSelected(true);
				sceneAnimee1.addTrafficAnormale(4);
				sceneAnimee2.addTrafficAnormale(4);
				sceneAnimee3.addTrafficAnormale(4);
				break;
			}
		}

	}
	//Reiner 
	/**
	 * Setter qui modifie la vitesse des voitures de la scène
	 * @param vitesse la vitesse des voitures de la scène
	 */
	public void setVitesse(int vitesse) {
		spnVitesse.setValue(vitesse);
	}
	//Reiner
	/**
	 * Setter qui modifie le taux d'apparition des voitures de la scène
	 * @param taux le taux d'apparition des voitures de la scène
	 */
	public void setTaux(int taux) {
		spnTauxDApparition.setValue(taux);
	}
	//Reiner
	/**
	 * Setter qui modifie le nombre de voitures générées par la scène
	 * @param nbVoitures le nombre de voitures générées par la scène
	 */
	public void setNombreDeVoituresMax(int nbVoitures) {
		this.nbVoituresMax = nbVoitures;
		System.out.println("Le nombre de voitures générées sera : " + nbVoitures);
		sceneAnimee1.setNbVoituresMax(nbVoitures);
		sceneAnimee2.setNbVoituresMax(nbVoitures);
		sceneAnimee3.setNbVoituresMax(nbVoitures);
	}
	
	//Reiner
		/**
		 * Setter qui modifie le nombre de voitures maximum générées par la scène
		 * @param nbVoitures le nombre de voitures maximum générées par la scène
		 */
	public int getNombreDeVoituresMax() {
		return(this.nbVoituresMax);
	}
	/**
	 * Setter du type des images des voitures
	 * @param typeImages type des images
	 */
	//Mamadou
	public void setTypeImages(int typeImages) {
			this.typeImages = typeImages;
			sceneAnimee1.setTypeImages(typeImages);
			sceneAnimee2.setTypeImages(typeImages);
			sceneAnimee3.setTypeImages(typeImages);
	}
	public void miseAJourText() {
		nbVoituresAGenerer.setText(Integer.toString(sceneAnimee1.getNbVoituresMax()));
	}
	public void setTrafficAnormal(int voie) {
		sceneAnimee1.addTrafficAnormale(voie);
		sceneAnimee2.addTrafficAnormale(voie);
		sceneAnimee3.addTrafficAnormale(voie);
	}
	public void setTrafficNormal(int voie) {
		sceneAnimee1.addTrafficNormale(voie);
		sceneAnimee2.addTrafficNormale(voie);
		sceneAnimee3.addTrafficNormale(voie);
	}
	public void setNbVoies(int nbVoiesEst, int nbVoiesOuest, int nbVoiesSud, int nbVoiesNord) {
		//Voies Est
		sceneAnimee1.setNbVoiesEst(nbVoiesEst);
		sceneAnimee2.setNbVoiesEst(nbVoiesEst);
		sceneAnimee3.setNbVoiesEst(nbVoiesEst);
		//Voies Ouest
		sceneAnimee1.setNbVoiesOuest(nbVoiesOuest);
		sceneAnimee2.setNbVoiesOuest(nbVoiesOuest);
		sceneAnimee3.setNbVoiesOuest(nbVoiesOuest);
		//Voies Sud
		sceneAnimee1.setNbVoiesSud(nbVoiesSud);
		sceneAnimee2.setNbVoiesSud(nbVoiesSud);
		sceneAnimee3.setNbVoiesSud(nbVoiesSud);
		//Voies Nord
		sceneAnimee1.setNbVoiesNord(nbVoiesNord);
		sceneAnimee2.setNbVoiesNord(nbVoiesNord);
		sceneAnimee3.setNbVoiesNord(nbVoiesNord);
	}
}

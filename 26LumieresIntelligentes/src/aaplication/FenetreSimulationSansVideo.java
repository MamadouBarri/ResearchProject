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

import javax.swing.event.ChangeEvent;
import sceneAnimee.SceneAnimee;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SpinnerNumberModel;


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
	private SceneAnimee sceneAnimee2;
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

	/**
	 * Create the frame.
	 */
	public FenetreSimulationSansVideo(){
		setTitle("Simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1800, 900);

		fenStats = new FenetreStatistiques();
		fenStats.setVisible(false);

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
		pnSimulations.setBounds(0, 0, 1308, 829);
		contentPane.add(pnSimulations);
		pnSimulations.setLayout(null);

		JLabel lblSimulationSansLAlgorithme = new JLabel("Simulation sans l'algorithme");
		lblSimulationSansLAlgorithme.setBounds(229, 25, 214, 14);
		pnSimulations.add(lblSimulationSansLAlgorithme);

		sceneAnimee2 = new SceneAnimee();
		sceneAnimee2.setBounds(698, 109, 600, 600);
		pnSimulations.add(sceneAnimee2);

		sceneAnimee1 = new SceneAnimee();
		sceneAnimee1.setBounds(37, 109, 600, 600);
		pnSimulations.add(sceneAnimee1);

		JLabel lblSimulationAvecLalgorithme = new JLabel("Simulation avec l'algorithme");
		lblSimulationAvecLalgorithme.setBounds(928, 65, 144, 14);
		pnSimulations.add(lblSimulationAvecLalgorithme);

		JLabel lblVideo = new JLabel("Param\u00E8tres");
		lblVideo.setBounds(1550, 98, 123, 14);
		contentPane.add(lblVideo);

		JLabel lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setBounds(1462, 144, 123, 14);
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
				}
			}
		});
		spnVitesse.setModel(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(1)));
		spnVitesse.setBounds(1569, 141, 48, 20);
		contentPane.add(spnVitesse);

		JLabel lblKmParHeure = new JLabel("km/h");
		lblKmParHeure.setBounds(1627, 144, 46, 14);
		contentPane.add(lblKmParHeure);

		JLabel lblTauxDApparition = new JLabel("Taux d'apparition :");
		lblTauxDApparition.setBounds(1463, 193, 123, 14);
		contentPane.add(lblTauxDApparition);

		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//On modifie le taux pour les deux fenêtres d'animation ainsi que le nombre de voitures dans le même écouteur
				sceneAnimee1.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				sceneAnimee2.setTauxDApparition((Integer)spnTauxDApparition.getValue());
				//System.out.println("Nouveau taux d'apparition : " + (Integer)spnTauxDApparition.getValue());
			}
		});
		spnTauxDApparition.setModel(new SpinnerNumberModel(new Integer(60), new Integer(1), null, new Integer(1)));
		spnTauxDApparition.setBounds(1570, 190, 48, 20);
		contentPane.add(spnTauxDApparition);

		JLabel lblVoituresParMinute = new JLabel("Voitures/Minute");
		lblVoituresParMinute.setBounds(1628, 193, 126, 14);
		contentPane.add(lblVoituresParMinute);

		chkbxVoie3 = new JCheckBox("Voie SUD");
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
		chkbxVoie3.setBounds(1479, 293, 97, 23);
		contentPane.add(chkbxVoie3);

		chkbxVoie4 = new JCheckBox("Voie EST");
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
		chkbxVoie4.setBounds(1589, 293, 97, 23);
		contentPane.add(chkbxVoie4);

		chkbxVoie2 = new JCheckBox("Voie OUEST");
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
		chkbxVoie2.setBounds(1589, 267, 97, 23);
		contentPane.add(chkbxVoie2);

		chkbxVoie1 = new JCheckBox("Voie NORD");
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
		chkbxVoie1.setBounds(1479, 267, 97, 23);
		contentPane.add(chkbxVoie1);

		chkbxTraficAnormal = new JCheckBox("Trafic anormal");
		chkbxTraficAnormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxTraficAnormal.isSelected()) {
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
		chkbxTraficAnormal.setBounds(1462, 232, 112, 23);
		contentPane.add(chkbxTraficAnormal);

		JPanel pnEmplacementsDesBoutons = new JPanel();
		pnEmplacementsDesBoutons.setBounds(1503, 338, 142, 414);
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
		btnProchainImage.setBounds(34, 222, 76, 76);
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
	}

	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut rouvrir la fenetre de depart
	 * @param objEcouteur objet qui desire savoir si on veut rouvrir la fenetre de depart
	 */
	public void addVisibiliteFenDepartListener(VisibiliteFenDepartListener objEcouteur) {
		listeEcouteurs.add(objEcouteur);
	}
	/**
	 * indique aux objets ecouteurs qu'on desire rouvrir la fenetre de depart
	 */
	private void leverEvenFenetreDepartVisible() {	
		for(VisibiliteFenDepartListener ecout : listeEcouteurs ) {
			ecout.rendreFenetreDepartVisible();
		}
	}
	/**
	 * ajoute un objet à la liste d'objet qui desirent savoir quand on veut rouvrir la fenetre de parametres
	 * @param visibiliteFenParamListener objet qui desire savoir quand on veut rouvrir la fenetre de parametres
	 */
	public void addVisibiliteFenParamListener(VisibiliteFenParamListener visibiliteFenParamListener) {
		listeEcouteursFenParam.add(visibiliteFenParamListener);
	}
	/**
	 * indique aux objets ecouteurs qu'on desire rouvrir la fenetre de parametres
	 */
	private void leverEvenFenetreParametresListener() {
		for(VisibiliteFenParamListener ecout : listeEcouteursFenParam) {
			ecout.rendreFenetreParamVisible();
		}
	}
	public void setTraficAnormal(boolean anom) {
		//sceneAnimee1.setTrafficAnormale(anom);
		//sceneAnimee2.setTrafficAnormale(anom);
		chkbxVoie3.setEnabled(anom);
		chkbxVoie4.setEnabled(anom);
		chkbxVoie2.setEnabled(anom);
		chkbxVoie1.setEnabled(anom);
		chkbxTraficAnormal.setSelected(anom);
	}
	public void setVoieAnormale(int[] tabVoiesAnom) {
		for(int i=0; i<tabVoiesAnom.length;i++) {
			switch(tabVoiesAnom[i]) {
			//indique qu'une voie a du trafic normal
			case 0:
				//indique à la scene la voie normale selon l'indice(ex: si la valeur de tab[1] = 0, on sait que la voie 2 (ou ouest) a du traffic normal
				switch(i) {
				case 0:
					chkbxVoie1.setSelected(false);
					sceneAnimee1.addTrafficNormale(1);
					sceneAnimee2.addTrafficNormale(1);
					break;
				case 1:
					chkbxVoie2.setSelected(false);
					sceneAnimee1.addTrafficNormale(2);
					sceneAnimee2.addTrafficNormale(2);
					break;
				case 2:
					chkbxVoie3.setSelected(false);
					sceneAnimee1.addTrafficNormale(3);
					sceneAnimee2.addTrafficNormale(3);
					break;
				case 3:
					chkbxVoie4.setSelected(false);
					sceneAnimee1.addTrafficNormale(4);
					sceneAnimee2.addTrafficNormale(4);
					break;
				}
				break;
				//si tab[i] vaut une valeur, cela veut dire qu'il y a une voie avec du traffic anormale
				//on identifie cette voie avec la valeur de tab[i]
			case 1:
				chkbxVoie1.setSelected(true);
				sceneAnimee1.addTrafficAnormale(1);
				sceneAnimee2.addTrafficAnormale(1);
				break;
			case 2:
				chkbxVoie2.setSelected(true);
				sceneAnimee1.addTrafficAnormale(2);
				sceneAnimee2.addTrafficAnormale(2);
				break;
			case 3:
				chkbxVoie3.setSelected(true);
				sceneAnimee1.addTrafficAnormale(3);
				sceneAnimee2.addTrafficAnormale(3);
				break;
			case 4:
				chkbxVoie4.setSelected(true);
				sceneAnimee1.addTrafficAnormale(4);
				sceneAnimee2.addTrafficAnormale(4);
				break;
			}
		}

	}
	public void setVitesse(int vitesse) {
		spnVitesse.setValue(vitesse);
	}
	public void setTaux(int taux) {
		spnTauxDApparition.setValue(taux);
	}
	public void setNombreDeVoituresMax(int nbVoitures) {
		this.nbVoituresMax = nbVoitures;
		System.out.println("Le nombre de voitures générées sera : " + nbVoitures);
		sceneAnimee1.setNbVoituresMax(nbVoitures);
		sceneAnimee2.setNbVoituresMax(nbVoitures);
	}
	public int getNombreDeVoituresMax() {
		return(this.nbVoituresMax);
	}
}

package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sceneAnimee.SceneAnimee;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.SpinnerNumberModel;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;

import ecouteursperso.ResultatsFenParamListener;
import ecouteursperso.VisibiliteFenDepartListener;
import ecouteursperso.VisibiliteFenSimulListener;
import ecouteursperso.VisibiliteFenSimulVideoListener;

import javax.swing.event.ChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
/**
 * JFrame qui permet l'utilisateur de modifier les paramètres de la simulation
 * @author Gayta//
 *
 */
public class FenetreParametres extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup btngrpChoixDeTypeDeSimulation = new ButtonGroup();
	private JSpinner spnVitesse;
	private JSpinner spnTauxDApparition;
	private JCheckBox chkbxTrfcAnom1;
	private JCheckBox chkbxTrfcAnom2;
	private JCheckBox chkbxTrfcAnom4;
	private JCheckBox chkbxTrfcAnom3;
	private JLabel lblVoieEst;
	private JLabel lblVoieOuest;
	private JLabel lblVoieSud;
	private JCheckBox chkbxTrfcAnom;
	private JSpinner spnNbVoitures;
	////listes contenant les objets qui veulent ecouter à cet objet
	private ArrayList<VisibiliteFenDepartListener> listeEcouteursFenDepart = new ArrayList<VisibiliteFenDepartListener>();
	private ArrayList<VisibiliteFenSimulListener> listeEcouteursFenSimul = new ArrayList<VisibiliteFenSimulListener>();
	private ArrayList<VisibiliteFenSimulVideoListener> listeEcouteursFenSimulVideo = new ArrayList<VisibiliteFenSimulVideoListener>();
	private ArrayList<ResultatsFenParamListener> listeEcouteursDeParam = new ArrayList<ResultatsFenParamListener>();
	//tableau contenant les voies avec traffic anormale
	int[] tabTrafficAnom = new int[4];
	int nbVoituresMax = 0;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	//booleans des images des voitures
	private int typeImages = 0; // 0 : images normales ; 1 : images de voitures de sport ; 2 : images de voitures classiques; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreParametres frame = new FenetreParametres();
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
	public FenetreParametres() {
		setTitle("Modifier les param\u00E8tres de la simulation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 644);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmMenuDeDepart = new JMenuItem("Menu de d\u00E9part");
		mntmMenuDeDepart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leverEvenFenetreDepartVisible();
			}
		});
		mnMenu.add(mntmMenuDeDepart);
		
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
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Param\u00E8tres", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(SystemColor.scrollbar);
		panel.setBounds(0, 0, 622, 584);
		contentPane.add(panel);
		
		JLabel lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setBounds(6, 26, 123, 14);
		panel.add(lblVitesseMoyenne);
		
		JLabel lblNbVoitures = new JLabel("Nombre de voitures :");
		lblNbVoitures.setBounds(6, 73, 123, 14);
		panel.add(lblNbVoitures);
		
		JLabel lblTauxDApparition = new JLabel("Taux d'apparition :");
		lblTauxDApparition.setBounds(6, 124, 123, 14);
		panel.add(lblTauxDApparition);
		
		spnVitesse = new JSpinner();
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				leverEvenGetParams();
			}
		});
		spnVitesse.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnVitesse.setBounds(113, 23, 48, 20);
		panel.add(spnVitesse);
		
		chkbxTrfcAnom = new JCheckBox("Trafic anormal");
		chkbxTrfcAnom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(chkbxTrfcAnom.isSelected()) {
					setEtatVoiesAnom(true);
				} else {
					setEtatVoiesAnom(false);
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom.setBounds(6, 159, 112, 23);
		panel.add(chkbxTrfcAnom);
		
		chkbxTrfcAnom1 = new JCheckBox("Voie NORD");
		chkbxTrfcAnom1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom1.isSelected()) {
					tabTrafficAnom[0] = 1;
				} else {
					tabTrafficAnom[0] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom1.setBounds(23, 194, 97, 23);
		panel.add(chkbxTrfcAnom1);
		
		chkbxTrfcAnom2 = new JCheckBox("Voie OUEST");
		chkbxTrfcAnom2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom2.isSelected()) {
					tabTrafficAnom[1] = 2;
				} else {
					tabTrafficAnom[1] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom2.setBounds(133, 194, 97, 23);
		panel.add(chkbxTrfcAnom2);
		
		chkbxTrfcAnom3 = new JCheckBox("Voie SUD");
		chkbxTrfcAnom3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom3.isSelected()) {
					tabTrafficAnom[2] = 3;
				} else {
					tabTrafficAnom[2] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom3.setBounds(23, 220, 97, 23);
		panel.add(chkbxTrfcAnom3);
		
		chkbxTrfcAnom4 = new JCheckBox("Voie EST");
		chkbxTrfcAnom4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom4.isSelected()) {
					tabTrafficAnom[3] = 4;
				} else {
					tabTrafficAnom[3] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom4.setBounds(133, 220, 97, 23);
		panel.add(chkbxTrfcAnom4);
		
		setEtatVoiesAnom(false);
		
		JLabel lblUnitesTauxDApparition = new JLabel("Voitures/Minute");
		lblUnitesTauxDApparition.setBounds(171, 124, 126, 14);
		panel.add(lblUnitesTauxDApparition);
		
		JLabel lblUniteVitesse = new JLabel("km/h");
		lblUniteVitesse.setBounds(171, 26, 46, 14);
		panel.add(lblUniteVitesse);
		
		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				leverEvenGetParams();
			}
		});
		spnTauxDApparition.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnTauxDApparition.setBounds(113, 121, 48, 20);
		panel.add(spnTauxDApparition);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leverEvenFenetreSimulationSansVideoVisible();
			}
		});
		btnConfirmer.setBounds(507, 550, 105, 23);
		panel.add(btnConfirmer);
		
		SceneAnimee sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(161, 292, 242, 243);
		panel.add(sceneAnimee);
		sceneAnimee.setLayout(null);
		
		JLabel lblVoieNord = new JLabel("Voie NORD");
		lblVoieNord.setBounds(258, 267, 89, 14);
		panel.add(lblVoieNord);
		
		lblVoieEst = new JLabel("Voie EST");
		lblVoieEst.setBounds(417, 404, 84, 14);
		panel.add(lblVoieEst);
		
		lblVoieOuest = new JLabel("Voie OUEST");
		lblVoieOuest.setBounds(90, 404, 89, 14);
		panel.add(lblVoieOuest);
		
		lblVoieSud = new JLabel("Voie SUD");
		lblVoieSud.setBounds(263, 546, 84, 14);
		panel.add(lblVoieSud);
		
		spnNbVoitures = new JSpinner();
		spnNbVoitures.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nbVoituresMax = (int)spnNbVoitures.getValue();
				leverEvenGetParams();
			}
		});
		spnNbVoitures.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnNbVoitures.setBounds(139, 70, 48, 20);
		panel.add(spnNbVoitures);
		
		JRadioButton rdbtnVoituresNormales = new JRadioButton("voitures normales");
		rdbtnVoituresNormales.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(rdbtnVoituresNormales.isSelected()) {
					typeImages = 0;
					leverEvenGetParams();
				}
			}
		});
		rdbtnVoituresNormales.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
		rdbtnVoituresNormales.setForeground(Color.BLACK);
		rdbtnVoituresNormales.setSelected(true);
		buttonGroup.add(rdbtnVoituresNormales);
		rdbtnVoituresNormales.setBounds(296, 39, 127, 23);
		panel.add(rdbtnVoituresNormales);
		
		JRadioButton rdbtnVoituresDeSport = new JRadioButton("voitures de sport");
		rdbtnVoituresDeSport.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnVoituresDeSport.isSelected()) {
					typeImages = 1;
					leverEvenGetParams();
				}
			}
		});
		rdbtnVoituresDeSport.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
		buttonGroup.add(rdbtnVoituresDeSport);
		rdbtnVoituresDeSport.setBounds(298, 145, 125, 23);
		panel.add(rdbtnVoituresDeSport);
		
		JRadioButton rdbtnVoituresClassiques = new JRadioButton("voitures classiques");
		rdbtnVoituresClassiques.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(rdbtnVoituresClassiques.isSelected()) {
					typeImages = 2;
					leverEvenGetParams();
				}
			}
		});
		rdbtnVoituresClassiques.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
		buttonGroup.add(rdbtnVoituresClassiques);
		rdbtnVoituresClassiques.setBounds(451, 39, 141, 23);
		panel.add(rdbtnVoituresClassiques);
		
		JLabel lblImageVoitureNormal = new JLabel("New label");
		lblImageVoitureNormal.setIcon(new ImageIcon(FenetreParametres.class.getResource("/icones/iconeNormal.jpg")));
		lblImageVoitureNormal.setBounds(301, 68, 120, 70);
		panel.add(lblImageVoitureNormal);
		
		JLabel lblImageVoitureSport = new JLabel("New label");
		lblImageVoitureSport.setIcon(new ImageIcon(FenetreParametres.class.getResource("/icones/iconeSport.jpg")));
		lblImageVoitureSport.setBounds(303, 173, 120, 70);
		panel.add(lblImageVoitureSport);
		
		JLabel lblImageVoitureClassique = new JLabel("New label");
		lblImageVoitureClassique.setIcon(new ImageIcon(FenetreParametres.class.getResource("/icones/iconeClassique.jpg")));
		lblImageVoitureClassique.setBounds(463, 68, 120, 70);
		panel.add(lblImageVoitureClassique);
		
		JLabel lblTypeDeVoiture = new JLabel("Type de Voiture :");
		lblTypeDeVoiture.setBounds(291, 11, 112, 20);
		panel.add(lblTypeDeVoiture);
	}
	//Reiner
	/**
	 * Setter qui permet de changer l'état des composantes qui modifient la simulaion
	 * @param etat boolean indiquant l'état des composantes qui modifient la simulaion
	 */
	public void setEtatParams(boolean etat) {
		spnTauxDApparition.setEnabled(etat);
		spnVitesse.setEnabled(etat);
		spnNbVoitures.setEnabled(etat);
		chkbxTrfcAnom.setEnabled(etat);
	}
	//Reiner
	/**
	 * Setter qui permet de changer l'état des composantes qui modifient le trafic de la simulaion
	 * @param etat boolean indiquant l'état des composantes qui modifient le trafic de la simulaion
	 */
	public void setEtatVoiesAnom(boolean etat) {
		chkbxTrfcAnom1.setEnabled(etat);
		chkbxTrfcAnom2.setEnabled(etat);
		chkbxTrfcAnom3.setEnabled(etat);
		chkbxTrfcAnom4.setEnabled(etat);
	}
	//Reiner
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut rouvrir la fenetre de depart
	 * @param objEcouteur objet ecouteur qui desire savoir quand on veut rouvrir la fenetre de depart
	 */
	public void addVisibiliteFenDepartListener(VisibiliteFenDepartListener objEcouteur) {
		listeEcouteursFenDepart.add(objEcouteur);
	}
	//Reiner
	/**
	 * indique aux objets ecouteurs qu'on desire rouvrir la fenetre de depart
	 */
	private void leverEvenFenetreDepartVisible() {	
		for(VisibiliteFenDepartListener ecout : listeEcouteursFenDepart ) {
			ecout.rendreFenetreDepartVisible();
		}
	}
	//Reiner
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut ouvrir la fenetre de simulations sans video
	 * @param visibiliteFenSimulListener objet qui desire savoir quand on veut ouvrir la fenetre de simulations sans video
	 */
	public void addVisibiliteFenSimulListener(VisibiliteFenSimulListener visibiliteFenSimulListener) {
		listeEcouteursFenSimul.add(visibiliteFenSimulListener);
	}
	//Reiner
	/**
	 * indique aux objets ecouteurs qu'on desire ouvrir la fenetre de simulations sans video
	 */
	private void leverEvenFenetreSimulationSansVideoVisible() {	
		for(VisibiliteFenSimulListener ecout : listeEcouteursFenSimul ) {
			ecout.rendreFenetreSimulationsSansVideoVisible();
		}
	}
	//Reiner
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut ouvrir la fenetre de simulations avec video
	 * @param visibiliteFenSimulVideoListener objet qui desire savoir quand on veut ouvrir la fenetre de simulations avec video
	 */
	public void addVisibiliteFenSimulVideoListener(VisibiliteFenSimulVideoListener visibiliteFenSimulVideoListener) {
		listeEcouteursFenSimulVideo.add(visibiliteFenSimulVideoListener);
	}
	//Reiner
	/**
	 * indique aux objets ecouteurs qu'on desire ouvrir la fenetre de simulations avec video
	 */
	private void leverEvenFenetreSimulationAvecVideoVisible() {	
		for(VisibiliteFenSimulVideoListener ecout : listeEcouteursFenSimulVideo ) {
			ecout.rendreFenetreSimulationsAvecVideoVisible();
		}
	}
	//Reiner
	/**
	 * /**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut modifier les pararmètres des simulations la fenetre de simulations avec video
	 *  @param resultatsFenPramListener objet qui desire savoir quand on veut modifier les pararmètres des simulations la fenetre de simulations avec video
	 */
	public void addResultatsFenParamListener(ResultatsFenParamListener resultatsFenPramListener) {
	    listeEcouteursDeParam.add(resultatsFenPramListener);
	}
	//Reiner
		/**
		 * indique aux objets ecouteurs les paramètres de la fenetre de simulations avec video
		 */
	private void leverEvenGetParams() {
		for(ResultatsFenParamListener ecout : listeEcouteursDeParam) {
			System.out.println("on leve evenement");
			ecout.typeImages(typeImages);
			ecout.VitesseDesVoitures((int)spnVitesse.getValue());
			ecout.NombreDeVoituresAGenerer((int)spnNbVoitures.getValue());
			ecout.TauxDApparitionDesVoitures((int)spnTauxDApparition.getValue());
			if(chkbxTrfcAnom.isSelected()) {
				ecout.isTraficAnormal(true);
			} else {
				ecout.isTraficAnormal(false);
			}
			if(chkbxTrfcAnom1.isSelected()||chkbxTrfcAnom2.isSelected()||chkbxTrfcAnom3.isSelected()||chkbxTrfcAnom4.isSelected()) {
			ecout.setVoiesAvecTraficAnormal(tabTrafficAnom);
			}
			System.out.println(listeEcouteursDeParam.size());
		}
	}
}

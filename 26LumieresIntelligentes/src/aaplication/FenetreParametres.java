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
 * @author Gayta
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
	private JButton btnChoisirVideo;
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
		
		JRadioButton rdbtnSimuler = new JRadioButton("Simuler l'intersection");
		rdbtnSimuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnSimuler.isSelected()) {
					setEtatParams(true);
					if(chkbxTrfcAnom.isSelected()) {
						setEtatVoiesAnom(true);
					}
				} else {
					setEtatParams(false);
					setEtatVoiesAnom(true);					
				}
			}
		});
		btngrpChoixDeTypeDeSimulation.add(rdbtnSimuler);
		rdbtnSimuler.setSelected(true);
		rdbtnSimuler.setBounds(6, 19, 154, 23);
		panel.add(rdbtnSimuler);
		
		JRadioButton rdbtnVideo = new JRadioButton("Vid\u00E9o");
		rdbtnVideo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(rdbtnVideo.isSelected()) {
					setEtatParams(false);
					setEtatVoiesAnom(false);
				} else {
					setEtatParams(true);
					if(chkbxTrfcAnom.isSelected()) {
						setEtatVoiesAnom(true);
					}
				}
			}
		});
		btngrpChoixDeTypeDeSimulation.add(rdbtnVideo);
		rdbtnVideo.setBounds(373, 133, 139, 23);
		panel.add(rdbtnVideo);
		
		JLabel lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setBounds(21, 69, 123, 14);
		panel.add(lblVitesseMoyenne);
		
		JLabel lblNbVoitures = new JLabel("Nombre de voitures :");
		lblNbVoitures.setBounds(21, 116, 123, 14);
		panel.add(lblNbVoitures);
		
		JLabel lblTauxDApparition = new JLabel("Taux d'apparition :");
		lblTauxDApparition.setBounds(21, 167, 123, 14);
		panel.add(lblTauxDApparition);
		
		spnVitesse = new JSpinner();
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				leverEvenGetParams();
			}
		});
		spnVitesse.setModel(new SpinnerNumberModel(new Integer(20), null, null, new Integer(1)));
		spnVitesse.setBounds(128, 66, 48, 20);
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
		chkbxTrfcAnom.setBounds(373, 19, 112, 23);
		panel.add(chkbxTrfcAnom);
		
		chkbxTrfcAnom1 = new JCheckBox("Voie NORD");
		chkbxTrfcAnom1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom.isSelected()&&chkbxTrfcAnom1.isSelected()) {
					tabTrafficAnom[0] = 1;
				} else {
					tabTrafficAnom[0] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom1.setBounds(390, 54, 97, 23);
		panel.add(chkbxTrfcAnom1);
		
		chkbxTrfcAnom2 = new JCheckBox("Voie OUEST");
		chkbxTrfcAnom2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom.isSelected()&&chkbxTrfcAnom2.isSelected()) {
					tabTrafficAnom[1] = 2;
				} else {
					tabTrafficAnom[1] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom2.setBounds(500, 54, 97, 23);
		panel.add(chkbxTrfcAnom2);
		
		chkbxTrfcAnom3 = new JCheckBox("Voie SUD");
		chkbxTrfcAnom3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom.isSelected()&&chkbxTrfcAnom3.isSelected()) {
					tabTrafficAnom[2] = 3;
				} else {
					tabTrafficAnom[2] = 3;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom3.setBounds(390, 80, 97, 23);
		panel.add(chkbxTrfcAnom3);
		
		chkbxTrfcAnom4 = new JCheckBox("Voie EST");
		chkbxTrfcAnom4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(chkbxTrfcAnom.isSelected()&&chkbxTrfcAnom4.isSelected()) {
					tabTrafficAnom[3] = 4;
				} else {
					tabTrafficAnom[3] = 0;
				}
				leverEvenGetParams();
			}
		});
		chkbxTrfcAnom4.setBounds(500, 80, 97, 23);
		panel.add(chkbxTrfcAnom4);
		
		setEtatVoiesAnom(false);
		
		JLabel lblUnitesTauxDApparition = new JLabel("Voitures/Minute");
		lblUnitesTauxDApparition.setBounds(186, 167, 126, 14);
		panel.add(lblUnitesTauxDApparition);
		
		JLabel lblUniteVitesse = new JLabel("km/h");
		lblUniteVitesse.setBounds(186, 69, 46, 14);
		panel.add(lblUniteVitesse);
		
		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				leverEvenGetParams();
			}
		});
		spnTauxDApparition.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnTauxDApparition.setBounds(128, 164, 48, 20);
		panel.add(spnTauxDApparition);
		
		btnChoisirVideo = new JButton("Choisir une vid\u00E9o");
		btnChoisirVideo.setEnabled(false);
		btnChoisirVideo.setBounds(441, 177, 143, 23);
		panel.add(btnChoisirVideo);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnSimuler.isSelected()) {
					leverEvenFenetreSimulationSansVideoVisible();
				} else {
					leverEvenFenetreSimulationAvecVideoVisible(); 
				}
			}
		});
		btnConfirmer.setBounds(258, 550, 105, 23);
		panel.add(btnConfirmer);
		
		SceneAnimee sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(258, 250, 242, 243);
		panel.add(sceneAnimee);
		sceneAnimee.setLayout(null);
		
		JLabel lblVoieNord = new JLabel("Voie NORD");
		lblVoieNord.setBounds(355, 225, 89, 14);
		panel.add(lblVoieNord);
		
		lblVoieEst = new JLabel("Voie EST");
		lblVoieEst.setBounds(514, 362, 84, 14);
		panel.add(lblVoieEst);
		
		lblVoieOuest = new JLabel("Voie OUEST");
		lblVoieOuest.setBounds(187, 362, 89, 14);
		panel.add(lblVoieOuest);
		
		lblVoieSud = new JLabel("Voie SUD");
		lblVoieSud.setBounds(360, 504, 84, 14);
		panel.add(lblVoieSud);
		
		spnNbVoitures = new JSpinner();
		spnNbVoitures.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nbVoituresMax = (int)spnNbVoitures.getValue();
				leverEvenGetParams();
			}
		});
		spnNbVoitures.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnNbVoitures.setBounds(154, 113, 48, 20);
		panel.add(spnNbVoitures);
		
		JRadioButton rdbtnVoituresNormales = new JRadioButton("voitures normales");
		rdbtnVoituresNormales.setSelected(true);
		buttonGroup.add(rdbtnVoituresNormales);
		rdbtnVoituresNormales.setBounds(33, 221, 125, 23);
		panel.add(rdbtnVoituresNormales);
		
		JRadioButton rdbtnVoituresDeSport = new JRadioButton("voitures de sport");
		buttonGroup.add(rdbtnVoituresDeSport);
		rdbtnVoituresDeSport.setBounds(35, 334, 125, 23);
		panel.add(rdbtnVoituresDeSport);
		
		JRadioButton rdbtnVoituresClassiques = new JRadioButton("voitures classiques");
		buttonGroup.add(rdbtnVoituresClassiques);
		rdbtnVoituresClassiques.setBounds(33, 446, 127, 23);
		panel.add(rdbtnVoituresClassiques);
		
		JLabel lblImageVoitureNormal = new JLabel("New label");
		lblImageVoitureNormal.setIcon(new ImageIcon("C:\\Users\\Barry_000\\git\\other\\26lumieresintelligentes\\26LumieresIntelligentes\\ressources\\iconeNormal.jpg"));
		lblImageVoitureNormal.setBounds(33, 250, 127, 77);
		panel.add(lblImageVoitureNormal);
		
		JLabel lblImageVoitureSport = new JLabel("New label");
		lblImageVoitureSport.setIcon(new ImageIcon("C:\\Users\\Barry_000\\git\\other\\26lumieresintelligentes\\26LumieresIntelligentes\\ressources\\iconeSport.jpg"));
		lblImageVoitureSport.setBounds(33, 362, 127, 77);
		panel.add(lblImageVoitureSport);
		
		JLabel lblImageVoitureClassique = new JLabel("New label");
		lblImageVoitureClassique.setIcon(new ImageIcon("C:\\Users\\Barry_000\\git\\other\\26lumieresintelligentes\\26LumieresIntelligentes\\ressources\\iconeClassique.jpg"));
		lblImageVoitureClassique.setBounds(33, 477, 127, 77);
		panel.add(lblImageVoitureClassique);
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
		btnChoisirVideo.setEnabled(!etat);
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
			ecout.VitesseDesVoitures((int)spnVitesse.getValue());
			ecout.NombreDeVoituresAGenerer((int)spnNbVoitures.getValue());
			ecout.TauxDApparitionDesVoitures((int)spnTauxDApparition.getValue());
			if(chkbxTrfcAnom.isSelected()) {
				ecout.isTraficAnormal(true);
			} else {
				ecout.isTraficAnormal(false);
			}
			ecout.setVoiesAvecTraficAnormal(tabTrafficAnom);
			ecout.NombreDeVoituresAGenerer(nbVoituresMax);
		}
	}
}

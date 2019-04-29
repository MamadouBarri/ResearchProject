package aaplication;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import sceneAnimee.SceneAnimee;
import javax.swing.JSpinner;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
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

import javax.swing.event.ChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
/**
 * JFrame qui permet l'utilisateur de modifier les paramètres de la simulation
 * @author Gayta//
 *
 */
public class FenetreParametres extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
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
	private ArrayList<ResultatsFenParamListener> listeEcouteursDeParam = new ArrayList<ResultatsFenParamListener>();
	//tableau contenant les voies avec traffic anormale
	int[] tabTrafficAnom = new int[4];
	int nbVoituresMax = 0;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	//booleans des images des voitures
	private int typeImages = 0; // 0 : images normales ; 1 : images de voitures de sport ; 2 : images de voitures classiques; 
	private JSpinner spnNbVoiesNord;
	private JSpinner spnNbVoiesOuest;
	private JSpinner spnNbVoiesSud;
	private JSpinner spnNbVoiesEst;
	private JMenuItem mntmAPropos;

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
		setBounds(1920/2-630/2, 1080/2-538/2, 630, 600);
		
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
		
		mntmAPropos = new JMenuItem("\u00C0 Propos");
		mntmAPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Auteurs : Mamadou Barri et Reiner Luis Gayta\n"+"Date de création : 29 avril 2019\n"+"Cette application a été conçue dans le cadre du cours SCD");
			}
		});
		mnMenu.add(mntmAPropos);
		mnMenu.add(mntmQuitter);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(SystemColor.scrollbar);
		panel.setBounds(0, 0, 614, 542);
		contentPane.add(panel);
		
		JLabel lblVitesseMoyenne = new JLabel("Vitesse moyenne :");
		lblVitesseMoyenne.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblVitesseMoyenne.setBounds(6, 94, 123, 14);
		panel.add(lblVitesseMoyenne);
		
		JLabel lblNbVoitures = new JLabel("Nombre de voitures :");
		lblNbVoitures.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblNbVoitures.setBounds(6, 141, 123, 14);
		panel.add(lblNbVoitures);
		
		JLabel lblTauxDApparition = new JLabel("Taux d'apparition :");
		lblTauxDApparition.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblTauxDApparition.setBounds(6, 192, 123, 14);
		panel.add(lblTauxDApparition);
		
		spnVitesse = new JSpinner();
		spnVitesse.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				leverEvenGetParams();
			}
		});
		spnVitesse.setModel(new SpinnerNumberModel(60, 20, 100, 1));
		spnVitesse.setBounds(126, 92, 48, 20);
		panel.add(spnVitesse);
		
		chkbxTrfcAnom = new JCheckBox("Trafic anormal (augmentation du flux)");
		chkbxTrfcAnom.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		chkbxTrfcAnom.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
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
		chkbxTrfcAnom.setBounds(6, 227, 224, 23);
		panel.add(chkbxTrfcAnom);
		
		chkbxTrfcAnom1 = new JCheckBox("Voie NORD");
		chkbxTrfcAnom1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		chkbxTrfcAnom1.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
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
		chkbxTrfcAnom1.setBounds(23, 262, 97, 23);
		panel.add(chkbxTrfcAnom1);
		
		chkbxTrfcAnom2 = new JCheckBox("Voie OUEST");
		chkbxTrfcAnom2.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		chkbxTrfcAnom2.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
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
		chkbxTrfcAnom2.setBounds(133, 262, 97, 23);
		panel.add(chkbxTrfcAnom2);
		
		chkbxTrfcAnom3 = new JCheckBox("Voie SUD");
		chkbxTrfcAnom3.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		chkbxTrfcAnom3.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
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
		chkbxTrfcAnom3.setBounds(23, 288, 97, 23);
		panel.add(chkbxTrfcAnom3);
		
		chkbxTrfcAnom4 = new JCheckBox("Voie EST");
		chkbxTrfcAnom4.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		chkbxTrfcAnom4.setBackground(UIManager.getColor("ScrollBar.trackForeground"));
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
		chkbxTrfcAnom4.setBounds(133, 288, 97, 23);
		panel.add(chkbxTrfcAnom4);
		
		setEtatVoiesAnom(false);
		
		JLabel lblUnitesTauxDApparition = new JLabel("Voitures/Minute");
		lblUnitesTauxDApparition.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblUnitesTauxDApparition.setBounds(185, 192, 126, 14);
		panel.add(lblUnitesTauxDApparition);
		
		JLabel lblUniteVitesse = new JLabel("km/h");
		lblUniteVitesse.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblUniteVitesse.setBounds(184, 94, 46, 14);
		panel.add(lblUniteVitesse);
		
		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				leverEvenGetParams();
			}
		});
		spnTauxDApparition.setModel(new SpinnerNumberModel(60, 30, 120, 1));
		spnTauxDApparition.setBounds(127, 190, 48, 20);
		panel.add(spnTauxDApparition);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		btnConfirmer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leverEvenFenetreSimulationSansVideoVisible();
			}
		});
		btnConfirmer.setBounds(243, 508, 105, 23);
		panel.add(btnConfirmer);
		
		SceneAnimee sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(345, 120, 200, 200);
		sceneAnimee.setTailleLum(50);
		panel.add(sceneAnimee);
		sceneAnimee.setLayout(null);
		
		JLabel lblVoieNord = new JLabel("Voies NORD");
		lblVoieNord.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblVoieNord.setBounds(419, 73, 89, 14);
		panel.add(lblVoieNord);
		
		lblVoieEst = new JLabel("Voies EST");
		lblVoieEst.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblVoieEst.setBounds(553, 200, 84, 14);
		panel.add(lblVoieEst);
		
		lblVoieOuest = new JLabel("Voies OUEST");
		lblVoieOuest.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblVoieOuest.setBounds(275, 200, 89, 14);
		panel.add(lblVoieOuest);
		
		lblVoieSud = new JLabel("Voies SUD");
		lblVoieSud.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblVoieSud.setBounds(422, 352, 84, 14);
		panel.add(lblVoieSud);
		
		spnNbVoitures = new JSpinner();
		spnNbVoitures.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nbVoituresMax = (Integer)spnNbVoitures.getValue();
				leverEvenGetParams();
			}
		});
		spnNbVoitures.setModel(new SpinnerNumberModel(60, 0, 150, 1));
		spnNbVoitures.setBounds(126, 139, 48, 20);
		panel.add(spnNbVoitures);
		
		JRadioButton rdbtnVoituresNormales = new JRadioButton("Voitures normales");
		rdbtnVoituresNormales.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
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
		rdbtnVoituresNormales.setBounds(28, 374, 147, 23);
		panel.add(rdbtnVoituresNormales);
		
		JRadioButton rdbtnVoituresDeSport = new JRadioButton("Voitures de sport");
		rdbtnVoituresDeSport.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
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
		rdbtnVoituresDeSport.setBounds(420, 374, 125, 23);
		panel.add(rdbtnVoituresDeSport);
		
		JRadioButton rdbtnVoituresClassiques = new JRadioButton("Voitures classiques");
		rdbtnVoituresClassiques.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
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
		rdbtnVoituresClassiques.setBounds(220, 374, 141, 23);
		panel.add(rdbtnVoituresClassiques);
		
		JLabel lblImageVoitureNormal = new JLabel("New label");
		lblImageVoitureNormal.setIcon(new ImageIcon(FenetreParametres.class.getResource("/icones/iconeNormal.jpg")));
		lblImageVoitureNormal.setBounds(33, 403, 120, 70);
		panel.add(lblImageVoitureNormal);
		
		JLabel lblImageVoitureSport = new JLabel("New label");
		lblImageVoitureSport.setIcon(new ImageIcon(FenetreParametres.class.getResource("/icones/iconeSport.jpg")));
		lblImageVoitureSport.setBounds(425, 403, 120, 70);
		panel.add(lblImageVoitureSport);
		
		JLabel lblImageVoitureClassique = new JLabel("New label");
		lblImageVoitureClassique.setIcon(new ImageIcon(FenetreParametres.class.getResource("/icones/iconeClassique.jpg")));
		lblImageVoitureClassique.setBounds(232, 403, 120, 70);
		panel.add(lblImageVoitureClassique);
		
		JLabel lblTypeDeVoiture = new JLabel("Type de Voiture :");
		lblTypeDeVoiture.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 11));
		lblTypeDeVoiture.setBounds(23, 346, 112, 20);
		panel.add(lblTypeDeVoiture);
		
		spnNbVoiesNord = new JSpinner();
		spnNbVoiesNord.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				sceneAnimee.setNbVoiesNord((int)spnNbVoiesNord.getValue());
				leverEvenGetParams();
			}
		});
		spnNbVoiesNord.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spnNbVoiesNord.setBounds(432, 88, 29, 20);
		panel.add(spnNbVoiesNord);
		
		spnNbVoiesSud = new JSpinner();
		spnNbVoiesSud.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sceneAnimee.setNbVoiesSud((int)spnNbVoiesSud.getValue());
				leverEvenGetParams();
			}
		});
		spnNbVoiesSud.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spnNbVoiesSud.setBounds(432, 329, 29, 20);
		panel.add(spnNbVoiesSud);
		
		spnNbVoiesEst = new JSpinner();
		spnNbVoiesEst.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sceneAnimee.setNbVoiesEst((int)spnNbVoiesEst.getValue());
				leverEvenGetParams();
			}
		});
		spnNbVoiesEst.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spnNbVoiesEst.setBounds(560, 217, 29, 20);
		panel.add(spnNbVoiesEst);
		
		spnNbVoiesOuest = new JSpinner();
		spnNbVoiesOuest.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				sceneAnimee.setNbVoiesOuest((int)spnNbVoiesOuest.getValue());
				leverEvenGetParams();
			}
		});
		spnNbVoiesOuest.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spnNbVoiesOuest.setBounds(295, 217, 29, 20);
		panel.add(spnNbVoiesOuest);
		
		JLabel lblParametres = new JLabel("Param\u00E8tres");
		lblParametres.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 29));
		lblParametres.setHorizontalAlignment(SwingConstants.CENTER);
		lblParametres.setBounds(133, 11, 330, 50);
		panel.add(lblParametres);
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
			ecout.VitesseDesVoitures((Integer)spnVitesse.getValue());
			ecout.NombreDeVoituresAGenerer((Integer)spnNbVoitures.getValue());
			ecout.TauxDApparitionDesVoitures((Integer)spnTauxDApparition.getValue());
			ecout.setNbVoies((Integer)spnNbVoiesEst.getValue(), (Integer)spnNbVoiesOuest.getValue(), (Integer)spnNbVoiesSud.getValue(), (Integer)spnNbVoiesNord.getValue());
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

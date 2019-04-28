package aaplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ecouteursperso.VisibiliteFenDepartListener;
import ecouteursperso.VisibiliteFenParamListener;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
/**
 * JFrame contenant les instructions qui indiquent comment utiliser l'application
 * @author Gayta
 *
 */

public class FenetreInstructions extends JFrame {
	//listes contenant les objets qui veulent ecouter à cet objet
	private ArrayList<VisibiliteFenDepartListener> listeEcouteurs = new ArrayList<VisibiliteFenDepartListener>();
	private ArrayList<VisibiliteFenParamListener> listeEcouteursFenParam = new ArrayList<VisibiliteFenParamListener>();
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FenetreInstructions frame = new FenetreInstructions();
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
	public FenetreInstructions() {
		setTitle("Guide d'utilisation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1920/2-1005/2, 1080/2-693/2, 1005, 693);
		
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
		
		JMenuItem mntmDebuterLaSimulation = new JMenuItem("D\u00E9buter la simulation");
		mntmDebuterLaSimulation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leverEvenFenetreParametresListener();
			}
		});
		mnMenu.add(mntmDebuterLaSimulation);
		
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
		/**
		 * Page 1
		 */
		ImageAvecDefilementConcepts guideDUtilisationPage1 = new ImageAvecDefilementConcepts();
		guideDUtilisationPage1.setBounds(23, 12, (int)(this.getWidth()*956.0/1005.0), (int)(this.getHeight()*610.0/693.0));
		
		//Pour fixer couleur du cadre
		guideDUtilisationPage1.setBackground(Color.gray);
		//Pour modifier la largeur du cadre 
		guideDUtilisationPage1.setLargeurCadre(0);
		//Pour charger l'image pre-fabriquee
		guideDUtilisationPage1.setFichierImage("Guide 1.PNG");
		/**
		 * Page 2
		 */
		ImageAvecDefilementConcepts guideDUtilisationPage2 = new ImageAvecDefilementConcepts();
		guideDUtilisationPage2.setBounds(23, 12, (int)(this.getWidth()*956.0/1005.0), (int)(this.getHeight()*610.0/693.0));
		
		//Pour fixer couleur du cadre
		guideDUtilisationPage2.setBackground(Color.gray);
		//Pour modifier la largeur du cadre 
		guideDUtilisationPage2.setLargeurCadre(0);
		//Pour charger l'image pre-fabriquee
		guideDUtilisationPage2.setFichierImage("Guide 2.PNG");
		/**
		 * Page 3
		 */
		ImageAvecDefilementConcepts guideDUtilisationPage3 = new ImageAvecDefilementConcepts();
		guideDUtilisationPage3.setBounds(23, 12, (int)(this.getWidth()*956.0/1005.0), (int)(this.getHeight()*610.0/693.0));
		
		//Pour fixer couleur du cadre
		guideDUtilisationPage3.setBackground(Color.gray);
		//Pour modifier la largeur du cadre 
		guideDUtilisationPage3.setLargeurCadre(0);
		//Pour charger l'image pre-fabriquee
		guideDUtilisationPage3.setFichierImage("guide 3.PNG");
		/**
		 * Page 4
		 */
		ImageAvecDefilementConcepts guideDUtilisationPage4 = new ImageAvecDefilementConcepts();
		guideDUtilisationPage4.setBounds(23, 12, (int)(this.getWidth()*956.0/1005.0), (int)(this.getHeight()*610.0/693.0));
		//Pour fixer couleur du cadre
		guideDUtilisationPage4.setBackground(Color.gray);
		//Pour modifier la largeur du cadre 
		guideDUtilisationPage4.setLargeurCadre(0);
		//Pour charger l'image pre-fabriquee
		guideDUtilisationPage4.setFichierImage("guide 4.PNG");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, (int)(this.getWidth()*969.0/1005.0), (int)(this.getHeight()*611.0/693.0));
		tabbedPane.addTab("Page 1", guideDUtilisationPage1);
		tabbedPane.addTab("Page 2", guideDUtilisationPage2);
		tabbedPane.addTab("Page 3", guideDUtilisationPage3);
		tabbedPane.addTab("Page 4", guideDUtilisationPage4);
		contentPane.add(tabbedPane);
		
		
	}
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut rouvrir la fenetre de depart
	 * @param objEcouteur objet qui desire savoir quand on veut rouvrit la fenetre de depart
	 */
	public void addVisibiliteFenDepartListener(VisibiliteFenDepartListener objEcouteur) {
		listeEcouteurs.add(objEcouteur);
	}
	/**
	 * indique aux objets ecouteurs si on desire faire apparaitre la fenetre de depart
	 */
	private void leverEvenFenetreDepartVisible() {	
		for(VisibiliteFenDepartListener ecout : listeEcouteurs ) {
			ecout.rendreFenetreDepartVisible();
		}
	}
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut ouvrir la fenetre de parametres
	 * @param visibiliteFenParamListener objet qui desire savoir quand on veut ouvrir la fenetre de parametres
	 */
	public void addVisibiliteFenParamListener(VisibiliteFenParamListener visibiliteFenParamListener) {
		listeEcouteursFenParam.add(visibiliteFenParamListener);
	}
	/**
	 * indique aux objet écouteurs qu'on desire faire apparaitre la fenetre de parametres
	 */
	private void leverEvenFenetreParametresListener() {
		for(VisibiliteFenParamListener ecout : listeEcouteursFenParam) {
			ecout.rendreFenetreParamVisible();
		}
	}
}

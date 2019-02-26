package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ecouteursperso.VisibiliteFenDepartListener;
import ecouteursperso.VisibiliteFenParamListener;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FenetreConcepts extends JFrame {
	
	//Listes contenant les objets qui veulent écouter à cette fenetre
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
					FenetreConcepts frame = new FenetreConcepts();
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
	public FenetreConcepts() {
		setTitle("Concepts scientifiques");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 323);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnMenu = new JMenu("Menu");
		menuBar.add(mnMenu);
		
		JMenuItem mntmMenuDeDepart = new JMenuItem("Menu de d\u00E9part");
		mntmMenuDeDepart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 239);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
	}
	/**
	 * ajoute un objet à la liste d'objets qui desirent savoir quand on veut rouvrir la fenetre de depart
	 * @param objEcouteur objet qui desire savoir quand on veut rouvrir la fenetre de depart
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
	 * ajoute à la liste d'objets qui desirent savoir quand on veut ouvrir la fenetre de parametres
	 * @param visibiliteFenParamListener objet ecouteur qui desire savoir quand on veut ouvrir la fenetre de parametres
	 */
	public void addVisibiliteFenParamListener(VisibiliteFenParamListener visibiliteFenParamListener) {
		listeEcouteursFenParam.add(visibiliteFenParamListener);
	}
	/**
	 * indique aux objets ecouteurs si on desire faire apparaitre la fenetre de parametres
	 */
	private void leverEvenFenetreParametresListener() {
		for(VisibiliteFenParamListener ecout : listeEcouteursFenParam) {
			ecout.rendreFenetreParamVisible();
		}
	}
}

package aaplication;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import sceneAnimee.SceneAnimee;

public class FenetreSimulationAvecVideo extends JFrame {
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
						FenetreSimulationAvecVideo frame = new FenetreSimulationAvecVideo();
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
		public FenetreSimulationAvecVideo() {
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
			});//� CHANGER 
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
		}
}
package aaplication;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.SwingConstants;
import ecouteursperso.VisibiliteFenDepartListener;
import ecouteursperso.VisibiliteFenParamListener;
import ecouteursperso.VisibiliteFenSimulListener;
import ecouteursperso.ResultatsFenParamListener;
import ecouteursperso.VisibiliteFenStatistiquesListener;
/**
 * JFrame de départ permettant l'utilisateur d'accéder tous les fenêtres de notre application
 * @author Gayta
 *
 */

public class App26LumieresIntelligentes extends JFrame {

	private JPanel contentPane;
	private java.net.URL  urlPhotoDeDepart = getClass().getClassLoader().getResource("photoDeDepart.jpg");
	FenetreConcepts concepts;
	FenetreInstructions instructions;
	FenetreParametres parametres;
	FenetreSimulationSansVideo simulSansVideo;
	FenetreStatistiques fenetreStats;
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
	//Reiner
		/**
		 * Constructeur de la fenêtre
		 */
	public App26LumieresIntelligentes() {
		//création de la fenetre de simulations sans video
		simulSansVideo = new FenetreSimulationSansVideo();
		//ecouteur qui permet l'utilisateur de revenir à la fenetre de depart à partir de la fenetre de simulations sans video
		simulSansVideo.addVisibiliteFenDepartListener(new VisibiliteFenDepartListener(){
			@Override
			public void rendreFenetreDepartVisible() {
				simulSansVideo.setVisible(false);
				setVisible(true);
			}
		});
		//ecouteur qui permet l'utilisateur de revenir à la fenetre de parametres à partir de la fenêtre de simulations sans video
		simulSansVideo.addVisibiliteFenParamListener(new VisibiliteFenParamListener() {
			public void rendreFenetreParamVisible() {
				simulSansVideo.setVisible(false);
				parametres.setVisible(true);
			}});
		//creation de la fenetre de concepts scientifiques
		concepts = new FenetreConcepts();
		//ecouteur qui permet l'utilisateur de revenir à la fenetre de depart à partir de la fenetre de concepts scientifiques
		concepts.addVisibiliteFenDepartListener(new VisibiliteFenDepartListener() {

			@Override
			public void rendreFenetreDepartVisible() {
				concepts.setVisible(false);
				setVisible(true);
			}
			
		});
		//ecouteur qui permet l'utilisateur d'ouvrir la fenetre de parametres à partir de la fenetre de concepts scientifiques
		concepts.addVisibiliteFenParamListener(new VisibiliteFenParamListener() {
			public void rendreFenetreParamVisible() {
				concepts.setVisible(false);
				parametres.setVisible(true);
			}
		});
		//creation de la fenetre d'instructions
		instructions = new FenetreInstructions();
		//ecouteur qui permet l'utilisateur de revenir à la fenetre de depart à partir de la fenetre d'instructions
		instructions.addVisibiliteFenDepartListener(new VisibiliteFenDepartListener() {
			public void rendreFenetreDepartVisible() {
				instructions.setVisible(false);
				setVisible(true);
			}
		});
		//ecouteur qui permet l'utilisateur d'ouvrir la fenetre de parametres à partir de la fenetre d'instructions
		instructions.addVisibiliteFenParamListener(new VisibiliteFenParamListener() {
			@Override
			public void rendreFenetreParamVisible() {
				parametres.setVisible(true);
				instructions.setVisible(false);
			}
		});
		//creation de la fenetre de parametres
		parametres = new FenetreParametres();
		//ecouteur qui permet l'utilisateur de revenir à la fenetre de depart à partir de la fenetre de parametres
		parametres.addVisibiliteFenDepartListener(new VisibiliteFenDepartListener() {

			@Override
			public void rendreFenetreDepartVisible() {
				parametres.setVisible(false);
				setVisible(true);
			}
			
		});
		//ecouteur qui permet l'utilisateur d'ouvrir la fenetre de simulations sans video
		parametres.addVisibiliteFenSimulListener(new VisibiliteFenSimulListener() {

			@Override
			public void rendreFenetreSimulationsSansVideoVisible() {
				parametres.setVisible(false);
				simulSansVideo.miseAJourText();
				simulSansVideo.setVisible(true);
			}
			
		});
		parametres.addResultatsFenParamListener(new ResultatsFenParamListener() {
			public void VitesseDesVoitures(int vitesse) {
				simulSansVideo.setVitesse(vitesse);
			}

			@Override
			public void TauxDApparitionDesVoitures(int taux) {
				simulSansVideo.setTaux(taux);
			}

			@Override
			public void NombreDeVoituresAGenerer(int nbVoitures) {
				simulSansVideo.setNombreDeVoituresMax(nbVoitures);
			}

			@Override
			public void isTraficAnormal(boolean anormale) {
				simulSansVideo.setTraficAnormal(anormale);
			}

			@Override
			public void setVoiesAvecTraficAnormal(int[] listeVoiesAnormales) {
				simulSansVideo.setVoieAnormale(listeVoiesAnormales);
			}

			@Override
			public void typeImages(int typeImages) {
				simulSansVideo.setTypeImages(typeImages);
			}

			@Override
			public void setNbVoies(int nbVoiesEst, int nbVoiesOuest, int nbVoiesSud, int nbVoiesNord) {
				simulSansVideo.setNbVoies(nbVoiesEst, nbVoiesOuest, nbVoiesSud, nbVoiesNord);
			}
			
		});
		
		setTitle("Lumi\u00E8res Intelligentes - Mamadou Barri, Reiner Luis Gayta\r\n");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 675, 543);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitre = new JLabel("Lumi\u00E8res intelligentes");
		lblTitre.setForeground(Color.YELLOW);
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 36));
		lblTitre.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitre.setBounds(39, 26, 568, 110);
		contentPane.add(lblTitre);
		
		JButton btnDepart = new JButton("Commencer");
		btnDepart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parametres.setVisible(true);
				setVisible(false);
			}
		});
		btnDepart.setBounds(248, 171, 171, 46);
		contentPane.add(btnDepart);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				instructions.setVisible(true);
				setVisible(false);
			}
		});
		btnInstructions.setBounds(248, 246, 171, 46);
		contentPane.add(btnInstructions);
		
		JButton btnConceptsScientifiques = new JButton("Concepts Scientifiques");
		btnConceptsScientifiques.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				concepts.setVisible(true);
				setVisible(false);
			}
		});
		btnConceptsScientifiques.setBounds(248, 325, 171, 46);
		contentPane.add(btnConceptsScientifiques);
		
		JButton btnQuitter = new JButton("Quitter");
		btnQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnQuitter.setBounds(248, 400, 171, 46);
		contentPane.add(btnQuitter);
		
		JLabel lblphoto = new JLabel((new ImageIcon(urlPhotoDeDepart)));
		lblphoto.setBounds(0, 0, 659, 504);
		contentPane.add(lblphoto);
	}
}

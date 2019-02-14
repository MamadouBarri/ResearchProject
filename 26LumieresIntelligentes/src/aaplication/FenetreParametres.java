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
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class FenetreParametres extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup btngrpChoixDeTypeDeSimulation = new ButtonGroup();
	private JSpinner spnVitesse;
	private JSpinner spnTauxDApparition;
	private JSpinner spnNbVoies1;
	private JSpinner spnNbVoies4;
	private JSpinner spnNbVoies2;
	private JSpinner spnNbVoies3;
	private JCheckBox chkbxTrfcAnom1;
	private JCheckBox chkbxTrfcAnom2;
	private JCheckBox chkbxTrfcAnom4;
	private JCheckBox chkbxTrfcAnom3;
	private JButton btnChoisirVideo;
	private JLabel lblVoie2;
	private JLabel lblVoie4;
	private JLabel lblVoie3;
	private JCheckBox chkbxTrfcAnom;
	private JSpinner spnNbVoitures;

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

	/**
	 * Create the frame.
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
		
		JLabel label = new JLabel("Vitesse moyenne :");
		label.setBounds(21, 69, 123, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Nombre de voitures :");
		label_1.setBounds(21, 116, 123, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Taux d'apparition :");
		label_2.setBounds(21, 167, 123, 14);
		panel.add(label_2);
		
		chkbxTrfcAnom = new JCheckBox("Trafic anormal");
		chkbxTrfcAnom.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(chkbxTrfcAnom.isSelected()) {
					setEtatVoiesAnom(true);
				} else {
					setEtatVoiesAnom(false);
				}
			}
		});
		chkbxTrfcAnom.setBounds(373, 19, 112, 23);
		panel.add(chkbxTrfcAnom);
		
		chkbxTrfcAnom1 = new JCheckBox("Voie 1");
		chkbxTrfcAnom1.setBounds(390, 54, 97, 23);
		panel.add(chkbxTrfcAnom1);
		
		chkbxTrfcAnom2 = new JCheckBox("Voie 2");
		chkbxTrfcAnom2.setBounds(500, 54, 97, 23);
		panel.add(chkbxTrfcAnom2);
		
		chkbxTrfcAnom3 = new JCheckBox("Voie 3");
		chkbxTrfcAnom3.setBounds(390, 80, 97, 23);
		panel.add(chkbxTrfcAnom3);
		
		chkbxTrfcAnom4 = new JCheckBox("Voie 4");
		chkbxTrfcAnom4.setBounds(500, 80, 97, 23);
		panel.add(chkbxTrfcAnom4);
		
		setEtatVoiesAnom(false);
		
		JLabel label_3 = new JLabel("Voitures/Minute");
		label_3.setBounds(186, 167, 126, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("km/h");
		label_4.setBounds(186, 69, 46, 14);
		panel.add(label_4);
		
		spnVitesse = new JSpinner();
		spnVitesse.setModel(new SpinnerNumberModel(new Integer(20), null, null, new Integer(1)));
		spnVitesse.setBounds(128, 66, 48, 20);
		panel.add(spnVitesse);
		
		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnTauxDApparition.setBounds(128, 164, 48, 20);
		panel.add(spnTauxDApparition);
		
		btnChoisirVideo = new JButton("Choisir une vid\u00E9o");
		btnChoisirVideo.setEnabled(false);
		btnChoisirVideo.setBounds(441, 177, 143, 23);
		panel.add(btnChoisirVideo);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.setBounds(258, 550, 105, 23);
		panel.add(btnConfirmer);
		
		spnNbVoies3 = new JSpinner();
		spnNbVoies3.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spnNbVoies3.setBounds(299, 496, 29, 20);
		panel.add(spnNbVoies3);
		
		SceneAnimee sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(190, 250, 242, 243);
		panel.add(sceneAnimee);
		sceneAnimee.setLayout(null);
		
		spnNbVoies1 = new JSpinner();
		spnNbVoies1.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spnNbVoies1.setBounds(299, 224, 29, 20);
		panel.add(spnNbVoies1);
		
		spnNbVoies4 = new JSpinner();
		spnNbVoies4.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spnNbVoies4.setBounds(151, 359, 29, 20);
		panel.add(spnNbVoies4);
		
		spnNbVoies2 = new JSpinner();
		spnNbVoies2.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spnNbVoies2.setBounds(442, 359, 29, 20);
		panel.add(spnNbVoies2);
		
		JLabel lblVoie1 = new JLabel("Voie #1");
		lblVoie1.setBounds(292, 207, 46, 14);
		panel.add(lblVoie1);
		
		lblVoie2 = new JLabel("Voie #2");
		lblVoie2.setBounds(481, 362, 46, 14);
		panel.add(lblVoie2);
		
		lblVoie4 = new JLabel("Voie #4");
		lblVoie4.setBounds(98, 362, 46, 14);
		panel.add(lblVoie4);
		
		lblVoie3 = new JLabel("Voie #3");
		lblVoie3.setBounds(292, 519, 46, 14);
		panel.add(lblVoie3);
		
		spnNbVoitures = new JSpinner();
		spnNbVoitures.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		spnNbVoitures.setBounds(143, 113, 48, 20);
		panel.add(spnNbVoitures);
	}
	
	public void setEtatParams(boolean etat) {
		spnTauxDApparition.setEnabled(etat);
		spnVitesse.setEnabled(etat);
		spnNbVoitures.setEnabled(etat);
		chkbxTrfcAnom.setEnabled(etat);
		btnChoisirVideo.setEnabled(!etat);
	}
	public void setEtatVoiesAnom(boolean etat) {
		chkbxTrfcAnom1.setEnabled(etat);
		chkbxTrfcAnom2.setEnabled(etat);
		chkbxTrfcAnom3.setEnabled(etat);
		chkbxTrfcAnom4.setEnabled(etat);
	}
}

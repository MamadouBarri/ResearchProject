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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FenetreParametres extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JSpinner spnVitesseMoyenne;
	private JSpinner spnTauxDApparition;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_3;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_2;
	private JButton button;
	private JCheckBox checkBox;
	private JSpinner spnNbVoies1;
	private JSpinner spnNbVoies4;
	private JSpinner spnNbVoies3;
	private JSpinner spnNbVoies2;
	private double tauxDApparition;
	

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
		setTitle("Choisir le nombre de voies");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 638, 623);
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
		
		JRadioButton radioButton = new JRadioButton("Simuler l'intersection");
		radioButton.setSelected(true);
		radioButton.setBounds(6, 19, 154, 23);
		panel.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("Vid\u00E9o");
		radioButton_1.setBounds(373, 133, 139, 23);
		panel.add(radioButton_1);
		
		JLabel label = new JLabel("Vitesse moyenne :");
		label.setBounds(21, 69, 123, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Nombre de voitures :");
		label_1.setBounds(21, 116, 123, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Taux d'apparition :");
		label_2.setBounds(21, 167, 123, 14);
		panel.add(label_2);
		
		checkBox = new JCheckBox("Trafic anormal");
		checkBox.setBounds(373, 19, 112, 23);
		panel.add(checkBox);
		
		checkBox_1 = new JCheckBox("Voie 1");
		checkBox_1.setBounds(390, 54, 97, 23);
		panel.add(checkBox_1);
		
		checkBox_2 = new JCheckBox("Voie 2");
		checkBox_2.setBounds(500, 54, 97, 23);
		panel.add(checkBox_2);
		
		checkBox_3 = new JCheckBox("Voie 3");
		checkBox_3.setBounds(390, 80, 97, 23);
		panel.add(checkBox_3);
		
		checkBox_4 = new JCheckBox("Voie 4");
		checkBox_4.setBounds(500, 80, 97, 23);
		panel.add(checkBox_4);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(151, 113, 48, 20);
		panel.add(textField);
		
		JLabel label_3 = new JLabel("Voitures/Minute");
		label_3.setBounds(186, 167, 126, 14);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("km/h");
		label_4.setBounds(186, 69, 46, 14);
		panel.add(label_4);
		
		spnVitesseMoyenne = new JSpinner();
		spnVitesseMoyenne.setBounds(128, 66, 48, 20);
		panel.add(spnVitesseMoyenne);
		
		spnTauxDApparition = new JSpinner();
		spnTauxDApparition.setBounds(128, 164, 48, 20);
		panel.add(spnTauxDApparition);
		
		button = new JButton("Choisir une vid\u00E9o");
		button.setBounds(441, 177, 143, 23);
		panel.add(button);
		
		JButton btnConfirmer = new JButton("Confirmer");
		btnConfirmer.setBounds(272, 530, 89, 23);
		panel.add(btnConfirmer);
		
		spnNbVoies3 = new JSpinner();
		spnNbVoies3.setModel(new SpinnerNumberModel(new Integer(1), null, null, new Integer(1)));
		spnNbVoies3.setBounds(299, 496, 29, 20);
		panel.add(spnNbVoies3);
		
		SceneAnimee sceneAnimee = new SceneAnimee();
		sceneAnimee.setBounds(190, 250, 242, 243);
		panel.add(sceneAnimee);
		
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
	}
}

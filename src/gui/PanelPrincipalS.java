package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class PanelPrincipalS {

	private JFrame frmGrupoPrctica;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelPrincipalS window = new PanelPrincipalS();
					window.frmGrupoPrctica.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PanelPrincipalS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGrupoPrctica = new JFrame();
		frmGrupoPrctica.setTitle("Grupo3 Pr\u00E1ctica1");
		frmGrupoPrctica.setBounds(100, 100, 674, 448);
		frmGrupoPrctica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGrupoPrctica.getContentPane().setLayout(null);
		
		JLabel lblNmeroDeGeneraciones = new JLabel("N\u00FAmero de generaciones:");
		lblNmeroDeGeneraciones.setBounds(10, 0, 123, 29);
		frmGrupoPrctica.getContentPane().add(lblNmeroDeGeneraciones);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(10, 26, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinner);
		
		JLabel lblTamaoDePoblacin = new JLabel("Tama\u00F1o de poblaci\u00F3n:");
		lblTamaoDePoblacin.setBounds(10, 48, 123, 29);
		frmGrupoPrctica.getContentPane().add(lblTamaoDePoblacin);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(10, 78, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinner_1);
		
		JLabel lblProbabilidadDeCruce = new JLabel("Probabilidad de cruce: (ej. 0.2)");
		lblProbabilidadDeCruce.setBounds(10, 103, 149, 29);
		frmGrupoPrctica.getContentPane().add(lblProbabilidadDeCruce);
		
		textField = new JTextField();
		textField.setText("0.6");
		textField.setBounds(10, 128, 86, 20);
		frmGrupoPrctica.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblProbabilidadDeMutacin = new JLabel("Probabilidad de mutaci\u00F3n: (ej. 0.2)");
		lblProbabilidadDeMutacin.setBounds(10, 151, 166, 29);
		frmGrupoPrctica.getContentPane().add(lblProbabilidadDeMutacin);
		
		textField_1 = new JTextField();
		textField_1.setText("0.05");
		textField_1.setColumns(10);
		textField_1.setBounds(10, 176, 86, 20);
		frmGrupoPrctica.getContentPane().add(textField_1);
		
		JLabel lblPorcentajeDeElitismo = new JLabel("Porcentaje de elitismo: (ej. 0.2)");
		lblPorcentajeDeElitismo.setBounds(10, 200, 166, 29);
		frmGrupoPrctica.getContentPane().add(lblPorcentajeDeElitismo);
		
		textField_2 = new JTextField();
		textField_2.setText("0.02");
		textField_2.setColumns(10);
		textField_2.setBounds(10, 225, 86, 20);
		frmGrupoPrctica.getContentPane().add(textField_2);
		
		JLabel lblPrecisinej = new JLabel("Precisi\u00F3n:");
		lblPrecisinej.setBounds(10, 251, 166, 29);
		frmGrupoPrctica.getContentPane().add(lblPrecisinej);
		
		textField_3 = new JTextField();
		textField_3.setText("0.0001");
		textField_3.setColumns(10);
		textField_3.setBounds(10, 276, 86, 20);
		frmGrupoPrctica.getContentPane().add(textField_3);
	}
}

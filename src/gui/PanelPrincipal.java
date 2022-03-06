package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.math.plot.Plot2DPanel;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.cruces.CruceMonopunto;
import algoritmoGenetico.mutaciones.MutacionBasica;
import algoritmoGenetico.seleccion.SeleccionRuleta;
import algoritmoGenetico.TiposCruce;
import algoritmoGenetico.TiposSeleccion;
import algoritmoGenetico.TiposMutacion;
import algoritmoGenetico.TiposFuncion;

public class PanelPrincipal {

	private static JFrame frmGrupoPrctica;
	private static JTextField textFieldProbCruce;
	private static JTextField textFieldProbMutacion;
	private static JTextField textFieldPorcElitismo;
	private static JTextField textFieldPrecision;

	private static AlgoritmoGenetico ag;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PanelPrincipal window = new PanelPrincipal();
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
	public PanelPrincipal() {
		initialize();
	}
	
	private static void inicializarAG(int maxgeneraciones, int nIndividuos,
			double probCruce, double probMutacion, double porcElitismo,
			TiposSeleccion s, TiposCruce c, TiposMutacion m, TiposFuncion f) {
		ag = new AlgoritmoGenetico();
		ag.setProbCruce(probCruce);
		ag.setProbMutacion(probMutacion);
		ag.setElitism(porcElitismo);
		ag.setMaxGeneraciones(maxgeneraciones);
		ag.inicializarPoblacion(f, nIndividuos);
		
		switch(s) {
		case Ruleta:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		case EstocasticoUniversal:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		case Truncamiento:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		case Restos:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		case TorneoProbabilistico:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		case TorneoDeterminista:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		}
		
		switch(c) {
		case Uniforme:
			ag.setCruce(new CruceMonopunto());
			break;
		case Monopunto:
			ag.setCruce(new CruceMonopunto());
			break;
		}
		
		switch(m) {
		case Basica:
			ag.setMutacion(new MutacionBasica());
			break;
		}
	}


	private static void bucleAG() {
		int generacionActual = 0;
		while(generacionActual < ag.getMaxGeneraciones()) {
			ag.Seleccion();
			ag.Cruce();
			ag.Mutacion();
			ag.Evaluar(generacionActual);
			//generaGrafica();
			//Siguiente generacion
			generacionActual++;
		}
		System.out.println("El mejor individuo de la evolucion ha dado: "+ ag.getMejorIndividuo().getFitness());
		generarGrafica();
	}
	
	private static void generarGrafica() {
		LinePlot grafica = new LinePlot();
		grafica.addArrayOfPoints("Mejor Absoluto", ag.getMejoresAbsolutos());
		grafica.addArrayOfPoints("Mejor de la generacion", ag.getMejoresGeneraciones());
		grafica.addArrayOfPoints("Media de generaciones", ag.getMediasGeneraciones());
		grafica.plot();
		
		JPanel panelGrafica = new JPanel();
		Plot2DPanel plt = grafica.getGraph();
		plt.setPreferredSize(new Dimension(591, 408));
		panelGrafica.add(plt);
		panelGrafica.setBounds(215, 56, 591, 408);
		frmGrupoPrctica.getContentPane().add(panelGrafica);
		frmGrupoPrctica.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmGrupoPrctica = new JFrame();
		frmGrupoPrctica.setTitle("Grupo3 Pr\u00E1ctica1");
		frmGrupoPrctica.setBounds(100, 100, 853, 549);
		frmGrupoPrctica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGrupoPrctica.getContentPane().setLayout(null);
		
		JLabel lblNmeroDeGeneraciones = new JLabel("N\u00FAmero de generaciones:");
		lblNmeroDeGeneraciones.setBounds(10, 0, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblNmeroDeGeneraciones);
		
		JSpinner spinnerNGeneraciones = new JSpinner();
		spinnerNGeneraciones.setModel(new SpinnerNumberModel(100, 2, 1000, 1));
		spinnerNGeneraciones.setBounds(10, 26, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinnerNGeneraciones);
		
		JLabel lblTamaoDePoblacin = new JLabel("Tama\u00F1o de poblaci\u00F3n:");
		lblTamaoDePoblacin.setBounds(10, 48, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblTamaoDePoblacin);
		
		JSpinner spinnerTamPoblacion = new JSpinner();
		spinnerTamPoblacion.setModel(new SpinnerNumberModel(100, 2, 1000, 1));
		spinnerTamPoblacion.setBounds(10, 78, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinnerTamPoblacion);
		
		JLabel lblProbabilidadDeCruce = new JLabel("Probabilidad de cruce: (ej. 0.2)");
		lblProbabilidadDeCruce.setBounds(10, 103, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblProbabilidadDeCruce);
		
		textFieldProbCruce = new JTextField();
		textFieldProbCruce.setText("0.6");
		textFieldProbCruce.setBounds(10, 128, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldProbCruce);
		textFieldProbCruce.setColumns(10);
		
		JLabel lblProbabilidadDeMutacin = new JLabel("Probabilidad de mutaci\u00F3n:");
		lblProbabilidadDeMutacin.setBounds(10, 151, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblProbabilidadDeMutacin);
		
		textFieldProbMutacion = new JTextField();
		textFieldProbMutacion.setText("0.05");
		textFieldProbMutacion.setColumns(10);
		textFieldProbMutacion.setBounds(10, 176, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldProbMutacion);
		
		JLabel lblPorcentajeDeElitismo = new JLabel("Porcentaje de elitismo:");
		lblPorcentajeDeElitismo.setBounds(10, 200, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblPorcentajeDeElitismo);
		
		textFieldPorcElitismo = new JTextField();
		textFieldPorcElitismo.setText("0.02");
		textFieldPorcElitismo.setColumns(10);
		textFieldPorcElitismo.setBounds(10, 225, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldPorcElitismo);
		
		JLabel lblPrecisinej = new JLabel("Precisi\u00F3n:");
		lblPrecisinej.setBounds(10, 251, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblPrecisinej);
		
		textFieldPrecision = new JTextField();
		textFieldPrecision.setText("0.0001");
		textFieldPrecision.setColumns(10);
		textFieldPrecision.setBounds(10, 276, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldPrecision);
		
		JLabel lblMetodoSeleccion = new JLabel("Metodo Selecci\u00F3n:");
		lblMetodoSeleccion.setBounds(10, 304, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoSeleccion);
		
		JComboBox comboBoxMetodoSeleccion = new JComboBox();
		comboBoxMetodoSeleccion.setModel(new DefaultComboBoxModel(TiposSeleccion.values()));
		comboBoxMetodoSeleccion.setBounds(10, 328, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoSeleccion);
		
		JLabel lblMetodoSeleccion_1 = new JLabel("Metodo Cruce:");
		lblMetodoSeleccion_1.setBounds(10, 361, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoSeleccion_1);
		
		JComboBox comboBoxMetodoCruce = new JComboBox();
		comboBoxMetodoCruce.setModel(new DefaultComboBoxModel(TiposCruce.values()));
		comboBoxMetodoCruce.setBounds(10, 385, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoCruce);
		
		JLabel lblMetodoSeleccion_2 = new JLabel("Metodo Mutaci\u00F3n:");
		lblMetodoSeleccion_2.setBounds(10, 418, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoSeleccion_2);
		
		JComboBox comboBoxMetodoMutacion = new JComboBox();
		comboBoxMetodoMutacion.setModel(new DefaultComboBoxModel(TiposMutacion.values()));
		comboBoxMetodoMutacion.setBounds(10, 442, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoMutacion);
		
		JButton botonEvolucionar = new JButton("Evolucionar");
		botonEvolucionar.setBounds(700, 476, 127, 23);
		frmGrupoPrctica.getContentPane().add(botonEvolucionar);
		
		JPanel panel = new JPanel();
		panel.setBounds(215, 56, 591, 408);
		frmGrupoPrctica.getContentPane().add(panel);
		
		JComboBox comboBoxFuncion = new JComboBox();
		comboBoxFuncion.setModel(new DefaultComboBoxModel(TiposFuncion.values()));
		comboBoxFuncion.setBounds(375, 20, 123, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxFuncion);
		
		JLabel lblFuncin = new JLabel("Funci\u00F3n:");
		lblFuncin.setBounds(323, 17, 54, 29);
		frmGrupoPrctica.getContentPane().add(lblFuncin);
		
		//Añadimos la funcionalidad de que empiece el AG
		botonEvolucionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inicializarAG((int)spinnerNGeneraciones.getValue(), (int)spinnerTamPoblacion.getValue(),
						Double.parseDouble(textFieldProbCruce.getText()), Double.parseDouble(textFieldProbMutacion.getText()), Double.parseDouble(textFieldPorcElitismo.getText()),
						TiposSeleccion.values()[comboBoxMetodoSeleccion.getSelectedIndex()] , TiposCruce.values()[comboBoxMetodoCruce.getSelectedIndex()],
						TiposMutacion.values()[comboBoxMetodoMutacion.getSelectedIndex()], TiposFuncion.values()[comboBoxFuncion.getSelectedIndex()]);
				bucleAG();
			}

		});
		
		
	}
}

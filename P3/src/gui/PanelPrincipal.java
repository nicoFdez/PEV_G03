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
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.math.plot.Plot2DPanel;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.TiposCruce;
import algoritmoGenetico.TiposInicializacion;
import algoritmoGenetico.TiposMultiplexor;
import algoritmoGenetico.TiposMutacion;
import algoritmoGenetico.TiposSeleccion;
import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.InfoMultiplexor;
import algoritmoGenetico.seleccion.SeleccionEstocasticoUniversal;
import algoritmoGenetico.seleccion.SeleccionRanking;
import algoritmoGenetico.seleccion.SeleccionRestos;
import algoritmoGenetico.seleccion.SeleccionRuleta;
import algoritmoGenetico.seleccion.SeleccionTorneoDeterminista;
import algoritmoGenetico.seleccion.SeleccionTorneoProbabilistico;
import algoritmoGenetico.seleccion.SeleccionTruncamiento;
import javax.swing.SwingConstants;


//Clase que implementa el punto de entrada de la ejecuci�n del programa y que sostiene tanto la ventana como el bucle principal
public class PanelPrincipal {

	private static JFrame frmGrupoPrctica;
	private static JTextField textFieldProbCruce;
	private static JTextField textFieldProbMutacion;
	private static JTextField textFieldPorcElitismo;
	private static JLabel TextoMejorIndividuo;
	private static JLabel TextoPeorIndividuo;
	private static JLabel TextoMediaPoblacion;
	private static JLabel TextoNCruces;
	private static JLabel TextoNMutaciones;

	private static AlgoritmoGenetico ag;
	private JTextField textFieldProbTorneo;
	private JTextField textFieldValorTruncamiento;

	
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
	
	//M�todo que recibe los par�metros configurados en la ventana de la aplicaci�n y los utliiza para configurar el algoritmo gen�tico
	private static void inicializarAG(int maxgeneraciones, int nIndividuos,
			double probCruce, double probMutacion, double porcElitismo,
			TiposSeleccion s, TiposCruce c, TiposMutacion m,
			int tamTorneo, double probTorneo, double valorTruncamiento,
			TiposInicializacion ini, TiposMultiplexor mux, int profMaxima) {
		//Creo y el algoritmo gen�tico y configuro los par�metros m�s generales
		
		InfoMultiplexor.init();
		ag = new AlgoritmoGenetico();
		ag.setProbCruce(probCruce);
		ag.setProbMutacion(probMutacion);
		ag.setElitism(porcElitismo);
		ag.setMaxGeneraciones(maxgeneraciones);
		
		switch(mux) {
		case Mux11:
			ag.inicializarPoblacion(nIndividuos, profMaxima, ini);
			break;
		case Mux6:
			ag.inicializarPoblacion(nIndividuos, profMaxima, ini);
			break;
		default:
			break;		
		}	
		
		//Preparo un operador de selecci�n u otro dependiendo de lo que me haya dicho la ventana
		switch(s) {
		case Ruleta:
			ag.setSeleccion(new SeleccionRuleta());
			break;
		case EstocasticoUniversal:
			ag.setSeleccion(new SeleccionEstocasticoUniversal());
			break;
		case Truncamiento:
			ag.setSeleccion(new SeleccionTruncamiento(valorTruncamiento));
			break;
		case Restos:
			ag.setSeleccion(new SeleccionRestos());
			break;
		case TorneoProbabilistico:
			ag.setSeleccion(new SeleccionTorneoProbabilistico(tamTorneo, probTorneo));
			break;
		case TorneoDeterminista:
			ag.setSeleccion(new SeleccionTorneoDeterminista(tamTorneo));
			break;
		case Ranking:
			ag.setSeleccion(new SeleccionRanking(1.5));
			break;
		}
		
		//Preparo un operador de cruce u otro dependiendo de lo que me haya dicho la ventana
		switch(c) {
		case Arbol:
			break;
		}
		
		//Preparo un operador de mutaci�n u otro dependiendo de lo que me haya dicho la ventana
		switch(m) {		
		case Terminal:
			break;
		}
	}


	//M�todo que sostiene el bucle principal de la evoluci�n del algoritmo gen�tico
	private static void bucleAG() {
		int generacionActual = 0;
		while(generacionActual < ag.getMaxGeneraciones()) {
			ag.saveElites();
			ag.Seleccion();
			ag.Cruce();
			ag.Mutacion();
			ag.recoverSavedElites();
			ag.Evaluar(generacionActual);
			generacionActual++;
		}
		Individuo a = ag.getMejorIndividuo();
		System.out.println("El mejor resultado: "+ a.getFitness());
		generarGrafica();
	}
	
	
	//M�todo que genera una gr�fica a partir de los datos que se hayan recogido de la evoluci�n del AG
	// y lo a�ade a la ventana para su visualizaci�n
	private static void generarGrafica() {
		
		//Generamos una grafica que contenga los datos del mejor hasta la fecha, el mejor de la generacion y la media de la generacion
		//a medida que van pasando las generaciones
		LinePlot grafica = new LinePlot();
		grafica.addArrayOfPoints("Mejor Absoluto", ag.getMejoresAbsolutos());
		grafica.addArrayOfPoints("Mejor de la generacion", ag.getMejoresGeneraciones());
		grafica.addArrayOfPoints("Media de generaciones", ag.getMediasGeneraciones());
		grafica.plot();
		
		TextoMejorIndividuo.setText("Mejor individuo: "+ ag.getMejorIndividuo().getFitness() +"   ");
		TextoPeorIndividuo.setText("Peor individuo: "+ ag.getPeorFitness());
		TextoMediaPoblacion.setText("Media poblaci�n: "+ ag.getMediaPoblacion());
		TextoNCruces.setText("N�mero de cruces: " + ag.getNCruces());
		TextoNMutaciones.setText("N�mero de mutaciones: " + ag.getNMutaciones());
		
		//A�adimos un panel con la grafica al JFrame para que se pueda ver en pantalla la grafica
		JPanel panelGrafica = new JPanel();
		Plot2DPanel plt = grafica.getGraph();
		plt.setPreferredSize(new Dimension(580, 373));
		panelGrafica.add(plt);
		panelGrafica.setBounds(183, 75, 580, 373);
		frmGrupoPrctica.getContentPane().add(panelGrafica);
		
		frmGrupoPrctica.setVisible(true);
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmGrupoPrctica = new JFrame();
		frmGrupoPrctica.setTitle("Grupo3 Pr\u00E1ctica 2");
		frmGrupoPrctica.setBounds(100, 100, 811, 567);
		frmGrupoPrctica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGrupoPrctica.getContentPane().setLayout(null);
		
		JLabel lblNmeroDeGeneraciones = new JLabel("N\u00FAmero de generaciones:");
		lblNmeroDeGeneraciones.setBounds(10, 41, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblNmeroDeGeneraciones);
		
		JSpinner spinnerNGeneraciones = new JSpinner();
		spinnerNGeneraciones.setModel(new SpinnerNumberModel(100, 2, 1000, 1));
		spinnerNGeneraciones.setBounds(10, 67, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinnerNGeneraciones);
		
		JLabel lblTamaoDePoblacin = new JLabel("Tama\u00F1o de poblaci\u00F3n:");
		lblTamaoDePoblacin.setBounds(10, 89, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblTamaoDePoblacin);
		
		JSpinner spinnerTamPoblacion = new JSpinner();
		spinnerTamPoblacion.setModel(new SpinnerNumberModel(100, 2, 1000, 1));
		spinnerTamPoblacion.setBounds(10, 119, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinnerTamPoblacion);
		
		JLabel lblProbabilidadDeCruce = new JLabel("Probabilidad de cruce: (ej. 0.2)");
		lblProbabilidadDeCruce.setBounds(10, 144, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblProbabilidadDeCruce);
		
		textFieldProbCruce = new JTextField();
		textFieldProbCruce.setText("0.6");
		textFieldProbCruce.setBounds(10, 169, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldProbCruce);
		textFieldProbCruce.setColumns(10);
		
		JLabel lblProbabilidadDeMutacin = new JLabel("Probabilidad de mutaci\u00F3n:");
		lblProbabilidadDeMutacin.setBounds(10, 192, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblProbabilidadDeMutacin);
		
		textFieldProbMutacion = new JTextField();
		textFieldProbMutacion.setText("0.05");
		textFieldProbMutacion.setColumns(10);
		textFieldProbMutacion.setBounds(10, 217, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldProbMutacion);
		
		JLabel lblPorcentajeDeElitismo = new JLabel("Porcentaje de elitismo:");
		lblPorcentajeDeElitismo.setBounds(10, 241, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblPorcentajeDeElitismo);
		
		textFieldPorcElitismo = new JTextField();
		textFieldPorcElitismo.setText("0.02");
		textFieldPorcElitismo.setColumns(10);
		textFieldPorcElitismo.setBounds(10, 266, 86, 20);
		frmGrupoPrctica.getContentPane().add(textFieldPorcElitismo);
		
		JLabel lblMetodoSeleccion = new JLabel("Metodo Selecci\u00F3n:");
		lblMetodoSeleccion.setBounds(10, 369, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoSeleccion);
		
		JComboBox comboBoxMetodoSeleccion = new JComboBox();
		comboBoxMetodoSeleccion.setModel(new DefaultComboBoxModel(TiposSeleccion.values()));
		comboBoxMetodoSeleccion.setBounds(10, 393, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoSeleccion);
		
		JLabel lblMetodoSeleccion_1 = new JLabel("Metodo Cruce:");
		lblMetodoSeleccion_1.setBounds(10, 413, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoSeleccion_1);
		
		JComboBox comboBoxMetodoCruce = new JComboBox();
		comboBoxMetodoCruce.setModel(new DefaultComboBoxModel(TiposCruce.values()));
		comboBoxMetodoCruce.setBounds(10, 437, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoCruce);
		
		JLabel lblMetodoSeleccion_2 = new JLabel("Metodo Mutaci\u00F3n:");
		lblMetodoSeleccion_2.setBounds(10, 459, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoSeleccion_2);
		
		JComboBox comboBoxMetodoMutacion = new JComboBox();
		comboBoxMetodoMutacion.setModel(new DefaultComboBoxModel(TiposMutacion.values()));
		comboBoxMetodoMutacion.setBounds(10, 483, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoMutacion);
		
		JButton botonEvolucionar = new JButton("Evolucionar");
		botonEvolucionar.setBounds(10, 7, 127, 23);
		frmGrupoPrctica.getContentPane().add(botonEvolucionar);
		
		JPanel panel = new JPanel();
		panel.setBounds(183, 75, 580, 373);
		frmGrupoPrctica.getContentPane().add(panel);
		
		TextoMejorIndividuo = new JLabel("Mejor Individuo: ");
		TextoMejorIndividuo.setBounds(327, 48, 174, 14);
		frmGrupoPrctica.getContentPane().add(TextoMejorIndividuo);
		
		JLabel lbLabelTamTorneo = new JLabel("Tama\u00F1o torneo:");
		lbLabelTamTorneo.setBounds(194, 483, 102, 22);
		frmGrupoPrctica.getContentPane().add(lbLabelTamTorneo);
		
		JSpinner spinnerTamTorneo = new JSpinner();
		spinnerTamTorneo.setModel(new SpinnerNumberModel(3, 2, 10, 1));
		spinnerTamTorneo.setBounds(293, 484, 48, 20);
		frmGrupoPrctica.getContentPane().add(spinnerTamTorneo);
		
		JLabel lbLabelProbTorneo = new JLabel("Probabilidad torneo [0.1,0.5]:");
		lbLabelProbTorneo.setBounds(351, 482, 162, 22);
		frmGrupoPrctica.getContentPane().add(lbLabelProbTorneo);
		
		textFieldProbTorneo = new JTextField();
		textFieldProbTorneo.setText("0.6");
		textFieldProbTorneo.setColumns(10);
		textFieldProbTorneo.setBounds(517, 484, 42, 20);
		frmGrupoPrctica.getContentPane().add(textFieldProbTorneo);
		
		JLabel lbLabelValorTruncamiento = new JLabel("Valor truncamiento [0.1,0.5]:");
		lbLabelValorTruncamiento.setBounds(569, 483, 162, 22);
		frmGrupoPrctica.getContentPane().add(lbLabelValorTruncamiento);
		
		textFieldValorTruncamiento = new JTextField();
		textFieldValorTruncamiento.setText("0.5\r\n");
		textFieldValorTruncamiento.setColumns(10);
		textFieldValorTruncamiento.setBounds(730, 484, 48, 20);
		frmGrupoPrctica.getContentPane().add(textFieldValorTruncamiento);
		
		TextoMediaPoblacion = new JLabel("Media poblaci\u00F3n: ");
		TextoMediaPoblacion.setBounds(327, 29, 174, 14);
		frmGrupoPrctica.getContentPane().add(TextoMediaPoblacion);
		
		TextoPeorIndividuo = new JLabel("Peor Individuo: ");
		TextoPeorIndividuo.setBounds(327, 11, 174, 14);
		frmGrupoPrctica.getContentPane().add(TextoPeorIndividuo);
		
		TextoNCruces = new JLabel("N\u00FAmero de cruces:");
		TextoNCruces.setHorizontalAlignment(SwingConstants.LEFT);
		TextoNCruces.setBounds(147, 48, 203, 14);
		frmGrupoPrctica.getContentPane().add(TextoNCruces);
		
		TextoNMutaciones = new JLabel("N\u00FAmero de mutaciones:");
		TextoNMutaciones.setHorizontalAlignment(SwingConstants.LEFT);
		TextoNMutaciones.setBounds(147, 16, 203, 14);
		frmGrupoPrctica.getContentPane().add(TextoNMutaciones);
		
		JLabel lblMetodoInicializacin = new JLabel("Metodo Inicializaci\u00F3n:");
		lblMetodoInicializacin.setBounds(10, 322, 188, 29);
		frmGrupoPrctica.getContentPane().add(lblMetodoInicializacin);
		
		JComboBox comboBoxMetodoInicializacion = new JComboBox();
		comboBoxMetodoInicializacion.setModel(new DefaultComboBoxModel(TiposInicializacion.values()));
		comboBoxMetodoInicializacion.setBounds(10, 346, 149, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxMetodoInicializacion);
		
		JLabel lblMetodoInicializacin_1 = new JLabel("Multiplexor:");
		lblMetodoInicializacin_1.setBounds(545, 8, 91, 20);
		frmGrupoPrctica.getContentPane().add(lblMetodoInicializacin_1);
		
		JComboBox comboBoxTipoMultiplexor = new JComboBox();
		comboBoxTipoMultiplexor.setModel(new DefaultComboBoxModel(TiposMultiplexor.values()));
		comboBoxTipoMultiplexor.setBounds(614, 7, 91, 22);
		frmGrupoPrctica.getContentPane().add(comboBoxTipoMultiplexor);
		
		JLabel lblMetodoInicializacin_1_1 = new JLabel("Profundidad m\u00E1xima:");
		lblMetodoInicializacin_1_1.setBounds(511, 41, 132, 20);
		frmGrupoPrctica.getContentPane().add(lblMetodoInicializacin_1_1);
		
		JSpinner spinnerProfMaxima = new JSpinner();
		spinnerProfMaxima.setModel(new SpinnerNumberModel(7, 2, 10, 1));
		spinnerProfMaxima.setBounds(642, 41, 76, 20);
		frmGrupoPrctica.getContentPane().add(spinnerProfMaxima);
		
		//A�adimos la funcionalidad de que empiece el AG
		botonEvolucionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				inicializarAG((int)spinnerNGeneraciones.getValue(), (int)spinnerTamPoblacion.getValue(),
						Double.parseDouble(textFieldProbCruce.getText()), Double.parseDouble(textFieldProbMutacion.getText()), Double.parseDouble(textFieldPorcElitismo.getText()),
						TiposSeleccion.values()[comboBoxMetodoSeleccion.getSelectedIndex()] , TiposCruce.values()[comboBoxMetodoCruce.getSelectedIndex()],
						TiposMutacion.values()[comboBoxMetodoMutacion.getSelectedIndex()],
						(int)spinnerTamTorneo.getValue(),Double.parseDouble(textFieldProbTorneo.getText()),Double.parseDouble(textFieldValorTruncamiento.getText()),
						TiposInicializacion.values()[comboBoxMetodoInicializacion.getSelectedIndex()], TiposMultiplexor.values()[comboBoxTipoMultiplexor.getSelectedIndex()], (int)spinnerProfMaxima.getValue());
				bucleAG();
			}

		});
		
		
	}
}

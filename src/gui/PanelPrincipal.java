package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.cruces.CruceMonopunto;
import algoritmoGenetico.mutaciones.MutacionBasica;
import algoritmoGenetico.seleccion.SeleccionRuleta;
import java.awt.Color;
import javax.swing.JPanel;

public class PanelPrincipal {
	private static Text probCruce;
	private static Text probMutacion;
	private static Text elitismo;
	private static Text precision;
	
	static AlgoritmoGenetico ag;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		generarVentana();
		Initialice();
		run();
	}
	
	private static void Initialice() {
		ag = new AlgoritmoGenetico();
		ag.setElitism(false);
		ag.setMaxGeneraciones(100);
		ag.inicializarPoblacion(1, 100);
		ag.setSeleccion(new SeleccionRuleta());
		ag.setCruce(new CruceMonopunto());
		ag.setMutacion(new MutacionBasica());
		
	}


	private static void run() {
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
	}
	
	public static void generarVentana() {
		Display display = Display.getDefault();
		Shell G03_P1 = new Shell();
		G03_P1.setSize(874, 496);
		G03_P1.setText("G03_P1");
		G03_P1.setLayout(null);
		
		Label lblNmeroDeGeneraciones = new Label(G03_P1, SWT.NONE);
		lblNmeroDeGeneraciones.setBounds(5, 5, 136, 15);
		lblNmeroDeGeneraciones.setText("N\u00FAmero de generaciones:");
		
		Spinner nGeneraciones = new Spinner(G03_P1, SWT.BORDER);
		nGeneraciones.setMaximum(1000);
		nGeneraciones.setMinimum(1);
		nGeneraciones.setSelection(100);
		nGeneraciones.setBounds(5, 25, 67, 22);
		
		Label lblTamaoDeLa = new Label(G03_P1, SWT.NONE);
		lblTamaoDeLa.setBounds(5, 52, 129, 15);
		lblTamaoDeLa.setText("Tama\u00F1o de la poblaci\u00F3n:");
		
		Spinner tamPoblacion = new Spinner(G03_P1, SWT.BORDER);
		tamPoblacion.setMaximum(1000);
		tamPoblacion.setMinimum(2);
		tamPoblacion.setSelection(100);
		tamPoblacion.setBounds(5, 72, 67, 22);
		
		Label lblProbabilidadDeCruce = new Label(G03_P1, SWT.NONE);
		lblProbabilidadDeCruce.setBounds(5, 100, 159, 15);
		lblProbabilidadDeCruce.setText("Probabilidad de cruce: (ej. 0.2)");
		
		probCruce = new Text(G03_P1, SWT.BORDER);
		probCruce.setText("0.6");
		probCruce.setBounds(5, 119, 67, 21);
		
		Label lblProbabilidadDeMutacin = new Label(G03_P1, SWT.NONE);
		lblProbabilidadDeMutacin.setText("Probabilidad de mutaci\u00F3n: (ej. 0.2)");
		lblProbabilidadDeMutacin.setBounds(5, 146, 188, 15);
		
		probMutacion = new Text(G03_P1, SWT.BORDER);
		probMutacion.setText("0.05");
		probMutacion.setToolTipText("");
		probMutacion.setBounds(5, 163, 67, 21);
		
		Label lblPrecisin = new Label(G03_P1, SWT.NONE);
		lblPrecisin.setText("Porcentaje de elitismo: (ej. 0.2)");
		lblPrecisin.setBounds(5, 190, 188, 15);
		
		elitismo = new Text(G03_P1, SWT.BORDER);
		elitismo.setText("0.02");
		elitismo.setToolTipText("");
		elitismo.setBounds(5, 211, 67, 21);
		
		Label lblPrecisin_1 = new Label(G03_P1, SWT.NONE);
		lblPrecisin_1.setText("Precisi\u00F3n");
		lblPrecisin_1.setBounds(5, 238, 188, 15);
		
		precision = new Text(G03_P1, SWT.BORDER);
		precision.setText("0.0001");
		precision.setToolTipText("");
		precision.setBounds(5, 259, 67, 21);
		
		Label lblPrecisin_1_1 = new Label(G03_P1, SWT.NONE);
		lblPrecisin_1_1.setText("M\u00E9todo de selecci\u00F3n:");
		lblPrecisin_1_1.setBounds(5, 286, 188, 15);
		
		Combo seleccion = new Combo(G03_P1, SWT.NONE);
		seleccion.setItems(new String[] {"Estoc\u00E1stico universal", "Restos", "Ruleta", "Torneo determinista", "Torneo probabil\u00EDstico", "Truncamiento"});
		seleccion.setBounds(5, 307, 129, 23);
		seleccion.select(2);
		
		Label lblPrecisin_1_1_1 = new Label(G03_P1, SWT.NONE);
		lblPrecisin_1_1_1.setText("M\u00E9todo de cruce:");
		lblPrecisin_1_1_1.setBounds(5, 336, 188, 15);
		
		Combo cruce = new Combo(G03_P1, SWT.NONE);
		cruce.setItems(new String[] {"Monopunto", "Uniforme"});
		cruce.setBounds(5, 357, 129, 23);
		cruce.select(0);
		
		Label lblPrecisin_1_1_1_1 = new Label(G03_P1, SWT.NONE);
		lblPrecisin_1_1_1_1.setText("M\u00E9todo de mutaci\u00F3n:");
		lblPrecisin_1_1_1_1.setBounds(5, 386, 188, 15);
		
		Combo mutacion = new Combo(G03_P1, SWT.NONE);
		mutacion.setItems(new String[] {"B\u00E1sica"});
		mutacion.setBounds(5, 407, 129, 23);
		mutacion.select(0);
		
		Composite composite = new Composite(G03_P1, SWT.NONE);
		composite.setBounds(232, 25, 586, 387);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Button boton = new Button(G03_P1, SWT.NONE);
		boton.setBounds(728, 418, 75, 25);
		boton.setText("Empezar");
		boton.addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				System.out.println("Hola");
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		G03_P1.open();
		G03_P1.layout();
		while (!G03_P1.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}

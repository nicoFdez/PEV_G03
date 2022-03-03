package main;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.seleccion.*;
import algoritmoGenetico.cruces.*;
import algoritmoGenetico.mutaciones.*;




public class Main {
	public static void main(String[] args) {
		
		/*
		// define your data
		double[] generaciones = { 1, 2, 3, 4, 5, 6, 7, 8, 9 ,10 };
		double[] fitness = { 12, 25, 32, 45, 65, 67 , 70, 72, 73, 76};
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		// define the legend position
		plot.addLegend("SOUTH");
		// add a line plot to the PlotPanel
		plot.addLinePlot("EVOLUCIÓN", generaciones, fitness);
		// put the PlotPanel in a JFrame like a JPanel
		JFrame frame = new JFrame("a plot panel");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
		*/
		Initialice();
		run();
		
	}
	
	private static void Initialice() {
		elitism = true;
		ag = new AlgoritmoGenetico();
		ag.inicializarPoblacion(100);
		ag.setMaxGeneraciones(100);
		ag.setSeleccion(new SeleccionRuleta());
		ag.setCruce(new CruceMonopunto());
		ag.setMutacion(new MutacionBasica());
	}


	private static void run() {
		int generacionActual = 0;
		while(generacionActual < ag.getMaxGeneraciones()) {
			if(elitism)ag.saveElites();
			ag.Seleccion();
			ag.Cruce();
			ag.Mutacion();
			if(elitism)ag.recoverSavedElites();
			ag.Evaluar();
			//generaGrafica();
			//Siguiente generacion
			generacionActual++;
		}
		System.out.println("El mejor individuo de la evolucion ha dado: "+ag.getMejorIndividuo().getFitness()); 
	}
	
	static AlgoritmoGenetico ag;
	static boolean elitism;
	
	
	

}

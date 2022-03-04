package main;

import algoritmoGenetico.AlgoritmoGenetico;
import algoritmoGenetico.seleccion.*;
import gui.LinePlot;
import algoritmoGenetico.cruces.*;
import algoritmoGenetico.mutaciones.*;




public class Main {
	public static void main(String[] args) {		
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
	
	static AlgoritmoGenetico ag;
	
	
	

}

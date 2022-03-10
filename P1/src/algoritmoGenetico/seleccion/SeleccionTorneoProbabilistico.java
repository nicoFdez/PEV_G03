package algoritmoGenetico.seleccion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operación de selección por el método de torneo probabilistico
public class SeleccionTorneoProbabilistico implements Seleccion {

	//Constructoras
	public SeleccionTorneoProbabilistico(){
		this.tamTorneo = 3;
		this.probSacarPrimero = 0.5;
	}
	public SeleccionTorneoProbabilistico(int tamTorn, double probTorn){
		this.tamTorneo = tamTorn;
		this.probSacarPrimero = probTorn;
	}
	
	
	//Metodo que recibe una poblacion y realica una selección de individuos por el método de torneo probabilistico
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		Random rand = new Random();
		int[] poblacionSeleccionada = new int[nIndividuos];
		Individuo[] individuosTorneo = new Individuo[tamTorneo];
		List poblacionListada = Arrays.asList(poblacion);

		
		//Para cada individuo de la poblacion final tomo k participantes de la poblacion inicial,
		//hago un torneo con estos y según un random me quedo con el primero o con el ultimo
		for(int i=0; i<nIndividuos; i++) {
			// Saco k participantes
			for(int j = 0 ; j <  this.tamTorneo; j++ ) 
				individuosTorneo[j] = poblacion[rand.nextInt(nIndividuos)];
			
			//Hago que compitan 
			individuosTorneo = competirTorneo(individuosTorneo, minimization);
			
			//Una vez que tenemos el torneo hecho, tomamos un random que nos indica 
			//si nos quedamos con el mejor o el peor
			poblacionSeleccionada[i] = rand.nextDouble() > this.probSacarPrimero ? 
					poblacionListada.indexOf(individuosTorneo[0]) : 								//busco el primero que salio del torneo
						poblacionListada.indexOf(individuosTorneo[individuosTorneo.length-1]);		//busco el ultimo que salio del torneo
		}
		
		return poblacionSeleccionada;
	}
	
	//Metodo que toma una serie de individuos  y los orden según lo requiera el AG, haciendo maximizacion o minimizacion
		private Individuo[] competirTorneo(Individuo[] participantes, boolean minimization) {
			Comparator comp;
			if(minimization)		comp = new ComparadorMin();
			else 					comp = new ComparadorMax();
			
			Arrays.sort(participantes, comp);
			return participantes;
		}
		
		int tamTorneo;
		double probSacarPrimero;
}

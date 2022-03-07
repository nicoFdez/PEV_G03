package algoritmoGenetico.seleccion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionTorneoDeterminista implements Seleccion{

	
	public  SeleccionTorneoDeterminista() {}
	
	public  SeleccionTorneoDeterminista(int tamTorn) {this.tamTorneo = tamTorn;}

	
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;

		//Para cada individuo de la poblacion final tomo 3 participantes de la poblacion inicial 
		//hago un torneo con estos y me quedo con aquel que haya salido primero
		Random rand = new Random();
		int[] poblacionSeleccionada = new int[nIndividuos];
		Individuo[] individuosTorneo = new Individuo[tamTorneo];
		List poblacionListada = Arrays.asList(poblacion);

		
		for(int i=0; i<nIndividuos; i++) {
			// Saco k participantes
			for(int j = 0 ; j <  this.tamTorneo; j++ ) {
				int r = rand.nextInt(nIndividuos);
				individuosTorneo[j] = poblacion[r];
			}
			//Hago que compitan y me quedo con el mejor
			individuosTorneo = competirTorneo(individuosTorneo, minimization);
			
			//Los individuos vienen ordenados en el array individuosTorneo pero para sacar el indice del primero
			//tengo que buscarlo en el array inicial que me llega de la poblacion
			poblacionSeleccionada[i] =  poblacionListada.indexOf(individuosTorneo[0]);
		}
		
		return poblacionSeleccionada;
	}
	
	
	//Metodo que toma una serie de individuos  y los orden según lo requiera el AG, haciendo maximizacion o minimizacion
	private Individuo[] competirTorneo(Individuo[] participantes, boolean minimization) {		
		Comparator comp;
		if(minimization)
			comp = new ComparadorMin();
		else 
			comp = new ComparadorMax();
		
		Arrays.sort(participantes, comp);
		return participantes;
	}
	
	int tamTorneo;
}

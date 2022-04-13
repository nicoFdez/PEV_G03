package algoritmoGenetico.seleccion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operación de selección por el método de truncamiento
public class SeleccionTruncamiento implements Seleccion{

	//Constructoras
	public  SeleccionTruncamiento() {this.valorTruncamiento = 0.5;}
	public  SeleccionTruncamiento(double valTrunc) {this.valorTruncamiento = valTrunc;}

	
	//Metodo que recibe una poblacion y realica una selección de individuos por el método de truncamiento
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		int nIndividuos = poblacion.length;
		int[] poblacionSeleccionada = new int[nIndividuos];
		List poblacionListada = Arrays.asList(poblacion);
		
		//Me hago con todos los individuos ordenados 
		Individuo[] individuosOrdenados = competirTorneo(poblacion, minimization);
		
		//Para sacar cada individuo de la seleccion final los obtengo haciendo
		//más o menos copias de los mejores según el valor del truncamiento
		for(int i=0; i<nIndividuos; i++) {
			poblacionSeleccionada[i] = poblacionListada.indexOf(individuosOrdenados[(int)(i* this.valorTruncamiento)]) ;
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
	
	double valorTruncamiento;
}

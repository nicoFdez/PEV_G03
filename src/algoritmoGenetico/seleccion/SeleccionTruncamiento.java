package algoritmoGenetico.seleccion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionTruncamiento implements Seleccion{

	
	public  SeleccionTruncamiento() {}
	public  SeleccionTruncamiento(double valTrunc) {this.valorTruncamiento = valTrunc;}

	
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		int nIndividuos = poblacion.length;
		int[] poblacionSeleccionada = new int[nIndividuos];
		List poblacionListada = Arrays.asList(poblacion);
		
		//Me hago con todos los individuos ordenados 
		Individuo[] individuosOrdenados = competirTorneo(poblacion, minimization);
		
		//Para sacar cada individuo de la seleccion final los obtengo de una forma u otra
		//dependiendo del valor de truncamiento
		for(int i=0; i<nIndividuos; i++) {
			poblacionSeleccionada[i] = poblacionListada.indexOf(individuosOrdenados[(int)(i* this.valorTruncamiento)]) ;
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
	
	double valorTruncamiento;
}

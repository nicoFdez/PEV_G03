package algoritmoGenetico.seleccion;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operación de selección por el método de ruleta
public class SeleccionRanking implements Seleccion{

	//Constructora
	public SeleccionRanking(double presionSelectiva) {
		this.beta = presionSelectiva;
	}
	
	public SeleccionRanking() {
		this.beta = 1.5;
	}
	
	//Metodo que recibe una poblacion y realica una selección de individuos por el método de ruleta
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		int nIndividuos = poblacion.length;
		double[] acumProb = new double[nIndividuos];
		
		Comparator comp;
		if(minimization)	comp = new ComparadorMin();
		else 				comp = new ComparadorMax();		
		Arrays.sort(poblacion, comp);
		
		for(int i=0; i<nIndividuos; i++) {
			double prob = 1.0/(double)nIndividuos * (this.beta-(2.0*(this.beta-1.0)*(((double)i-1.0)/((double)nIndividuos-1.0))));
			if(i!=0) acumProb[i] = prob + acumProb[i-1];
			else acumProb[i] = prob;
		}
		
		//Una vez que ya tengo estos datos toca hacer la ruleta, en la que hacemos tantas selecciones como individuos
		//en la poblacion, cada uno de estos es representado por una seccion de la ruleta, sacamos un número aleatorio 
		//y vemos cual es su traduccion en la ruleta para determinar el individuo seleccionado
		Random rand = new Random();
		int[] poblacionSeleccionada = new int[nIndividuos];
		for(int i=0; i<nIndividuos; i++) {
			//Nuevo random
			double r = rand.nextDouble();
			int j=0;
			
			//Buscamos el individuo que lo representa en nuestro array
			while(acumProb[j] < r)
				j++;
			
			//Nos lo guardamos en la nueva poblacion
			poblacionSeleccionada[i] = j;
		}
		
		return poblacionSeleccionada;
	}

	private double beta;
}

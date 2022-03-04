package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionEstocasticoUniversal implements Seleccion{

	
	public void SeleccionEstocasticoUniversal() {}
	
	@Override
	public Individuo[] seleccionar(Individuo[] poblacion) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		double[] fitness = new double[nIndividuos];
		
		//Recorro toda la poblacion para hacerme tanto con sus fitness como con el total 
		for(int i=0; i<nIndividuos; i++) {
			double f = poblacion[i].getFitness();
			fitness[i] = f;
			fitnessTotal += f;
		}
		
		//Reutilizo el array fitness para tener en este el fitness acumulado de los individuos
		for(int i=0; i<nIndividuos; i++) {
			//Paso el valor a un numero entre 0 y 1
			fitness[i] /= fitnessTotal;
			//si no estoy en el primer elemento del array, le sumo el valor almacenado en la posicion anterior
			if(i!=0) fitness[i] += fitness[i-1];
		}
		

		Random rand = new Random();
		Individuo[] poblacionSeleccionada = new Individuo[nIndividuos];
		
		//Sacamos un numero aleatorio entre 0 y 1/numero de individuos 
		double step = 1.0/nIndividuos;
		double start = rand.nextDouble()* step;
		for(int i=0; i<nIndividuos; i++) {
			//Saco un punto en el espacio [0,1] y busco aquel individuo que lo representa 
			double spot = start + (step*i);
			int j=0;
			//Buscamos el individuo que lo representa en nuestro array
			while(!(fitness[j] > spot)) 
				j++;
			//Nos lo guardamos en la nueva poblacion
			poblacionSeleccionada[i] = poblacion[j-1];
		}
		
		return poblacionSeleccionada;
	}
}
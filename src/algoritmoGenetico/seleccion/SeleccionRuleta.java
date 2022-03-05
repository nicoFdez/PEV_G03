package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionRuleta implements Seleccion{

	
	public void SeleccionRuleta() {}
	
	@Override
	public int[] seleccionar(Individuo[] poblacion) {
		
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
			while(fitness[j] < r) 
				j++;
			//Nos lo guardamos en la nueva poblacion
			poblacionSeleccionada[i] = j;
		}
		
		return poblacionSeleccionada;
	}
}

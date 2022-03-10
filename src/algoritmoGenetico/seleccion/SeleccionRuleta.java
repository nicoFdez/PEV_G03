package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operación de selección por el método de ruleta
public class SeleccionRuleta implements Seleccion{

	//Constructora
	public SeleccionRuleta() {}
	
	//Metodo que recibe una poblacion y realica una selección de individuos por el método de ruleta
@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		double[] fitness = new double[nIndividuos];
		
		//Recorro toda la población analizando el fitness de cada individuo y quedandome con el valor máximo y mínimo
		double max = poblacion[0].getFitness();
		double min = poblacion[0].getFitness();	
		for(int i=0; i<nIndividuos; i++) {
			double f = poblacion[i].getFitness();
			fitness[i] = f;
			if(f > max) max = f;
			if(f < min) min = f;
		}
		
		//Transformamos el fitness de cada individuo para que esté en un rango que nos interese y lo acumulamos para sacar el fitness total 
		for(int i=0; i<nIndividuos; i++) {
			double f;
			if(minimization) f = max - fitness[i];
			else f = fitness[i] + min;
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

package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;


//Clase encargada de realizar la operación de selección por el método estocastico universal
public class SeleccionEstocasticoUniversal implements Seleccion{

	//Constructora
	public SeleccionEstocasticoUniversal() {}
	
	//Metodo que recibe una poblacion y realica una selección de individuos por el método estocastico universal
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		double[] fitness = new double[nIndividuos];
		Random rand = new Random();
		int[] poblacionSeleccionada = new int[nIndividuos];
		
		//Recorro toda la poblacion preguntando por sus fitness y me quedo con el máximo y mínimo
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

		
		//Sacamos un numero aleatorio entre 0 y 1/nIndividuos a partir del que vamos a realizar los saltos necesarios en el método de selección estocástico
		double step = 1.0/nIndividuos;
		double start = rand.nextDouble()* step;
		
		//Hacemos tantos saltos como individuos vamos a seleccionar haciendo saltos de longitud step
		for(int i=0; i<nIndividuos; i++) {
			//Sacamos un punto dentro del rango [0,1] y buscamos el individuo que lo representa
			double spot = start + (step*i);
			int j=0;
			while(!(fitness[j] > spot)) 
				j++;
			
			//Nos lo guardamos en la nueva poblacion
			poblacionSeleccionada[i] = j;
		}
		
		return poblacionSeleccionada;
	}
}

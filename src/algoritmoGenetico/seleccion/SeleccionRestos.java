package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionRestos implements Seleccion{

	
	public void SeleccionRestos() {}
	
	@Override
	public int[] seleccionar(Individuo[] poblacion, boolean minimization) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		double[] fitness = new double[nIndividuos];
		
		//Evitar fitness negativos
		double max = poblacion[0].getFitness();
		double min = poblacion[0].getFitness();	
		for(int i=0; i<nIndividuos; i++) {
			double f = poblacion[i].getFitness();
			fitness[i] = f;
			if(f > max) max = f;
			if(f < min) min = f;
		}
		
		//Recorro toda la poblacion para hacerme tanto con sus fitness como con el total 
		for(int i=0; i<nIndividuos; i++) {
			double f;
			if(minimization) f = max - fitness[i];
			else f = fitness[i] + min;
			fitness[i] = f;
			fitnessTotal += f;
		}
		
		//Paso los fitness de cada individuo a sus probabilidades de seleccion
		for(int i=0; i<nIndividuos; i++) {
			fitness[i] /= fitnessTotal;
		}
		
		//Parte de seleccion por restos
		int[] poblacionSeleccionada = new int[nIndividuos];
		int indicePoblacionSeleccionada=0;
		//Recorro todos los individuos de la poblacion inicial para analizar si tengo que incluirlos o no
		for(int i=0; i<nIndividuos; i++) {
			//Saco el numero de copias que le corresponden al individuo actual
			int numCopias = (int)fitness[i]*nIndividuos;
			//Meto tantas copias en la poblacion final
			for(int j = 0; j<numCopias; j++) {
				poblacionSeleccionada[indicePoblacionSeleccionada] = i;
				indicePoblacionSeleccionada++;
			}
		}
		
		Random rand = new Random();
		//La seleccion por restos necesita ser completada con "cualquier otro metodo de seleccion" 
		//la seleccion restante se hace utilizando selección por ruleta
		
		for(int i = indicePoblacionSeleccionada; i< nIndividuos; i++)
		{
			//------------RULETA-------------------					
			//Reutilizo el array fitness para tener en este el fitness acumulado de los individuos
			for(int j=0; j<nIndividuos; j++) {
				//si no estoy en el primer elemento del array, le sumo el valor almacenado en la posicion anterior
				if(j!=0) fitness[j] += fitness[j-1];
			}
			
			for(int j=0; j<nIndividuos - indicePoblacionSeleccionada; j++) {
				//Nuevo random
				double r = rand.nextDouble();
				//Buscamos el individuo que lo representa en nuestro array
				int k=0;
				while(fitness[k] < r) 
					k++;
				//Nos lo guardamos en la nueva poblacion
				poblacionSeleccionada[i] = k;
			}		
			//-------------------------------------
			
		}	
		
		return poblacionSeleccionada;
	}
}

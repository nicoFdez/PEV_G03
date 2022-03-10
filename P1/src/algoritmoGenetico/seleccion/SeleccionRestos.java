package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;


//Clase encargada de realizar la operación de selección por el método de restos
public class SeleccionRestos implements Seleccion{

	//Constructora
	public SeleccionRestos() {}
	
	
	//Metodo que recibe una poblacion y realica una selección de individuos por el método de restos
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
		
		//Paso los fitness de cada individuo a sus probabilidades de seleccion
		for(int i=0; i<nIndividuos; i++) 
			fitness[i] /= fitnessTotal;
		
		//Parte de seleccion por restos
		int[] poblacionSeleccionada = new int[nIndividuos];
		int indicePoblacionSeleccionada=0;
		
		//Recorro todos los individuos de la poblacion inicial para analizar si tengo que incluirlos o no
		for(int i=0; i<nIndividuos; i++) {
			
			//Saco el numero de copias que le corresponden al individuo actual
			int numCopias = (int)Math.round(fitness[i]*nIndividuos);
			
			//Meto tantas copias en la poblacion final
			for(int j = 0; j<numCopias; j++) {
				poblacionSeleccionada[indicePoblacionSeleccionada] = i;
				indicePoblacionSeleccionada++;
			}
		}
		
		//La seleccion por restos necesita ser completada con "cualquier otro metodo de seleccion" 
		//la seleccion restante se hace utilizando selección por ruleta
		Random rand = new Random();
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

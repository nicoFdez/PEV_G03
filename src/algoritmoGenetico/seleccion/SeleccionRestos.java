package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionRestos implements Seleccion{

	
	public void SeleccionRestos() {}
	
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
		
		//Paso los fitness de cada individuo a sus probabilidades de seleccion
		for(int i=0; i<nIndividuos; i++) {
			fitness[i] /= fitnessTotal;
		}
		
		//Parte de seleccion por restos
		Individuo[] poblacionSeleccionada = new Individuo[nIndividuos];
		int indicePoblacionSeleccionada=0;
		//Recorro todos los individuos de la poblacion inicial para analizar si tengo que incluirlos o no
		for(int i=0; i<nIndividuos; i++) {
			//Saco el numero de copias que le corresponden al individuo actual
			int numCopias = (int)fitness[i]*nIndividuos;
			//Meto tantas copias en la poblacion final
			for(int j = 0; j<numCopias; j++) {
				poblacionSeleccionada[indicePoblacionSeleccionada] = poblacion[i];
				indicePoblacionSeleccionada++;
			}
		}
		
		Random rand = new Random();
		//La seleccion por restos necesita ser completada con "cualquier otro metodo de seleccion" 
		//la seleccion restante se hace de manera aleatorio
		for(int i = indicePoblacionSeleccionada; i< nIndividuos; i++)
		{
			poblacionSeleccionada[i] = poblacion[rand.nextInt(nIndividuos)];
		}	
		
		return poblacionSeleccionada;
	}
}

package algoritmoGenetico.seleccion;

import java.util.Random;
import algoritmoGenetico.individuos.Individuo;

public class SeleccionRuleta implements Seleccion{

	@Override
	public Individuo[] seleccionar(Individuo[] poblacion) {
		
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		
		double[] fitness = new double[nIndividuos];
		for(int i=0; i<nIndividuos; i++) {
			double f = poblacion[i].getFitness();
			fitness[i] = f;
			fitnessTotal += f;
		}
		
		for(int i=0; i<nIndividuos; i++) {
			fitness[i] /= fitnessTotal;
			if(i!=0) fitness[i] += fitness[i-1];
		}
		
		//Ruleta
		Random rand = new Random();
		Individuo[] poblacionSeleccionada = new Individuo[nIndividuos];
		for(int i=0; i<nIndividuos; i++) {
			double r = rand.nextDouble();
			int j=0;
			while(fitness[j] < r) 
				j++;
			poblacionSeleccionada[i] = poblacion[j];
		}
		
		return poblacionSeleccionada;
	}
}

package algoritmoGenetico.mutaciones;

import java.util.Random;

import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operación de mutación básicas sobre una población
public class MutacionIntercambio implements Mutacion {

	//Constructora
	public MutacionIntercambio() {}
	
	//Método que recorre toda la población de individuos y les pide que se muten indicando la probabilidad de que
	//esta operación ocurra
	@Override
	public void mutar(Individuo[] poblacion, double probMutacion) {
		Random rand = new Random();
		//Recorremos la poblacion y la indicamos que se mute con el metodo que representamos
		for(int i=0; i<poblacion.length; i++) {
			double r = rand.nextDouble();
			if(r<probMutacion)
				poblacion[i].mutacionIntercambio(probMutacion);
		}
	}

}

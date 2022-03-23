package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operación de mutación básicas sobre una población
public class MutacionHeuristica implements Mutacion {

	//Constructora
	public MutacionHeuristica() {}
	
	//Método que recorre toda la población de individuos y les pide que se muten indicando la probabilidad de que
	//esta operación ocurra
	@Override
	public void mutar(Individuo[] poblacion, double probMutacion) {
		
		//Recorremos la poblacion y la indicamos que se mute con el metodo que representamos
		for(int i=0; i<poblacion.length; i++) {
			poblacion[i].mutacionHeuristica(probMutacion);
		}
	}

}

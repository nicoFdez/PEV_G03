package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

//Clase encargada de realizar la operaci�n de mutaci�n b�sicas sobre una poblaci�n
public class MutacionHeuristica implements Mutacion {

	//Constructora
	public MutacionHeuristica() {}
	
	//M�todo que recorre toda la poblaci�n de individuos y les pide que se muten indicando la probabilidad de que
	//esta operaci�n ocurra
	@Override
	public void mutar(Individuo[] poblacion, double probMutacion) {
		
		//Recorremos la poblacion y la indicamos que se mute con el metodo que representamos
		for(int i=0; i<poblacion.length; i++) {
			poblacion[i].mutacionHeuristica(probMutacion);
		}
	}

}

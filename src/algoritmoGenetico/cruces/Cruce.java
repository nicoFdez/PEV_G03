package algoritmoGenetico.cruces;

import algoritmoGenetico.individuos.Individuo;


//Interfaz que especifica los m�todos que deben de tener las clases que pretendan realizar operaciones de cruce sobre
//una poblaci�n de individuos
public interface Cruce {

	public Individuo[] cruzar(Individuo[] poblacion, double probCruce);

}

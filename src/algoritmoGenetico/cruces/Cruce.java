package algoritmoGenetico.cruces;

import algoritmoGenetico.individuos.Individuo;


//Interfaz que especifica los métodos que deben de tener las clases que pretendan realizar operaciones de cruce sobre
//una población de individuos
public interface Cruce {

	public Individuo[] cruzar(Individuo[] poblacion, double probCruce);

}

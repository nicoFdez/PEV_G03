package algoritmoGenetico.cruces;

import algoritmoGenetico.individuos.Individuo;

public interface Cruce {

	public Individuo[] cruzar(Individuo[] poblacion, double probCruce);

}

package algoritmoGenetico.seleccion;

import algoritmoGenetico.individuos.Individuo;

public interface Seleccion {

	public Individuo[] seleccionar(Individuo[] poblacion);
}

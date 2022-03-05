package algoritmoGenetico.seleccion;

import algoritmoGenetico.individuos.Individuo;

public interface Seleccion {

	public int[] seleccionar(Individuo[] poblacion);
}

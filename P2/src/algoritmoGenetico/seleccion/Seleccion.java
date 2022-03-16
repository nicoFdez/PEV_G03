package algoritmoGenetico.seleccion;

import algoritmoGenetico.individuos.Individuo;

//Interfaz que especifica los m�todos que todo objeto encargado de hacer operaciones de selecci�n de poblaci�n debe de implementar
public interface Seleccion {

	public int[] seleccionar(Individuo[] poblacion, boolean minimization);
}

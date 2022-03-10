package algoritmoGenetico.seleccion;

import algoritmoGenetico.individuos.Individuo;

//Interfaz que especifica los métodos que todo objeto encargado de hacer operaciones de selección de población debe de implementar
public interface Seleccion {

	public int[] seleccionar(Individuo[] poblacion, boolean minimization);
}

package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

//Interfaz que especifica los métodos que tienen que tener aquellos objetos que quieran realizar operaciones de mutación sobre una población
public interface Mutacion {
	
	public void mutar(Individuo[] poblacion, double probMutacion);
	
	public int getNMutaciones();
}

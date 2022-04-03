package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

//Interfaz que especifica los m�todos que tienen que tener aquellos objetos que quieran realizar operaciones de mutaci�n sobre una poblaci�n
public interface Mutacion {
	
	public void mutar(Individuo[] poblacion, double probMutacion);
	
	public int getNMutaciones();
}

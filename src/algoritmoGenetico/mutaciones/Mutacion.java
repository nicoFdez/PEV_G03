package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

public interface Mutacion {
	
	public void mutar(Individuo[] poblacion, double probMutacion);
}

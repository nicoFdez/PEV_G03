package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

public class MutacionBasica implements Mutacion {

	@Override
	public void mutar(Individuo[] poblacion, double probMutacion) {
		
		for(int i=0; i<poblacion.length; i++) {
			poblacion[i].mutacionBasica(probMutacion);
		}
	}

}

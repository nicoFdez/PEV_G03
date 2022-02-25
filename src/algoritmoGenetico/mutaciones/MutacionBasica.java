package algoritmoGenetico.mutaciones;

import algoritmoGenetico.individuos.Individuo;

public class MutacionBasica implements Mutacion {

	public MutacionBasica() {}
	
	@Override
	public void mutar(Individuo[] poblacion, double probMutacion) {
		
		//Recorremos la poblacion y la indicamos que se mute con el metodo que representamos
		for(int i=0; i<poblacion.length; i++) {
			poblacion[i].mutacionBasica(probMutacion);
		}
	}

}

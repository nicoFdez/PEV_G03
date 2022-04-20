package algoritmoGenetico.individuos;

import java.util.Random;


//Clase abstracta que engloba las variables y los m�todos m�nimos que debe tener un individuo que vaya a participar en una evoloci�n
public abstract class Individuo<T> {
	protected MyTree<T> cromosoma;
	
	public double getFitness() {
		return 0;
	}
	
	public void mutacionTerminal(double probMutacion) {}
	
	public void initialize() {
		
	}
	
	public MyTree<T> getCromosoma() {
		return this.cromosoma;
	}

}

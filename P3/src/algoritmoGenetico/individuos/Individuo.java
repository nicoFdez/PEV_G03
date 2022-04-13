package algoritmoGenetico.individuos;

import java.util.Random;


//Clase abstracta que engloba las variables y los métodos mínimos que debe tener un individuo que vaya a participar en una evoloción
public abstract class Individuo<T> {
	protected T[] cromosoma;
	protected int[] tamGenes;
	protected double[] min;
	protected double[] max;
	protected int tamTotal;
	protected int nGenes;
	
	protected Random rand;
	
	protected int tamGen(double precision, double min, double max) {
		return (int) (Math.log10(((max - min) / precision) + 1) / Math.log10(2));
	}
	
	public double getFitness() {
		return 0;
	}
	
	public void mutacionHeuristica(double probMutacion) {}
	public void mutacionInsercion(double probMutacion) {}
	public void mutacionIntercambio(double probMutacion) {}
	public void mutacionInversion(double probMutacion) {}
	
	public void copyFromAnother(Individuo<T> other) {
		
	}
	
	public void initialize() {
		
	}
	
	public T[] getCromosoma() {
		return this.cromosoma;
	}

	public void setCromosoma(T[] cromosoma) {
		this.cromosoma = cromosoma;
	}
}

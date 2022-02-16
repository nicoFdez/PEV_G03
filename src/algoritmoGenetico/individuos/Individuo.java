package algoritmoGenetico.individuos;

import java.util.Random;

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
}

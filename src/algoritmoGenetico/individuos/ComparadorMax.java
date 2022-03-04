package algoritmoGenetico.individuos;

import java.util.Comparator;

public class ComparadorMax implements Comparator<Individuo> {

	@Override
	public int compare(Individuo o1, Individuo o2) {
		if(o1.getFitness() < o2.getFitness()) return 1;
		if(o1.getFitness() > o2.getFitness()) return -1;
		else return 0;
	}

}

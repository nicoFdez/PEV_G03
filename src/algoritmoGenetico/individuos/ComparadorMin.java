package algoritmoGenetico.individuos;

import java.util.Comparator;


//Clase que se encarga de recibir una pareja de infividuos y determinar si uno es menor que otro
public class ComparadorMin implements Comparator<Individuo> {

	@Override
	public int compare(Individuo o1, Individuo o2) {
		if(o1.getFitness() > o2.getFitness()) return 1;
		if(o1.getFitness() < o2.getFitness()) return -1;
		else return 0;
	}

}

package algoritmoGenetico.individuos;

import java.util.Comparator;


//Clase que se encarga de recibir una pareja de infividuos y determinar si uno es mayor que otro
public class ComparadorMax implements Comparator<Individuo> {

	@Override
	public int compare(Individuo o1, Individuo o2) {
		double fitness1 =o1.getValor();
		double fitness2 =o2.getValor();
		
		if(fitness1 < fitness2) return 1;
		else if(fitness1 > fitness2) return -1;
		else return 0;
	}

}

package algoritmoGenetico;

import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.IndividuoF1;

public class AlgoritmoGenetico {
	private int tamPoblacion;
	private Individuo[] poblacion;
	private double[] fitness;
	private int maxGeneraciones;
	private double probCruce;
	private double probMutacion;
	private int tamTorneo;
	private Individuo elMejor;
	private int pos_mejor;
}

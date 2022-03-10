package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;


//clase que representa un individuo preparado para realizar una evoluci�n sobre la funcion1
public class IndividuoF1 extends IndividuoBoolean {
	
	//Constructoras
	public IndividuoF1(Individuo other) {
		super(other);
	}

	public IndividuoF1(double precision){
		this.rand = new Random();

		this.nGenes = 2;
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];	
		this.min[0] = -3.0;
		this.min[1] = 4.1;
		this.max[0] = 12.1;
		this.max[1] = 5.8;
		
		//tamaño del array de "bits" que tiene cada uno de los genes
		this.tamGenes = new int[this.nGenes];
		this.tamGenes[0] = tamGen(precision, min[0], max[0]);
		this.tamGenes[1] = tamGen(precision, min[1], max[1]);
		
		//Tamaño total de individuo en bits
		this.tamTotal = this.tamGenes[0] + this.tamGenes[1];
		this.cromosoma = new Boolean[tamTotal];
	}
	

	//Metod que aplica la funcion 1 y devuelvel el valor
	//Funcion a representar: f(x1 , x2) = 21.5 + x1.sen(4π x1)+x2.sen(20π x2)
	@Override
	public double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		//System.out.println("(" + this.getFenotipo(0) + ", " + this.getFenotipo(1) + ")");
		return (21.5 + x1 * Math.sin(4 * Math.PI * x1) + x2 * Math.sin(20 * Math.PI * x2));
		
	}
	
}

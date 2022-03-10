package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;


//clase que representa un individuo preparado para realizar una evoluci�n sobre la funcion2
public class IndividuoF2 extends IndividuoBoolean {
	
	//Constructoras
	public IndividuoF2(Individuo other) {
		super(other);
	}

	public IndividuoF2(double precision){
		this.rand = new Random();
		
		this.nGenes = 2;
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];	
		this.min[0] = -10.0;
		this.min[1] = -10.0;
		this.max[0] = 10.0;
		this.max[1] = 10.0;
		
		//tamaño del array de "bits" que tiene cada uno de los genes
		this.tamGenes = new int[this.nGenes];
		this.tamGenes[0] = tamGen(precision, min[0], max[0]);
		this.tamGenes[1] = tamGen(precision, min[1], max[1]);
		
		//Tamaño total de individuo en bits
		this.tamTotal = this.tamGenes[0] + this.tamGenes[1];
		this.cromosoma = new Boolean[tamTotal];
	}
	
	
	//Metod que aplica la funcion 1 y devuelvel el valor
	//Funcion a aplicar: f (xi,i = 1..2) = (∑i. cos((i +1)x1 + i))(∑i. cos((i +1)x2 + i))
	public double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		
		double result =0;
		double leftSideOfTheEcuation = 0;
		double rightSideOfTheEcuation = 0;
		//Generamos el sumatorio de cada una de las partes de la ecuación
		for(double i = 1; i <= 5; i++) {
			leftSideOfTheEcuation +=  (i*(Math.cos((i+1)* x1 + i)));
			rightSideOfTheEcuation +=  (i*(Math.cos((i+1)* x2 + i)));
		}
		result = leftSideOfTheEcuation * rightSideOfTheEcuation;
		return result;
	}
	
}

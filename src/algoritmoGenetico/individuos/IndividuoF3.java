package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

public class IndividuoF3 extends IndividuoBoolean {
	
	
	public IndividuoF3(Individuo other) {
		super(other);
	}
	
	public IndividuoF3(double precision){
		this.rand = new Random();
		
		this.nGenes = 2;
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];	
		this.min[0] = -512.0;
		this.min[1] = -512.0;
		this.max[0] = 512.0;
		this.max[1] = 512.0;
		
		//tamaño del array de "bits" que tiene cada uno de los genes
		this.tamGenes = new int[this.nGenes];
		this.tamGenes[0] = tamGen(precision, min[0], max[0]);
		this.tamGenes[1] = tamGen(precision, min[1], max[1]);
		
		//Tamaño total de individuo en bits
		this.tamTotal = this.tamGenes[0] + this.tamGenes[1];
		this.cromosoma = new Boolean[tamTotal];
	}
	
	//Metod que aplica la funcion 1 y devuelvel el valor
	public double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		
		double result =0;
		double leftSideOfTheEcuation = -(x2+47)*Math.sin(Math.sqrt(Math.abs(x2+(x1/2)+47)));
		double rightSideOfTheEcuation = x1*Math.sin(Math.sqrt(Math.abs(x1-(x2+47))));
		result = leftSideOfTheEcuation - rightSideOfTheEcuation;
		return result;
	}
}

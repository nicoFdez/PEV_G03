package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

public class IndividuoF4 extends IndividuoBoolean {
	
	public IndividuoF4(Individuo other) {
		super(other);
	}
	
	public IndividuoF4(double precision, int nParams){
		this.rand = new Random();
		
		this.nGenes = nParams;
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];
		for(int i = 0; i<this.nGenes;i++) {
			this.min[i] = 0;
			this.max[i] = Math.PI;
		}
		
		//tamaño del array de "bits" que tiene cada uno de los genes
		this.tamGenes = new int[this.nGenes];
		this.tamTotal=0;
		for(int i = 0; i<this.nGenes;i++) {
			this.tamGenes[i] = tamGen(precision, min[i], max[i]);
			this.tamTotal += this.tamGenes[i];
		}
		
		//Tamaño total de individuo en bits
		this.cromosoma = new Boolean[tamTotal];
	}
	
	
	//Metod que aplica la funcion 1 y devuelvel el valor
	public double getValor() {
		double[] genes = new double[this.nGenes];
		for(int i = 0; i< this.nGenes; i++) {
			genes[i] = this.getFenotipo(i);
		}
		
		double result =0;
		for(int i = 0; i< this.nGenes ; i++) {
			double actualStep=0;
			actualStep = Math.sin(genes[i])* Math.pow(
									Math.sin(((i+1)*Math.pow(genes[i], 2))/Math.PI)
									, 20);
			
			result += actualStep;
		}
		result = -result;
		return result;
	}
}

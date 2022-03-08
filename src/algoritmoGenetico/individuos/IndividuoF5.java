package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

public class IndividuoF5 extends Individuo<Double> {
	
	public IndividuoF5(Individuo other) {
		this.nGenes = other.nGenes;
		
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];
		
		for(int i=0; i<this.min.length; i++) 
			this.min[i] = other.min[i];
		for(int i=0; i<this.max.length; i++) 
			this.max[i] = other.max[i];
		
		this.tamGenes = new int[this.nGenes];	
		for(int i=0; i<this.tamGenes.length; i++) {
			this.tamGenes[i]=other.tamGenes[i];
		}
		
		for(int i=0; i<this.nGenes; i++)
			this.tamTotal += this.tamGenes[i];
		
		this.cromosoma = new Double[tamTotal];
		
		for(int i=0; i<this.cromosoma.length; i++)
			this.cromosoma[i] = (Double)other.cromosoma[i];
	}
	
	public IndividuoF5(int nParams){
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
			this.tamGenes[i] = 1;
			this.tamTotal += this.tamGenes[i];
		}
		
		//Tamaño total de individuo en bits
		this.cromosoma = new Double[tamTotal];
	}
	
	@Override
	public double getFitness() {
		return this.getValor();
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
	
	public double getFenotipo(int val) {
		return this.cromosoma[val];
	}
	
	@Override
	public void initialize() {
		for(int i = 0; i < this.tamTotal; i++) {
			Double r = this.min[i] + (this.rand.nextDouble() * (this.max[i] - this.min[i]));
			this.cromosoma[i] = r;
		}
	}
	
	@Override
	public void copyFromAnother(Individuo<Double> other) {
		//Tomamos el cromosoma del individuo que es mejor que nosotros y lo copiamos
		Double[] otherCromo = other.getCromosoma();
		for(int i = 0; i<otherCromo.length; i++) 
			this.cromosoma[i] = otherCromo[i].doubleValue();
	}
	
	//Metodo que toma una probabilidad de mutacion y con ella se aplica a su propio cromosoma una mutacion basica 
	//donde por cada uno de los genes del cromosoma prueba a ver si es necesario realizar una mutacion
	@Override
	public void mutacionBasica(double probMutacion) {
		Random rand = new Random();
		for(int i=0; i<this.cromosoma.length; i++) {
			double r = rand.nextDouble();
			if(r <= probMutacion) {
				cromosoma[i] = this.min[i] + (r * (this.max[i] - this.min[i]));
			}
		}
	}
}

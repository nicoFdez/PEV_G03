/**
 * 
 */
package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

//Clase abstracta que complementa las caracteristicas de la clase Individuo y se ajusta para tratar con cromosomas que sean de tipo boolean (binarios)
public abstract class IndividuoBoolean extends Individuo<Boolean> {
	//Constructora por defecto
	public IndividuoBoolean(){
		
	}
	
	//Constructora por copia
	//Toma un individuo que debe de ser del mimso tipo y analiza sus variables para igualar sus valores
	public IndividuoBoolean(Individuo other) {
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
		
		this.cromosoma = new Boolean[tamTotal];
		
		for(int i=0; i<this.cromosoma.length; i++)
			this.cromosoma[i] = (Boolean)other.cromosoma[i];
	}
	
	//Metodo que inicializa el individuo, al ser el genotipo un array de booleanos la inicializacion es
	//un monton de booleanos aleatorios
	@Override
	public void initialize() {
		for(int i = 0; i < this.tamTotal; i++) 
			this.cromosoma[i] = this.rand.nextBoolean();

	}
	

	public double getValor() {
		return 0;
	}
	
	@Override
	public double getFitness() {
		return this.getValor();
	}
	
	//Metodo que toma un índice y devuelve el gen que se encuentre en dicha posicion dentro del cromosoma en una representación no binaria
	public double getFenotipo(int val) {
		double result=0;
		int inicio =0;
		for(int i = 0; i<val; i++) {
			inicio+=this.tamGenes[i];
		}
	
		Boolean[] gen = Arrays.copyOfRange(this.cromosoma, inicio, inicio + this.tamGenes[val]); 
		double value = this.min[val] + bin2dec(gen) * (this.max[val]-this.min[val])/(Math.pow(2, this.tamGenes[val]) - 1);

		return value;
	}
	
	//Metodo que recibe un individuo del mismo tipo y copia su cromosoma
	@Override
	public void copyFromAnother(Individuo<Boolean> other) {
		//Tomamos el cromosoma del individuo que es mejor que nosotros y lo copiamos
		Boolean[] otherCromo = other.getCromosoma();
		for(int i = 0; i<otherCromo.length; i++) 
			this.cromosoma[i] = otherCromo[i];
	}
		
	
	//Metodo que toma un numero codificado en binario y devuelve su representacion en decimal
	private double bin2dec(Boolean[] values) {
		long result = 0;
		for (boolean bit : values) {
		    result = result * 2 + (bit ? 1 : 0);
		}			
		return result;
	}
	
	//Metodo que toma una probabilidad de mutacion y con ella se aplica a su propio cromosoma una mutacion basica 
	//donde por cada uno de los genes del cromosoma prueba a ver si es necesario realizar una mutacion
	@Override
	public void mutacionBasica(double probMutacion) {
		Random rand = new Random();
		for(int i=0; i<this.cromosoma.length; i++) {
			double r = rand.nextDouble();
			if(r <= probMutacion) {
				cromosoma[i] = rand.nextBoolean();
			}
		}
	}
	
	//Metodo que devuelve una copia exacta del cromosoma de este individuo
	@Override
	public Boolean[] getCromosoma() {
		Boolean[] result = new Boolean[this.cromosoma.length];
		
		for(int i=0; i<this.cromosoma.length; i++)
			result[i] = this.cromosoma[i].booleanValue();
		
		return result;
	}
}

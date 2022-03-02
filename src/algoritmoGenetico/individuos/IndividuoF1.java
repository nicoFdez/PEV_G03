package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

public class IndividuoF1 extends Individuo<Boolean> {
	
	public IndividuoF1(double precision){
		this.rand = new Random();
		this.precision = precision;
		
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
	
	//Metodo que inicializa el individuo, al ser el genotipo un array de booleanos la inicializacion es
	//un monton de booleanos aleatorios
	@Override
	public void initialize() {
		for(int i = 0; i < this.tamTotal; i++) 
			this.cromosoma[i] = this.rand.nextBoolean();

	}
	
	//Metod que aplica la funcion 1 y devuelvel el valor
	public double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
		//System.out.println("Individuo con datos  a= "+ x1+ " y x2= "+x2 );
		return (21.5 + x1 * Math.sin(4 * Math.PI * x1) + x2 * Math.sin(20 * Math.PI * x2));
	}
	
	//Como en esta funcion el objetivo es llegar a un maximo el fitness lo podemos ver como el mismo valor de aplicar nuestras 
	//x a la funcion 1
	@Override
	public double getFitness() {
		return this.getValor();
	}
	
	
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
	
	@Override
	public void copyFromAnother(Individuo<Boolean> other) {
		Boolean[] otherCromo = other.getCromosoma();
		for(int i = 0; i<otherCromo.length; i++) {
			if(otherCromo[i])
				this.cromosoma[i] = true;
			else 
				this.cromosoma[i] = false;
			//this.cromosoma[i] = otherCromo[i];
		}
		
	}
		
	public static double ConvertRange(
			double originalStart, double originalEnd, // original range
			double newStart, double newEnd, // desired range
			double value) // value to convert
		{
		    double scale = (double)(newEnd - newStart) / (originalEnd - originalStart);
		    return (int)(newStart + ((value - originalStart) * scale));
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
	
	
	private double precision;
}

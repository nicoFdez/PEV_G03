package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

public class IndividuoF4 extends Individuo<Boolean> {
	
	public IndividuoF4(double precision){
		this.rand = new Random();
		this.precision = precision;
		
		this.nGenes = 2;
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];
		for(int i = 0; i<this.min.length;i++) {
			this.min[i] = 0;
			this.max[i] = Math.PI;
			
		}
		
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
		double dimensiones = 2;
		double result =0;
		for(int i = 0; i< dimensiones ; i++) {
			double actualStep=0;
			actualStep = Math.sin(i)*
							Math.pow(
									Math.sin(((i+1)*Math.pow(i, 2))/Math.PI)
									, 20);
			
			result += actualStep;
		}
		result = -result;
		return result;
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

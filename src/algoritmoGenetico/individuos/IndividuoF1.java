package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Random;

public class IndividuoF1 extends Individuo<Boolean> {
	
	public IndividuoF1(double precision){
		this.rand = new Random();
		this.precision = precision;
		
		this.nGenes = 2;
		this.cromosoma = new Boolean[nGenes];
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];	
		this.min[0] = -3.0;
		this.min[1] = 4.1;
		this.max[0] = 12.1;
		this.max[1] = 5.8;
		
		//tamaño del array de "bits" que tiene cada uno de los genes
		this.tamGenes[0] = tamGen(precision, min[0], max[0]);
		this.tamGenes[1] = tamGen(precision, min[1], max[1]);
		
		//Tamaño total de individuo en bits
		this.tamTotal = this.tamGenes[0] + this.tamGenes[1];
	}
	
	//Metodo que inicializa el individuo, al ser el genotipo un array de booleanos la inicializacion es
	//un monton de booleanos aleatorios
	public void initialize() {
		for(int i = 0; i < this.tamTotal; i++) 
			this.cromosoma[i] = this.rand.nextBoolean();

	}
	
	//Metod que aplica la funcion 1 y devuelvel el valor
	public double getValor() {
		double x1 = this.getFenotipo(0), x2 = this.getFenotipo(1);
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
		
		//Tamaño de las partes de un numero 
		//La parte decimal la sacamos según precisión
		int numBitsDecimal= this.tamGen(1, 0, (1/this.precision)-1);
		//La parte entera es el total menos la parte decimal
		int numBitsParteEntera = this.tamGenes[val]-numBitsDecimal;
		
		//Sacamos el inicio del cromosoma en el que empieza un gen
		int inicio =0;
		for(int i = 0; i<val; i++) {
			inicio+=this.tamGenes[i];
		}
		
		//Me quedo con el gen en cuestion
		Boolean[] gen = Arrays.copyOfRange(this.cromosoma, inicio, inicio + this.tamGenes[val]);
		//Divido en sus partes entera y deciaml
		Boolean[] entera = Arrays.copyOfRange(gen, 0, numBitsParteEntera);
		Boolean[] decimal = Arrays.copyOfRange(gen, numBitsParteEntera, numBitsParteEntera+numBitsDecimal);

		result = bin2dec(entera);
		result = result + (bin2dec(decimal)* this.precision);
		
		return result;
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

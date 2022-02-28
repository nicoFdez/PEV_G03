package algoritmoGenetico;

import algoritmoGenetico.cruces.Cruce;
import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.IndividuoF1;
import algoritmoGenetico.mutaciones.Mutacion;
import algoritmoGenetico.seleccion.*;


public class AlgoritmoGenetico {
	
	
	public AlgoritmoGenetico() {
		this.elMejor = new IndividuoF1(0.001);
		this.elMejor.initialize();
		
		this.probCruce = 0.6;
		this.probMutacion = 0.05;
	}
	
	public void Seleccion() {
		this.selector.seleccionar(this.poblacion);
	}
	
	public void Cruce() {
		this.poblacion = this.cruzador.cruzar(this.poblacion, this.probCruce);
	}
	
	public void Mutacion() {
		this.mutador.mutar(this.poblacion, this.probMutacion);
	}
	
	
	public void Evaluar() {
		this.aptitudAcumulada=0;
		this.aptitudMedia=0;
		
		//Miramos todos los individuos de nuestra poblacion
		this.pos_mejor = 0;
		for(int i =0 ; i< this.poblacion.length;i++) {
			//Nos informamos del fitness de cada individuo
			this.fitness[i]= this.poblacion[i].getFitness();
			this.aptitudAcumulada += this.fitness[i];
			if(this.fitness[i] > this.fitness[this.pos_mejor]) {
				this.pos_mejor = i;
			}
			//En caso de que sea intereante nos lo quedamos
			if(this.fitness[i] > elMejor.getFitness()) {
				elMejor= poblacion[i];
			}
		}
		
		//Sacamos la aptitud media
		this.aptitudMedia = this.aptitudAcumulada/this.tamPoblacion;
		
		System.out.println("Mejor hasta el momento: " + this.elMejor.getFitness());
		System.out.println("Mejor actual: " + this.poblacion[this.pos_mejor].getFitness());
		System.out.println("Media poblacion: " + aptitudMedia);
		System.out.println("------------------");
	}
	
	
	public void setSeleccion(Seleccion sel) {
		selector = sel;
	}
	
	public void setCruce(Cruce cruz) {
		cruzador = cruz;
	}
	
	public void setMutacion(Mutacion mut) {
		mutador = mut;
	}
	
	
	public Individuo[] getPoblacion() {
		return this.poblacion;
	}
	
	public double getProbabilidadCruce() {
		return this.probCruce;
	}
	
	public double getProbabilidadMutacion() {
		return this.probMutacion;
	}
	
	public Individuo getMejorIndividuo() {
		return this.elMejor;
	}
	
	public void setMaxGeneraciones(int n) {
		this.maxGeneraciones = n;
	}
	
	public int getMaxGeneraciones() {
		return this.maxGeneraciones;
	}
	
	public void inicializarPoblacion(int tam) {
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoF1(0.001);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
	}

	private Seleccion selector;
	private Cruce cruzador;
	private Mutacion mutador;
	
	private int tamPoblacion;
	private Individuo[] poblacion;
	private double[] fitness;
	private double aptitudAcumulada;
	private double aptitudMedia;
	
	private int maxGeneraciones;
	private double probCruce;
	private double probMutacion;
	private int tamTorneo;
	
	private Individuo elMejor;
	private int pos_mejor;
}

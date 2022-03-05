package algoritmoGenetico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import algoritmoGenetico.cruces.Cruce;
import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.IndividuoF1;
import algoritmoGenetico.individuos.IndividuoF2;
import algoritmoGenetico.mutaciones.Mutacion;
import algoritmoGenetico.seleccion.*;


public class AlgoritmoGenetico {
	
	
	public AlgoritmoGenetico() {
		this.probCruce = 0.6;
		this.probMutacion = 0.05;
		this.porcElitismo = 0.03;
	}
	
	public void Seleccion() {
		if(this.elitism) {
			Arrays.sort(this.poblacion, comp);
			this.saveElites();
		}
		int[] pobSeleccionada = this.selector.seleccionar(this.poblacion);
		
		switch(this.tipoPoblacion) {
		case 1:
			this.poblacion = seleccion1(pobSeleccionada);
			break;
		case 2:
			this.poblacion = seleccion2(pobSeleccionada);
			break;
		}
		
		
		if(this.elitism) {
			Arrays.sort(this.poblacion, comp);		
			//Meter elite
			this.recoverSavedElites();
		}
	}
	
	private Individuo[] seleccion1(int[] pobSelec) {
		Individuo[] pob = new Individuo[this.tamPoblacion];
		for(int i=0; i<this.tamPoblacion; i++) {
			pob[i] = new IndividuoF1(this.poblacion[pobSelec[i]]);
		}
		return pob;
	}
	
	private Individuo[] seleccion2(int[] pobSelec) {
		Individuo[] pob = new Individuo[this.tamPoblacion];
		for(int i=0; i<this.tamPoblacion; i++) {
			pob[i] = new IndividuoF2(this.poblacion[pobSelec[i]]);
		}
		return pob;
	}
	
	public void Cruce() {
		this.poblacion = this.cruzador.cruzar(this.poblacion, this.probCruce);
	}
	
	public void Mutacion() {
		this.mutador.mutar(this.poblacion, this.probMutacion);
	}
	
	
	public void Evaluar(int nGeneracion) {
		this.aptitudAcumulada=0;
		this.aptitudMedia=0;
		
		//Miramos todos los individuos de nuestra poblacion
		this.pos_mejor = 0;
		for(int i =0 ; i< this.poblacion.length;i++) {
			//Nos informamos del fitness de cada individuo
			double fitnessActual = this.poblacion[i].getFitness();
			this.fitness[i]= fitnessActual;
			this.aptitudAcumulada += this.fitness[i];
			//Si estamos maximizando y este es mejor, o si estamos minimizando y este es mas bajo
			if((this.maximize &&  this.fitness[i] > this.fitness[this.pos_mejor]) ||
				(!this.maximize &&  this.fitness[i] < this.fitness[this.pos_mejor])	) {
				this.pos_mejor = i;
			}

			//En caso de que sea intereante nos lo quedamos
		}
		
		//Si estamos maximizando y este es mejor, o si estamos minimizando y este es mas bajo
		if(( this.maximize && this.fitness[this.pos_mejor] > elMejor.getFitness()) ||
				( !this.maximize && this.fitness[this.pos_mejor] < elMejor.getFitness())) {			
			this.elMejor.copyFromAnother(this.poblacion[this.pos_mejor]);
		}
				
		//Sacamos la aptitud media
		this.aptitudMedia = this.aptitudAcumulada/this.tamPoblacion;
		System.out.println("Mejor hasta el momento: " + this.elMejor.getFitness());
		System.out.println("Mejor actual: " + this.poblacion[this.pos_mejor].getFitness());
		System.out.println("Media poblacion: " + aptitudMedia);
		System.out.println("------------------");
		
		this.mediasGeneracion[nGeneracion] = this.aptitudMedia;
		this.mejoresGeneracion[nGeneracion] = this.poblacion[this.pos_mejor].getFitness();
		this.mejorAbsoluto[nGeneracion] = this.elMejor.getFitness();
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
	
	public double[] getMejoresAbsolutos() {
		return this.mejorAbsoluto;
	}
	
	public double[] getMediasGeneraciones() {
		return this.mediasGeneracion;
	}
	
	public double[] getMejoresGeneraciones() {
		return this.mejoresGeneracion;
	}
	
	public void inicializarPoblacion(int tipo, int tam) {
		
		this.tipoPoblacion = tipo;
		
		this.mejorAbsoluto= new double[this.maxGeneraciones];
		this.mediasGeneracion= new double[this.maxGeneraciones];
		this.mejoresGeneracion = new double[this.maxGeneraciones];
		switch(tipo) {
		case 1:
			inicializarPoblacion1(tam, 0.0001);
			comp = new ComparadorMax();
			break;
		case 2:
			inicializarPoblacion2(tam, 0.0001);
			break;
		}
	}
	
	
	public void inicializarPoblacion1(int tam, double precision) {
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoF1(precision);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
		this.elMejor = new IndividuoF1(precision);
		this.elMejor.initialize();
		
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		//Inicializamos
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoF1(precision);
			elites[i].initialize();
			eliteValues[i] = 0;
		}
		
		this.comp = new ComparadorMax();
		this.maximize = true;
	}
	
	public void inicializarPoblacion2(int tam, double precision) {
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoF2(precision);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
		this.elMejor = new IndividuoF2(precision);
		this.elMejor.initialize();
		
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		//Inicializamos
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoF2(precision);
			elites[i].initialize();
			eliteValues[i] = 0;
		}
		
		this.comp = new ComparadorMin();
		this.maximize = false;
	}

	private void saveElites() {
		int nElites = this.elites.length;		
		for(int i = 0; i < nElites; i++) {
			this.elites[i].copyFromAnother(this.poblacion[i]);
		}
	}
	
	private void recoverSavedElites() {	
		int nElites = this.elites.length;		
		for(int i = this.tamPoblacion - nElites; i < this.tamPoblacion; i++) {
			this.poblacion[i].copyFromAnother(this.elites[i-(this.tamPoblacion - nElites)]);
		}	
	}
	
	public void setElitism(Boolean b) {
		this.elitism = b;
	}
	
	private Seleccion selector;
	private Cruce cruzador;
	private Mutacion mutador;
	
	private int tamPoblacion;
	private Individuo[] poblacion;
	private Individuo[] elites;
	private double[] eliteValues;
	private double[] fitness;
	private double aptitudAcumulada;
	private double aptitudMedia;
	
	private int maxGeneraciones;
	private double probCruce;
	private double probMutacion;
	private int tamTorneo;
	
	private Individuo elMejor;
	private int pos_mejor;
	
	private double porcElitismo;
	private boolean elitism;
	
	private Comparator comp;
	private boolean maximize;
	
	private double[] mejoresGeneracion;
	private double[] mediasGeneracion;
	private double[] mejorAbsoluto;
	
	int tipoPoblacion;
}

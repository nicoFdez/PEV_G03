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
import algoritmoGenetico.individuos.IndividuoF3;
import algoritmoGenetico.individuos.IndividuoF4;
import algoritmoGenetico.individuos.IndividuoF5;
import algoritmoGenetico.mutaciones.Mutacion;
import algoritmoGenetico.seleccion.*;

//Clase que implementa el esqueleto de un algoritmo genetico y proporciona las funciones necesarias
//para realizar un evolucion sobre un grupo de individuos
public class AlgoritmoGenetico {
	
	//Constructora
	public AlgoritmoGenetico() {
		
	}
	
	//Metodo que realiza la operacion de seleccion sobre la poblacion
	public void Seleccion() {	
		int[] pobSeleccionada;
		pobSeleccionada = this.selector.seleccionar(this.poblacion, !this.maximize);
		//Dependiendo del tipo de funcion que estemos analizando vamos a hacer una seleccion u otra
		switch(this.tipoPoblacion) {
		case Funcion1:
			this.poblacion = seleccion1(pobSeleccionada);
			break;
		case Funcion2:
			this.poblacion = seleccion2(pobSeleccionada);
			break;
		case Funcion3:
			this.poblacion = seleccion3(pobSeleccionada);
			break;
		case Funcion4:
			this.poblacion = seleccion4(pobSeleccionada);
			break;
		case Funcion5:
			this.poblacion = seleccion5(pobSeleccionada);
			break;
		}
	}
	
	///////////////////////Métodos de selección específicos de cada función////////////////////////////////////
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
	
	private Individuo[] seleccion3(int[] pobSelec) {
		Individuo[] pob = new Individuo[this.tamPoblacion];
		for(int i=0; i<this.tamPoblacion; i++) {
			pob[i] = new IndividuoF3(this.poblacion[pobSelec[i]]);
		}
		return pob;
	}
	
	private Individuo[] seleccion4(int[] pobSelec) {
		Individuo[] pob = new Individuo[this.tamPoblacion];
		for(int i=0; i<this.tamPoblacion; i++) {
			pob[i] = new IndividuoF4(this.poblacion[pobSelec[i]]);
		}
		return pob;
	}
	
	private Individuo[] seleccion5(int[] pobSelec) {
		Individuo[] pob = new Individuo[this.tamPoblacion];
		for(int i=0; i<this.tamPoblacion; i++) {
			pob[i] = new IndividuoF5(this.poblacion[pobSelec[i]]);
		}
		return pob;
	}
	///////////////////////Métodos de selección específicos de cada función////////////////////////////////////

	
	//Metodo encargado de realizar la operación de cruce sobre la población 
	public void Cruce() {
		this.poblacion = this.cruzador.cruzar(this.poblacion, this.probCruce);
	}
	
	//Metodo encargado de realizar la operación de mutacion sobre la población 
	public void Mutacion() {
		this.mutador.mutar(this.poblacion, this.probMutacion);
	}
	
	//Método que evalua la población de la generación en la que nos encontremos y con esto se almacena
	//datos como e mejor de la población, la media, etc para después de realizar la evolución por 
	//completo, poder mostrar dichos datos en una gráfica
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
		
		//Almacenamos estos datos para la futua gráfica
		this.mediasGeneracion[nGeneracion] = this.aptitudMedia;
		this.mejoresGeneracion[nGeneracion] = this.poblacion[this.pos_mejor].getFitness();
		this.mejorAbsoluto[nGeneracion] = this.elMejor.getFitness();
	}
	
	//Método que inicializa la pobalción sobre la que vamos a realizar la evolución 
	//Dependiendo de los parámetros que nos lleguen los individuos  serán de un tipo u otro
	public void inicializarPoblacion(TiposFuncion tipo, int tam, int nParamsf4) {
		
		this.tipoPoblacion = tipo;
		this.nParamsf4 = nParamsf4;
		
		this.mejorAbsoluto= new double[this.maxGeneraciones];
		this.mediasGeneracion= new double[this.maxGeneraciones];
		this.mejoresGeneracion = new double[this.maxGeneraciones];
		switch(tipo) {
		case Funcion1:
			inicializarPoblacion1(tam, 0.0001);
			comp = new ComparadorMax();
			break;
		case Funcion2:
			inicializarPoblacion2(tam, 0.0001);
			break;
		case Funcion3:
			inicializarPoblacion3(tam, 0.0001);
			break;
		case Funcion4:
			inicializarPoblacion4(tam, 0.0001);
			break;
		case Funcion5:
			inicializarPoblacion5(tam);
			break;
		}
	}
	
	////////////////////Métodos encargados de inicializar la población de una forma u otra dependiendo de la función que vayamos a analizar////////////////////////////
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

	public void inicializarPoblacion3(int tam, double precision) {
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoF3(precision);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
		this.elMejor = new IndividuoF3(precision);
		this.elMejor.initialize();
		
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		//Inicializamos
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoF3(precision);
			elites[i].initialize();
			eliteValues[i] = 0;
		}
		
		this.comp = new ComparadorMin();
		this.maximize = false;
	}
	
	public void inicializarPoblacion5(int tam) {
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoF5(this.nParamsf4);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
		this.elMejor = new IndividuoF5(this.nParamsf4);
		this.elMejor.initialize();
		
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		//Inicializamos
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoF5(this.nParamsf4);
			elites[i].initialize();
			eliteValues[i] = 0;
		}
		
		this.comp = new ComparadorMin();
		this.maximize = false;
	}
	
	public void inicializarPoblacion4(int tam, double precision) {
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoF4(precision, this.nParamsf4);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
		this.elMejor = new IndividuoF4(precision, this.nParamsf4);
		this.elMejor.initialize();
		
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		//Inicializamos
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoF4(precision, this.nParamsf4);
			elites[i].initialize();
			eliteValues[i] = 0;
		}
		
		this.comp = new ComparadorMin();
		this.maximize = false;
	}
	////////////////////Métodos encargados de inicializar la población de una forma u otra dependiendo de la función que vayamos a analizar////////////////////////////

	
	//Metodo que se encarga de salvar los mejores individuos de esta generación en caso de que estemos haciendo la evolución usando elitismo
	public void saveElites() {	
		if(this.elitism) {
			Arrays.sort(this.poblacion, comp);			
			int nElites = this.elites.length;		
			for(int i = 0; i < nElites; i++) {
				this.elites[i].copyFromAnother(this.poblacion[i]);
			}	
		}
	}
	
	//Metodo que se encarga de incluir los mejores individuos de esta generación a la población en caso de que estemos haciendo la evolución usando elitismo
	public void recoverSavedElites() {
		if(this.elitism) {
			Arrays.sort(this.poblacion, comp);
			int nElites = this.elites.length;		
			for(int i = this.tamPoblacion - nElites; i < this.tamPoblacion; i++) {
				this.poblacion[i].copyFromAnother(this.elites[i-(this.tamPoblacion - nElites)]);
			}
		}
	}
	
	//Setters encargados de configurar parámetros que alterarán la manera en la que se realiza la evolución de la población////////////////////////////////
	public void setElitism(double proportion) {
		this.elitism = proportion > 0;
		this.porcElitismo = proportion;
	}
	
	public void setProbCruce(double prob) {
		this.probCruce = prob;
	}
	
	public void setProbMutacion(double prob) {
		this.probMutacion = prob;
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
	//Setters encargados de configurar parámetros que alterarán la manera en la que se realiza la evolución de la población////////////////////////////////

	
	//Getters que informan sobre los parámetros que alterarán la manera en la que se realiza la evolución de la población////////////////////////////////
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
	//Getters que informan sobre los parámetros que alterarán la manera en la que se realiza la evolución de la población////////////////////////////////

	
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
	
	private TiposFuncion tipoPoblacion;
	
	private int nParamsf4;
}

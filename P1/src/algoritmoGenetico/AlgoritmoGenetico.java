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
		Individuo[] nuevaPoblacion = new Individuo[this.tamPoblacion];
		int[] pobSeleccionada;
		pobSeleccionada = this.selector.seleccionar(this.poblacion, !this.maximize);
		//Dependiendo del tipo de funcion que estemos analizando vamos a hacer una seleccion u otra
		switch(this.tipoPoblacion) {
		case Funcion1:
			for(int i=0; i<this.tamPoblacion; i++) {
				nuevaPoblacion[i] = new IndividuoF1(this.poblacion[pobSeleccionada[i]]);
				
			}
			break;
		case Funcion2:
			for(int i=0; i<this.tamPoblacion; i++) {
				this.poblacion[i] = new IndividuoF2(this.poblacion[pobSeleccionada[i]]);
			}
			break;
		case Funcion3:
			for(int i=0; i<this.tamPoblacion; i++) {
				this.poblacion[i] = new IndividuoF3(this.poblacion[pobSeleccionada[i]]);
			}
			break;
		case Funcion4:
			for(int i=0; i<this.tamPoblacion; i++) {
				this.poblacion[i] = new IndividuoF4(this.poblacion[pobSeleccionada[i]]);
			}
			break;
		case Funcion5:
			for(int i=0; i<this.tamPoblacion; i++) {
				this.poblacion[i] = new IndividuoF5(this.poblacion[pobSeleccionada[i]]);
			}
			break;
		}
		this.poblacion = nuevaPoblacion;
	}
	
	//Metodo encargado de realizar la operaci�n de cruce sobre la poblaci�n 
	public void Cruce() {
		this.poblacion = this.cruzador.cruzar(this.poblacion, this.probCruce);
	}
	
	//Metodo encargado de realizar la operaci�n de mutacion sobre la poblaci�n 
	public void Mutacion() {
		this.mutador.mutar(this.poblacion, this.probMutacion);
	}
	
	//M�todo que evalua la poblaci�n de la generaci�n en la que nos encontremos y con esto se almacena
	//datos como e mejor de la poblaci�n, la media, etc para despu�s de realizar la evoluci�n por 
	//completo, poder mostrar dichos datos en una gr�fica
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
		
		//Almacenamos estos datos para la futua gr�fica
		this.mediasGeneracion[nGeneracion] = this.aptitudMedia;
		this.mejoresGeneracion[nGeneracion] = this.poblacion[this.pos_mejor].getFitness();
		this.mejorAbsoluto[nGeneracion] = this.elMejor.getFitness();
	}
	
	//M�todo que inicializa la pobalci�n sobre la que vamos a realizar la evoluci�n 
	//Dependiendo de los par�metros que nos lleguen los individuos  ser�n de un tipo u otro
	public void inicializarPoblacion(TiposFuncion tipo, int tam, int nParamsf4) {
		
		this.tipoPoblacion = tipo;
		this.nParamsf4 = nParamsf4;
		
		this.mejorAbsoluto= new double[this.maxGeneraciones];
		this.mediasGeneracion= new double[this.maxGeneraciones];
		this.mejoresGeneracion = new double[this.maxGeneraciones];
		double precision = 0.0001;
		
		this.poblacion = new Individuo[tam];
		this.tamPoblacion = tam;
		this.fitness = new double[tamPoblacion];
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		
		switch(tipo) {
		case Funcion1:	
			for(int i=0; i<this.tamPoblacion; i++)
				this.poblacion[i] = new IndividuoF1(precision);
			this.elMejor = new IndividuoF1(precision);
			for(int i = 0; i< numElites; i++)
				elites[i] = new IndividuoF1(precision);
			comp = new ComparadorMax();
			this.maximize = true;
			break;
			
		case Funcion2:
			for(int i=0; i<this.tamPoblacion; i++)
				this.poblacion[i] = new IndividuoF2(precision);
			this.elMejor = new IndividuoF2(precision);
			for(int i = 0; i< numElites; i++)
				elites[i] = new IndividuoF2(precision);
			this.comp = new ComparadorMin();
			this.maximize = false;
			break;
			
		case Funcion3:
			for(int i=0; i<this.tamPoblacion; i++)
				this.poblacion[i] = new IndividuoF3(precision);
			this.elMejor = new IndividuoF3(precision);
			for(int i = 0; i< numElites; i++)
				elites[i] = new IndividuoF3(precision);
			this.comp = new ComparadorMin();
			this.maximize = false;
			break;
			
		case Funcion4:
			for(int i=0; i<this.tamPoblacion; i++)
				this.poblacion[i] = new IndividuoF4(precision, this.nParamsf4);
			this.elMejor = new IndividuoF4(precision, this.nParamsf4);
			for(int i = 0; i< numElites; i++)
				elites[i] = new IndividuoF4(precision, this.nParamsf4);
			this.comp = new ComparadorMin();
			this.maximize = false;
			break;
			
		case Funcion5:
			for(int i=0; i<this.tamPoblacion; i++)
				this.poblacion[i] = new IndividuoF5(this.nParamsf4);
			this.elMejor = new IndividuoF5(this.nParamsf4);
			for(int i = 0; i< numElites; i++)
				elites[i] = new IndividuoF5(this.nParamsf4);
			this.comp = new ComparadorMin();
			this.maximize = false;
			break;
		}
		
		//Inicializaci�n com�n a todas las funciones
		for(int i=0; i<this.tamPoblacion; i++)
			this.poblacion[i].initialize();
		this.elMejor.initialize();
		
		for(int i = 0; i< numElites; i++){
			elites[i].initialize();
			eliteValues[i] = 0;
		}
	}
	
	//Metodo que se encarga de salvar los mejores individuos de esta generaci�n en caso de que estemos haciendo la evoluci�n usando elitismo
	public void saveElites() {	
		if(this.elitism) {
			Arrays.sort(this.poblacion, comp);
			int nElites = this.elites.length;		
			for(int i = 0; i < nElites; i++) {
				this.elites[i].copyFromAnother(this.poblacion[i]);
			}	
		}
	}
	
	//Metodo que se encarga de incluir los mejores individuos de esta generaci�n a la poblaci�n en caso de que estemos haciendo la evoluci�n usando elitismo
	public void recoverSavedElites() {
		if(this.elitism) {
			Arrays.sort(this.poblacion, comp);
			int nElites = this.elites.length;		
			for(int i = this.tamPoblacion - nElites; i < this.tamPoblacion; i++) {
				this.poblacion[i].copyFromAnother(this.elites[i-(this.tamPoblacion - nElites)]);
			}
		}
	}
	
	//Setters encargados de configurar par�metros que alterar�n la manera en la que se realiza la evoluci�n de la poblaci�n////////////////////////////////
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
	//Setters encargados de configurar par�metros que alterar�n la manera en la que se realiza la evoluci�n de la poblaci�n////////////////////////////////

	
	//Getters que informan sobre los par�metros que alterar�n la manera en la que se realiza la evoluci�n de la poblaci�n////////////////////////////////
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
	//Getters que informan sobre los par�metros que alterar�n la manera en la que se realiza la evoluci�n de la poblaci�n////////////////////////////////

	
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

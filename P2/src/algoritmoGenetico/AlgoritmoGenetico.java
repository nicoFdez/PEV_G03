package algoritmoGenetico;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import algoritmoGenetico.cruces.Cruce;
import algoritmoGenetico.individuos.ComparadorMax;
import algoritmoGenetico.individuos.ComparadorMin;
import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.IndividuoAeropuerto;
import algoritmoGenetico.mutaciones.Mutacion;
import algoritmoGenetico.seleccion.Seleccion;

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
		
		Individuo[] aux = Arrays.copyOf(this.poblacion, this.poblacion.length);
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoAeropuerto(aux[pobSeleccionada[i]]);
		}
	}
	
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
	public void inicializarPoblacion(int tam, int nVuelos, int nPistas) {		
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
		
		for(int i=0; i<this.tamPoblacion; i++) {
			this.poblacion[i] = new IndividuoAeropuerto(nVuelos, nPistas);
			this.poblacion[i].initialize();
		}
		this.elMejor = new IndividuoAeropuerto(nVuelos, nPistas);
		this.elMejor.initialize();
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoAeropuerto(nVuelos, nPistas);
			elites[i].initialize();
			eliteValues[i] = 0;
		}
		comp = new ComparadorMin();
		this.maximize = false;
	}
	
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
			
			//Desordenamos la poblacion
			List<Individuo> poblacionListada = Arrays.asList(this.poblacion);
			Collections.shuffle(poblacionListada);
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
}

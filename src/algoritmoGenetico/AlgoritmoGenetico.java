package algoritmoGenetico;

import java.util.Arrays;

import algoritmoGenetico.cruces.Cruce;
import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.IndividuoF1;
import algoritmoGenetico.mutaciones.Mutacion;
import algoritmoGenetico.seleccion.*;


public class AlgoritmoGenetico {
	
	
	public AlgoritmoGenetico() {
		this.probCruce = 0.6;
		this.probMutacion = 0.05;
		this.porcElitismo = 0.03;
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
			double fitnessActual = this.poblacion[i].getFitness();
			this.fitness[i]= fitnessActual;
			this.aptitudAcumulada += this.fitness[i];
			if(this.fitness[i] > this.fitness[this.pos_mejor]) {
				this.pos_mejor = i;
			}
			//En caso de que sea intereante nos lo quedamos
		}
		
		if(this.fitness[this.pos_mejor] > elMejor.getFitness()) {			
			this.elMejor.copyFromAnother(this.poblacion[this.pos_mejor]);
		}
		
		//Sacamos la aptitud media
		this.aptitudMedia = this.aptitudAcumulada/this.tamPoblacion;
		//this.elMejor = new IndividuoF1(0.001);
		//this.elMejor.initialize();
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
			this.poblacion[i] = new IndividuoF1(0.0001);
			this.poblacion[i].initialize();
		}
		
		this.fitness = new double[tamPoblacion];
		this.elMejor = new IndividuoF1(0.0001);
		this.elMejor.initialize();
		
		//Preparamos huecos para los elites
		int numElites = (int)((double)tam*this.porcElitismo);
		elites = new Individuo[numElites];
		eliteValues = new double[numElites];
		//Inicializamos
		for(int i = 0; i< numElites; i++) {
			elites[i] = new IndividuoF1(0.0001);
			eliteValues[i] =0;
		}
	}

	public void saveElites() {
		//Reseteamos los elites porque nos toca buscar nuevos en la generacion actual
		for(int i = 0; i< eliteValues.length;i++) {
			eliteValues[i]=0;
		}
		
		//Recorro toda la poblacion preguntando a cada individuo si es mejor que lo que tenemos actualmente
		//guardado como la seccion de elites
		for(int i = 0 ; i < this.poblacion.length; i++) {
			double currentIndividualValue = this.poblacion[i].getFitness();
			//Sabiendo el fitness del individuo actual recorro los valores de mis elites buscando a alguien
			//que sea remplazado por este nuevo individuo
			for(int j = 0; j< eliteValues.length;j++) {
				if(currentIndividualValue > eliteValues[j]) {
					eliteValues[j] = currentIndividualValue;
					elites[j].copyFromAnother(this.poblacion[i]);
				}
			}
		
		}
	}
	
	public void recoverSavedElites() {
		//vamos a recorrer todos los elites mirando si los podemos reincorporar en la poblacion actual
		for(int eliteIndex = 0; eliteIndex < elites.length; eliteIndex ++) {
			//Miro el valor del elite actual que estoy analizando
			double eliteValue = elites[eliteIndex].getFitness();
			//recorro la poblacion hasta encontrar alguien que sea peor que el elite que estoy analizando
			int i =0;
			while(i < this.poblacion.length && this.poblacion[i].getFitness() > eliteValue)
				i++;
			//Si he encontrado a alguien lo sobreescribo para que sea igual al elite
			if(i< this.poblacion.length)
				this.poblacion[i].copyFromAnother(elites[eliteIndex]);
		}
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
}

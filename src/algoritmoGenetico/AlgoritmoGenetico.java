package algoritmoGenetico;
import java.util.*;

import algoritmoGenetico.cruces.Cruce;
import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.IndividuoF1;
import algoritmoGenetico.mutaciones.Mutacion;
import algoritmoGenetico.seleccion.*;


public class AlgoritmoGenetico {
	
	
	public AlgoritmoGenetico() {
		
	}
	
	public void Seleccion() {
		this.selector.seleccionar(this.poblacion);
	}
	public void Cruce() {
		this.cruzador.cruzar(this.poblacion, this.probCruce);
	}
	
	public void Mutacion() {
		this.mutador.mutar(this.poblacion, this.probMutacion);
	}
	
	
	public void Evaluar() {
		this.aptitudAcumulada=0;
		this.aptitudMedia=0;
		
		//Miramos todos los individuos de nuestra poblacion
		for(int i =0 ; i< this.poblacion.length;i++) {
			//Nos informamos del fitness de cada individuo
			this.fitness[i]= this.poblacion[i].getFitness();
			this.aptitudAcumulada += this.fitness[i];
			//En caso de que sea intereante nos lo quedamos
			if(this.fitness[i] > elMejor.getFitness()) {
				elMejor= poblacion[i];
				this.pos_mejor= i;
			}
		}
		
		//Sacamos la aptitud media
		this.aptitudMedia = this.aptitudAcumulada/this.tamPoblacion;
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
	
	public int getMaxGeneraciones() {
		return this.maxGeneraciones;
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

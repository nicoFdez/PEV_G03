package algoritmoGenetico.individuos;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.operadoresArbol.OperadorAnd;
import algoritmoGenetico.individuos.operadoresArbol.OperadorArbol;
import algoritmoGenetico.individuos.operadoresArbol.OperadorIF;
import algoritmoGenetico.individuos.operadoresArbol.OperadorNOT;
import algoritmoGenetico.individuos.operadoresArbol.OperadorOR;
import algoritmoGenetico.individuos.operadoresArbol.OperadorTerminal;


//clase que representa un individuo preparado para realizar una evolución sobre la funcion5
public class IndividuoMultiplexor6 extends Individuo  {
	
	//Constructoras
	public IndividuoMultiplexor6(Individuo other) {
		
		this.cromosoma = new MyTree(((IndividuoMultiplexor6)other).getArbol(),null);
		this.maxDepth = ((IndividuoMultiplexor6)other).getMaxDepth();
	}
	
	public IndividuoMultiplexor6(){
		
	}
	
	@Override
	public double getFitness() {
		return this.getValor();
	}
	
	//Metod que aplica la funcion 5 y devuelvel el valor
	private double getValor() {
		return getValor(this.cromosoma);
	}
	
	//Metod que hace la asignación de vuelos a las pistas del aeropuerto
	private double getValor(MyTree  cromo) {
		
		int nAciertos = 0;
		
		for(int i=0; i<64; i++) {
			int valorTruth = InfoMultiplexor.getSalida(i);
			
			int[] bin = InfoMultiplexor.toBinary(i, 6);
			this.cromosoma.getData().evaluar(bin, cromo);
		}
		
		return 0;
	}
	
	public static int AND(int a, int b) {
		if(a==0 || b==0) return 0;
		else return 1;
	}
	
	public static int OR(int a, int b) {
		if(a==1 || b==1) return 1;
		else return 0;
	}
	
	public static int NOT(int a) {
		if(a==1) return 0;
		else return 1;
	}
	
	public static int IF(int a, int b, int c) {
		if(a==1) return b;
		else return c;
	}
	
	@Override
	public void initialize() {
	
		
	}
	
	public MyTree  initializeCreciente(int profundidadActual, int profundidadMaxima) {
		MyTree  newNode = null;
		if(profundidadActual < maxDepth) {
			//Sacas el tipo que le toca dentro de los operadores
			int low = 0;
			int high = InfoMultiplexor.ValoresNodos6.values().length;
			int result = rand.nextInt(high-low) + low;
			newNode = new MyTree ();
			
			//Dependiendo del tipo se crean X hijos
			OperadorArbol tipoResultante = newNode.getData();
			int numHijos=0;
			switch (InfoMultiplexor.ValoresNodos6.values()[result]) {
				case AND:	numHijos = 2; newNode = new MyTree (new OperadorAnd(result)); 					break;
				case OR:	numHijos = 2; newNode = new MyTree (new OperadorOR(result)); 					break;
				case NOT:	numHijos = 1; newNode = new MyTree (new OperadorNOT(result)); 					break;
				case IF:	numHijos = 3; newNode = new MyTree (new OperadorIF(result)); 					break;
				default: 	numHijos = 0; newNode = new MyTree (new OperadorTerminal(result));				break;
			}
			
			for(int i = 0;i<numHijos; i++) 
				newNode.addChild(initializeCreciente(profundidadActual+1, profundidadMaxima));
		}
		
		//Tenemos un nodo terminal
		else {
			//Sacas el tipo que le toca dentro de los terminales
			int low = 0;
			int high = InfoMultiplexor.numTerminales;
			int result = rand.nextInt(high-low) + low;
			newNode = new MyTree (new OperadorTerminal(result));
		}
		return newNode;
	}
	
	public MyTree  initializeCompleta(int profundidadActual, int profundidadMaxima) {
		MyTree  newNode = null;
		if(profundidadActual < maxDepth) {
			//Sacas el tipo que le toca dentro de los operadores
			int low = InfoMultiplexor.numTerminales;
			int high = InfoMultiplexor.ValoresNodos6.values().length;
			int result = rand.nextInt(high-low) + low;
			
			//Dependiendo del tipo se crean X hijos
			OperadorArbol tipoResultante = newNode.getData();
			int numHijos=0;
			switch (InfoMultiplexor.ValoresNodos6.values()[result]) {
				case AND:	numHijos = 2; newNode = new MyTree(new OperadorAnd(result)); 					break;
				case OR:	numHijos = 2; newNode = new MyTree(new OperadorOR(result)); 					break;
				case NOT:	numHijos = 1; newNode = new MyTree(new OperadorNOT(result)); 					break;
				case IF:	numHijos = 3; newNode = new MyTree(new OperadorIF(result)); 					break;
				default: 	numHijos = 0; 	break;
			}
			for(int i = 0;i<numHijos; i++) 
				newNode.addChild(initializeCompleta(profundidadActual+1, profundidadMaxima));
			
		}
		
		//Tenemos un nodo terminal
		else {
			//Sacas el tipo que le toca dentro de los terminales
			int low = 0;
			int high = InfoMultiplexor.numTerminales;
			int result = rand.nextInt(high-low) + low;
			newNode = new MyTree(new OperadorTerminal(result));
		}
		return newNode;
	}
	
	//Metodo que toma dos posiciones aleatorias dentro del cromosoma e intercambia el contenido de dichas posiciones
	@Override
	public void mutacionTerminal(double probMutacion) {
		MyTree tree = this.cromosoma;
		Random rnd = new Random();
		while(!tree.isLeaf()) {
			List<MyTree> children =  tree.getChildren();
			tree = children.get(rnd.nextInt(children.size()));
		}
		
		//Preguntamos por el tipo del nodo terminal y sacamos uno nuevo que no coincida con el actual
		InfoMultiplexor.ValoresNodos6 tipoNodo = InfoMultiplexor.ValoresNodos6.values()[((OperadorTerminal)tree.getData()).getIndice()];
		InfoMultiplexor.ValoresNodos6 nuevoTipo = InfoMultiplexor.ValoresNodos6.values()[rnd.nextInt(InfoMultiplexor.numTerminales)];
		while(nuevoTipo == tipoNodo) {
			nuevoTipo = InfoMultiplexor.ValoresNodos6.values()[rnd.nextInt(InfoMultiplexor.numTerminales)];
		}
		
		//Seteamos el arbol al nuevo valor
		tree.setData(new OperadorTerminal(nuevoTipo.ordinal()));
	}
	
	@Override
	public MyTree getCromosoma() {	
		return null;
	}
	
	public MyTree getArbol(){
		return this.cromosoma;
	}
	
	public int getMaxDepth() {
		return maxDepth.intValue();
	}

	Integer maxDepth;
	Random rand = new Random();
	
}

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
	
	public int getDepth() {
		return this.cromosoma.getMaxDepth(cromosoma);
	}
	
	@Override
	public double getFitness() {
		//BLOATING 
		if(this.cromosoma.getMaxDepth(this.cromosoma)>this.profundidadMedia && rand.nextInt()%2==0) {
			//System.out.println("Alguien se ha jodido");
			//return 1;
		}
		return this.getValor();
	}
	
	//Metod que aplica la funcion 5 y devuelvel el valor
	private double getValor() {
		return getValor(this.cromosoma);
	}
	
	//Metod que hace la asignación de vuelos a las pistas del aeropuerto
	private double getValor(MyTree  cromo) {
		
		int nAciertos = 0;
		for(int i=0; i<InfoMultiplexor.numPosibilidades; i++) {
			int valorTruth = InfoMultiplexor.getSalida(i);
			
			int[] bin = InfoMultiplexor.toBinary(i, InfoMultiplexor.numTerminales);
			int valorArbol = this.cromosoma.getData().evaluar(bin, cromo);
			
			if(valorTruth == valorArbol) nAciertos++;
		}
		
		return nAciertos;
	}
	
	@Override
	public MyTree  initializeCreciente(int profundidadActual, int profundidadMaxima) {
		MyTree  newNode = null;
		if(profundidadActual < profundidadMaxima - 1) {
			//Sacas el tipo que le toca dentro de los operadores
			int low = 0;
			int high = InfoMultiplexor.numNodosDistintosTipos;
			int result = rand.nextInt(high-low) + low;
			newNode = new MyTree ();
			
			//Dependiendo del tipo se crean X hijos
			OperadorArbol tipoResultante = newNode.getData();
			int numHijos=0;
			switch (InfoMultiplexor.getTipoNodo(result)) {
				case NODOAND:	numHijos = 2; newNode = new MyTree (new OperadorAnd(result)); 					break;
				case NODOOR:	numHijos = 2; newNode = new MyTree (new OperadorOR(result)); 					break;
				case NODONOT:	numHijos = 1; newNode = new MyTree (new OperadorNOT(result)); 					break;
				case NODOIF:	numHijos = 3; newNode = new MyTree (new OperadorIF(result)); 					break;
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
	
	@Override
	public MyTree initializeCompleta(int profundidadActual, int profundidadMaxima) {
		MyTree newNode = null;
		if(profundidadActual < profundidadMaxima - 1) {
			//Sacas el tipo que le toca dentro de los operadores
			int low = InfoMultiplexor.numTerminales;
			int high = InfoMultiplexor.numNodosDistintosTipos;
			int result = rand.nextInt(high-low) + low;
			
			int numHijos=0;
			switch (InfoMultiplexor.getTipoNodo(result)) {
				case NODOAND:	numHijos = 2; newNode = new MyTree(new OperadorAnd(result)); 					break;
				case NODOOR:	numHijos = 2; newNode = new MyTree(new OperadorOR(result)); 					break;
				case NODONOT:	numHijos = 1; newNode = new MyTree(new OperadorNOT(result)); 					break;
				case NODOIF:	numHijos = 3; newNode = new MyTree(new OperadorIF(result)); 					break;
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
	public void mutacionTerminal() {
		MyTree tree = this.cromosoma;
		Random rnd = new Random();
		while(!tree.isLeaf()) {
			List<MyTree> children =  tree.getChildren();
			tree = children.get(rnd.nextInt(children.size()));
		}
		
		//Preguntamos por el tipo del nodo terminal y sacamos uno nuevo que no coincida con el actual
		int tipoNodo = tree.getData().getIndice();
		int nuevoTipo = rnd.nextInt(InfoMultiplexor.numTerminales);
		while(nuevoTipo == tipoNodo) {
			nuevoTipo = rnd.nextInt(InfoMultiplexor.numTerminales);
		}
		
		//Seteamos el arbol al nuevo valor
		tree.setData(new OperadorTerminal(nuevoTipo));
	}
	
	//Metodo que toma dos posiciones aleatorias dentro del cromosoma e intercambia el contenido de dichas posiciones
	@Override
	public void mutacionFuncional() {
		MyTree tree = this.cromosoma;
		Random rnd = new Random();
		List<MyTree> nodos =  tree.getPreOrden();
		
		int orIndex = InfoMultiplexor.ValoresNodos6.OR.ordinal();
		int andIndex = InfoMultiplexor.ValoresNodos6.AND.ordinal();
		
		int k =0;
		while(k<nodos.size()) {
			int tipoOperador = nodos.get(k).getOperator().getIndice();
			if(tipoOperador == orIndex || tipoOperador == andIndex)break;
			k++;
		}
		
		//Si k ha llegado hasta el final, significa que no existen nodos ni de tipo AND ni OR, y son los únicos que pueden
		//ser mutados de esta forma, asi que como no hay ninguno de estos no se puede hacer nada
		if(k>=nodos.size())return;
		
		int index = rnd.nextInt(nodos.size());
		while(nodos.get(index).getData().getIndice() != orIndex &&
			nodos.get(index).getData().getIndice() != andIndex ) {
			index = rnd.nextInt(nodos.size());
		}
		 
		if(nodos.get(index).getData().getIndice() == orIndex) {
			nodos.get(index).setData(new OperadorAnd(andIndex));
		}
		else {
			nodos.get(index).setData(new OperadorAnd(orIndex));
		}
	}
	
	@Override
	public void mutacionSubarbol() {
		Random rnd = new Random();
		int prof = rnd.nextInt(this.maxDepth);
		int p = 0;
		
		MyTree tree = this.cromosoma;
		while(!tree.isLeaf() && p<prof) {
			List<MyTree> children =  tree.getChildren();
			tree = children.get(rnd.nextInt(children.size()));
			p++;
		}
		
		if(tree.isLeaf()) {
			tree = tree.getParent();
			p--;
		}
		
		int tipoInicializacion = rnd.nextInt(2);
		if(tipoInicializacion == 0) {
			tree = initializeCompleta(p, this.maxDepth);
		}
		else {
			tree = initializeCreciente(p, this.maxDepth);
		}
	}
	
}

package algoritmoGenetico.individuos;

import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.InfoMultiplexor.TipoNodo;
import algoritmoGenetico.individuos.operadoresArbol.OperadorAnd;
import algoritmoGenetico.individuos.operadoresArbol.OperadorArbol;
import algoritmoGenetico.individuos.operadoresArbol.OperadorIF;
import algoritmoGenetico.individuos.operadoresArbol.OperadorNOT;
import algoritmoGenetico.individuos.operadoresArbol.OperadorOR;
import algoritmoGenetico.individuos.operadoresArbol.OperadorTerminal;


//Clase abstracta que engloba las variables y los m�todos m�nimos que debe tener un individuo que vaya a participar en una evoloci�n
public class Individuo {
	
	public void  copyFromAnother(Individuo other){
		this.cromosoma = new MyTree(other.cromosoma, null);
		this.profundidadMedia = other.profundidadMedia;
		this.maxDepth = other.maxDepth;
	}
	
	public void setMaxDepth(int depth) {
		this.maxDepth = depth;
	}
	
	public MyTree getArbol(){
		return this.cromosoma;
	}
	
	public void setArbol(MyTree tree){
		this.cromosoma = tree;
	}
	
	public void setProfundidadMedia(int prof) {
		this.profundidadMedia=prof;
	}
	
	//Constructoras
	public Individuo(Individuo other) {
		
		this.cromosoma = new MyTree(other.getArbol(),null);
		this.maxDepth = other.getMaxDepth();
	}
	
	public Individuo(){
		
	}
	
	public int getMaxDepth() {
		return this.cromosoma.getMaxDepth(cromosoma);
	}
	
	public double getFitness() {
		int value =(int)this.getValor();
		//BLOATING 
		if(InfoMultiplexor.BloatingCheck && this.cromosoma.getMaxDepth(this.cromosoma)>this.profundidadMedia && rand.nextInt()%2==0) {
			int alturaDeMas = this.cromosoma.getMaxDepth(this.cromosoma)-this.maxDepth;
			
			value -= (InfoMultiplexor.ConstantePenalizacion*alturaDeMas);
			if(value <0)value =0;
		}

		return value;
	}
	
	//Metod que aplica la funcion 5 y devuelvel el valor
	public double getValor() {
		return getValor(this.cromosoma);
	}
	
	//Metodo que prueba todas las posibilidades del multiplexor que tengamos y hace un recuento del numero de casos 
	//que hemos acertado correctamente
	private double getValor(MyTree  cromo) {
		//En caso de que no haya habido ninguna modificaci�n no hacemos nada porque ya lo ten�amos calculado de antes
		if(!this.cromosoma.hasBeenModified()) return lastFitness;
		
		int nAciertos = 0;
		for(int i=0; i<InfoMultiplexor.numPosibilidades; i++) {
			int valorTruth = InfoMultiplexor.getSalida(i);
			
			int[] bin = InfoMultiplexor.toBinary(i, InfoMultiplexor.numTerminales);
			int valorArbol = this.cromosoma.getData().evaluar(bin, cromo);
			
			if(valorTruth == valorArbol) nAciertos++;
		}
		
		lastFitness = nAciertos;
		this.cromosoma.setModified(false);
		return nAciertos;
	}
	
	
	//M�todoq ue realiza una inicializaci�n creciente del arbol 
	public MyTree  initializeCreciente(int profundidadActual, int profundidadMaxima) {
		MyTree  newNode = null;
		if(profundidadActual < profundidadMaxima - 1) {
			
			//Sacas el tipo que le toca dentro de los operadores
			int low = 0;
			int high = InfoMultiplexor.numNodosDistintosTipos;
			double probTerminal = rand.nextDouble();
			if(probTerminal <0.2) {
				low = 0;
				high = InfoMultiplexor.getIndiceTipoNodo(TipoNodo.NODOAND);
			}
			else {
				low = InfoMultiplexor.getIndiceTipoNodo(TipoNodo.NODOAND);
				high = InfoMultiplexor.numNodosDistintosTipos;
			}
			
			
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
	
	
	//M�todoq ue realiza una inicializaci�n completa del arbol 
	public MyTree initializeCompleta(int profundidadActual, int profundidadMaxima) {
		MyTree newNode = null;
		//Si todavia no hemos llegado a la profundidad m�xima
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
	
	//Metodo que busca un nodo terminal en el arbol y lo intercambia por otro nodo terminal distinto
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
		this.cromosoma.setModified(true);
	}
	
	//Metodo que busca un nodo funcion en el arbol y lo intercambia por otro nodo funcion distinto que tenga el mismo n�mero de hijos
	public void mutacionFuncional() {
		MyTree tree = this.cromosoma;
		Random rnd = new Random();
		List<MyTree> nodos =  tree.getPreOrden();
		
		//Pedimos los indices de los nodos que representan un OR o un AND, porque son los �nicos nodos que pueden intervenir en esta mutaci�n
		int orIndex = InfoMultiplexor.getIndiceTipoNodo(InfoMultiplexor.TipoNodo.NODOOR);		//InfoMultiplexor.ValoresNodos6.OR.ordinal();
		int andIndex = InfoMultiplexor.getIndiceTipoNodo(InfoMultiplexor.TipoNodo.NODOAND); 	//.ValoresNodos6.AND.ordinal();
		
		//Bucasmos alg�n nodo que sea de estos tipos, en caso de no entonctrarlo lo anotamos y salimos porque entonces no se puede mutar este individuo as�
		int k =0;
		while(k<nodos.size()) {
			int tipoOperador = nodos.get(k).getOperator().getIndice();
			if(tipoOperador == orIndex || tipoOperador == andIndex)break;
			k++;
		}
		
		//Si k ha llegado hasta el final, significa que no existen nodos ni de tipo AND ni OR, y son los �nicos que pueden
		//ser mutados de esta forma, asi que como no hay ninguno de estos no se puede hacer nada
		if(k>=nodos.size())return;
		
		//Buscamos alg�n nodo de tipo OR o AND
		int index = rnd.nextInt(nodos.size());
		while(nodos.get(index).getData().getIndice() != orIndex &&
			nodos.get(index).getData().getIndice() != andIndex ) {
			index = rnd.nextInt(nodos.size());
		}
		
		//Ponemos el nodo alternativo al que el nodo ya tiene
		if(nodos.get(index).getData().getIndice() == orIndex) 	nodos.get(index).setData(new OperadorAnd(andIndex));
		else 													nodos.get(index).setData(new OperadorAnd(orIndex));
		
		this.cromosoma.setModified(true);
	}
	
	/*//M�todo que toma un nodo dentro de nuestro arbol, lo elimina, y genera un nuevo subarbol desde 0 con altura m�xima la altura a la que se 
	 * Inicializaron los arboles al inicio de la ejecuci�n/
	 */
	public void mutacionSubarbol() {
		Random rnd = new Random();
		List<MyTree> nodos =  this.cromosoma.getPreOrden();
		
		int r = rnd.nextInt(nodos.size());
		MyTree tree = nodos.get(r);
		MyTree parent = tree.getParent();
		int pos = -1;
		if(parent!=null) pos = parent.getChildren().indexOf(tree);		
		
		int prof = tree.getMaxDepth(tree);
		int profTotal = this.cromosoma.getMaxDepth(this.cromosoma);
		int maxProf = profTotal - prof;
		
		MyTree newTree;
		int tipoInicializacion = rnd.nextInt(2);
		if(tipoInicializacion == 0)			newTree = initializeCompleta(0, InfoMultiplexor.profMaximaInicial);
		else 								newTree = initializeCreciente(0, InfoMultiplexor.profMaximaInicial);
		
		if(pos != -1) {
			parent.changeChild(newTree, pos);
		}
		else this.cromosoma = newTree;
		
		this.cromosoma.setModified(true);
	}
	
	

	Random rand = new Random();
	Integer maxDepth;
	int profundidadMedia = Integer.MAX_VALUE;
	private MyTree cromosoma;
	private boolean chromoChanged = true;
	private int lastFitness=0;
}

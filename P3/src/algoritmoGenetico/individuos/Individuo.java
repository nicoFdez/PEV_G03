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


//Clase abstracta que engloba las variables y los métodos mínimos que debe tener un individuo que vaya a participar en una evoloción
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
		if(InfoMultiplexor.BloatingCheck && this.cromosoma.getMaxDepth(this.cromosoma)>this.profundidadMedia && rand.nextInt()%10==0) {
			System.out.println("Me han jodido");
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
		//En caso de que no haya habido ninguna modificación no hacemos nada porque ya lo teníamos calculado de antes
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
	
	
	//Métodoq ue realiza una inicialización creciente del arbol 
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
	
	
	//Métodoq ue realiza una inicialización completa del arbol 
	public MyTree initializeCompleta(int profundidadActual, int profundidadMaxima) {
		MyTree newNode = null;
		//Si todavia no hemos llegado a la profundidad máxima
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
	
	//Metodo que busca un nodo funcion en el arbol y lo intercambia por otro nodo funcion distinto que tenga el mismo número de hijos
	public void mutacionFuncional() {
		MyTree tree = this.cromosoma;
		Random rnd = new Random();
		List<MyTree> nodos =  tree.getPreOrden();
		
		//Pedimos los indices de los nodos que representan un OR o un AND, porque son los únicos nodos que pueden intervenir en esta mutación
		int orIndex = InfoMultiplexor.getIndiceTipoNodo(InfoMultiplexor.TipoNodo.NODOOR);		//InfoMultiplexor.ValoresNodos6.OR.ordinal();
		int andIndex = InfoMultiplexor.getIndiceTipoNodo(InfoMultiplexor.TipoNodo.NODOAND); 	//.ValoresNodos6.AND.ordinal();
		
		//Bucasmos algún nodo que sea de estos tipos, en caso de no entonctrarlo lo anotamos y salimos porque entonces no se puede mutar este individuo así
		int k =0;
		while(k<nodos.size()) {
			int tipoOperador = nodos.get(k).getOperator().getIndice();
			if(tipoOperador == orIndex || tipoOperador == andIndex)break;
			k++;
		}
		
		//Si k ha llegado hasta el final, significa que no existen nodos ni de tipo AND ni OR, y son los únicos que pueden
		//ser mutados de esta forma, asi que como no hay ninguno de estos no se puede hacer nada
		if(k>=nodos.size())return;
		
		//Buscamos algún nodo de tipo OR o AND
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
	
	
	//Método que toma un nodo dentro de nuestro arbol, lo elimina, y genera un nuevo subarbol desde 0
	public void mutacionSubarbol() {
		Random rnd = new Random();
		int prof = rnd.nextInt(this.maxDepth);
		int p = 0;
		
		//Vamos bajando por el arbol de manera aleatoria hasta llegar a la profundidad dicha o hasta que nos encontremos con un nodo hoja
		MyTree tree = this.cromosoma;
		while(!tree.isLeaf() && p<prof) {
			List<MyTree> children =  tree.getChildren();
			tree = children.get(rnd.nextInt(children.size()));
			p++;
		}
		
		//En caso de que hayamos termiando en una hoja nos volvemos un nodo atras antes de realizar la mutación
		if(tree.isLeaf()) {
			tree = tree.getParent();
			p--;
		}
		
		//Decidimos de manera aleatoria cómo vamos a inicializar el resto del subarbol
		int tipoInicializacion = rnd.nextInt(2);
		if(tipoInicializacion == 0)			tree = initializeCompleta(p, this.maxDepth);
		else 								tree = initializeCreciente(p, this.maxDepth);
		
		this.cromosoma.setModified(true);
	}

	Random rand = new Random();
	Integer maxDepth;
	int profundidadMedia = Integer.MAX_VALUE;
	private MyTree cromosoma;
	private boolean chromoChanged = true;
	private int lastFitness=0;
}

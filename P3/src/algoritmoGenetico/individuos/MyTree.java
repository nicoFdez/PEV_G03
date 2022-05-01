package algoritmoGenetico.individuos;
import java.util.ArrayList;
import java.util.List;

import algoritmoGenetico.individuos.InfoMultiplexor.TipoNodo;
import algoritmoGenetico.individuos.operadoresArbol.OperadorAnd;
import algoritmoGenetico.individuos.operadoresArbol.OperadorArbol;
import algoritmoGenetico.individuos.operadoresArbol.OperadorIF;
import algoritmoGenetico.individuos.operadoresArbol.OperadorNOT;
import algoritmoGenetico.individuos.operadoresArbol.OperadorOR;
import algoritmoGenetico.individuos.operadoresArbol.OperadorTerminal;


/*clase que tiene como objetivo representar la estructura de un arbol n-ario
 * Como dato contiene un operador para el problema del multiplexor, una referencia a su padre
 * y referencias a los posibles n hijos del arbol*/
public class MyTree {
    private List<MyTree> children = new ArrayList<MyTree>();
    private MyTree parent = null;
    private OperadorArbol data = null;

    
    //Constructora
    public MyTree() {
    	data = new OperadorArbol();
    }
    
    //Constructora
    public MyTree(OperadorArbol data) {
        this.data = data;
    }

    //Constructora
    public MyTree(OperadorArbol data, MyTree parent) {
        this.data = data;
        this.parent = parent;
    }
    
    //Constructora por copia
    public MyTree(MyTree other, MyTree myParent) {
    	//Me pongo como padre a quien me han dicho
    	parent = myParent;
    	
    	//Le preguntamos al multiplexor por el tipo que representa el operador que estamos copiando
    	TipoNodo tipo = InfoMultiplexor.getTipoNodo(other.getOperator().getIndice());
    	switch(tipo) {
    	case NODOAND:
        	data = new OperadorAnd(other.getOperator().getIndice());
    		break;
    	case NODOOR:
        	data = new OperadorOR(other.getOperator().getIndice());
    		break;
    	case NODOIF:
        	data = new OperadorIF(other.getOperator().getIndice());
    		break;
    	case NODONOT:
        	data = new OperadorNOT(other.getOperator().getIndice());
    		break;
    		
		default:
        	data = new OperadorTerminal(other.getOperator().getIndice());
			break;
    	}
    	
    	
    	//Recorro los hijos del qu eme estoy copiando ypor cada uno repito este proceso
    	List<MyTree> otherChildren = other.getChildren();
    	for(int i = 0; i< otherChildren.size();i++) {
    		MyTree newChild = new MyTree(otherChildren.get(i),this);
    		this.children.add(newChild);
    	}
    }

    //Metodo que devuelve una lista con todos los hijos directos de este subarbol
    public List<MyTree> getChildren() {
        return children;
    }
    
    //Metodo que recorre el arbol en el sentido Preorden y devuelve una lista con los nodos que lo componen
    public List<MyTree>  getPreOrden() {
    	List<MyTree> preorden = new ArrayList<MyTree>();
    	//Nos metemos nosotros
    	preorden.add(this);
    	
    	//para cada hijo vamos de izquierda a derecha preguntando por su preorden
    	for(int i = 0; i<this.children.size();i++) {
    		List<MyTree> listaHijo = this.children.get(i).getPreOrden();
    		for(int j =0;j<listaHijo.size();j++) {
    			preorden.add(listaHijo.get(j));
    		}
    	}
    	
        return preorden;
    }
    
    //Metodo que recorre el arbol hasta llegar a las hojas y va devolviendo 1 hasta volver a la raiz
    //Esto devuelve la profundidad de la hoja más lejana de la raiz
    public int getMaxDepth(MyTree tree) {
    	int max = 0;
    	for(int i=0; i<tree.children.size(); i++) {
    		int sizeActual = getMaxDepth(tree.getChildren().get(i));
    		if(sizeActual > max) {
    			max = sizeActual;
    		}
    	}
    	return max + 1;
    }
    
    
    //Metodo que devuelve el operador almacenado en este nodo
    public OperadorArbol getOperator() {
    	return data;
    }

    //Metodo que establece el padre del subarbol en el que nos encontramos al nodo que nos pasan
    public void setParent(MyTree parent) {
        this.parent = parent;
    }
    
    
    //Metodo que devuelve el padre del subarbol en elq ue nos encontramos
    public MyTree getParent() {
    	return this.parent;
    }
    
    //Metodo que recibe un nodo y incorpora en los hijos de este subarbol y lo sustituye 
    //por el hijo que se encuentre enla posición que nos dicen
    public void changeChild(MyTree newChild, int position){
    	//EN caso de que me pidas un hijo en una posicion que no existe no hacemos nada
    	if(position >= children.size())return;
    	
    	children.remove(position);
    	children.add(position, newChild);
    }

    
    //Metodo que recibe un operador y lo añade un nuevo nodo con dicho contenido a este subarbol
    public void addChild(OperadorArbol data) {
        MyTree child = new MyTree(data);
        child.setParent(this);
        this.children.add(child);
    }

    //Metodo que recibe un nodo y lo añade a los hijos de este subarbol
    public void addChild(MyTree child) {
        child.setParent(this);
        this.children.add(child);
    }

    public OperadorArbol getData() {
        return this.data;
    }

    //Metodo que setea el contenido del subarbol en el que nos encontramos
    public void setData(OperadorArbol data) {
        this.data = data;
    }

    //Metodo que informa de si el nodo en el que nos encontramos es una raiz o no
    public boolean isRoot() {
        return (this.parent == null);
    }

  //Metodo que informa de si el nodo en el que nos encontramos es una hoja o no
    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    //Metodo que elimina el padre de este subarbol, convirtiendolo en una raiz
    public void removeParent() {
        this.parent = null;
    }
}
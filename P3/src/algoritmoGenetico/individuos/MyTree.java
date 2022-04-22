package algoritmoGenetico.individuos;
import java.util.ArrayList;
import java.util.List;

import algoritmoGenetico.individuos.operadoresArbol.OperadorArbol;
import algoritmoGenetico.individuos.operadoresArbol.OperadorTerminal;

public class MyTree {
    private List<MyTree> children = new ArrayList<MyTree>();
    private MyTree parent = null;
    private OperadorArbol data = null;
    private OperadorArbol myOperator;

    public MyTree() {
    	data = new OperadorArbol();

    }
    
    public MyTree(MyTree other, MyTree myParent) {
    	//Me pongo como padre a quien me han dicho
    	parent = myParent;
    	//Operador por copia del operador
    	myOperator = new OperadorArbol(other.getOperator());
    	
    	//Recorro los hijos del qu eme estoy copiando ypor cada uno repito este proceso
    	List<MyTree> otherChildren = other.getChildren();
    	for(int i = 0; i< otherChildren.size();i++) {
    		MyTree newChild = new MyTree(otherChildren.get(i),this);
    		this.children.add(newChild);
    	}
    }
    
    
    public MyTree(OperadorArbol data) {
        this.data = data;
    }

    public MyTree(OperadorArbol data, MyTree parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<MyTree> getChildren() {
        return children;
    }
    
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
    
    
    public OperadorArbol getOperator() {
    	return myOperator;
    }

    public void setParent(MyTree parent) {
        parent.addChild(this);
        this.parent = parent;
    }
    
    
    public MyTree getParent() {
    	return this.parent;
    }
    
    public void changeChild(MyTree newChild, int position){
    	//EN caso de que me pidas un hijo en una posicion que no existe no hacemos nada
    	if(position >= children.size())return;
    	
    	children.remove(position);
    	children.add(position, newChild);
    }

    public void addChild(OperadorArbol data) {
        MyTree child = new MyTree(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(MyTree child) {
        child.setParent(this);
        this.children.add(child);
    }

    public OperadorArbol getData() {
        return this.data;
    }

    public void setData(OperadorArbol data) {
        this.data = data;
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isLeaf() {
        return this.children.size() == 0;
    }

    public void removeParent() {
        this.parent = null;
    }
}
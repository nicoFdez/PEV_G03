package algoritmoGenetico.individuos;
import java.util.ArrayList;
import java.util.List;

public class MyTree<T> {
    private List<MyTree<T>> children = new ArrayList<MyTree<T>>();
    private MyTree<T> parent = null;
    private T data = null;

    public MyTree(T data) {
        this.data = data;
    }

    public MyTree(T data, MyTree<T> parent) {
        this.data = data;
        this.parent = parent;
    }

    public List<MyTree<T>> getChildren() {
        return children;
    }
    
    public List<MyTree<T>>  getPreOrden() {
    	List<MyTree<T>> preorden = new ArrayList<MyTree<T>>();
    	//Nos metemos nosotros
    	preorden.add(this);
    	
    	//para cada hijo vamos de izquierda a derecha preguntando por su preorden
    	for(int i = 0; i<this.children.size();i++) {
    		List<MyTree<T>> listaHijo = this.children.get(i).getPreOrden();
    		for(int j =0;j<listaHijo.size();j++) {
    			preorden.add(listaHijo.get(j));
    		}
    	}
    	
        return preorden;
    }

    public void setParent(MyTree<T> parent) {
        parent.addChild(this);
        this.parent = parent;
    }
    
    
    public MyTree<T> getParent() {
    	return this.parent;
    }
    
    public void changeChild(MyTree<T> newChild, int position){
    	//EN caso de que me pidas un hijo en una posicion que no existe no hacemos nada
    	if(position >= children.size())return;
    	
    	children.remove(position);
    	children.add(position, newChild);
    }

    public void addChild(T data) {
        MyTree<T> child = new MyTree<T>(data);
        child.setParent(this);
        this.children.add(child);
    }

    public void addChild(MyTree<T> child) {
        child.setParent(this);
        this.children.add(child);
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
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
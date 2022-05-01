package algoritmoGenetico.individuos;

import java.util.Random;


//Clase abstracta que engloba las variables y los métodos mínimos que debe tener un individuo que vaya a participar en una evoloción
public abstract class Individuo<T> {
	protected MyTree cromosoma;
	
	public double getFitness() {
		return 0;
	}
	
	public void mutacionTerminal() {}
	public void mutacionFuncional() {}
	public void mutacionSubarbol() {}
	
	public MyTree initializeCompleta(int profundidadActual, int profundidadMaxima) {return null;}
	public MyTree initializeCreciente(int profundidadActual, int profundidadMaxima) {return null;}
	
	public void  copyFromAnother(Individuo<T> other){
		this.cromosoma = new MyTree(other.cromosoma, null);
		this.profundidadMedia = other.profundidadMedia;
		this.maxDepth = other.maxDepth;
	}

	public int getMaxDepth() {
		return this.cromosoma.getMaxDepth(this.cromosoma);
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

	Random rand = new Random();
	Integer maxDepth;
	int profundidadMedia = Integer.MAX_VALUE;
}

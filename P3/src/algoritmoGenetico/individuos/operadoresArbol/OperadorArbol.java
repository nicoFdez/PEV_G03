package algoritmoGenetico.individuos.operadoresArbol;
import algoritmoGenetico.individuos.MyTree;


//Clase padre que contiene el indice que representara a cada  uno de los tipos de operadores y los métodos que cada uno de 
//estos van a reimplementar
public class OperadorArbol {

	
	public OperadorArbol() {
		
	}
	
	public OperadorArbol(int index) {
		indice=index;
	}
	
	public OperadorArbol(OperadorArbol other) {
		indice = other.indice;
	}
	
	public int evaluar(int[] configuracionActual,MyTree miArbol) {
		return 0;
	}
	
	public int getIndice() {
		return indice;
	}
	
	int indice=0; 
}


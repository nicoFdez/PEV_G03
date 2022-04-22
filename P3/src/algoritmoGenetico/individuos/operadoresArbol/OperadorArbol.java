package algoritmoGenetico.individuos.operadoresArbol;
import algoritmoGenetico.individuos.MyTree;

public class OperadorArbol {

	
	public OperadorArbol() {
		
	}
	
	public OperadorArbol(int index) {
		indice=index;
	}
	
	public OperadorArbol(OperadorArbol other) {
		
	}
	
	public int evaluar(int[] configuracionActual,MyTree miArbol) {
		return 0;
	}
	
	public int getIndice() {
		return indice;
	}
	
	int indice=0; 
}


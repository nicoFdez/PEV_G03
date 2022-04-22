package algoritmoGenetico.individuos.operadoresArbol;

import java.util.List;

import algoritmoGenetico.individuos.MyTree;

public class OperadorTerminal extends OperadorArbol {

	
	public OperadorTerminal(OperadorTerminal other) {
		super(other);
		indice = other.indice;
	}
	
	public OperadorTerminal(int indiceTerminales) {
		super(indiceTerminales);
	}
	
	@Override
	public int evaluar(int[] configuracionActual,MyTree miArbol) {
		//Devuelvo el valor que se encuentre en el indice que representa este terminal
		return configuracionActual[indice];
	}
	

	
}

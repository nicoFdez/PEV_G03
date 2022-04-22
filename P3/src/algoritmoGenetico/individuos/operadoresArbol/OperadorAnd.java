package algoritmoGenetico.individuos.operadoresArbol;

import java.util.List;

import algoritmoGenetico.individuos.MyTree;

public class OperadorAnd extends OperadorArbol {

	public OperadorAnd(OperadorAnd other) {
		super(other);
		
	}
	
	public OperadorAnd(int index) {
		super(index);
	}
	
	@Override
	public int evaluar(int[] configuracionActual,MyTree miArbol) {
		//Me quedo con los hijos del arbol y me aseguro de que tiene exactamente 2 porque si no no puedo hacer mi operacion correctamente
		List<MyTree> hijos = miArbol.getChildren();
		if(hijos.size() != 2)
			System.out.print("Operador AND tiene problemas a la hora de obtener exactamente 2 hijos");
		
		//Les pido a los 2 hijos que se evaluen
		int a = hijos.get(0).getOperator().evaluar(configuracionActual, hijos.get(0));
		int b = hijos.get(1).getOperator().evaluar(configuracionActual, hijos.get(1));
		
		//Devuelvo el resulatado de aplicar la operacion AND sobre mis hijos
		return ((a==1) && (b ==1)) ? 1 : 0;
	}
}

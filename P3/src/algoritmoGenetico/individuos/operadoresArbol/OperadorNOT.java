package algoritmoGenetico.individuos.operadoresArbol;

import java.util.List;

import algoritmoGenetico.individuos.MyTree;

public class OperadorNOT extends OperadorArbol {

	
	public OperadorNOT(OperadorNOT other) {
		super(other);
		
	}
	
	public OperadorNOT(int index) {
		super(index);
	}
	
	@Override
	public int evaluar(int[] configuracionActual,MyTree miArbol) {
		//Me quedo con los hijos del arbol y me aseguro de que tiene exactamente 2 porque si no no puedo hacer mi operacion correctamente
		List<MyTree> hijos = miArbol.getChildren();
		if(hijos.size() != 1)
			System.out.print("Operador NOT tiene problemas a la hora de obtener exactamente 1 hijo");
		
		//Les pido a los 2 hijos que se evaluen
		int a = hijos.get(0).getOperator().evaluar(configuracionActual, hijos.get(0));
		
		//Devuelvo el resulatado de aplicar la operacion NOT sobre mi hijo
		return (a==1) ? 0 : 1;
	}
	
}

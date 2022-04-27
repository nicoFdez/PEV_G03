package algoritmoGenetico.individuos.operadoresArbol;

import java.util.List;

import algoritmoGenetico.individuos.MyTree;

public class OperadorIF extends OperadorArbol {

	public OperadorIF(OperadorIF other) {
		super(other);
	}
	
	
	public OperadorIF(int index) {
		super(index);
	}
	
	@Override
	public int evaluar(int[] configuracionActual,MyTree miArbol) {
		//Me quedo con los hijos del arbol y me aseguro de que tiene exactamente 2 porque si no no puedo hacer mi operacion correctamente
		List<MyTree> hijos = miArbol.getChildren();
		if(hijos.size() != 3)
			System.out.print("Operador IF tiene problemas a la hora de obtener exactamente 3 hijos");
		
		//Les pido a los 3 hijos que se evaluen
		int a = hijos.get(0).getOperator().evaluar(configuracionActual, hijos.get(0));
		int b = hijos.get(1).getOperator().evaluar(configuracionActual, hijos.get(1));
		int c = hijos.get(2).getOperator().evaluar(configuracionActual, hijos.get(2));

		//Devuelvo el resulatado de aplicar la operacion IF sobre mi hijo
		return (a==1) ? b : c;
	}
	
}

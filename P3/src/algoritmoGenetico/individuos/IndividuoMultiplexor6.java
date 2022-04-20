package algoritmoGenetico.individuos;

import java.util.List;
import java.util.Random;


//clase que representa un individuo preparado para realizar una evolución sobre la funcion5
public class IndividuoMultiplexor6 extends Individuo<InfoMultiplexor.ValoresNodos6> {
	
	//Constructoras
	public IndividuoMultiplexor6(Individuo other) {
		
	}
	
	public IndividuoMultiplexor6(){
		
	}
	
	@Override
	public double getFitness() {
		return this.getValor();
	}
	
	//Metod que aplica la funcion 5 y devuelvel el valor
	private double getValor() {
		return getValor(this.cromosoma);
	}
	
	//Metod que hace la asignación de vuelos a las pistas del aeropuerto
	private double getValor(MyTree<InfoMultiplexor.ValoresNodos6> cromo) {
		
		int nAciertos = 0;
		
		for(int i=0; i<64; i++) {
			int valorTruth = InfoMultiplexor.getSalida(i);
			
			int[] bin = InfoMultiplexor.toBinary(i, 6);
			int a0 = bin[0];
			int a1 = bin[1];
			int d0 = bin[2];
			int d1 = bin[3];
			int d2 = bin[4];
			int d3 = bin[5];
		}
		
		
		
		return 0;
	}
	
	public static int AND(int a, int b) {
		if(a==0 || b==0) return 0;
		else return 1;
	}
	
	public static int OR(int a, int b) {
		if(a==1 || b==1) return 1;
		else return 0;
	}
	
	public static int NOT(int a) {
		if(a==1) return 0;
		else return 1;
	}
	
	public static int IF(int a, int b, int c) {
		if(a==1) return b;
		else return c;
	}
	
	@Override
	public void initialize() {
	
	}
	
	//Metodo que toma dos posiciones aleatorias dentro del cromosoma e intercambia el contenido de dichas posiciones
	@Override
	public void mutacionTerminal(double probMutacion) {
		MyTree<InfoMultiplexor.ValoresNodos6> tree = myTree;
		Random rnd = new Random();
		while(!tree.isLeaf()) {
			List<MyTree<InfoMultiplexor.ValoresNodos6>> children =  tree.getChildren();
			tree = children.get(rnd.nextInt(children.size()));
		}
		
		InfoMultiplexor.ValoresNodos6 tipoNodo = tree.getData();
		InfoMultiplexor.ValoresNodos6 nuevoTipo = InfoMultiplexor.ValoresNodos6.values()[rnd.nextInt(InfoMultiplexor.numTerminales)];
		while(nuevoTipo == tipoNodo) {
			nuevoTipo = InfoMultiplexor.ValoresNodos6.values()[rnd.nextInt(InfoMultiplexor.numTerminales)];
		}
	}
	
	@Override
	public MyTree<InfoMultiplexor.ValoresNodos6> getCromosoma() {	
		return null;
	}
	
	public MyTree<InfoMultiplexor.ValoresNodos6> getArbol(){
		return this.myTree;
	}

	MyTree<InfoMultiplexor.ValoresNodos6> myTree;
}

package algoritmoGenetico.cruces;

import java.util.ArrayList;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;
import algoritmoGenetico.individuos.MyTree;


//Clase que implementa el cruce entre individuos mediante el método monopunto
public class CruceArboles<T> implements Cruce {

	//Constructora
	public CruceArboles() {}
	
	
	//Método que toma un apoblación y una probabilidad a partir de la que analiza los individuos
	//y los cruza haciendo uso del método uniforme
	@Override
	public Individuo[] cruzar(Individuo[] poblacion, double probCruce) 
	{
		//Preparamos una lista en la que nos vamos a quedar con aquells individuos que tienen que ser cruzados
		ArrayList<Individuo> individuosCruzar = new ArrayList<Individuo>();
		Random rand = new Random();
		
		//Recorremos todos los individuos y segun un random y la probabilidad de cruce vemos a cuales les toca 
		for(int i = 0 ; i< poblacion.length; i++) {
			//Si la probabilidad se da, nos quedamos con este individuo
			if(rand.nextDouble() <= probCruce) 
				individuosCruzar.add(poblacion[i]);
		}
		
		//En caso de que la lista de individuos seleccionados sea impar, nos deshacemos de uno de estos 
		//debido a que los cruces los vamos a hacer por parejas y no podemos repetir individuos
		if(individuosCruzar.size() % 2 != 0) 
			individuosCruzar.remove(0);
		
		//Recorremos los individuos por parejas y hacemos que se crucen
		for(int i=0; i<individuosCruzar.size(); i+=2 ) {
			intercambioSubarboles(individuosCruzar.get(i), individuosCruzar.get(i+1));
			nCruces++;
		}
		
		return poblacion;
	}
	
	
	//Método que toma 2 individuos y realiza el cruce arbol sobre estos
	private void intercambioSubarboles(Individuo a, Individuo b) {
		operacionesArboles(a.getArbol(), b.getArbol());
	}
	
	private void operacionesArboles( MyTree cromoPadre, MyTree cromoMadre) {
		//Obtenemos todos los nodos de los arboles ordenados en modo preorden
		ArrayList<MyTree> listaPadre =  (ArrayList<MyTree>) cromoPadre.getPreOrden();
		ArrayList<MyTree> listaMadre =  (ArrayList<MyTree>) cromoMadre.getPreOrden();	
		
		Random rand = new Random();
		//Nodo involucrado del padre
		int posPrimerNodo = rand.nextInt(listaPadre.size());
		
		//Nodo involucrado de la madre
		int posSegundoNodo = rand.nextInt(listaMadre.size());

		
		//Sacamos los padres de los involucrados
		MyTree padrePrimerNodo = listaPadre.get(posPrimerNodo).getParent();
		MyTree padreSegundoNodo = listaMadre.get(posSegundoNodo).getParent() ;
		
		//Intercambiamos el subarbol del padre por el que nos da la madre
		if(padrePrimerNodo != null) {			
			int indice = padrePrimerNodo.getChildren().indexOf(listaPadre.get(posPrimerNodo));
			padrePrimerNodo.changeChild(listaMadre.get(posSegundoNodo), indice);
		}
		else cromoPadre = listaMadre.get(posSegundoNodo);
		
		//Lo mismo pero para la madre
		if(padreSegundoNodo != null) {
			int indice = padreSegundoNodo.getChildren().indexOf(listaMadre.get(posSegundoNodo));
			padreSegundoNodo.changeChild(listaPadre.get(posPrimerNodo), indice);
		}
		else cromoMadre = listaPadre.get(posPrimerNodo);
		
	}
	
	
	
	@Override
	public int getNCruces() {
		return this.nCruces;
	}
		
	private int nCruces;
}

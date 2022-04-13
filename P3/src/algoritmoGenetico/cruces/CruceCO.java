package algoritmoGenetico.cruces;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el método monopunto
public class CruceCO<T> implements Cruce {

	//Constructora
	public CruceCO() {}
	
	
	//Método que toma un apoblación y una probabilidad a partir de la que analiza los individuos
	//y los cruza haciendo uso del método uniforme
	@Override
	public Individuo[] cruzar(Individuo[] poblacion, double probCruce) 
	{
		//Preparamos una lista en la que nos vamos a quedar con aquells individuos que tienen que ser cruzados
		ArrayList<Individuo<T>> individuosCruzar = new ArrayList<Individuo<T>>();
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
			cruceCO((Individuo<Integer>)individuosCruzar.get(i), (Individuo<Integer>)individuosCruzar.get(i+1));
			nCruces++;
		}
		
		
		return poblacion;
	}
	
	
	//Método que toma 2 individuos y realiza el cruce CO sobre estos
		private void cruceCO(Individuo<Integer> a, Individuo<Integer> b) {
			//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
			Integer[] cromoA = a.getCromosoma();
			Integer[] cromoB = b.getCromosoma();
			
			//Nos hacemos con la longitud del cromosoma para recorrerlo entero
			int l = cromoA.length;
			
			Integer[] cromo1 = new Integer[l];
			Integer[] cromo2 = new Integer[l];
			
			//Codificamos los cromosomas
			cromo1 = codificar(cromoA);
			cromo2 = codificar(cromoB);
			
			//Cruce monopunto
			Random rand = new Random();
			int cutPoint=rand.nextInt(l-1)+1;
			for(int i = 0 ; i< cutPoint; i++) {
				cromo1[i] = cromo2[i];
			}
			for(int i = cutPoint ; i< l; i++) {
				cromo2[i] = cromo1[i];
			}
			
			//Decodificar
			cromo1 = decodificar(cromo1);
			cromo2 = decodificar(cromo2);
			
			a.setCromosoma(cromo1);
			b.setCromosoma(cromo2);
		}
		
		//Método que toma un cromosoma y lo codifica según el orden en el que se encuentren sus elementos
		private Integer[] codificar( Integer[] cromoHijo) {
			//Preparo la lista d elas posiciones
			int l = cromoHijo.length;
			ArrayList<Integer> posiciones = new ArrayList(l); 
			for(int i = 1; i<=l; i++) {
				posiciones.add(i);
			}
			
			//Por cada elemento del cromosoma, intercambio su valor por la posicion en la que se encuentre dentro de la lista
			for(int i = 0; i<l; i++) {
				int pos =posiciones.indexOf(cromoHijo[i]);
				posiciones.remove(cromoHijo[i]);
				cromoHijo[i] = pos+1;
			}
			return cromoHijo;
		}
		
		//Método que toma una lista de posiciones y transforma los valores almacenados en valores con sentidop para el cromosoma
		private Integer[] decodificar( Integer[] cromoHijo) {
			//Preparo la lista de las posiciones
			int l = cromoHijo.length;
			ArrayList<Integer> posiciones = new ArrayList(l); 
			for(int i = 1; i<=l; i++) {
				posiciones.add(i);
			}
			
			//Por cada elemento de la lista, intercambio su valor por el valor almacenado en la posicion que me especifique cada elemento del cromosoma
			for(int i = 0; i<l; i++) {	
				int posicionBorrar = cromoHijo[i].intValue()-1;
				int aux = posiciones.get(posicionBorrar);
				posiciones.remove(posicionBorrar);
				cromoHijo[i] = aux;
			}
			return cromoHijo;
		}
	
	@Override
	public int getNCruces() {
		return this.nCruces;
	}
		
	private int nCruces;
}

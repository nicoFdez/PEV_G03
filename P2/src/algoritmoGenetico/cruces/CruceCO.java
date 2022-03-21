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
		for(int i=0; i<individuosCruzar.size(); i+=2 ) 	
			cruceCO((Individuo<Integer>)individuosCruzar.get(i), (Individuo<Integer>)individuosCruzar.get(i+1));
		
		
		return poblacion;
	}
	
	
	//Método que toma 2 individuos y realiza el cruce OX sobre estos
		private void cruceCO(Individuo<Integer> a, Individuo<Integer> b) {
			//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
			Integer[] cromoA = a.getCromosoma();
			Integer[] cromoB = b.getCromosoma();
			//Nos hacemos con la longitud del cromosoma para recorrerlo entero
			int l = cromoA.length;
			
			Integer[] cromo1 = new Integer[l];
			Integer[] cromo2 = new Integer[l];
			
			cromo1 = codificar(cromo1);
			cromo2 = codificar(cromo2);
			
			//Cruce monopunto
			Random rand = new Random();
			int cutPoint=rand.nextInt(l);
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
		
		private Integer[] codificar( Integer[] cromoHijo) {
			int l = cromoHijo.length;
			ArrayList<Integer> posiciones = new ArrayList(l); 
			for(int i = 1; i<=l; i++) {
				posiciones.add(i);
			}
			
			for(int i = 0; i<l; i++) {
				int pos =posiciones.indexOf(cromoHijo[i]);
				cromoHijo[i] = pos;
				posiciones.remove(cromoHijo[i]);
			}
			return cromoHijo;
		}
		
		private Integer[] decodificar( Integer[] cromoHijo) {
			int l = cromoHijo.length;
			ArrayList<Integer> posiciones = new ArrayList(l); 
			for(int i = 1; i<=l; i++) {
				posiciones.add(i);
			}
			
			for(int i = 0; i<l; i++) {
				cromoHijo[i] = posiciones.get(cromoHijo[i].intValue());
				posiciones.remove(cromoHijo[i].intValue());
			}
			return cromoHijo;
		}
	
	
}

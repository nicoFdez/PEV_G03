package algoritmoGenetico.cruces;

import java.util.ArrayList;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;

public class CruceUniforme<T> implements Cruce {

	
	public void CruceUniforme() {}
	
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
		
		//Recorremos los individuos por parejas y hacemos que se crucen de forma uniforme
		for(int i=0; i<individuosCruzar.size(); i+=2 ) 	
			cruceUniforme(individuosCruzar.get(i), individuosCruzar.get(i+1));
		
		
		return null;
	}
	
	
	private void cruceUniforme(Individuo a, Individuo b) {
		//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
		Random rand = new Random();
		Object[] cromo1 = a.getCromosoma();
		Object[] cromo2 = b.getCromosoma();
		
		//Nos hacemos con la longitud del cromosoma para recorrerlo entero
		int l = cromo1.length;
		
		//Recorremos el cromosoma y dependiendo de lo que nos diga el random hacemos un intercambio de los genes o no
		for(int i=0; i<l; i++) {
			//Suponemos que <0.5 es lo mismo que 0 y por lo tanto no toca cruzar, lo  mismo para >=0.5 pero con 1
			if(rand.nextDouble() < 0.5) {
				Object aux = cromo1[i];
				cromo1[i] = cromo2[i];
				cromo2[i] = aux;
			}

		}
	}
}

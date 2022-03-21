package algoritmoGenetico.cruces;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el método monopunto
public class CruceCX<T> implements Cruce {

	//Constructora
	public CruceCX() {}
	
	
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
			cruceCX(individuosCruzar.get(i), individuosCruzar.get(i+1));
		
		
		return poblacion;
	}
	
	
	//Método que toma 2 individuos y realiza el cruce OX sobre estos
		private void cruceCX(Individuo a, Individuo b) {
			//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
			Random rand = new Random();
			Object[] cromoA = a.getCromosoma();
			Object[] cromoB = b.getCromosoma();
			//Nos hacemos con la longitud del cromosoma para recorrerlo entero
			int l = cromoA.length;
			
			Object[] cromo1 = new Object[l];
			Object[] cromo2 = new Object[l];
			
			cromo1 = operacionesCX(cromo1, cromoA, cromoB);
			cromo2 = operacionesCX(cromo2, cromoB,cromoA);
			a.setCromosoma(cromo1);
			b.setCromosoma(cromo2);
		}
		
		private Object[] operacionesCX( Object[] cromoHijo, Object[] cromoPadre,Object[] cromoMadre) {
			int l = cromoPadre.length;
			int puntero = 0;
			
			//Me voy moviendo por los cromosomas hasta encontrar algo que no es null, lo cual significa que hemos completado un ciclo
			while(cromoHijo[puntero] == null) {
				cromoHijo[puntero] = cromoPadre[puntero];
				int j = 0;
				while(j<l && cromoMadre[j] != cromoPadre[puntero]) {
					j++;
				}
				puntero = j;	
			}
			
			//Con un ciclo completo, lo que falta es rellenar los huecos
			for(int i = 0; i< l; i++) {
				if(cromoHijo[i] == null) 
					cromoHijo[i] = cromoMadre[i];
			}
			
			return cromoHijo;
		}
	
	
}

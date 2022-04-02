package algoritmoGenetico.cruces;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el método monopunto
public class CruceOXPP<T> implements Cruce {

	//Constructora
	public CruceOXPP() {}
	
	
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
			cruceOXPP(individuosCruzar.get(i), individuosCruzar.get(i+1));
		
		
		return poblacion;
	}
	
	
	//Método que toma 2 individuos y realiza el cruce OX sobre estos
		private void cruceOXPP(Individuo a, Individuo b) {
			//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
			Random rand = new Random();
			Object[] cromo1 = a.getCromosoma();
			Object[] cromo2 = b.getCromosoma();
			Object[] cromoA = a.getCromosoma();
			Object[] cromoB = b.getCromosoma();
			
			//Nos hacemos con la longitud del cromosoma para recorrerlo entero
			int l = cromo1.length;
			
			//Escogemos los límites
			int limit1 = rand.nextInt(l);
			int limit2 = rand.nextInt(l);
			while(limit2 == limit1)
				limit2 = rand.nextInt(l);	
			if(limit2 < limit1) {
				int aux = limit1;
				limit1 = limit2;
				limit2 = aux;
			}
			
			//Intercambiamos la parte entre los limites 
			for(int i=0; i<l; i++) {
				if(i==limit1 || i==limit2) {
					cromo1[i] = cromoB[i];
					cromo2[i] = cromoA[i];
				}
				else {
					cromo1[i] = null;
					cromo2[i] = null;
				}
			}
			
			cromo1 = operacionesOXPP(limit1,limit2, cromo1, cromoA);
			cromo2 = operacionesOXPP(limit1,limit2, cromo2, cromoB);
			a.setCromosoma(cromo1);
			b.setCromosoma(cromo2);
		}
		
		private Object[] operacionesOXPP(int limit1, int limit2, Object[] cromoHijo, Object[] cromoPadre) {
			int l = cromoPadre.length;
			//-----------------Cromosoma 1
			//Vamos intetandocolocar los valores de fuera
			int indiceHijo=(limit2+1)%l;
			int indicePadre = (limit2+1)%l;
			int indiceComprobador = limit1;
			
			//Voy a recorrer todos los elementos que se encuentran fuera de la zona central
			//Este bucle comienza en el elemento inmediatamente a la derecha del limite derecho y avanza circularmente hasta
			//llegar de nuevo a dicho limite derecho, lo cual significa que hemos terminado
			while(indiceHijo!=limit2) {
				//Si ya está relleno no nos interesa hacer nada
				if(indiceHijo == limit1) {
					indiceHijo++;
					continue;
				}
				
				//Preguntamos si el elemento que estamos analizando se encuentra ya repetido en el hijo
				int indiceRepetido = estaRepetido(cromoHijo, cromoPadre[indicePadre]);
				
				//Si se ha repetido avanzamos el indice del padre para seguir analizando con nuevos elementos
				if(indiceRepetido != cromoHijo.length) 	{
					indicePadre = (indicePadre+1)%l;
					continue;
				}
				//Si no está repetido nos lo quedamos y avanzamos los indices
				else {
					cromoHijo[indiceHijo] = cromoPadre[indicePadre];
					indicePadre = (indicePadre+1)%l;
					indiceHijo = (indiceHijo+1)%l; 
				}
			}
			
			return cromoHijo;
		}
		
		//Metodo que toma un cromosoma y un valor, recorre el cromosoma buscando dicho valor y devuelve el indice en el que se encuentra dicho valor 
		//o en caso de no existir devuelve un indice fuera del array
		int estaRepetido(Object[] cromoHijo,  Object culpable) {
			int indiceCulpable =0;
			boolean repetido = false;
			while(indiceCulpable < cromoHijo.length && !repetido) {
				//Si se ha repetido paramos porque nos interesa saber el lugar en el que se encuentra el repetido para saber su homólogo
				if(cromoHijo[indiceCulpable] == culpable) {
					repetido = true;
					break;
				}
				indiceCulpable++;
			}
			
			return indiceCulpable;
		}
	
	
}

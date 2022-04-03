package algoritmoGenetico.cruces;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el método monopunto
public class CrucePMX<T> implements Cruce {

	//Constructora
	public CrucePMX() {}
	
	
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
			crucePMX(individuosCruzar.get(i), individuosCruzar.get(i+1));
			nCruces++;
		}
		
		return poblacion;
	}
	
	
	//Método que toma 2 individuos y realiza el cruce PMX sobre estos
	private void crucePMX(Individuo a, Individuo b) {
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
		//limit1 = 3;
		//limit2 = 6;
		
		while(limit2 == limit1)
			limit2 = rand.nextInt(l);	
		if(limit2 < limit1) {
			int aux = limit1;
			limit1 = limit2;
			limit2 = aux;
		}
		
		//Intercambiamos la parte entre los limites 
		for(int i=0; i<l; i++) {
			if(i>=limit1 && i<limit2) {
				cromo1[i] = cromoB[i];
				cromo2[i] = cromoA[i];
			}
			else {
				cromo1[i] = null;
				cromo2[i] = null;
			}
		}
		
		cromo1 = operacionesPMX(limit1, limit2, cromo1, cromoA);
		cromo2 = operacionesPMX(limit1, limit2, cromo2, cromoB);
		a.setCromosoma(cromo1);
		b.setCromosoma(cromo2);
	}
	
	private Object[] operacionesPMX(int limit1, int limit2, Object[] cromoHijo, Object[] cromoPadre) {
		int l = cromoPadre.length;
		//-----------------Cromosoma 1
		//Vamos intetandocolocar los valores de fuera
		int i=limit2;
		//Voy a recorrer todos los elementos que se encuentran fuera de la zona central
		while(i!=limit1) {
			int indiceRepetido = estaRepetido(cromoHijo, cromoPadre[i], limit1, limit2);
			int indiceACoger = i;
			
			//Mientras esté repetido sigo buscando aquel elemento que no se encuentre dentro de la zona central
			//La condicion es que no sea el limite derecho porque eso significa que el elemento se ha encontrado el
			//elemento que se está buscando dentro de la zona central, por lo que ya está repetido
			while(indiceRepetido != limit2) {
				indiceACoger = indiceRepetido;
				indiceRepetido = estaRepetido(cromoHijo, cromoPadre[indiceRepetido], limit1, limit2);
			}
			
			cromoHijo[i] = cromoPadre[indiceACoger];			
			i = (i+1)%l; 
		}
				
		return cromoHijo;
	}
	
	
	//Metodo que toma un cromosoma y un valor, recorre el cromosoma buscando dicho valor y devuelve el indice en el que se encuentra dicho valor 
	//o en caso de no existir devuelve un indice fuera del array
	int estaRepetido(Object[] cromoHijo,  Object culpable, int limit1, int limit2) {
		int indiceCulpable =limit1;
		boolean repetido = false;
		while(indiceCulpable!=limit2 && !repetido) {
			//Si se ha repetido paramos porque nos interesa saber el lugar en el que se encuentra el repetido para saber su homólogo
			if(cromoHijo[indiceCulpable] == culpable) {
				repetido = true;
				break;
			}
			indiceCulpable++;
		}
		
		return indiceCulpable;
	}
	
	@Override
	public int getNCruces() {
		return this.nCruces;
	}
		
	private int nCruces;
}

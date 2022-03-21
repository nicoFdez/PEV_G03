package algoritmoGenetico.cruces;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el m�todo monopunto
public class CruceOX<T> implements Cruce {

	//Constructora
	public CruceOX() {}
	
	
	//M�todo que toma un apoblaci�n y una probabilidad a partir de la que analiza los individuos
	//y los cruza haciendo uso del m�todo uniforme
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
			cruceOX(individuosCruzar.get(i), individuosCruzar.get(i+1));
		
		
		return poblacion;
	}
	
	
	//M�todo que toma 2 individuos y realiza el cruce OX sobre estos
	private void cruceOX(Individuo a, Individuo b) {
		//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
		Random rand = new Random();
		Object[] cromo1 = a.getCromosoma();
		Object[] cromo2 = b.getCromosoma();
		Object[] cromoA = a.getCromosoma();
		Object[] cromoB = b.getCromosoma();
		
		//Nos hacemos con la longitud del cromosoma para recorrerlo entero
		int l = cromo1.length;
		
		//Escogemos los l�mites
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
			if(i>=limit1 && i<limit2) {
				cromo1[i] = cromoB[i];
				cromo2[i] = cromoA[i];
			}
			else {
				cromo1[i] = null;
				cromo2[i] = null;
			}
		}
		
		//-----------------Cromosoma 1
		//Vamos intetandocolocar los valores de fuera
		int indiceHijo=limit2;
		int indicePadre = limit2;
		int indiceComprobador = limit1;
		//Voy a recorrer todos los elementos que se encuentran fuera de la zona central
		while(indiceHijo!=limit1) {
			
			boolean repetido = false;
			indiceComprobador = limit1;
			//Vemos si esta repetido con algun elemento dentro de la zona central
			while(indiceComprobador!=indiceHijo && !repetido) {
				//Si se ha repetido paramos porque nos interesa saber el lugar en el que se encuentra el repetido para saber su hom�logo
				if(cromoA[indicePadre] == cromo1[indiceComprobador]) {
					repetido = true;
					break;
				}
				indiceComprobador = (indiceComprobador+1)%l;
			}
			
			//Si despues de analizar lo que ya tiene el hijo vemos que se ha repetido con lo que ofrece el padre en dicha posicion 
			//nos vamos a comprobar la siguiente posicion del padre
			if(repetido) { 
				indicePadre = (indicePadre+1)%l;
				continue;
			}
			//Si lo que da el padre nos e encuentra en el hijo aun nos lo quedamos y avanzamos en los indices
			else {
				cromo1[indiceHijo] = cromoA[indicePadre];
				indicePadre = (indicePadre+1)%l;
				indiceHijo = (indiceHijo+1)%l; 
			}	
		}
		
		//-----------------Cromosoma 2
		//repetimos el proceso pero con el segundo hijo
		indiceHijo=limit2;
		indicePadre = limit2;
		indiceComprobador = limit1;		
		//Voy a recorrer todos los elementos que se encuentran fuera de la zona central
		while(indiceHijo!=limit1) {
			
			boolean repetido = false;
			indiceComprobador = limit1;
			//Vemos si esta repetido con algun elemento dentro de la zona central
			while(indiceComprobador!=indiceHijo && !repetido) {
				//Si se ha repetido paramos porque nos interesa saber el lugar en el que se encuentra el repetido para saber su hom�logo
				if(cromoB[indicePadre] == cromo2[indiceComprobador]) {
					repetido = true;
					break;
				}
				indiceComprobador = (indiceComprobador+1)%l;
			}
			
			//Si despues de analizar lo que ya tiene el hijo vemos que se ha repetido con lo que ofrece el padre en dicha posicion 
			//nos vamos a comprobar la siguiente posicion del padre
			if(repetido) { 
				indicePadre = (indicePadre+1)%l;
				continue;
			}
			//Si lo que da el padre nos e encuentra en el hijo aun nos lo quedamos y avanzamos en los indices
			else {
				cromo2[indiceHijo] = cromoB[indicePadre];
				indicePadre = (indicePadre+1)%l;
				indiceHijo = (indiceHijo+1)%l; 
			}	
		}
		
		a.setCromosoma(cromo1);
		b.setCromosoma(cromo2);
	}
}

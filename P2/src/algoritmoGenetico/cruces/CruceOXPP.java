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
				if(i==limit1 && i==limit2) {
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
			while(indiceHijo!=limit2) {
				//Si ya está relleno no nos interesa hacer nada
				if(indiceHijo == limit1) continue;
				
				boolean repetido = false;
				indiceComprobador = 0;
				//Vemos si esta repetido con algun elemento dentro de la zona central
				while(indiceComprobador<l && !repetido) {
					//Si se ha repetido paramos porque nos interesa saber el lugar en el que se encuentra el repetido para saber su homólogo
					if(cromoPadre[indicePadre] == cromoHijo[indiceComprobador]) {
						repetido = true;
						break;
					}
					indiceComprobador++;
				}
				
				//Si despues de analizar lo que ya tiene el hijo vemos que se ha repetido con lo que ofrece el padre en dicha posicion 
				//nos vamos a comprobar la siguiente posicion del padre
				if(repetido) { 
					indicePadre = (indicePadre+1)%l;
				}
				//Si lo que da el padre nos e encuentra en el hijo aun nos lo quedamos y avanzamos en los indices
				else {
					cromoHijo[indiceHijo] = cromoPadre[indicePadre];
					indicePadre = (indicePadre+1)%l;
					indiceHijo = (indiceHijo+1)%l; 
				}	
			}
			
			return cromoHijo;
		}
	
	
}

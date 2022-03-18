package algoritmoGenetico.cruces;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el m�todo monopunto
public class CrucePMX<T> implements Cruce {

	//Constructora
	public CrucePMX() {}
	
	
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
			cruceUniforme(individuosCruzar.get(i), individuosCruzar.get(i+1));
		
		
		return poblacion;
	}
	
	
	//M�todo que toma 2 individuos y realiza el cruce PMX sobre estos
	private void crucePMX(Individuo a, Individuo b) {
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
		int i=limit2;
		while(i!=limit1) {
			boolean repetido = false;
			//Vemos si esta repetido
			int j=limit1;
			while(j!=limit2 && !repetido) {
				if(cromo1[j] == cromoA[i]) {
					repetido = true;
					break;
				}
				j++;
			}
			if(!repetido) cromo1[i] = cromoA[i];
			else cromo1[i] = cromoB[j];
			
			i = (i+1)%l; 
		}
		
		//-----------------Cromosoma 2
		//Vamos intetandocolocar los valores de fuera
		i = limit2;
		while(i!=limit1) {
			boolean repetido = false;
			//Vemos si esta repetido
			int j = limit1;
			while(j!=limit2 && !repetido) {
				if(cromo2[j] == cromoB[i]) {
					repetido = true;
					break;
				}
				j++;
			}
			if(!repetido) cromo2[i] = cromoB[i];
			else cromo2[i] = cromoA[j];
			
			i = (i+1)%l; 
		}
		
		a.setCromosoma(cromo1);
		b.setCromosoma(cromo2);
	}
}

package algoritmoGenetico.cruces;

import java.util.ArrayList;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el m�todo aritmetico
public class CruceAritmetico<T> implements Cruce {

	//Constructoras
	public CruceAritmetico() {
		this.alpha = 0.6;
	}
	public CruceAritmetico(double alpha) {
		this.alpha = alpha;
	}
	
	//M�todo que toma un apoblaci�n y una probabilidad a partir de la que analiza los individuos
	//y los cruza haciendo uso del m�todo aritmetico
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
			cruceAritmetico(individuosCruzar.get(i), individuosCruzar.get(i+1));
		
		
		return poblacion;
	}
	
	//M�todo que realiza el cruce monpunto sobre 2 individuos que tengan cromosomas con tipos reales
	private void cruceAritmetico(Individuo a, Individuo b) {
		//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
		Random rand = new Random();
		Object[] cromo1 = a.getCromosoma();
		Object[] cromo2 = b.getCromosoma();
		Object[] h1 = a.getCromosoma();
		Object[] h2 = b.getCromosoma();
		
		//Nos hacemos con la longitud del cromosoma para recorrerlo entero
		int l = cromo1.length;
		
		//Recorremos el cromosoma y dependiendo de lo que nos diga el random hacemos un intercambio de los genes o no
		for(int i=0; i<l; i++) {
			Double x = (Double)cromo1[i];
			Double y = (Double)cromo2[i];			
			h1[i] = ((1-this.alpha) * x) + (this.alpha *y);
			h2[i] = (this.alpha * x) + ((1-this.alpha) *y);
		}
		a.setCromosoma(h1);
		b.setCromosoma(h2);
	}
	
	private double alpha = 0.6;
}

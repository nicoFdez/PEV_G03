package algoritmoGenetico.cruces;

import java.util.ArrayList;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;


//Clase que implementa el cruce entre individuos mediante el método monopunto
public class CruceMonopunto<T> implements Cruce{

	//Constructora
	public CruceMonopunto(){}
	
	
	//Método que toma un apoblación y una probabilidad a partir de la que analiza los individuos
	//y los cruza haciendo uso del método monopunto
	@Override
	public Individuo[] cruzar(Individuo[] poblacion, double probCruce) 
	{
		//Preparamos una lista en la que nos vamos a quedar con aquells individuos que tienen que ser cruzados
		ArrayList<Integer> individuosCruzar = new ArrayList<Integer>();
		Random rand = new Random();
		
		//Recorremos todos los individuos y segun un radom y la probabilidad de cruce vemos a cuales les toca 
		for(int i = 0 ; i< poblacion.length; i++) {
			//Si la probabilidad se da, nos quedamos con este individuo
			if(rand.nextDouble() <= probCruce) 
				individuosCruzar.add(i);
		}
		
		//En caso de que la lista de individuos seleccionados sea impar, nos deshacemos de uno de estos 
		//debido a que los cruces los vamos a hacer por parejas y no podemos repetir individuos
		if(individuosCruzar.size() % 2 != 0) 
			individuosCruzar.remove(0);
		
		//Recorremos los individuos por parejas y hacemos que se crucen 
		for(int i=0; i<individuosCruzar.size(); i+=2 )
			cruceMonopunto(poblacion[individuosCruzar.get(i)], poblacion[individuosCruzar.get(i+1)]);
		
		return poblacion;
	}
	
	
	//Método que realiza el cruce monpunto sobre 2 individuos
	private void cruceMonopunto(Individuo a, Individuo b) {
		//Nos preparamos un random y preguntamos por los cromosomas de ambos individuos que tenemos que cruzar
		Random rand = new Random();
		Object[] cromo1 = a.getCromosoma();
		Object[] cromo2 = b.getCromosoma();

		
		//Nos hacemos con la longitud del cromosoma para recorrerlo entero
		int l = cromo1.length;
		//Determinamos un punto intermedio en el cormosoma a partid del cual vamos a hacer le cruce
		int r = rand.nextInt(l);
		
		//Recorremos el cromosoma y dependiendo de donde nos encontremos con respecto al punto de corte 
		//Hacemos el cruce en una direccion u otra
		for(int i=0; i<l; i++) 
			if(i < r) cromo2[i] = cromo1[i];
			else cromo1[i] = cromo2[i];
		
		a.setCromosoma(cromo1);
		b.setCromosoma(cromo2);
	}

}

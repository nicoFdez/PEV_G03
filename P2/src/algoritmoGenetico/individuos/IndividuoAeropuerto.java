package algoritmoGenetico.individuos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.InfoVuelos.TiposVuelo;


//clase que representa un individuo preparado para realizar una evolución sobre la funcion5
public class IndividuoAeropuerto extends Individuo<Integer> {
	
	//Constructoras
	public IndividuoAeropuerto(Individuo other) {
		this.rand = new Random();
		this.nGenes = other.nGenes;
		this.numPistas = ((IndividuoAeropuerto)(other)).numPistas;
		
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];
		
		for(int i=0; i<this.min.length; i++) 
			this.min[i] = other.min[i];
		for(int i=0; i<this.max.length; i++) 
			this.max[i] = other.max[i];
		
		this.tamGenes = new int[this.nGenes];	
		for(int i=0; i<this.tamGenes.length; i++) {
			this.tamGenes[i]=other.tamGenes[i];
		}
		
		for(int i=0; i<this.nGenes; i++)
			this.tamTotal += this.tamGenes[i];
		
		this.cromosoma = new Integer[tamTotal];
		
		for(int i=0; i<this.cromosoma.length; i++)
			this.cromosoma[i] = (Integer)other.cromosoma[i];
	}
	
	public IndividuoAeropuerto(int nVuelos, int nPistas){
		this.rand = new Random();
		this.numPistas = nPistas;
		
		this.nGenes = nVuelos;
		
		//Limites que puede alcanzar el individuo
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];
		for(int i = 0; i<this.nGenes;i++) {
			this.min[i] = 1;
			this.max[i] = nVuelos;
		}
		
		//tamaño del array de "bits" que tiene cada uno de los genes
		this.tamGenes = new int[this.nGenes];
		this.tamTotal=0;
		for(int i = 0; i<this.nGenes;i++) {
			this.tamGenes[i] = 1;
			this.tamTotal += this.tamGenes[i];
		}
		
		//Tamaño total de individuo en bits
		this.cromosoma = new Integer[tamTotal];
	}
	
	@Override
	public double getFitness() {
		return this.getValor();
	}
	
	//Metod que aplica la funcion 5 y devuelvel el valor
	private double getValor() {
		return getValor(this.cromosoma);
	}
	
	//Metod que aplica la funcion 5 y devuelvel el valor
	private double getValor(Integer[] cromo) {
		
		cromo = new Integer[]{8, 9, 10, 11, 12, 7, 6, 5, 4, 3, 2, 1};
		
		double fitnessTotal =0;
		
		double[] genes = new double[this.nGenes];
		InfoVuelos info = InfoVuelos.getInstance();
		
		double[] TLAs = new double[this.numPistas];
		ArrayList<Integer>[] pistas = new ArrayList[this.numPistas];
		for(int i = 0; i< this.numPistas; i++) {
			pistas[i] = new ArrayList<Integer>();
		}
		
		//Para cada elemento del cromosoma, cada vuelo que se vaya a producir
		for(int i = 0;i<cromo.length; i++) {
			
			//Me apunto cual es el vuelo que toca ahora y su tipo
			int idVuelo = cromo[i];
			TiposVuelo tipoVueloActual = info.tipoVuelos[i];
			
			//Variables para saber donde acaba el vuelo y a que hora
			int pistaAAterrizar =0;
			double minHoraAterrizaje = Double.MAX_VALUE;
			double minTEL = info.TEL[0][idVuelo-1];
			
			//Recorro todas las pistas preguntando para quedarme con aquella que antes le permita aterrizar
			for(int j = 0; j<this.numPistas; j++) {
				
				//Momento en el que puedo llegar a la pista (restamos 1 al id vuelo porque la representacion va de [1,numVuelos]
				double miTEL = info.TEL[j][idVuelo-1];
				if(miTEL < minTEL) minTEL = miTEL;
				//Tiempo extra que me tengo que esperar por el vuelo anterior en dicha pista
				double tEspera = 0;
				if(pistas[j].size() > 0) {
					int idVueloAnterior = pistas[j].get(pistas[j].size()-1)-1;
					TiposVuelo tipoVueloAnterior = info.tipoVuelos[idVueloAnterior];
					tEspera = info.SEP[tipoVueloActual.ordinal()][tipoVueloAnterior.ordinal()];
				}
				
				//Tiempo en el que la pista se queda libre
				double horaLibre = TLAs[j]+ tEspera;
				//Me quedo con el maximo de cuando puedo llegar y cuando la pista esta libre
				double horaAterrizaje = Math.max(horaLibre, miTEL); 
				//Me quedo con la pista a la que antes pueda aterrizar
				if(horaAterrizaje < minHoraAterrizaje) {
					minHoraAterrizaje = horaAterrizaje;
					pistaAAterrizar = j;
				}
			}
			
			//Registro en la pista seleccionada tanto el vuelo que llega como la hora
			pistas[pistaAAterrizar].add(idVuelo);
			TLAs[pistaAAterrizar] = minHoraAterrizaje;

			//Acumulo el cuadrado del retardo para el fitness
			fitnessTotal +=(Math.pow((minHoraAterrizaje-minTEL), 2));
		}
		
		return fitnessTotal;
	}
	
	public double getFenotipo(int val) {
		return this.cromosoma[val];
	}
	
	@Override
	public void initialize() {
		/*boolean[] visited = new boolean[(int)this.max[0]];
		for(int i = 0; i < this.tamTotal; i++) {
			Integer r = 1 + this.rand.nextInt((int)this.max[i]);
			while(visited[r-1]) 
				r = 1 + this.rand.nextInt((int)this.max[i]);
			
			this.cromosoma[i] = r.intValue();
			visited[r-1]=true;
		}*/
		for(int i = 0; i<this.cromosoma.length;i++) {
			this.cromosoma[i] = i+1;
		}
		List<Integer> intList = Arrays.asList(this.cromosoma);
		Collections.shuffle(intList);
		intList.toArray(this.cromosoma);
	}
	
	@Override
	public void copyFromAnother(Individuo<Integer> other) {
		//Tomamos el cromosoma del individuo que es mejor que nosotros y lo copiamos
		Integer[] otherCromo = other.getCromosoma();
		for(int i = 0; i<otherCromo.length; i++) 
			this.cromosoma[i] = otherCromo[i].intValue();
	}
	
	//Metodo que toma una probabilidad de mutacion y toma una serie de elementos del cromosoma (numInserciones)
	//y los reinserta en nuevas posiciones sacadas aleatoriamente dentro del array
	@Override
	public void mutacionInsercion(double probMutacion) {
		//Tomamos un elemento del cromosoma al azar y lo que hacemos es insertarlo en una nueva posición elegida al azar
		Random rnd = new Random();
		int numInserciones = 1;
		List<Integer> cromoListado = new ArrayList<Integer>();
		for(int i = 0; i<this.cromosoma.length;i++) {
			cromoListado.add(this.cromosoma[i].intValue());
		}
		
		//Voy a hacer la inserción tantas veces como se me pida
		for(int i = 0; i<numInserciones; i++) {
			//Decido quien es el que va a ser insertado y la nueva posición que va a ocupar
			int elemToInsert = 1+rnd.nextInt((int)this.max[0]);
			int nuevaPos = rnd.nextInt(this.nGenes);
			
			//Lo saco de la lista y lo pongo en su nuevo lugar
			int index = cromoListado.indexOf(elemToInsert);
			//No se ha encontrado el elemento
			if(index == -1) {	
				int a =0;
			}
			cromoListado.remove(index);
			cromoListado.add(nuevaPos, elemToInsert);
		}
		
		Integer[] result = new Integer[cromoListado.size()];
		for(int i = 0; i<cromoListado.size(); i++) {
			result[i] = (Integer)cromoListado.get(i);
		}
		this.cromosoma = result;
	}
	
	
	//Metodo que toma dos posiciones aleatorias dentro del cromosoma e intercambia el contenido de dichas posiciones
	@Override
	public void mutacionIntercambio(double probMutacion) {
		//Tomo dos posiciones aleatorias dentro del array 
		Random rnd = new Random();
		int posA = rnd.nextInt(this.nGenes);
		int posB = rnd.nextInt(this.nGenes);
		
		//Bucle para evitar que las dos posiciones sean en realidad la misma
		while(posB == posA) posB = rnd.nextInt(this.nGenes);
		
		//Hago un swap de lo que haya en estas posiciones
		int aux = this.cromosoma[posB];
		this.cromosoma[posB] = this.cromosoma[posA];
		this.cromosoma[posA] = aux;
	}
	
	//Metodo toma dos posiciones aleatorias dentro del cromosoma e invierte el orden en el que aparecen los elementos
	//que se encuentren dentro de este rango
	@Override
	public void mutacionInversion(double probMutacion) {
		//Tomamos 2 posiciones al azar entre las que vamos a invertir el orden de los elementos
		Random rnd = new Random();
		int posA = rnd.nextInt(this.nGenes);
		int posB = rnd.nextInt(this.nGenes);
		
		//Bucle para evitar que las dos posiciones sean en realidad la misma
		while(posB == posA) posB = rnd.nextInt(this.nGenes);
		//Nos aseguramos de que la posicionA se encuentra a la izquierda y la B a la derecha
		if(posB > posA) {
			int aux = posB;
			posB = posA;
			posA = aux;
		}
		
		//HAcemos una copia del cromosoma para que podamos hacer los intecambios
		Integer[] copiedCromo = Arrays.copyOf(this.cromosoma, this.cromosoma.length);		
		//Voy a hacer la inserción tantas veces como se me pida
		for(int i = posA; i<=posB; i++) 
			this.cromosoma[i] = copiedCromo[posB-(i-posA)];			
		
	}
	
	
	//Metodo que toma un número de posiciones aleatorias dentro del cromosoma y realiza todas las permutaciones posibles
	//de los elementos en dichas posiciones quedandose con aquella que haya sido la mejor de cara al problema
	@Override
	public void mutacionHeuristica(double probMutacion) {
		//Tomamos 2 posiciones al azar entre las que vamos a invertir el orden de los elementos
		Random rnd = new Random();
		int numPosiciones = 5;
		
		//Posiciones sobre las que vamos a realizar permutaciones
		Integer[] posiciones = new Integer[numPosiciones];
		for(int i = 0; i< numPosiciones; i++) 
			posiciones[i] = rnd.nextInt(this.nGenes);
		
		//Preparo el que va a contener el mejor cromosoma y las listas que hacen falta en el método de permutaciones
		Integer[] bestCromo = Arrays.copyOf(this.cromosoma, this.cromosoma.length);		
		List<Integer> posicionesListadas = Arrays.asList(Arrays.copyOf(posiciones, posiciones.length));
		List<Integer> permutacion = Arrays.asList(Arrays.copyOf(posiciones, posiciones.length));

		//hacemos todas las permutaciones posibles sobre la lista y al terminar nos quedamos como cromosoma lo
		//que tengamos almacenado en bestCromo
		probarPermutaciones(permutacion, posicionesListadas, this.cromosoma,bestCromo,0);
		this.cromosoma = bestCromo;
	}
	
	
	//Método que explora todas las posibles permutaciones sobre una lista de enteros
	//Recibe dos listas, una en la que se van a realizar las permutaciones y otra en la que se encuentra la lista
	// en el orden original, tambien recibe el cromosoma original del individuo y el mejor cromosoma, el 
	//cual al principio es el propio cromosoma del individuo
	//por último recibe un indice que indica en qué posicion dentro de la lista estamos realizando cambios en nuestra permutación actual
	private void probarPermutaciones(List<Integer> permutacion, List<Integer> posiciones ,Integer[] originalCromo,Integer[] bestCromo, int permutationPos){    
		//Desde la permutationPos probamos a intercambiarnos con todas las posiciones por delante nuestra
		//Cuando vuelva nos aseguramos de deshacer el swap que hicimos para que al volver a hacer otro el
		//cromosoma no empiece a desordenarse 
		for(int i = permutationPos; i < permutacion.size(); i++){
            Collections.swap(permutacion, i, permutationPos);
            probarPermutaciones(permutacion, posiciones , originalCromo , bestCromo, permutationPos+1);
            Collections.swap(permutacion, permutationPos, i);
        }
        //Esto significa que hemos terminado de generar la permitación y por lo tanto podemos comprobar el cromosoma
        if (permutationPos == permutacion.size() -1){
        	//Hacemos una copia y en ella realizamos los cambios pertinentes
    		Integer[] copiedCromo = Arrays.copyOf(originalCromo, originalCromo.length);	
    		for(int i = 0; i< posiciones.size(); i++) 
    			copiedCromo[posiciones.get(i)] = originalCromo[permutacion.get(i)];
    		//En caso de que esta copia sea mejor que el mejor acual, actualizamos el mejor cromosoma
    		if(getValor(copiedCromo) < getValor(bestCromo))
    			bestCromo = copiedCromo;
        }
    }
	
	
	
	@Override
	public Integer[] getCromosoma() {
		Integer[] result = new Integer[this.cromosoma.length];
		
		for(int i=0; i<this.cromosoma.length; i++)
			result[i] = this.cromosoma[i].intValue();
		
		return result;
	}
	
	private int numPistas;
}

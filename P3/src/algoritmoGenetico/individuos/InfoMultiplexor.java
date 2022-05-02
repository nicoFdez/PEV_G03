package algoritmoGenetico.individuos;

import java.util.Arrays;

//Singleton que vamos a utilizar para contener los datos de nuestro algoritmo 
//Va a almacenar todos los datos del problema: vuelos, tipos de vuelo, horas de llegada, número de pistas etc
public class InfoMultiplexor {
	
 // Static variable reference of single_instance
 // of type Singleton
 private static InfoMultiplexor single_instance = null;
 
 						//	Entradas	Salidas						Operadores
 public enum ValoresNodos6 	{A0,A1,		D0,D1,D2,D3,				AND,OR,NOT,IF}
 public enum ValoresNodos11 {A0,A1,A2,	D0,D1,D2,D3,D4,D5,D6,D7, 	AND,OR,NOT,IF} 
 
//Variables que sirven para determinar el tipo de multiplexor con el que estamos trabajando
 //y las caracteristicas del mismo
 public static boolean Multi6 = true;
 public static int numTerminales=6;
 public static int numNodosDistintosTipos = 10;
 public static int numPosibilidades=64;
 
 //Enum que nos permitirá distinguir los diferentes tipos de valores que pueden tener los operadores de los árboles
 public enum TipoNodo {TERMINAL, NODOAND, NODOOR, NODONOT, NODOIF}
 
 
 public static int ConstantePenalizacion = 5;
 public static boolean BloatingCheck=true;
 
 //Metodo que recibe un indice y a partir de este comprueba si estamos trabajando con un multiplexor u otro
 //para poder determinar el tipo de nodo que representa y devolver un Enum que lo indique
 public static TipoNodo getTipoNodo(int indice) {
	 if(Multi6) {
		 int value = indice-5;
		 if(value<0)value=0;
		 return TipoNodo.values()[value];
	 }
	 else {
		 int value = indice-10;
		 if(value<0)	value=0;
		 return TipoNodo.values()[value];
	 }
}
 
 
 public static int getIndiceTipoNodo(TipoNodo tipo) {
	 
	 return numTerminales+tipo.ordinal()-1;
 }
 
 
 //Metodo que inicializa el singleton para que esté preparado para trabajar con un multiplexor de 2048 posibilidades
public static void initAlternativo() {
	//Instancia del singleton
	 single_instance = new InfoMultiplexor();
	 entrada = new int[2048][11];
	 selectSize = 3;
	 
	 for(int i=0; i<2048; i++) {
		 entrada[i] = toBinary(i, 11);
	 }
	 numTerminales=11;
	 Multi6 = false;
	 numPosibilidades=2048;
	 numNodosDistintosTipos = 15;
	 ConstantePenalizacion = 8;
}
 
 
 //Metodo utilizado para inicializar el singleton para que esté preparado para trabajar con un multiplexor de 64 posibilidades
 public static void init() {
	 //Instancia del singleton
	 single_instance = new InfoMultiplexor();
	 entrada = new int[64][6];
	 selectSize = 2;
	 
	 for(int i=0; i<64; i++) {
		 entrada[i] = toBinary(i, 6);
	 }
	 numTerminales=6;
	 Multi6 = true;
	 numPosibilidades=64;
	 numNodosDistintosTipos = 10;
	 ConstantePenalizacion = 5;
 }

 
 //Metodo que revibe un número y una base y transforma dicho numero en una array de bits que lo representan pero en forma binaria
 public static int[] toBinary(int number, int base) {
    final int[] ret = new int[base];
    for (int i = 0; i < base; i++) {
        ret[base - 1 - i] = (1 << i & number);
    }
	for(int i = 0; i <ret.length;i++) {
		if(ret[i]>0)ret[i]=1;
		else ret[i]=0;
	}
    return ret;
}
 
 
 //Metodo que recibe un array de bits y lo convierte en un número entero
private static int binToDec(int[] bin) {
	int sum = 0;
	for(int i=0; i<bin.length; i++) {
		if(bin[i] == 1) sum += Math.pow(2, i);
	}
	return sum;
}
 


//Metodo que recibe un índice y analiza el caso concreto del multiplexor que querramos ver, en dicho caso
//saca la parte de la entrada y las distintas salidas del multiplexor para determinar cuál es el valor que
//dicho nos devolvería
public static int getSalida(int e) {
	int[] caso = entrada[e];
	int[] select = Arrays.copyOfRange(caso, 0, selectSize);
	int[] entradas = Arrays.copyOfRange(caso, selectSize, caso.length);
	
	int pos = binToDec(select);
	return entradas[pos];
}

 // Static method
 // Static method to create instance of Singleton class
 public static InfoMultiplexor getInstance()
 {
     return single_instance;
 }
 
 
 public static int[][] entrada;
 private static int selectSize;
 
}

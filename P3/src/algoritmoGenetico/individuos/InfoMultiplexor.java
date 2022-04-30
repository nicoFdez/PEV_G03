package algoritmoGenetico.individuos;

import java.util.Arrays;

//Singleton que vamos a utilizar para contener los datos de nuestro algoritmo 
//Va a almacenar todos los datos del problema: vuelos, tipos de vuelo, horas de llegada, número de pistas etc
public class InfoMultiplexor {
	
 // Static variable reference of single_instance
 // of type Singleton
 private static InfoMultiplexor single_instance = null;

 public enum ValoresNodos6 	{A0,A1,		D0,D1,D2,D3,				AND,OR,NOT,IF}
 public enum ValoresNodos11 {A0,A1,A2,	D0,D1,D2,D3,D4,D5,D6,D7, 	AND,OR,NOT,IF} 

 
 public static boolean Multi6 = true;
 public enum TipoNodo {TERMINAL, NODOAND, NODOOR, NODONOT, NODOIF}

 public static int numTerminales=6;
 public static int numNodosDistintosTipos = 10;
 public static int numPosibilidades=64;
 
 
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
}
 
 
 //Metodo utilizado para inicializar el singleton
 //Recibe un entero que especifica el ejemplo de datos que queremos utilizar para ejecutar el algoritmo
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
 }

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
 
private static int binToDec(int[] bin) {
	int sum = 0;
	for(int i=0; i<bin.length; i++) {
		if(bin[i] == 1) sum += Math.pow(2, i);
	}
	return sum;
}
 
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

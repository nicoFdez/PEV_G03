package algoritmoGenetico.individuos;

import java.util.Arrays;

//Singleton que vamos a utilizar para contener los datos de nuestro algoritmo 
//Va a almacenar todos los datos del problema: vuelos, tipos de vuelo, horas de llegada, n�mero de pistas etc
public class InfoMultiplexor {
	
 // Static variable reference of single_instance
 // of type Singleton
 private static InfoMultiplexor single_instance = null;

 public enum ValoresNodos6 {A0,A1,D0,D1,D2,D3,AND,OR,NOT,IF}
 
 public static int numTerminales=6;
 
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
	
 }

 public static int[] toBinary(int number, int base) {
	    final int[] ret = new int[base];
	    for (int i = 0; i < base; i++) {
	        ret[base - 1 - i] = (1 << i & number);
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

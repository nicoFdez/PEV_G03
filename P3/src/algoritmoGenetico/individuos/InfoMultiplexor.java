package algoritmoGenetico.individuos;



//Singleton que vamos a utilizar para contener los datos de nuestro algoritmo 
//Va a almacenar todos los datos del problema: vuelos, tipos de vuelo, horas de llegada, número de pistas etc
public class InfoMultiplexor {
	
 // Static variable reference of single_instance
 // of type Singleton
 private static InfoMultiplexor single_instance = null;

 public enum TiposFunciones {A0,A1,D0,D1,D2,D3,AND,OR,NOT,IF
	}
 
 public static int numTerminales=6;
 
 //Metodo utilizado para inicializar el singleton
 //Recibe un entero que especifica el ejemplo de datos que queremos utilizar para ejecutar el algoritmo
 public static void init(int example, int fitness) {
	 //Instancia del singleton
	 single_instance = new InfoMultiplexor();
	 
	 tipoFitness = fitness;
	 
	 //Tiempos de espera entre diferentes tipos de vuelos
	 SEP = new double [3][3];
	 SEP[0] = new double[] {1,1.5,2};
     SEP[1] = new double[] {1,1.5,1.5};
     SEP[2] = new double[] {1,1,1};
	 
     //Inicializamos de una forma u otra dependiendo de lo que nos hayan dicho

 }
 
 // Static method
 // Static method to create instance of Singleton class
 public static InfoMultiplexor getInstance()
 {
     return single_instance;
 }
 
 public enum TiposVuelo {W,G,P};
 
 public static int[][] TEL;
 public static double[][] SEP;
 public static TiposVuelo[] tipoVuelos;
 public static int tipoFitness;
}

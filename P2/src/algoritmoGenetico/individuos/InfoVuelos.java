package algoritmoGenetico.individuos;



//Singleton que vamos a utilizar para contener los datos de nuestro algoritmo 
//Va a almacenar todos los datos del problema: vuelos, tipos de vuelo, horas de llegada, n�mero de pistas etc
public class InfoVuelos {
	
 // Static variable reference of single_instance
 // of type Singleton
 private static InfoVuelos single_instance = null;

 
 //Set de datos por defecto de la practica
 private static void init1()
 {   
	 TEL = new int[3][12];
	 tipoVuelos = new TiposVuelo[12];
	 
	 TEL[0]= new int[]{11, 15, 6, 6,  9, 7, 15, 6, 6,  9, 7, 9};
     TEL[1]= new int[]{10, 17, 7, 7, 12, 6, 17, 7, 7, 12, 6, 7};
     TEL[2]= new int[]{ 9, 19, 8, 8, 15, 5, 19, 8, 8, 15, 5, 5};
         
     tipoVuelos = new TiposVuelo[] {TiposVuelo.W,TiposVuelo.G,TiposVuelo.W,
    		 						TiposVuelo.W,TiposVuelo.P,TiposVuelo.W,
    		 						TiposVuelo.G,TiposVuelo.W,TiposVuelo.W,
    		 						TiposVuelo.P,TiposVuelo.W,TiposVuelo.G};
 }
 
 //Set de datos de ejemplo con m�s vuelos 
 private static void init2()
 {   
	 TEL = new int[3][20];
	 tipoVuelos = new TiposVuelo[20];
	 
     TEL[0]= new int[]{11, 15, 6, 6,  9, 7, 15, 6, 6,  9, 7, 9, 15, 11,  7, 3, 12, 11, 18, 20};
     TEL[1]= new int[]{10, 17, 7, 7, 12, 6, 17, 7, 7, 12, 6, 7, 17, 10, 16, 4, 10,  9, 17, 20};
     TEL[2]= new int[]{ 9, 19, 8, 8, 15, 5, 19, 8, 8, 15, 5, 5, 19,  9,  8, 5, 10,  8, 21, 20};
     
     tipoVuelos = new TiposVuelo[] {TiposVuelo.W,TiposVuelo.P,TiposVuelo.P,TiposVuelo.W,TiposVuelo.P,
    		 						TiposVuelo.W,TiposVuelo.G,TiposVuelo.W,TiposVuelo.W,TiposVuelo.P,
    		 						TiposVuelo.G,TiposVuelo.G,TiposVuelo.W,TiposVuelo.W,TiposVuelo.W,
    		 						TiposVuelo.W,TiposVuelo.G,TiposVuelo.G,TiposVuelo.W,TiposVuelo.P};
 }
 
 //Set de datos de ejemplo con m�s vuelos y m�s pistas
 private static void init3()
 {   
	 TEL = new int[5][20];
	 tipoVuelos = new TiposVuelo[12];
	 
	 TEL[0]= new int[]{11, 15, 6,  6,  9,  7, 15,  6, 6,  9, 7, 9, 15, 11,  7, 3, 12, 11, 18, 20};
     TEL[1]= new int[]{10, 17, 7,  7, 12,  6, 17,  7, 7, 12, 6, 7, 17, 10, 16, 4, 10,  9, 17, 20};
     TEL[2]= new int[]{ 9, 19, 8,  8, 15,  5, 19,  8, 8, 15, 5, 5, 19,  9,  8, 5, 10,  8, 21, 20};   
     TEL[3]= new int[]{20, 17, 9, 10,  4, 16, 10, 17, 7,  6, 9, 6,  6, 15,  7, 9,  6,  6, 15, 11};
     TEL[4]= new int[]{11, 15, 6,  6,  9,  7, 15,  6, 6,  9, 6, 7, 17, 10, 16, 4, 10,  9, 17, 20};
     
     tipoVuelos = new TiposVuelo[] {TiposVuelo.W,TiposVuelo.G,TiposVuelo.W,TiposVuelo.W,TiposVuelo.P,
									TiposVuelo.W,TiposVuelo.P,TiposVuelo.W,TiposVuelo.G,TiposVuelo.W,
									TiposVuelo.G,TiposVuelo.G,TiposVuelo.P,TiposVuelo.W,TiposVuelo.P,
									TiposVuelo.W,TiposVuelo.G,TiposVuelo.W,TiposVuelo.W,TiposVuelo.P};
 }

 
 //Metodo utilizado para inicializar el singleton
 //Recibe un entero que especifica el ejemplo de datos que queremos utilizar para ejecutar el algoritmo
 public static void init(int example, int fitness) {
	 //Instancia del singleton
	 single_instance = new InfoVuelos();
	 
	 tipoFitness = fitness;
	 
	 //Tiempos de espera entre diferentes tipos de vuelos
	 SEP = new double [3][3];
	 SEP[0] = new double[] {1,1.5,2};
     SEP[1] = new double[] {1,1.5,1.5};
     SEP[2] = new double[] {1,1,1};
	 
     //Inicializamos de una forma u otra dependiendo de lo que nos hayan dicho
	 switch(example) {
	 case 1:
		 init1();
		 break;
	 case 2:
		 init2();
		 break;
	 case 3:
		 init3();
		 break;
	 }
 }
 
 // Static method
 // Static method to create instance of Singleton class
 public static InfoVuelos getInstance()
 {
     return single_instance;
 }
 
 public enum TiposVuelo {W,G,P};
 
 public static int[][] TEL;
 public static double[][] SEP;
 public static TiposVuelo[] tipoVuelos;
 public static int tipoFitness;
}

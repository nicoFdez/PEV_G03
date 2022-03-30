package algoritmoGenetico.individuos;

//Class 1
//Helper class
class InfoVuelos {
	
 // Static variable reference of single_instance
 // of type Singleton
 private static InfoVuelos single_instance = null;


 // Constructor
 // Here we will be creating private constructor
 // restricted to this class itself
 private InfoVuelos()
 {     
     TEL[0]= new int[]{11,15,6,6,9,7,15,6,6,9,7,9};
     TEL[1]= new int[]{10,17,7,7,12,6,17,7,7,12,6,7};
     TEL[2]= new int[]{9,19,8,8,15,5,19,8,8,15,5,5};
     
     SEP[0] = new double[] {1,1.5,2};
     SEP[1] = new double[] {1,1.5,1.5};
     SEP[2] = new double[] {1,1,1};
     
     tipoVuelos = new TiposVuelo[] {TiposVuelo.W,TiposVuelo.G,TiposVuelo.W,
    		 						TiposVuelo.W,TiposVuelo.P,TiposVuelo.W,
    		 						TiposVuelo.G,TiposVuelo.W,TiposVuelo.W,
    		 						TiposVuelo.P,TiposVuelo.W,TiposVuelo.G};
 }

 // Static method
 // Static method to create instance of Singleton class
 public static InfoVuelos getInstance()
 {
     if (single_instance == null)
         single_instance = new InfoVuelos();

     return single_instance;
 }
 
 
 public int[][] TEL = new int[3][12];
 public double[][] SEP = new double [3][3];
 public enum TiposVuelo {W,G,P};
 public TiposVuelo[] tipoVuelos = new TiposVuelo[12];
}

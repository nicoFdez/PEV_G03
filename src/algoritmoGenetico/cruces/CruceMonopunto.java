package algoritmoGenetico.cruces;

import java.util.ArrayList;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;

public class CruceMonopunto<T> implements Cruce{

	@Override
	public Individuo[] cruzar(Individuo[] poblacion, double probCruce) 
	{
		ArrayList<Individuo<T>> individuosCruzar = new ArrayList<Individuo<T>>();
		Random rand = new Random();
		
		for(int i = 0 ; i< poblacion.length; i++) {
			double r = rand.nextDouble();
			if(r <= probCruce) {
				individuosCruzar.add(poblacion[i]);
			}
		}
		if(individuosCruzar.size() % 2 != 0) {
			individuosCruzar.remove(0);
		}
		
		for(int i=0; i<individuosCruzar.size(); i+=2 ) {
			
			cruceMonopunto(individuosCruzar.get(i), individuosCruzar.get(i+1));
		}
		
		return null;
	}
	
	
	private void cruceMonopunto(Individuo a, Individuo b) {
		Random rand = new Random();
		Object[] cromo1 = a.getCromosoma();
		Object[] cromo2 = b.getCromosoma();
		
		int l = cromo1.length;
		int r = rand.nextInt(l);
		
		for(int i=0; i<l; i++) {
			if(i < r) cromo2[i] = cromo1[i];
			else cromo1[i] = cromo2[i];
		}
	}

}

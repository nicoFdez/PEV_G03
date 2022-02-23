package algoritmoGenetico.cruces;

import java.util.Random;

import algoritmoGenetico.individuos.Individuo;

public class CruceMonopunto implements Cruce{

	@Override
	public Individuo[] cruzar(Individuo[] poblacion, double probCruce) 
	{
		Boolean [] visited = new Boolean[poblacion.length];
		Random rand = new Random();
		
		for(int i = 0 ; i< poblacion.length; i++) {
			double res = rand.nextDouble();
			if(res <= probCruce && !visited[i]) {
				visited[i]=true;
				//Mientras ya te hayas cruzado sigo buscando alguien nuevo
				int indice = rand.nextInt();
				while(visited[indice]) indice = rand.nextInt();
				
				visited[indice] = true;
				
			}
		}
		
		return null;
	}
	
	
	private void cruceMonopunto(Individuo a, Individuo b) {
		
		
	}

}

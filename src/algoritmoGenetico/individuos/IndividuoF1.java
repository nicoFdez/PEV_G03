package algoritmoGenetico.individuos;

import java.util.Random;

public class IndividuoF1 extends Individuo<Boolean> {
	
	public IndividuoF1(double precision){
		this.rand = new Random();
		
		this.nGenes = 2;
		this.cromosoma = new Boolean[nGenes];
		
		this.min = new double[this.nGenes];
		this.max = new double[this.nGenes];	
		this.min[0] = -3.0;
		this.min[1] = 4.1;
		this.max[0] = 12.1;
		this.max[1] = 5.8;
		
		this.tamGenes[0] = tamGen(precision, min[0], max[0]);
		this.tamGenes[1] = tamGen(precision, min[1], max[1]);

		this.tamTotal = this.tamGenes[0] + this.tamGenes[1];
	}
	
	public void initialize() {
		for(int i = 0; i < this.tamTotal; i++) 
			this.cromosoma[i] = this.rand.nextBoolean();

	}
}

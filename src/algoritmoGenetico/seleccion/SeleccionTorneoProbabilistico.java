package algoritmoGenetico.seleccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import algoritmoGenetico.individuos.Individuo;

public class SeleccionTorneoProbabilistico implements Seleccion {

	
	public SeleccionTorneoProbabilistico(){
		
	}
	@Override
	public Individuo[] seleccionar(Individuo[] poblacion) {
		
		//Preparo las variables con las que voy a hacer esta clase de seleccion
		double fitnessTotal = 0;
		int nIndividuos = poblacion.length;
		int tamTorneo = 3;
		double probSacarPrimero = 0.5;

		//Para cada individuo de la poblacion final tomo 3 participantes de la poblacion inicial 
		//hago un torneo con estos y me quedo con aquel que haya salido primero
		Random rand = new Random();
		Individuo[] poblacionSeleccionada = new Individuo[nIndividuos];
		Individuo[] individuosTorneo = new Individuo[tamTorneo];
		for(int i=0; i<nIndividuos; i++) {
			// Saco k participantes
			for(int j = 0 ; j <  tamTorneo; j++ ) {
				int r = rand.nextInt(nIndividuos);
				individuosTorneo[j] = poblacion[r];
			}
			//Hago que compitan y me quedo con el mejor
			individuosTorneo = competirTorneo(individuosTorneo);
			//Una vez que tenemos el torneo hecho tomamos un random que nos indica si nos quedamos con
			//el mejor o el peor
			poblacionSeleccionada[i] = rand.nextDouble() > probSacarPrimero ? 
					individuosTorneo[0] : individuosTorneo[individuosTorneo.length-1];
		}
		
		return poblacionSeleccionada;
	}
	
	private Individuo[] competirTorneo(Individuo[] participantes) {
		//En estas listas voy a ir almacenando los fitness y los participantes en orden decreciente
		List<Individuo> finalistas = new ArrayList<Individuo>();
		List<Double> fitness = new ArrayList<Double>();
		
		//Recorro todos los participantes y para cada uno me quedo con su fitness y 
		//analizo la posicion dentro de las listas que le corresponde teniendo en cuenta qu e
		//va en orden decreciete
		for(int i = 0; i< participantes.length; i++) {
			int position =0;
			double fitnessParticipante = participantes[i].getFitness();
			//Busco una nueva pos mientras los finalistas sean mejores que yo
			while(position < fitness.size() && fitness.get(i)> fitnessParticipante) 
				position++;
			
			//Si ha ocurrido esto es o que no había nadie en la lista aun o soy el peor participante
			if(position>= finalistas.size() && !finalistas.isEmpty()) position = finalistas.size()-1;
			
			//Añado el participante a su posicion dentro del torneo
			fitness.add(position,fitnessParticipante);
			finalistas.add(position,participantes[i]);
		}
		
		return (Individuo[])finalistas.toArray();
	}
}

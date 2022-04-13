package gui;

import org.math.plot.Plot2DPanel;


//Clase encargada de generar una grafica que se pueda mostrar por pantalla
public class LinePlot {
	
	//Constructora
	public LinePlot() {
		plot = new Plot2DPanel();
	}
	
	//Metodo que recibe una serie de coordenadas 2D y a partir de ellas genera una linea nueva en la
	//gráfica y le asocia un nombre que se añade a la leyenda
	public void addArrayOfPoints(String lineName, double[] y) {
		double[] x = new double[y.length];
		for(int i = 0 ; i< x.length; i++) {
			x[i] = i;
		}
		// add a line plot to the PlotPanel
		plot.addLinePlot(lineName, x, y);
	}
	
	
	//Metodo que añade una leyenda a la gráfica en la sección inferior de la gráfica
	public void plot() {
		// define the legend position
		plot.addLegend("SOUTH");
	}
	
	//Metodo que devuelve la gráfica que se ha formado hasta el momento
	public Plot2DPanel getGraph() {
		return this.plot;
	}
	
	Plot2DPanel plot ;
}
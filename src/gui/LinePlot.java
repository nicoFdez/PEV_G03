package gui;

import javax.swing.*;
import java.util.ArrayList; // import the ArrayList class

import org.math.plot.*;

public class LinePlot {
	
	/*public static void main(String[] args) {
	// define your data
	double[] x = { 1, 2, 3, 4, 5, 6 };
	double[] y = { 45, 89, 6, 32, 63, 12 };
	
	// create your PlotPanel (you can use it as a JPanel)
	this.plot = new Plot2DPanel();
	
	// define the legend position
	this.plot.addLegend("SOUTH");
	
	// add a line plot to the PlotPanel
	plot.addLinePlot("my plot", x, y);
	
	// put the PlotPanel in a JFrame like a JPanel
	JFrame frame = new JFrame("a plot panel");
	frame.setSize(600, 600);
	frame.setContentPane(plot);
	frame.setVisible(true);
	}*/
	
	public LinePlot() {
		plot = new Plot2DPanel();
	}
	
	
	/*public void addPoint(double x, double y) {
		xValues.add(x);
		yValues.add(y);
	}*/
	
	public void addArrayOfPoints(String lineName,double[] y) {
		double[] x = new double[y.length];
		for(int i = 0 ; i< x.length; i++) {
			x[i] = i;
		}
		// add a line plot to the PlotPanel
		plot.addLinePlot(lineName, x, y);
	}
	
	
	public void plot() {
		// define the legend position
		plot.addLegend("SOUTH");
		
		// put the PlotPanel in a JFrame like a JPanel
		JFrame frame = new JFrame("G03_P1");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}
	
	Plot2DPanel plot ;

}
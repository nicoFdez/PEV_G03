package gui;

import javax.swing.*;
import java.util.ArrayList; // import the ArrayList class

import org.math.plot.*;

public class LinePlot {
	public static void main(String[] args) {
	// define your data
	double[] x = { 1, 2, 3, 4, 5, 6 };
	double[] y = { 45, 89, 6, 32, 63, 12 };
	
	// create your PlotPanel (you can use it as a JPanel)
	Plot2DPanel plot = new Plot2DPanel();
	
	// define the legend position
	plot.addLegend("SOUTH");
	
	// add a line plot to the PlotPanel
	plot.addLinePlot("my plot", x, y);
	
	// put the PlotPanel in a JFrame like a JPanel
	JFrame frame = new JFrame("a plot panel");
	frame.setSize(600, 600);
	frame.setContentPane(plot);
	frame.setVisible(true);
	}
	
	public LinePlot() {
		xValues = new ArrayList<Double>();
		yValues = new ArrayList<Double>();
	}
	
	
	public void addPoint(double x, double y) {
		xValues.add(x);
		yValues.add(y);
	}
	
	public void plot() {
		
		double[] x = xValues.stream().mapToDouble(Double::doubleValue).toArray();
		double[] y = yValues.stream().mapToDouble(Double::doubleValue).toArray();
		
		
		// create your PlotPanel (you can use it as a JPanel)
		Plot2DPanel plot = new Plot2DPanel();
		
		// define the legend position
		plot.addLegend("SOUTH");
		
		// add a line plot to the PlotPanel
		plot.addLinePlot("my plot", x, y);
		
		// put the PlotPanel in a JFrame like a JPanel
		JFrame frame = new JFrame("a plot panel");
		frame.setSize(600, 600);
		frame.setContentPane(plot);
		frame.setVisible(true);
	}
	
	ArrayList<Double> xValues ; // Create an ArrayList object
	ArrayList<Double> yValues ; // Create an ArrayList object
}
package gui;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablePlot {

	
	TablePlot(){
	}
	
	public void agregarDatos(ArrayList<Integer>[] vuelosPistas,  ArrayList<Double>[] tlasVuelos) {
		
		modelo=new DefaultTableModel() {
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		this.tabla = new JTable();
		this.tabla.setModel(modelo);
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.tabla.getTableHeader().setReorderingAllowed(false);
		//Preparamos tantas columnas como pistas haya 
        for(int i = 0; i< vuelosPistas.length; i++) {
        	this.modelo.addColumn("P"+ (i+1) +" Vuelos:", vuelosPistas[i].toArray());
        	this.modelo.addColumn("TLAs:", tlasVuelos[i].toArray());
        }
    }
	
	public JScrollPane getTabla() {
		JScrollPane pane = new JScrollPane(this.tabla, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return pane;
	}
	
	DefaultTableModel modelo;
	JTable tabla;
}

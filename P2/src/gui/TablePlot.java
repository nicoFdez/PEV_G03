package gui;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TablePlot {

	
	TablePlot(){
	}
	
	public void prueba() {
        String[] columnas = {"Columna 0", "Columna 1", "Columna 2", "Columna 3"};

		this.modelo.setColumnIdentifiers(columnas);
		this.tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		this.tabla.setModel(modelo);
		// Borramos todas las filas en la tabla
        this.modelo.setRowCount(0);
        
        // Creamos los datos de una fila de la tabla
        Object[] datosFila = {"Datos 0,0", "Datos 0,1", "Datos 0,2", "Datos 0,3"};
        
        // agregamos esos datos
        this.modelo.addRow(datosFila);
        
        // Agregamos MUCHOS mas datos
        for(int x=0; x < 3; x++) {
            datosFila[0] = "Datos " + x + ", 0";
            datosFila[1] =  "Datos " + x + ", 1";
            datosFila[2] = "Datos " + x + ", 2";
            datosFila[3] = "Datos " + x + ", 3";
            
            this.modelo.addRow(datosFila);
        }
	}
	
	public void agregarDatos(ArrayList<Integer>[] vuelosPistas,  ArrayList<Double>[] tlasVuelos) {
        String[] columnas = {"Pista 1", "TLAs pista 1", "Pista 2", "TLAs pista 2", "Pista 3", "TLAs pista 3"};
		//this.modelo.setColumnIdentifiers(columnas);
		
		this.tabla.setModel(modelo);
		//this.modelo.setColumnCount(6);
		//Preparamos tantas columnas como pistas haya 
        for(int i = 0; i< vuelosPistas.length; i++) {
        	this.modelo.addColumn("Pista "+i+" vuelos:", vuelosPistas[i].toArray());
        	this.modelo.addColumn("Pista "+i+" TLAs:", tlasVuelos[i].toArray());
        }
    }
	
	public JTable getTabla() {
		return this.tabla;
	}
	
	DefaultTableModel modelo=new DefaultTableModel();
	JTable tabla = new JTable();
}

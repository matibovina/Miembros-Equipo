package main;

import java.sql.SQLException;

import javax.swing.JDialog;

import graphicInterface.Altas;

import modelo.DAOMiembro;
import modelo.Miembro;

public class Inicio {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Altas window = new Altas(); // Crea instancia de la vista y la hace visible.
		window.setVisible(true);
	}

}

package modelo;

import java.sql.SQLException;
import java.util.ArrayList;

public class Miembro {
	// Atributos de miembro
	private int ID;
	private String nombre;
	private int edad;
	private String cargo;

	public Miembro() {
	}

	public Miembro(int ID, String nombre, int edad, String cargo) {
		super();
		this.ID = ID;
		this.nombre = nombre;
		this.edad = edad;
		this.cargo = cargo;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	// Metodos CRUD que llaman a los metodos del DAO.
	public void insert() throws SQLException {
		DAOMiembro.getInstance().insert(this);
	}

	public void delete(int id) throws SQLException {
		DAOMiembro.getInstance().delete(id);
	}

	public void update() throws SQLException {
		DAOMiembro.getInstance().update(this);
	}

	public ArrayList<Miembro> obtenerLista() throws SQLException {

		ArrayList<Miembro> listaMiembros = DAOMiembro.getInstance().obtenerLista_DAO();
		return listaMiembros;
	}

	public int cantidadMiembros() throws SQLException {

		int cantidadMiembros = DAOMiembro.getInstance().cantidadMiembros();
		return cantidadMiembros;
	}

	public float mediaEdad() throws SQLException {
		float promedio = DAOMiembro.getInstance().promedioEdades();
		return promedio;
	}

	public ArrayList<Miembro> buscarMiembros(String cargo) throws SQLException {
		return DAOMiembro.getInstance().buscarPorCargo(cargo);
	}

}

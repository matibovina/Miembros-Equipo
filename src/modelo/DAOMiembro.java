package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.DBConnection;

public class DAOMiembro {
	// Clase DAO con patron singleton. Se crea una instancia estatica de DAOMiembro
	// con la conexion a la BD.
	private Connection conn = null;
	private static DAOMiembro instance = null;

	public DAOMiembro() throws SQLException {
		// TODO Auto-generated constructor stub
		conn = DBConnection.getConnection();
	}

	public static DAOMiembro getInstance() throws SQLException {
		if (instance == null)
			instance = new DAOMiembro();
		return instance;
	}

	// Metodos del CRUD / Incluye buscador por cargo y por ID.
	public void insert(Miembro miembro) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("INSERT INTO miembros_equipo.miembros (nombre, edad, cargo) VALUES (?, ?, ?)");
		ps.setString(1, miembro.getNombre());
		ps.setInt(2, miembro.getEdad());
		ps.setString(3, miembro.getCargo());

		ps.executeUpdate();
		ps.close();

	}

	public void update(Miembro miembro) throws SQLException {
		PreparedStatement ps = conn
				.prepareStatement("UPDATE miembros_equipo.miembros SET nombre = ?, edad = ?, cargo = ? WHERE (id = ?)");

		ps.setString(1, miembro.getNombre());
		ps.setInt(2, miembro.getEdad());
		ps.setString(3, miembro.getCargo());
		ps.setInt(4, miembro.getID());

		ps.executeUpdate();
		ps.close();
	}

	public void delete(int id) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("DELETE FROM miembros_equipo.miembros WHERE (id = ?)");
		ps.setInt(1, id);
		ps.executeUpdate();
		ps.close();
	}

	public ArrayList<Miembro> obtenerLista_DAO() throws SQLException {

		PreparedStatement ps = conn.prepareStatement("SELECT * FROM miembros_equipo.miembros");
		ResultSet result = ps.executeQuery();

		ArrayList<Miembro> listaMiembros = null;

		while (result.next()) {
			if (listaMiembros == null) {
				listaMiembros = new ArrayList<Miembro>();
			}
			listaMiembros.add(new Miembro(result.getInt("id"), result.getString("nombre"), result.getInt("edad"),
					result.getString("cargo")));
		}
		result.close();
		ps.close();

		return listaMiembros;

	}

	public Miembro traerPorId(int id) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM miembros_equipo.miembros WHERE (id = ?)");
		ps.setInt(1, id);
		ResultSet result = ps.executeQuery();

		Miembro m1 = null;

		if (result.next()) {
			m1 = new Miembro(result.getInt("id"), result.getString("nombre"), result.getInt("edad"),
					result.getString("cargo"));
		}
		result.close();
		ps.close();
		return m1;
	}

	public ArrayList<Miembro> buscarPorCargo(String cargo) throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM miembros_equipo.miembros WHERE cargo = ?");
		ps.setString(1, cargo);
		ResultSet result = ps.executeQuery();

		ArrayList<Miembro> listaCargoMiembros = null;

					
						while (result.next()) {

							if (listaCargoMiembros == null) {
								listaCargoMiembros = new ArrayList<Miembro>();

							}
							listaCargoMiembros.add(new Miembro(result.getInt("id"), result.getString("nombre"), result.getInt("edad"),
									result.getString("cargo")));

						}		
						
					 if(listaCargoMiembros == null) {
						listaCargoMiembros = new ArrayList<Miembro>();
					}
				



		result.close();
		ps.close();
	
		return listaCargoMiembros;
	}

	// metodo para contar la cantidad de miembros existentes.
	public int cantidadMiembros() throws SQLException {
		int cantidadMiembros = 0;
		PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM miembros_equipo.miembros");
		ResultSet result = ps.executeQuery();
		if (result.next()) {
			cantidadMiembros = Integer.parseInt(result.getString(1));
		}

		return cantidadMiembros;
	}

	// Metodo para calcular la edad de los miembros existentes en la base de datos.
	public float promedioEdades() throws SQLException {
		PreparedStatement ps = conn.prepareStatement("SELECT AVG(edad) FROM miembros_equipo.miembros");
		float promedio = 0;
		ResultSet result = ps.executeQuery();

		if (result.next()) {
			promedio = Float.parseFloat(result.getString(1));
		}
		promedio = Math.round(promedio * 100f) / 100f;

		return promedio;
	}
}

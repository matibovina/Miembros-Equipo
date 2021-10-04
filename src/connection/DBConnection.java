package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
	// Creacion de la conexion a la BD.

	private static final String JDBC_url = "jdbc:mysql://localhost:3306";

	private static Connection instance = null;

	public static Connection getConnection() throws SQLException {

		if (instance == null) {
			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "Aporapipe.5859");

			instance = DriverManager.getConnection(JDBC_url, props);
		}

		return instance;

	}

}

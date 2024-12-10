package transversal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionPostgre {

	private static final String URL= "jdbc:postgresql://localhost:5432/ventas";
	private static final String USER="postgres";
	private static final String PASSWORD="Adivinala1.";
	public Connection conexion = null;
	
	public Connection conectar() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR.Controlador no encontrado");
			e.printStackTrace();
		}
		try {
			conexion= DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (SQLException e) {
			System.out.println("Error al conectar con la base de datos");
			e.printStackTrace();
		}
		if(conexion != null) {
			System.out.println("Conexion OK");
		}
		
		return conexion;
		
		
		
	}	
}

package transversal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private static final String URL = "jdbc:mysql://localhost:3306/BDEJERCICIO1?serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "Adivinala1."; 
	public Connection conexion = null;
	
	public Connection conectar() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			conexion= DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(conexion != null) {
			System.out.println("Conexion OK");
		}
		
		return conexion;
		
		
		
	}
	
}

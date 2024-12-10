package controladores;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import modelos.Usuario;
import transversal.Conexion;

public class ControllerCSV {
	
	public static void crearCSV() {
		String cadSQL="";
		Usuario usuario= new Usuario();
		Conexion bd = new Conexion();
		ResultSet resultado = null;
		Statement sentencia = null;
		cadSQL="select * from usuarios;";
		Connection con = bd.conectar(); 
        System.out.println(con);
        
	    String fichero="usuarios.csv";
	    
	   // boolean existe = new File(fichero).exists();
	    
	    try {
	    	CsvWriter t2 = new CsvWriter(new FileWriter(fichero),',');
//			if (!existe){
				t2.write("IDUSUARIO");
				t2.write("NOMBRE");
				t2.write("APELLIDO1");
				t2.write("APELLIDO2");
				t2.write("DNI");
	    		t2.endRecord();
//			}
				
			
	    	sentencia= con.createStatement();
	    	resultado= sentencia.executeQuery(cadSQL);
	    	
	    			
	    	while(resultado.next()) {
	    		int idusuario = resultado.getInt("idusuario");
	    		String nombre = resultado.getString("nombre");
	    		String apellido1 = resultado.getString("apellido1");
	    		String apellido2 = resultado.getString("apellido2");
	    		String dni = resultado.getString("dni");
	    			    			
	    		String fila="";
	    		fila=""+idusuario+"\t"+nombre+"\t"+apellido1+"\t"+apellido2+"\t"+dni;
	    		System.out.println(fila);	
	    		
	    		t2.write(""+idusuario);
	    		t2.write(""+nombre);
	    		t2.write(""+apellido1);
	    		t2.write(""+apellido2);
	    		t2.write(""+dni);
	    		t2.endRecord();
	    	}//while
	    		
	    	t2.close();
			System.out.println("CSV generado");	
			
	    	}catch (SQLException | IOException e) {
	    		e.printStackTrace();
	    	}			  			    	
	}//fin CrearCSV
	
	

	
	public static void leerCSV(){
		
		String fichero="usuarios.csv";
		
    	try {
    		boolean existe = new File(fichero).exists();
    		if (!existe) {
    			System.out.println("El archivo CSV no existe");
    			return;
    		}
    		
    		CsvReader t1 = new CsvReader(new FileReader(fichero),',');
    		t1.readHeaders();
    		    		
     		//ZONA DE TECNOLOGÍA JDBC
    		//String cadSQL ="";
    		Conexion bd = new Conexion();
    		Connection con = bd.conectar(); 
//    		ResultSet resultado = null;
//    		Statement sentencia = null;
    		PreparedStatement sentenciaP = null;   		   		
    		
    		try {
				//sentencia= con.createStatement();
    		 
     		//String idusuario;
			String nombre;
    		String apellido1;
    		String apellido2;
    		String dni;
    				   		
    		//leer CSV registro por registro
    		while(t1.readRecord()) {
    			//idusuario = t1.get("idusuario");
    			nombre = t1.get("NOMBRE");
    			apellido1 = t1.get("APELLIDO1");
    			apellido2 = t1.get("APELLIDO2");
    			dni = t1.get("DNI");
    			
    			// Imprimir cada fila del CSV para verificar que se ha leído correctamente
                System.out.println("Leyendo registro: " + nombre + " " + apellido1 + " " + apellido2 + " " + dni);		               
                
                // No inserta filas en blanco
//                if (nombre == null || apellido1 == null || apellido2 == null || dni == null ||
//                        nombre.isEmpty() || apellido1.isEmpty() || apellido2.isEmpty() || dni.isEmpty()) {
//                        System.out.println("Registro vacío o incompleto. No se insertará.");
//                        continue; // Saltar el registro si alguno de los campos está vacío
//                }
//    			
    			// Crear la consulta de inserción usando PreparedStatement
//    			cadSQL="insert into usuarios (nombre,apellido1,apellido2,dni)"
//    				+"values(?,?,?,?);";
    			 
//    			sentenciaP = con.prepareStatement(cadSQL);
//						sentenciaP.setString(1, nombre);
//						sentenciaP.setString(2, apellido1);
//						sentenciaP.setString(3, apellido2);
//						sentenciaP.setString(4, dni);
//    			
				// Ejecutar la inserción y verificar el resultado
//				if (sentenciaP.executeUpdate() > 0) {
//				    System.out.println("Inserción Exitosa: " + nombre + " " + apellido1 + " " + apellido2);
//				} else {
//				    System.out.println("Inserción Fallida para: " + nombre + " " + apellido1 + " " + apellido2);
//				}
    		}
    	}
    		
//    	} catch (SQLException e) {
//    		e.printStackTrace();
//    	}
    	finally {
        	 if (sentenciaP != null) {
                 try {
                     sentenciaP.close();
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             }
        	 if (con != null) {
        	     try {
        	         con.close();
        	     } catch (SQLException e) {
        	         e.printStackTrace();
        	     }
        	 }
        }
        // Cerrar el CsvReader
        t1.close();
        System.out.println("Lectura del CSV "+fichero+" finalizada"); 
        
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }	
	}
}

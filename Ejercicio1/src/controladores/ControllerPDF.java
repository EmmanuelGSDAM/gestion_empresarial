package controladores;

import java.awt.Color;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import transversal.Conexion;



public class ControllerPDF {
	
	public static List<String> obtenerCentros() {
        String cadSQL = "";
        Conexion bd = new Conexion();
		Connection con = bd.conectar();
        ResultSet resultado = null;
        Statement sentencia = null;
        List<String> listaUsuarios = new ArrayList<>();
        try {
            cadSQL = "SELECT * FROM usuarios;";
            sentencia = con.createStatement();
            resultado = sentencia.executeQuery(cadSQL);
            
            while (resultado.next()) {
            	int idUsuario = resultado.getInt("IDUSUARIO");
            	String nombre = resultado.getString("NOMBRE");
            	String apellido1 = resultado.getString("APELLIDO1");
            	String apellido2 = resultado.getString("APELLIDO2");
            	String dni = resultado.getString("DNI");

            	// Imprimir los resultados.
            	listaUsuarios.add("   " + idUsuario + "     " + nombre + "     " + apellido1 + "     " + apellido2 + "     " + dni);
           }
        }catch (SQLException e) {
        	System.out.println(e.getMessage());
        }finally {
        	if (sentencia!=null) {
        		try {
        			sentencia.close();
        			resultado.close();
        			bd.conexion.close();
        		} catch (SQLException e) {
        			e.printStackTrace();
        		}
        	}
        }
        return listaUsuarios;
	}

	
	
	
	public static void crearPDF() {
		int pdfs = 600;
		PDDocument doc = null;
		try {
			doc = new PDDocument();
			PDPage page = new PDPage();
	        doc.addPage( page );
		    PDFont font = PDType1Font.COURIER_BOLD_OBLIQUE;
		    Color color = Color.blue; 
		    PDPageContentStream contentStream = null;
		    
		    try {
				contentStream = new PDPageContentStream(doc, page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		    
		    try {
			
		    	contentStream.beginText();
		        contentStream.setFont( font, 16 );
		        contentStream.setNonStrokingColor(color);
		        contentStream.moveTextPositionByAmount( 160, 700 );
		        contentStream.drawString( "------LISTADO DE USUARIOS------" );
		        contentStream.endText();
		            
		        contentStream.beginText();
		        contentStream.setFont( font, 12 );
		        contentStream.setNonStrokingColor(Color.GREEN);
		        contentStream.moveTextPositionByAmount( 75, 650 );
		        contentStream.drawString( "ID USUARIO - NOMBRE - APELLIDO1 - APELLIDO2 - DNI" );
		        contentStream.endText();
		            
		        List<String> listaUsuarios = obtenerCentros();
		        for (int i = 0; i < listaUsuarios.size(); i++) {
		        	contentStream.beginText();
		       		contentStream.setFont( font, 12 );
		       		contentStream.setNonStrokingColor(Color.magenta);
		       		contentStream.moveTextPositionByAmount(75, pdfs);
		       		contentStream.drawString(listaUsuarios.get(i));
		        	pdfs=pdfs-30;
		        	contentStream.endText();
			    }
			    
		        contentStream.close();
		        
		        String homeDirectory = System.getProperty("user.home");
		        doc.save(homeDirectory + "\\Documents\\prueba.pdf");

		        //doc.save( "C:\\Users\\Emmanuel Grande" );
		        
		        System.out.println("El pdf se ha generado correctamente en la ruta 'C:\\Users\\Emmanuel Grande\\Documents\\prueba.pdf'");
			        
		    } catch (IOException e) {
				e.printStackTrace();
		    }
		    
		    }finally {
		    	if( doc != null ) {
		    		try {
					doc.close();
		    		} catch (IOException e) {
					e.printStackTrace();
		    		}
		    	}
		    }
			
	}//FIN crearPDF
	
//	public static void verPDF() {
//		 String pdfPath = System.getProperty("user.home") + "\\Documents\\prueba.pdf"; // Cambia a la ruta correcta si es necesario
//
//	     SwingUtilities.invokeLater(() -> {
//	        // Crear y mostrar el VisorPDF
//	        VisorPDF viewer = new VisorPDF(pdfPath);
//	        viewer.setVisible(true);
//	     });
//	}

	
}

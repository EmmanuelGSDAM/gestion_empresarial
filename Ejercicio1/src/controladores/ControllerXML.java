package controladores;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

import com.csvreader.CsvReader;

public class ControllerXML {

	public static void crearXML() {
		String origen="usuarios.csv";
		
		try {
			boolean existe = new File(origen).exists();
			
			if(!existe) {
				System.out.println("Fin del proceso - El archivo no existe");
				return;
			}
			
			String destino="usuarios.xml";
			existe = new File(destino).exists();
			
			if(!existe) {
				System.out.println("Es la primera vez  que se genera el archivo usuarios.xml");
			} else {
				System.out.println("Añadiendo usuarios al archivo xml");
			}
			
			//lectura
			CsvReader t1 = new CsvReader(origen);
			t1.readHeaders();
			
			FileWriter fp = null;
			PrintWriter pw = null;
			fp = new FileWriter(destino);
			pw = new PrintWriter(fp);
			pw.println("<?xml version=\"1.0\"?>");
			pw.println("<usuarios>");
			
			while(t1.readRecord()) { //mientras pueda leer datos en la fila
				//leemos en cada interaccion
				String idUsuario = t1.get("IDUSUARIO");
				String nombre = t1.get("NOMBRE");
				String apellido1 = t1.get("APELLIDO1");
				String apellido2 = t1.get("APELLIDO2");
				String dni = t1.get("DNI");
				System.out.println(" IDUSUARIO--"+idUsuario+" NOMBRE--"+nombre+" APELLIDO1--"+apellido1+" APELLIDO2--"+apellido2+" DNI--"+dni);
				
				//escritura
				pw.println("<usuario>");
				pw.println("<idUsuario>"+idUsuario+"</idUsuario>");
				pw.println("<nombre>"+nombre+"</nombre>");
				pw.println("<apellido1>"+apellido1+"</apellido1>");
				pw.println("<apellido2>"+apellido2+"</apellido2>");
				pw.println("<dni>"+dni+"</dni>");
				pw.println("</usuario>");
			}
			
			pw.println("</usuarios>");
			t1.close();
			pw.close();
			fp.close();
			
		}catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();	
		}
	}//FIN crearXML
	
	public static void leerXML() {
		//Se crea un SAXBuilder para poder  parsear el archivo
		SAXBuilder builder = new SAXBuilder();
		File xmlFile = new File("usuarios.xml");
		
		try {
			//Se crea el nuevo documento a traves del archivo
			Document document = (Document) builder.build( xmlFile );
			//Se obtiene la  raiz 'tables' usuarios
			Element rootNode = document.getRootElement();
			//Se obtiene la lista  de hijos de la raiz 'tables'
			List list = rootNode.getChildren( "usuario" ); //tabla ahora usuario
			
			System.out.println("\tIDUSUARIO\tNOMBRE\tAPELLIDO1\tAPELLIDO2\tDNI");
			
			for(int i=0; i < list.size(); i++) {
				//Se obtiene el elemento 'tabla' ahora usuario
				Element tabla = (Element) list.get(i);
				//Se obtiene lista de hijos el tag 'tabla' ahora usuario
				List lista_campos = tabla.getChildren();
				String idUsuario = tabla.getChildText("idUsuario");
				String nombre = tabla.getChildText("nombre");
				String apellido1 = tabla.getChildText("apellido1");
				String apellido2 = tabla.getChildText("apellido2");
				String dni = tabla.getChildText("dni");
				
				System.out.println("\t"+idUsuario+"\t"+nombre+"\t"+apellido1+"\t"+apellido2+"\t"+dni);
			}
		}catch(IOException io) {
			System.out.println(io.getMessage());
		}catch(JDOMException jdomex) {
			System.out.println(jdomex.getMessage());
		}
	}
}

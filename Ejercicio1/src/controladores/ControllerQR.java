package controladores;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import modelos.Usuario;
import transversal.Conexion;
import vistas.ViewUsuarios;
import vistas.VisorQR;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class ControllerQR {

	public static void generarQR(Usuario usuario) {
		String cadSQL="";
		ControllerUsuario2 cu = new ControllerUsuario2();
		Conexion bd = new Conexion();
		Connection con = bd.conectar();
		Statement sentencia = null;
		
	    if (con != null) {
            try {
				sentencia = con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
	    }    
        
	    String dniModificar = ViewUsuarios.pedirDni();
	    Usuario usuarioModificado = ControllerUsuario2.buscar(dniModificar);

		String pathname =System.getProperty("user.home") + "\\Documents\\"+usuario.getNombre()+"_"+usuario.getDni()+".png";
		String textToQr = "NOMBRE \t APELLIDOS \t DNI \n"
				+usuario.getNombre()+" "
				+usuario.getApellido1()+" "
				+usuario.getApellido2()+" "
				+usuario.getDni()+" :)";
		
		try {
			writeQR(textToQr, pathname);
			System.out.println("Código QR generado con éxito \n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}//FIN generarQR
	
	public static void writeQR(String text, String pathname) throws WriterException, IOException {
		int width= 600;
		int heigh= 400;
		
		String imageFormat="png";
		
		BitMatrix bitMatrix= new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE,width, heigh);
		
		FileOutputStream outputStream = new FileOutputStream(new File(pathname));
		
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, outputStream);
	}//FIN writeQR
	
	public static void verQR() {
		Scanner sc =new Scanner(System.in);
		
		String nombre="";
		String dni="";
		System.out.println("Introduzca el nombre quiere ver:  ");
		nombre = sc.next();
		System.out.println("Introduzca también el dni: ");
		dni = sc.next();  

		String pathname = System.getProperty("user.home") + "\\Documents\\"+ nombre + "_" + dni + ".png";
		File archivoQR = new File(pathname);

		SwingUtilities.invokeLater(() -> {
		    packQR.VisorQR viewerQR;
			try {
				viewerQR = new packQR.VisorQR(pathname);
				viewerQR.setVisible(true);
				
			} catch (WriterException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}//FIN verQR
}

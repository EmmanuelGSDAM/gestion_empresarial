package vistas;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.google.zxing.WriterException;

public class VisorQR extends JFrame{

	public VisorQR(String pathname) throws WriterException, IOException {
		
		//Cargar imagen
		BufferedImage qrImage = ImageIO.read(new File (pathname));
		
		//Configurar el JFrame
		setTitle("Visor de Código QR");
		setSize(600,400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		//Crear panel para mostrar la imagen
		JLabel label = new JLabel( new ImageIcon(qrImage));
		add(label);
		
		//Hacer visible el JFrame
		setVisible(true);
	}
}

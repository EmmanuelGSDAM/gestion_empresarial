package vistas;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

public class VisorPDF  extends JFrame{

	private PDDocument document;
	private PDFRenderer pdfRenderer;
	private int currentPage =0;
	
	
	public VisorPDF(String pdfPath) {
		try {
			//Cargar documento
			document = PDDocument.load(new File(pdfPath));
			pdfRenderer = new PDFRenderer(document);
			
			setTitle("Visor de PDF");
			setSize(800,600);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			
			JPanel panel = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					try {
						g.drawImage(pdfRenderer.renderImageWithDPI(currentPage, 100), 0, 0, null);
					} catch(IOException e) {
						e.printStackTrace();
					}
				}
			};
			
			add(panel, BorderLayout.CENTER);
			
			//Controles de navegación
			JPanel controls = new  JPanel();
			JButton prevButton = new JButton("Anterior");
			JButton nextButton = new JButton("Siguiente");
			
			prevButton.addActionListener((ActionEvent e) -> {
				if(currentPage > 0) {
					currentPage--;
					panel.repaint();
				}
			});
			
			nextButton.addActionListener((ActionEvent e) -> {
				if(currentPage < document.getNumberOfPages() -1) {
					currentPage++;
					panel.repaint();
				}
			});
			
			controls.add(prevButton);
			controls.add(nextButton);
			add(controls, BorderLayout.SOUTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public static void main(String[] args) {
//		SwingUtilities.invokeLater(() -> {
//			//Cambia esto por la ruta correcta de tu archivo PDF
//			String pdfPath = "C:\\Users\\Emmanuel Grande\\Documents\\prueba.pdf";
//			VisorPDF viewer = new VisorPDF(pdfPath);
//			viewer.setVisible(true);
//		});
//	}

}


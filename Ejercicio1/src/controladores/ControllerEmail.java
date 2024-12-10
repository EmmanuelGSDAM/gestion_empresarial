package controladores;

import java.awt.Panel;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

public class ControllerEmail {

	public void enviarEmail() {
		String email="emmanuelgrande@centroculturalsalmantino.com";
		String correoEnvia = "3lp3p3elpepe@gmail.com";
		String contraseña = "gkjs cgnn oodg qyos";
		String receptor = email;
		String asunto = "CORREO ACCDD";
		String mensaje = "Hola mundo, prueba de envio de correo electrónico";
		
		System.out.println("Preparando Configuración");
		
		Properties propiedad = new Properties();
		
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable","true");
		propiedad.setProperty("mail.smtp.port","587");
		propiedad.setProperty("mail.smtp.auth","true");
		propiedad.setProperty("mail.smtp.user", correoEnvia);
		propiedad.setProperty("mail.smtp.password", contraseña);
		propiedad.setProperty("mail.transport.protocol","smtp");
		propiedad.setProperty("mail.smtp.socketFactory	.class","javax.net.ssl.SSLSocketFactory");	
		
		Session sesion = Session.getInstance(propiedad, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new  PasswordAuthentication(correoEnvia, contraseña);
			}
		});
		
		System.out.println("Configuración OK");
		System.out.println("Sesión OK");
		
		MimeMessage mail = new MimeMessage(sesion);
		try {
			mail.setFrom(new InternetAddress(correoEnvia));
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));
			mail.setSubject(asunto);
			mail.setText(mensaje);
			System.out.println("adjuntando..........................");
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart attachPart = new MimeBodyPart();
			String attachFile = "c:\\Users\\Emmanuel Grande\\Documents\\Marcos_56723456M.png";//TODO pepe
			
			try {
				attachPart.attachFile(attachFile);
			}catch (IOException e) {
				e.printStackTrace();
				System.out.println("ERROR");
				return;
			}
			
			multipart.addBodyPart(attachPart);
			mail.setContent(multipart);
			System.out.println("archivo adjunto preparado......................");
			
			System.out.println("Enviando........................");
			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contraseña);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
			transportar.close();
			
			JOptionPane.showMessageDialog(null, "Listo, revise su correo");
			
		} catch(AddressException ex) {
			Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
		} catch(MessagingException ex) {
			Logger.getLogger(Panel.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}
}

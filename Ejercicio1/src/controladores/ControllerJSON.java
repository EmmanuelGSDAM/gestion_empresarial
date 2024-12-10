package controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.google.gson.Gson;

import modelos.Usuario;

public class ControllerJSON  extends JFrame{
	private JButton generarJsonButton;

    public ControllerJSON() {
        setTitle("Generar archivo JSON de Usuarios");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        generarJsonButton = new JButton("Generar JSON");
        generarJsonButton.setBounds(50, 50, 200, 40);
        add(generarJsonButton);
        
        generarJsonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Usuario> usuarios = obtenerUsuarios();
                if (usuarios != null) {
                    generarJson(usuarios);
                }
            }
        });
    }

    List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/BDEJERCICIO1?serverTimezone=UTC";
        String user = "root";
        String password = "Adivinala1.";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            
        	Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM usuarios");

            while (rs.next()) {
                int id = rs.getInt("idUsuario");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido1");
                String apellido2 = rs.getString("apellido2");
                String dni = rs.getString("dni");
                usuarios.add(new Usuario(id, nombre, apellido1, apellido2, dni));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los datos de la base de datos", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return usuarios;
    }

    // Método para generar el archivo JSON
    public void generarJson(List<Usuario> usuarios) {
        Gson gson = new Gson(); // Usa Gson para convertir la lista de usuarios a JSON
        String json = gson.toJson(usuarios);

        try (FileWriter fileWriter = new FileWriter("usuarios.json")) {
            fileWriter.write(json);
            JOptionPane.showMessageDialog(this, "Archivo JSON generado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al escribir el archivo JSON", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void llamarJson() {
    	SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            new ControllerJSON().setVisible(true);
	        }
    	});
    }
}




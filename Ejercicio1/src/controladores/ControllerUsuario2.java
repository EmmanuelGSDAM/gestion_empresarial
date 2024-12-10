package controladores;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modelos.Usuario;
import transversal.Conexion;

public class ControllerUsuario2 {

    public static boolean insertar(Usuario usuario) {
        String cadSQL = "";
        Conexion bd = new Conexion();
        Connection conexion = bd.conectar();
        Statement sentencia = null;

        if (conexion != null) {
            try {
                sentencia = conexion.createStatement();
                cadSQL = "INSERT INTO usuarios(dni, nombre, apellido1, apellido2) VALUES ('" 
                        + usuario.getDni() + "','" 
                        + usuario.getNombre() + "','" 
                        + usuario.getApellido1() + "','" 
                        + usuario.getApellido2() + "');";

                return sentencia.executeUpdate(cadSQL) > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (sentencia != null) sentencia.close();
                    if (conexion != null) conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void listar() {
        String cadSQL = "SELECT * FROM usuarios WHERE borrado=FALSE;";
        Conexion bd = new Conexion();
        Connection conexion = bd.conectar();

        if (conexion != null) {
            try (Statement sentencia = conexion.createStatement();
                 ResultSet resultado = sentencia.executeQuery(cadSQL)) {

                System.out.println("ID \t NOMBRE \t\t APELLIDOS \t\t DNI \t\t ACTIVO ");
                while (resultado.next()) {
                    int idusuario = resultado.getInt("idusuario");
                    String nombre = resultado.getString("nombre");
                    String apellidos = resultado.getString("apellido1") + " " + resultado.getString("apellido2");
                    String dni = resultado.getString("dni");
                    boolean activo = resultado.getBoolean("activo");

                    System.out.println(idusuario + "\t" + nombre + "\t\t" + apellidos + "\t\t" + dni + "\t\t" + activo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean modificar(Usuario usuario) {
        String cadSQL = "UPDATE usuarios SET nombre='" + usuario.getNombre() + "', "
                + "apellido1='" + usuario.getApellido1() + "', "
                + "apellido2='" + usuario.getApellido2() + "' "
                + "WHERE dni='" + usuario.getDni() + "' AND activo=TRUE AND borrado=FALSE;";
        return ejecutarUpdate(cadSQL);
    }

    public boolean activarUsuario(String dni) {
        String cadSQL = "UPDATE usuarios SET activo = TRUE WHERE dni = '" + dni + "' AND borrado = FALSE;";
        return ejecutarUpdate(cadSQL);
    }

    public boolean desactivarUsuario(String dni) {
        String cadSQL = "UPDATE usuarios SET activo = FALSE WHERE dni = '" + dni + "' AND borrado = FALSE;";
        return ejecutarUpdate(cadSQL);
    }

    public static Usuario buscar(String dni) {
        String cadSQL = "SELECT * FROM usuarios WHERE dni='" + dni + "' AND borrado=FALSE;";
        Conexion bd = new Conexion();
        Connection conexion = bd.conectar();

        if (conexion != null) {
            try (Statement sentencia = conexion.createStatement();
                 ResultSet resultado = sentencia.executeQuery(cadSQL)) {

                if (resultado.next()) {
                    int idusuario = resultado.getInt("idusuario");
                    String nombre = resultado.getString("nombre");
                    String apellido1 = resultado.getString("apellido1");
                    String apellido2 = resultado.getString("apellido2");
                    return new Usuario(idusuario, nombre, apellido1, apellido2, dni);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static boolean eliminar(String dni) {
        String cadSQL = "UPDATE usuarios SET borrado=TRUE WHERE dni='" + dni + "';";
        return ejecutarUpdate(cadSQL);
    }

    public static boolean restaurar(String dni) {
        String cadSQL = "UPDATE usuarios SET borrado=FALSE WHERE dni='" + dni + "';";
        return ejecutarUpdate(cadSQL);
    }

    public static boolean restaurarTodos() {
        String cadSQL = "UPDATE usuarios SET borrado=FALSE WHERE borrado=TRUE;";
        return ejecutarUpdate(cadSQL);
    }

    private static boolean ejecutarUpdate(String cadSQL) {
        Conexion bd = new Conexion();
        Connection conexion = bd.conectar();

        if (conexion != null) {
            try (Statement sentencia = conexion.createStatement()) {
                return sentencia.executeUpdate(cadSQL) > 0;
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conexion != null) conexion.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}

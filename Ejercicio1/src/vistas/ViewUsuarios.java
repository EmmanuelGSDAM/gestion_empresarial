package vistas;

import modelos.Usuario;

import java.sql.SQLException;
import java.util.Scanner;

import controladores.ControllerUsuario2;

public class ViewUsuarios {
    private static final Scanner sc = new Scanner(System.in);

    public static Usuario pedirDatosUsuario() {
        System.out.println("Introduzca nombre: ");
        String nombre = sc.nextLine();

        System.out.println("Introduzca apellido 1: ");
        String apellido1 = sc.nextLine();

        System.out.println("Introduzca apellido 2: ");
        String apellido2 = sc.nextLine();

        System.out.println("Introduzca DNI: ");
        String dni = sc.nextLine();

        return new Usuario(nombre, apellido1, apellido2, dni);
    }

    public static String pedirDni() {
        System.out.println("Introduzca el DNI: ");
        return sc.nextLine();
    }

    public static void mostrarMenuModificar(Usuario usuario) {
        int opcion;
        ControllerUsuario2 cu = new ControllerUsuario2();
        System.out.println("=  GESTIÓN ERP BDEJERCICIO1      =");
        System.out.println("=  MENÚ GESTIÓN DEL PERSONAL     =");
        System.out.println("= 1. Cambiar datos de Usuario    =");
        System.out.println("= 2. Activar/desactivar          =");
        System.out.println("= 3. Atrás                       =");
        opcion = sc.nextInt();
        sc.nextLine(); 

        if (opcion == 1) {
            Usuario usuarioModificado = pedirDatosModificar(usuario);

            if (cu.modificar(usuarioModificado)) {
                System.out.println("Usuario modificado correctamente.");
            } else {
                System.err.println("Error al modificar el usuario.");
            }
        } else if (opcion == 2) {
        	System.out.println("¿Desea ACTIVAR o DESACTIVAR este usuario?");
            String accion = sc.nextLine(); 

            if (accion.equalsIgnoreCase("activar")) {
                if (cu.activarUsuario(usuario.getDni())) {
                    System.out.println("Usuario activado correctamente.");
                } else {
                    System.err.println("Error al activar el usuario.");
                }
            } else if (accion.equalsIgnoreCase("desactivar")) {
                if (cu.desactivarUsuario(usuario.getDni())) {
                    System.out.println("Usuario desactivado correctamente.");
                } else {
                    System.err.println("Error al desactivar el usuario.");
                }
            } else {
                System.err.println("ERROR. Por favor, escriba 'ACTIVAR' o 'DESACTIVAR'.");
            }
        } else if (opcion == 3) {
            System.out.println("VOLVIENDO AL MENÚ ANTERIOR...");
        } else {
            System.err.println("Opción no válida.");
        }
    }

    public static Usuario pedirDatosModificar(Usuario usuario) {
        System.out.println("Introduzca nombre (actual: " + usuario.getNombre() + "): ");
        String nombre = sc.nextLine();
        if (nombre.isEmpty()) nombre = usuario.getNombre();

        System.out.println("Introduzca apellido 1 (actual: " + usuario.getApellido1() + "): ");
        String apellido1 = sc.nextLine();
        if (apellido1.isEmpty()) apellido1 = usuario.getApellido1();

        System.out.println("Introduzca apellido 2 (actual: " + usuario.getApellido2() + "): ");
        String apellido2 = sc.nextLine();
        if (apellido2.isEmpty()) apellido2 = usuario.getApellido2();

        return new Usuario(usuario.getIdusuario(), nombre, apellido1, apellido2, usuario.getDni());
    }
}


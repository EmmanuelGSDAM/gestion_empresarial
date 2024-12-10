package vistas;

import java.util.Scanner;

import javax.swing.SwingUtilities;

//import controladores.ControllerUsuario;
import controladores.ControllerUsuario2;
import controladores.ControllerXML;
import controladores.ControllerCSV;
import controladores.ControllerEmail;
import controladores.ControllerGrafico;
import controladores.ControllerJSON;
import controladores.ControllerPDF;
import controladores.ControllerQR;
import modelos.Usuario;
import packPDF.VisorPDF;
import packQR.main1;

public class VEjercicio1 {

	public void menuPrincipal() {
		Scanner tc = new Scanner(System.in);
		int opcion=0;
		do {
			System.out.println("= GESTIÓN ERP BDEJERCICIO1 =");
			System.out.println("=      MENÚ PRINCIPAL      =");
			System.out.println("= 1. Gestion del personal  =");
			System.out.println("= 2. Modulos del ERP       =");
			System.out.println("= 3. Salir                 =");
			opcion = tc.nextInt();
			
			if(opcion==1) {
				System.out.println("");
				menuGestionPersonal();
			}
			else if(opcion==2) {
				System.out.println("");
				menuModulosERP();
			}
			else if(opcion==3) {
				System.out.println("SALIENDO...");
				System.gc();
				System.exit(1);
			}
			else {
				System.err.println("Opción no válida, por favor, eliga una de las opciones del 1 al 3");
			}
		} while (opcion !=3);
	}
	
	
	public void menuGestionPersonal() {
		Scanner tc = new Scanner(System.in);
		Usuario usuario = new Usuario();
		ControllerUsuario2 cu = new ControllerUsuario2();
		int opcion;
		do {
			System.out.println("=  GESTIÓN ERP BDEJERCICIO1      =");
			System.out.println("=  MENÚ GESTIÓN DEL PERSONAL     =");
			System.out.println("==================================");
			System.out.println("= 1. Insertar Usuario            =");
			System.out.println("= 2. Listar Usuarios             =");
			System.out.println("= 3. Consultar Usuario (DNI)     =");
			System.out.println("= 4. Modificar Usuario (DNI)     =");
			System.out.println("= 5. Eliminar Usuario            =");
			System.out.println("= 6. Restaurar Usuario           =");
			System.out.println("= 7. Restaurar todos los Usuarios=");
			System.out.println("= 8. Atras                       =");
			System.out.println("==================================");
			opcion = tc.nextInt();
			
			if(opcion==1) {
				System.out.println("");
				Usuario nuevoUsuario = ViewUsuarios.pedirDatosUsuario();
                if (cu.insertar(nuevoUsuario)) {
                    System.out.println("Usuario insertado correctamente.");
                } else {
                    System.err.println("Error al insertar el usuario.");
                }
			}
			else if(opcion==2) {
				System.out.println("");
				cu.listar();
			}
			else if(opcion==3) {
				System.out.println("");
				String dniConsulta = ViewUsuarios.pedirDni();
                Usuario usuarioConsultado = cu.buscar(dniConsulta);
                if (usuarioConsultado != null) {
                    System.out.println("Usuario encontrado: " + usuarioConsultado);
                } else {
                    System.err.println("Usuario no encontrado.");
                }
			}
			else if(opcion==4) {
				System.out.println("");
				String dniModificar = ViewUsuarios.pedirDni();
			    Usuario usuarioModificado = ControllerUsuario2.buscar(dniModificar);
			    
			    if (usuarioModificado == null) {
			        System.err.println("Usuario no encontrado con el DNI proporcionado.");
			        return;
			    }
			    ViewUsuarios.mostrarMenuModificar(usuarioModificado);
			}
			else if(opcion==5) {
				System.out.println("");
				String dniEliminar = ViewUsuarios.pedirDni();
                if (cu.eliminar(dniEliminar)) {
                    System.out.println("Usuario eliminado correctamente.");
                } else {
                    System.err.println("Error al eliminar el usuario.");
                }
			}
			else if(opcion==6) {
				System.out.println("");
				String dniRestaurar = ViewUsuarios.pedirDni();
                if (cu.restaurar(dniRestaurar)) {
                    System.out.println("Usuario restaurado correctamente.");
                } else {
                    System.err.println("Error al restaurar el usuario.");
                }
			}
			else if(opcion==7) {
				System.out.println("");
				if (cu.restaurarTodos()) {
                    System.out.println("Todos los usuarios han sido restaurados correctamente.");
                } else {
                    System.err.println("Error al restaurar los usuarios.");
                }
			}
			else if(opcion==8) {
				System.out.println("VOLVIENDO AL MENU PRINCIPAL...");
				return;
			}
		} while (opcion !=8);
	}
	
	public void menuModulosERP() {
		Scanner tc = new Scanner(System.in);
		int opcion=0;
		do {
		System.out.println("= GESTIÓN ERP BDEJERCICIO1 =");
		System.out.println("=    MENÚ DE MÓDULOS ERP   =");
		System.out.println("============================");
		System.out.println("= 1. Generar CSV           =");
		System.out.println("= 2. Leer CSV              =");
		System.out.println("= 3. Generar XML           =");
		System.out.println("= 4. leer XML              =");
		System.out.println("= 5. Generar PDF           =");
		System.out.println("= 6. leer PDF              =");
		System.out.println("= 7. Generar QR de usuario =");
		System.out.println("= 8. Ver QR de usuario     =");
		System.out.println("= 9. Enviar email          =");
		System.out.println("= 10. Generar gráfico      =");
		System.out.println("= 11. Generar Json         =");
		System.out.println("= 12. Leer Json            =");
		System.out.println("= 13. Atras                =");
		System.out.println("============================");
		opcion = tc.nextInt();

		if(opcion==1) {
		System.out.println("");
		ControllerCSV csv = new ControllerCSV();
		csv.crearCSV();
		return;
		}
		else if(opcion==2) {
		System.out.println("");
		System.out.println("Leyendo CSV...");
		SwingUtilities.invokeLater(() -> {
            packCSV.VisorCSV viewerCSV = new packCSV.VisorCSV();
            viewerCSV.setVisible(true);
        });
		}
		else if(opcion==3) {  
		System.out.println("");
		ControllerXML xml = new ControllerXML();
		xml.crearXML();
		return;
		}
		else if(opcion==4) {
		System.out.println("");
		System.out.println("Leyendo XML...");
		SwingUtilities.invokeLater(() -> {
            packXML.VisorXML viewerXML = new packXML.VisorXML();
            viewerXML.setVisible(true);
        });
		return;
		}
		else if(opcion==5) {
		System.out.println("");
		ControllerPDF pdf = new ControllerPDF();
		pdf.crearPDF();
		//return;
		}
		else if(opcion==6) {
		System.out.println("");
		System.out.println("Abriendo visor PDF...");
        String pdfPath = System.getProperty("user.home") + "\\Documents\\prueba.pdf";

        SwingUtilities.invokeLater(() -> {
            packPDF.VisorPDF viewer = new packPDF.VisorPDF(pdfPath);
            viewer.setVisible(true);
        });
		}
		else if(opcion==7) {
		System.out.println("");
		ControllerQR qr = new ControllerQR();
		Usuario usuario = new Usuario();
		qr.generarQR(usuario);
		
		}
		else if(opcion==8) {
		System.out.println("");
		System.out.println("Leyendo archivo JSON...");
		ControllerQR qr = new ControllerQR();
		Usuario usuario = new Usuario();
		qr.verQR();
		
		}
		else if(opcion==9) {
		System.out.println("");
		ControllerEmail em = new ControllerEmail();
		Usuario usuario = new Usuario();
		em.enviarEmail();
		}
		else if(opcion==10) {
		System.out.println("");
		ControllerGrafico cg = new ControllerGrafico();
		cg.crearGrafico();
		}
		else if(opcion==11) {
			ControllerJSON json = new ControllerJSON();
			json.llamarJson();
		}
		else if(opcion==12) {
			System.out.println("");
			System.out.println("Leyendo archivo JSON...");
			SwingUtilities.invokeLater(() -> {
	            packJSON.VisorJSON viewerJSON = new packJSON.VisorJSON();
	            viewerJSON.setVisible(true);
	        });
			return;
		}
		else if(opcion==13) {
		System.out.println("VOLVIENDO AL MENU PRINCIPAL...");
		return;
		}
		else {
		System.err.println("Opción no válida, por favor, eliga una de las opciones del 1 al 3");
		}
		} while (opcion !=13);
}



	
	
	
	
}//FIN CLASE

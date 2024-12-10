package vistas;

import java.util.Scanner;

import controladores.ControllerGrafico;

public class ViewGráfico {

	public int menuTipoDeGrafico() {
        Scanner sc = new Scanner(System.in);
        ControllerGrafico cg = new ControllerGrafico();

        System.out.println("============================");
        System.out.println("= GESTIÓN ERP BDEJERCICIO1 =");
        System.out.println("=  MENÚ TIPOS DE GRÁFICOS  =");
        System.out.println("============================");
        System.out.println("= 1. Gráfico Lineal        =");
        System.out.println("= 2. Gráfico de Barras     =");
        System.out.println("= 3. Gráfico de Tarta      =");
        System.out.println("= 4. Gráfico Lineal Azul   =");
        System.out.println("============================");
        System.out.println("\n Elija el gráfico que quiere ver (1-4)");
        int opcion = sc.nextInt();
        
        if (opcion < 1 || opcion > 4) {
            System.out.println("Opción no válida. Elige un número entre 1 y 4.");
            return menuTipoDeGrafico();
        }
        
        return opcion;
        
        
	}
}

package controladores;

import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.DateFormatSymbols;
import java.time.YearMonth;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import transversal.ConexionPostgre;
import vistas.ViewGráfico;

import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ControllerGrafico {

	public void crearGrafico() {
		Scanner sc = new Scanner(System.in);
		ViewGráfico vg = new ViewGráfico();
		int mes = 0;
		int anio = 0;
		int totalDias = 0;
		XYSeries series = new XYSeries("Ventas");
		
		System.out.println("Introduce mes: ");
		mes=sc.nextInt();
		System.out.println("Introduce año: ");
		anio=sc.nextInt();
		
        totalDias = diasMes(mes, anio);
        System.out.println("Dias en el mes: " + totalDias);
		
		int[] ventas = new int[totalDias];
		
		Random random = new Random();

        // Bucle para generar las ventas aleatorias
        for (int i = 0; i < ventas.length; i++) {
            //ventas[i] = random.nextInt(100); // Genera un número aleatorio entre 0 y 99
            ventas[i] = 50 + random.nextInt(151); // Genera un número aleatorio entre 50 y 200
            System.out.println("Venta del dia " + (i + 1) + ": " + ventas[i]);
            
          //Insertar en BBDD
            insertarVenta(anio,mes,i+1,ventas[i]);
        }
		
        // Introduccion de datos
		for (int i = 0; i < ventas.length; i++) {
			series.add((i+1),  ventas[i]);
		}//Se puede unir al bucle de arriba ^
		
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
		        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        
        JFreeChart chart=null;
        
        //TODO Hacer menu en el que elijas el estilo del grafico
        int opcion= vg.menuTipoDeGrafico();
        
        
        if(opcion == 1) {
            chart= graficoLineal(mes, anio, months, dataset);
		} else if(opcion == 2) {
	        chart = graficoBarras2D(mes, anio, months, dataset);
		} else if(opcion == 3) {
	        chart = graficoTarta(mes, anio, months, ventas);
		} else if(opcion == 4) {
			chart= graficoLineal(mes, anio, months, dataset);
	        lineaAzul(chart);

		} else {
			System.out.println("Error de entrada");
		}
//        chart= graficoLineal(mes, anio, months, dataset);
//        
//        chart = graficoBarras2D(mes, anio, months, dataset);
//        
//        chart = graficoTarta(mes, anio, months, ventas);
//        
//        //lineaAzul(chart);
        
        
        // Mostramos la grafica en pantalla
        ChartFrame frame = new ChartFrame("Grafico", chart); //titulo del frame
        frame.pack();
        frame.setVisible(true);
		
	}
	
	private static void insertarVenta(int anio, int mes, int dia, int monto) {
		ConexionPostgre bbdd = new ConexionPostgre();
		Connection conexion = bbdd.conectar();
		String cadSQL = "INSERT INTO ventas1 (fecha, monto) VALUES(?, ?)";
		
		try{
			PreparedStatement pstmt = conexion.prepareStatement(cadSQL);
			pstmt.setDate(1, java.sql.Date.valueOf(anio+"-"+mes+"-"+dia));
			pstmt.setInt(2, monto);
			pstmt.executeUpdate();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	private JFreeChart graficoTarta(int mes, int anio, String[] months,int[] ventas) {
		
		 // Crear conjunto de datos para el gráfico de tarta
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < ventas.length; i++) {
            dataset.setValue("Día " + (i + 1), ventas[i]); // Añadir ventas de cada día
        }
		
		
		JFreeChart chart;
		// Crear el gráfico de tarta
        chart = ChartFactory.createPieChart(
                "Ventas SOFTTEK " + months[mes-1] + " " + anio, // Título del gráfico
                (PieDataset) dataset, // Datos
                true, // Mostrar leyenda
                false, // Tooltips
                false // URLs
        );
		return chart;
	}

	private JFreeChart graficoBarras2D(int mes, int anio, String[] months, XYSeriesCollection dataset) {
		// Crear el gráfico de barras
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Ventas SOFTTEK " + months[mes-1] + " " + anio, // Título
                "Día", // Etiqueta Coordenada X
                false, // No utiliza un intervalo en el eje X
                "Cantidad", // Etiqueta Coordenada Y
                dataset, // Datos
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda
                false, // No usa tooltips
                false // No genera URLs
        );
        return chart;
	}

	private JFreeChart graficoLineal(int mes, int anio, String[] months, XYSeriesCollection dataset) {
		// (String title, String xAxisLabel, String yAxisLabel, XYDataset dataset, PlotOrientation orientation, boolean legend, boolean tooltips, boolean urls)
        //createXYLineChart se utiliza para crear gráficos de líneas y el resultado es un objeto JFreeChart, que representa el gráfico.
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Ventas SOFTTEK "+months[mes-1]+ " "+anio, // Titulo superior del gráfico con el mes en letra y el año en número
                "Dia", // Etiqueta Coordenada X son los dias del mes
                "Cantidad", // Etiqueta Coordenada Y. Representa las cantidades de ventas registradas para cada día
                dataset, // Datos, el conjunto de datos que se va a graficar
                PlotOrientation.VERTICAL,
                true, // Muestra la leyenda de los productos 
                false, //no muestra las pequeñas ventanas emergentes al pasar por un punto del gráfico
                true  //genera urls asociadas a los puntos para gráficos web interactivos
        );
        return chart;
	}

		
	
	private void lineaAzul(JFreeChart chart) {
		// Obtener el plot y cambiar el color de la línea a azul
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE); // Cambia el color de la serie 0 a azul
        plot.setRenderer(renderer);
	} 
	
	//controla también los años bisiestos
	public int diasMes(int mes, int anio) {
        YearMonth anyomes = YearMonth.of(anio, mes);
        return anyomes.lengthOfMonth();
    }
	
	
	
		

	
}

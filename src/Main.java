
import UImenu.Menu;
import market.admin.Checkout;
import market.admin.Client;
import market.SuperMarket;
import sim.Simulation;
import market.admin.Product;
import serializer.Serializer;
import util.Lista;
import util.MinHeap;
import util.generator.ProductoBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;


import serializer.Serializer;
import sim.Simulation;
import util.Lista;


public class Main {
    public static void main(String[] args) {
<<<<<<< HEAD
        Lista<String> plotear = new Lista<>();
        for (int i = 1; i < 15; i++) {

            Simulation simulation = new Simulation(i,15-i,2);
            try {
                simulation.simular();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            simulation.generarReportes();
            plotear.agregar(simulation.escribeLinea());
=======
        Menu menu = new Menu();
        //menu.principal();

            SuperMarket superMarket = new SuperMarket(11,12,15);
            /*
            superMarket.cargarProductos("productos.txt");
            superMarket.getAlmacen().agregarProducto(new Product(3,"pug", (float) 2.2));
            superMarket.getAlmacen().agregarProducto(new Product(2,"perro Salchicha", (float) 2.2));
            superMarket.getAlmacen().agregarProducto(new Product(1,"alimento para perro", (float) 2.2));
            for (int j = 0; j < 97; j++) {
                superMarket.formandoCliente();
            }

             */

            System.out.println("Estos son los productos con pocas existas");
            Serializer serializer = new Serializer();
            Date fecha = new Date();
            SimpleDateFormat sdt = new SimpleDateFormat(("dd_MM_yyyy(HH:mm:ss)"));
            String nombre = sdt.format(fecha);
            serializer.creaCarpeta("Tickets");
            serializer.creaCarpeta("ReportesDiarios");
            serializer.creaCarpeta("Faltantes");
            serializer.escribeTXT(superMarket, "ReportesDiarios"+ "/"+ "[reporte]"+nombre+".txt");
            serializer.escribeTXT(superMarket.reportePocasExistencias(), "Faltantes"+ "/"+ "[faltantes]"+nombre+".txt");
            serializer.escribeTXT(superMarket.getTickets(), "Tickets"+ "/"+ "[tickets]" +nombre+".txt");
            System.out.println("lleva ");

        ProductoBuilder productoBuilder = new ProductoBuilder();
        Lista<String> textos = new Lista<>();
        for (int i = 0; i < 10000; i++) {
            textos.agregar(String.valueOf(productoBuilder.next()));
        }
        serializer.escribeTXT(textos,"prodctosAlea.txt");
        /*
        Simulation simulation = new Simulation(7,8,150,500);
        simulation.simular();
        simulation.generarReportes();
        */
        Simulation simulation = new Simulation(7,8,150,2);
        try {
            simulation.simular();
        } catch (InterruptedException e) {
            e.printStackTrace();
>>>>>>> 2922d536a40aeaa2c130e9af49c0edbbebbdc877
        }
        Serializer serializer = new Serializer();
        serializer.escribeTXT(plotear, "datos.p");
    }
}

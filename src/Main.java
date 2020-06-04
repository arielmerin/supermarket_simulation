import UImenu.Menu;
import market.SuperMarket;
import market.admin.Client;
import serializer.Serializer;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        //menu.principal();
        for (int i = 0; i < 8800; i++) {

            SuperMarket superMarket = new SuperMarket(11,12,15);
            superMarket.cargarProductos("productos.txt");

            for (int j = 0; j < 97; j++) {
                superMarket.formandoCliente();
            }
            Serializer serializer = new Serializer();
            Date fecha = new Date();
            SimpleDateFormat sdt = new SimpleDateFormat(("dd_MM_yyyy(HH:mm:ss)"));
            String nombre = sdt.format(fecha);
            serializer.creaCarpeta("Tickets");
            serializer.creaCarpeta("ReportesDiarios");
            serializer.escribeTXT(superMarket, "ReportesDiarios"+ "/"+ "[reporte]"+nombre+".txt");
            serializer.escribeTXT(superMarket.getTickets(), "Tickets"+ "/"+ "[tickets]" +nombre+".txt");
            System.out.println("lleva "+i);
        }

    }
}

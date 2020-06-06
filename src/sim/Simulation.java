package sim;

import com.sun.java.accessibility.util.EventID;
import market.Simulable;
import market.SuperMarket;
import market.admin.Plotable;
import serializer.Serializer;
import util.Lista;
import util.generator.ProductoBuilder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import static util.Utilidades.random;
/**
 *
 */
public class Simulation implements Simulable, Plotable {

    private Lista<Plotable> plotables;
    private int cajas;
    private int clientes;
    private int veces;
    private SuperMarket costco;
    private int costo;
    private Serializer serializer;




    public Simulation(int cajas, int clientes, int veces){
        this.cajas = cajas;
        this.clientes = clientes;
        this.veces = veces;
        int rapidas = random(cajas);
        costco = new SuperMarket(rapidas + 2, cajas - rapidas, 100 );
        serializer = new Serializer();
    }


    @Override
    public double simular(int cajas, int clientes){
        generarProductosAleatorios(5000);
        cargarProductos("");
        System.out.println(costco.getAlmacen().getAlmacen());
        System.out.println(costco.reportePocasExistencias());
        for (int i = 0; i < 150; i++) {
            costco.formandoCliente();
        }
        generarReportes();
        return 0;
    }

    @Override
    public double promediar(int veces, int cajas, int clientes) {

        return 0;
    }

    @Override
    public String escribeLinea() {
        return clientes + "  " + costo ;
    }


    @Override
    public void guarda(BufferedWriter out) throws IOException {
        Iterator it = plotables.iterator();
        while (it.hasNext()){
            Plotable elem = (Plotable)it.next();
            out.write(elem.escribeLinea());
        }
    }

    public void generarReportes(){
        SimpleDateFormat sdt = new SimpleDateFormat(("dd_MM_yyyy(HH:mm:ss)"));
        String nombre = sdt.format(new Date());
        serializer.creaCarpeta("Reportes");
        serializer.creaCarpeta("Reportes/Tickets");
        serializer.creaCarpeta("Reportes/ReportesDiarios");
        serializer.creaCarpeta("Reportes/Faltantes");

        serializer.escribeTXT(costco, "Reportes/ReportesDiarios"+ "/"+ "[reporte]"+nombre+".txt");
        serializer.escribeTXT(costco.reportePocasExistencias(), "Reportes/Faltantes"+ "/"+ "[faltantes]"+nombre+".txt");
        serializer.escribeTXT(costco.getTickets(), "Reportes/Tickets"+ "/"+ "[tickets]" +nombre+".txt");
    }

    public void generarProductosAleatorios(int productos){
        ProductoBuilder productoBuilder = new ProductoBuilder();
        Lista<String> textos = new Lista<>();
        for (int i = 0; i < productos; i++) {
            textos.agregar(String.valueOf(productoBuilder.next()));
        }
        serializer.escribeTXT(textos,"productosGenerados.txt");
    }

    public void cargarProductos(String path){
        if (path == ""){
            costco.cargarProductos("productosGenerados.txt");
        }else {
            costco.cargarProductos(path);
        }
    }
}

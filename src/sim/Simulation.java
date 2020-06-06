package sim;

import market.SuperMarket;
import market.admin.Client;
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
 * <h1>Simulación</h1>
 * En esta clase se combinan todas la anteriores y en general se permite darle sentido a una simulación de compras en
 * un supermercado.
 * @autor Armando Aquino and Ariel Merino
 * @version 1.0
 */
public class Simulation implements Plotable {

    /**
     * lista de elementos que serán enviados para su almacenamiento en un .p y gnuplot pueda emplearlos para graficar
     */
    private Lista<Plotable> plotables;

    /**
     * Número de cajas que tendrá esta simulación
     */
    private int cajas;

    /**
     * Número de clientes pensados para la simulación.
     */
    private int clientes;

    /**
     * Número de veces que se ejecutará dicha simulación
     */
    private int veces;

    /**
     * El supermercado que servirá como base para obtener comportamiento de él.
     */
    private SuperMarket costco;


    private int promedio;

    /**
     * La cantidad de productos que generará el programa aleatoreamente
     */
    private int numProductos;

    private Lista<Client> clientesAtendidos;

    /**
     * Objeto que permitirá guardar archivos y serializarlos
     */
    private Serializer serializer;


    /**
     * Constructor principal de la clase para obtener el número de cajas rápidas, normales los clientes y el numero de veces
     * que se hizo el cálculo
     * @param cajasRapidas número de cajas que reciben clientes con menos de 20 artículos
     * @param cajasNormales Número de cajas que recibe clientes con más de 20 artículos
     * @param clientes El número de clientes que se pensará para el supermercado
     * @param veces Número de veces que se repite dicho cálculo para esta simulación
     */
    public Simulation(int cajasRapidas, int cajasNormales, int clientes, int productos, int veces){
        this.numProductos = productos;
        this.cajas = cajasNormales + cajasRapidas;
        this.clientes = clientes;
        this.veces = veces;
        int rapidas = random(cajas);
        costco = new SuperMarket(rapidas + 2, cajas - rapidas, 100 );
        serializer = new Serializer();
    }


    /**
     *
     * @return
     */
    public double simular(){
        generarProductosAleatorios(numProductos);
        cargarProductos("");

        for (int i = 0; i < clientes;) {
            if (costco.formandoCliente()){
                i++;
            }
        }
        System.out.println(costco.getCajas());

        return 0;
    }

    /**
     *
     * @param veces
     * @param cajas
     * @param clientes
     * @return
     */
    public double promediar(int veces, int cajas, int clientes) {

        return 0;
    }

    @Override
    public String escribeLinea() {
        return clientes + "  " + promedio;
    }


    @Override
    public void guarda(BufferedWriter out) throws IOException {
        Iterator it = plotables.iterator();
        while (it.hasNext()){
            Plotable elem = (Plotable)it.next();
            out.write(elem.escribeLinea());
        }
    }

    /**
     *
     */
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

    /**
     *
     * @param productos
     */
    public void generarProductosAleatorios(int productos){
        ProductoBuilder productoBuilder = new ProductoBuilder();
        Lista<String> textos = new Lista<>();
        for (int i = 0; i < productos; i++) {
            textos.agregar(String.valueOf(productoBuilder.next()));
        }
        serializer.escribeTXT(textos,"productosGenerados.txt");
    }

    /**
     *
     * @param path
     */
    public void cargarProductos(String path){
        if (path == ""){
            costco.cargarProductos("productosGenerados.txt");
        }else {
            costco.cargarProductos(path);
        }
    }
}

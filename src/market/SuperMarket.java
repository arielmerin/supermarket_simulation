package market;

import market.admin.*;
import util.Lista;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static util.Utilidades.ranInt;

/**
 *
 */
public class SuperMarket extends Thread implements Serializable {


    /**
     *
     */
    private final Wharehouse almacenPrincipal;

    /**
     *
     */
    private Lista<Client> tickets;

    /**
     *
     */
    private int clientes;

    /**
     *
     */
    private Lista<QuickCheckout> unifila;

    /**
     *
     * @return
     */
    public Lista<LargeCheckout> getCajas() {
        return cajas;
    }

    /**
     *
     */
    private Lista<LargeCheckout> cajas;

    /**
     *
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy - HH:mm:ss"));

    /**
     *
     */
    private Date fecha = new Date();

    /**
     * La creación de un supermercado, su apertura, está sujeta al número de cajas rápidas, el número de cajas normales
     * y el número de clientes
     * @param rapidas
     * @param normales
     * @param clientes
     */
    public SuperMarket(int rapidas, int normales, int clientes) {
        this.clientes = clientes;
        this.unifila = new Lista<>();
        this.cajas = new Lista<>();
        this.almacenPrincipal = new Wharehouse();
        for (int i = 0; i < rapidas; i++) {
            abreCaja(true);
        }
        for (int i = 0; i < normales; i++) {
            abreCaja(false);
        }

        this.tickets = new Lista<>();
    }

    public Lista<Client> getTickets() {
        return tickets;
    }

    public Wharehouse getAlmacen() {
        return almacenPrincipal;
    }

    public Lista<QuickCheckout> getUnifila() {
        return unifila;
    }

    /**
     * Proporciona el número total de ventas del supermercado entre ambos tipos de cajas
     * @return la suma de cada cantidad que se vendió
     */
    public double getTotalVentas() {
        double suma = 0;
        for (QuickCheckout cajaRapida: unifila) {
            suma += cajaRapida.calculaVentaTotal();
        }
        for (LargeCheckout cajaNormal: cajas){
            suma+= cajaNormal.calculaVentaTotal();
        }
        return Math.round(suma);
    }

    public void darAltaProducto(Product product) {
        almacenPrincipal.agregarProducto(product);
    }

    /**
     *
     * @param esRapida
     */
    public void abreCaja(boolean esRapida){
        if (esRapida){
            QuickCheckout quickCheckout = new QuickCheckout();
            unifila.agregarAlFinal(quickCheckout);
        }else {
            LargeCheckout largeCheckout = new LargeCheckout();
            cajas.agregar(largeCheckout);
        }
    }


    /**
     *
     */
    public void formandoCliente(){
        int aleatorio = ranInt(1,57);
        Client client = generaClienteAleatorio(aleatorio);
        if (client.getItems() <= 20){
            QuickCheckout cajara = unifila.getElemento(1);
            cajara.formarCliente(client);
            unifila.eliminar(cajara);
            unifila.agregar(cajara);
        }else {
            LargeCheckout caja = cajas.getElemento(1);
            caja.formarCliente(client);
            cajas.eliminar(caja);
            cajas.agregar(caja);
        }
    }

    /**
     *
     * @param client
     * @param id
     * @param cantidad
     */
    private void asignaProducto(Client client, int id, int cantidad){
        Product agregar = almacenPrincipal.getAlmacen().busquedaElemento(new Product(id));
        Product alCarrito = new Product(id);

        alCarrito.setUnits(cantidad);
        alCarrito.setName(agregar.getName());
        alCarrito.setPrice(agregar.getPrice());
        alCarrito.setTimes(cantidad);

        if (agregar.getUnits() >= cantidad){
            client.agregarAlCarrito(alCarrito);
            almacenPrincipal.modificarExistencias(-cantidad,id);
        }
    }


    /**
     * En este metodo se toma el path que se indica al principio para crear un flujo de lectura donde en cada linea que
     * encuentre dividira la linea en dos, la primera parte antes de que aparezca una coma y la segunda viene despues de
     * la coma, ademas esta pensado para que las lineas esten entre parentesis por lo que los elimina
     * @param ruta direccion del archivo a leer
     */
    public void cargarProductos(String ruta){
        try {
            Scanner input = new Scanner(new File(ruta));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] linea = line.split(",");
                int cantidad = Integer.parseInt(linea[0]);
                String nombre = linea[1];
                float presio = Float.parseFloat(linea[2]);
                Product nuevo = new Product(cantidad, nombre, presio);
                almacenPrincipal.agregarProducto(nuevo);
            }
            input.close();
        } catch (FileNotFoundException e){
            System.out.println("NO se encontro el archivo");
        } catch (Exception ex) {
            System.out.println("El documento no se pudo abrir, intente de nuevo");
        }
    }


    /**
     * Permite la creación de un cliente y la asignación aleatoria de un producto a través de un número porporcionado de
     * artículos
     * @param productosCliente número de productos que el cliente llevará en su canasta
     * @return Cliente construido
     */
    public Client generaClienteAleatorio(int productosCliente){
        Client client = new Client();
        for (int i = 0; i < productosCliente; i++) {
            int ran = ranInt(1, almacenPrincipal.getAlmacen().getTamanio());
            asignaProducto(client,ran,ran + 85);
        }
        tickets.agregar(client);
        return client;
    }


    /**
     *
     */
    public void inicializaCajas(){
        for (Checkout caja: cajas){
            caja.start();
        }
    }

    /**
     *
     */
    public void esperaCajas(){
        for (Checkout caja: cajas){
            try {
                caja.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param caja
     */
    public void dormirCaja(Checkout caja){
        caja.dormirCaja((long) (500 * Math.random()));
    }

    @Override
    public void run() {
        while (cajas.esVacia()){
            for (Checkout caja : cajas){
                caja.run();
            }
        }
    }

    @Override
    public String toString() {
        String now = formatter.format(fecha);
        return String.format(":::  SUPERMERCADO  :::\n\nFecha: %s\nTotal de ingresos: $%.2f\nCon las siguientes cajas: \n%s\n %s" +
                        "\n\n La caja que más clientes atendió fue: \n %s",
                now,getTotalVentas(), cajas, unifila, cajas.getElemento(2));
    }

    /**
     *
     * @return
     */
    public String reportePocasExistencias(){
        return "   :::    REPORTE DE POCAS EXISTENCIAS   :::\nFecha: " + formatter.format(fecha) +
                "\n\n" +
                "\nID   Cantidad   Nombre  Precio \n" +
                almacenPrincipal.pocasExistencias();
    }
}
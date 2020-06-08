package market;

import market.admin.*;
import util.Lista;
import util.MinHeap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimerTask;

import static util.Utilidades.random;

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
    private MinHeap<Checkout> unifila;

    /**
     *
     * @return
     */
    public MinHeap<Checkout> getCajas() {
        return cajas;
    }

    /**
     *
     */
    private MinHeap<Checkout> cajas;

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
        this.unifila = new MinHeap<>();
        this.cajas = new MinHeap<>();
        this.almacenPrincipal = new Wharehouse();
        for (int i = 0; i < rapidas; i++) {
            abreCaja(true);
        }
        for (int i = 0; i < normales; i++) {
            abreCaja(false);
        }

        this.tickets = new Lista<>();
    }

    /**
     *
     * @return
     */
    public Lista<Client> getTickets() {
        return tickets;
    }

    /**
     *
     * @return
     */
    public Wharehouse getAlmacen() {
        return almacenPrincipal;
    }

    /**
     *
     * @return
     */
    public MinHeap<Checkout> getUnifila() {
        return unifila;
    }

    /**
     * Proporciona el número total de ventas del supermercado entre ambos tipos de cajas
     * @return la suma de cada cantidad que se vendió
     */
    public double getTotalVentas() {
        double suma = 0;
        for (Checkout cajaRapida: unifila) {
            suma += cajaRapida.calculaVentaTotal();
        }
        for (Checkout cajaNormal: cajas){
            suma+= cajaNormal.calculaVentaTotal();
        }
        return Math.round(suma);
    }

    /**
     *
     * @param product
     */
    public void darAltaProducto(Product product) {
        almacenPrincipal.agregarProducto(product);
    }

    /**
     *
     * @param esRapida
     */
    public void abreCaja(boolean esRapida){
        if (esRapida){
            Checkout quickCheckout = new Checkout(true, 2);
            unifila.agrega(quickCheckout);
        }else {
            Checkout largeCheckout = new Checkout(false, 2);
            cajas.agrega(largeCheckout);
        }
    }

    /**
     * Genera un cliente aleatorio con un número aleatorio de elementos en su carrito
     * @return
     */
    public boolean formandoCliente(Client client){
        if (client.getItems() != 0){
            tickets.agregar(client);
            System.out.println(client);
            if (client.getItems() <= 20){
                Checkout cajara =  unifila.elimina();
                cajara.formarCliente(client);
                unifila.agrega(cajara);
            }else {
                Checkout caja = cajas.elimina();
                caja.formarCliente(client);
                cajas.agrega(caja);
            }
            return true;
        }
        return false;
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
            System.out.println("NO se encontro el archivo para cargar los productos");
        } catch (Exception ex) {
            System.out.println("El documento de los productos no se pudo abrir, intente de nuevo");
        }
    }


    /**
     * Permite la creación de un cliente y la asignación aleatoria de un producto a través de un número porporcionado de
     * artículos
     * @param productosCliente número de productos que el cliente llevará en su canasta
     * @return Cliente construido
     */
    private Client cargaCarritoCompras(int productosCliente){
        Client client = new Client();
        for (int i = 0; i < productosCliente; i++) {
            int ran = random(almacenPrincipal.getAlmacen().getTamanio() - 1);
            asignaProducto(client,ran+ 1,random(15) + 1);
        }
        return client;
    }


    public Client generaCliente(){
        return cargaCarritoCompras(random(10) + 18);
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
        while (cajas.esVacio()){
            for (Checkout caja : cajas){
                caja.dormirCaja(caja.getPorAtender() * caja.getTiempoAtiendePProdcuto());
                caja.run();
            }
        }
    }

    @Override
    public String toString() {
        String now = formatter.format(fecha);
        return String.format(":::  SUPERMERCADO  :::\n\nFecha: %s\nTotal de ingresos: $%.2f\nCon las siguientes cajas: \n%s\n %s" +
                        "\n\n La caja que más clientes atendió fue: \n %s",
                now,getTotalVentas(), cajas, unifila, cajas.elimina());
    }

    /**
     *
     * @return
     */
    public String reportePocasExistencias(){
        return "   :::    REPORTE DE POCAS EXISTENCIAS   :::\n    Fecha: " + formatter.format(fecha) +
                "\n\n" +
                " ----------------- QUEDAN POCOS ------------------" +
                "\n   ID    Cantidad         Nombre         Precio \n" +
                almacenPrincipal.pocasExistencias() +
                "\n ---------------- FALTAN ----------------------" +
                "\n   ID    Cantidad         Nombre         Precio \n" +
                almacenPrincipal.faltantes();
    }
}
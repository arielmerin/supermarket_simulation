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

import static util.Utilidades.random;

/**
 *<h1> Supermercado </h1>
 * Esta clase se encarga de darle todo el compportamiento de un super a los objetos que se empleen en las simulaciones
 * desde el número de cajas rápidas y un almaces hasta un conjunto de tickets diarios pues se considera la simulación
 * de un día
 */
public class SuperMarket  implements Serializable {


    /**
     *
     */
    private final Wharehouse almacenPrincipal;

    /**
     *
     */
    private int numClientesRapidos;


    /**
     *
     */
    private int numClientesLargos;

    /**
     *
     */
    private Lista<Client> tickets;


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
     */
    public SuperMarket(int rapidas, int normales) {
        this.unifila = new MinHeap<>();
        this.cajas = new MinHeap<>();
        this.almacenPrincipal = new Wharehouse();
        for (int i = 1; i <= rapidas; i++) {
            abreCaja(true);
        }
        for (int i = 1; i <= normales; i++) {
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
            Checkout quickCheckout = new Checkout(true);
            unifila.agrega(quickCheckout);
        }else {
            Checkout largeCheckout = new Checkout(false);
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
            if (client.getItems() <= 20){
                numClientesRapidos++;
                Checkout cajara =  unifila.elimina();
                cajara.formarCliente(client);
                unifila.agrega(cajara);
            }else {
                numClientesLargos++;
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

    /**
     *
     * @return
     */
    public Client generaCliente(){
        return cargaCarritoCompras(random(10) + 18);
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
                "\n\n\n ---------------- FALTAN ----------------------" +
                "\n   ID    Cantidad         Nombre         Precio \n" +
                almacenPrincipal.faltantes();
    }

    @Override
    public String toString() {
        String now = formatter.format(fecha);
        Lista<Checkout> cajasOrdenadas = new Lista<>();
        for (Checkout caja: cajas){
            cajasOrdenadas.agregar(caja);
        }
        Checkout masVendioNormal = cajasOrdenadas.getElemento( cajasOrdenadas.longitud() > 1? cajasOrdenadas.longitud()-1: 1);

        return String.format(":::  SUPERMERCADO  :::\n\nFecha: %s\nTotal de ingresos: $%.2f\nFueron atendidos %d clientes" +
                        " con a lo más 20 artículos\n\nFueron atendidos %d clientes con más de 20 artículos\n\nTotal de clientes " +
                        "atendidos: %d \n" +
                        " \nCon las siguientes cajas: \n%s\n %s" +
                        "\n\n La caja que más clientes atendió fue: \n %s",
                now,getTotalVentas(), numClientesRapidos, numClientesLargos, tickets.longitud() , cajas, unifila, masVendioNormal);
    }

}
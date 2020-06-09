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

import static util.Utilities.random;

/**
 *<h1> Supermarket </h1>
 *
 * @author Ariel Merino & Armando Aquino
 * @version 1.0
 *
 */
public class SuperMarket  implements Serializable {


    /**
     *
     */
    private final Warehouse mainWarehouse;

    /**
     *
     */
    private int numFastClients;


    /**
     *
     */
    private int numLargeClients;

    /**
     *
     */
    private Lista<Customer> tickets;


    /**
     *
     */
    private MinHeap<Checkout> singleLine;

    /**
     *
     */
    private MinHeap<Checkout> checkouts;

    /**
     *
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy - HH:mm:ss.SSS"));

    /**
     *
     */
    private Date date = new Date();

    /**
     *
     * @param rapidas
     * @param normales
     */
    public SuperMarket(int rapidas, int normales) {
        this.singleLine = new MinHeap<>();
        this.checkouts = new MinHeap<>();
        this.mainWarehouse = new Warehouse();
        for (int i = 1; i <= rapidas; i++) {
            enableCheckout(true);
        }
        for (int i = 1; i <= normales; i++) {
            enableCheckout(false);
        }

        this.tickets = new Lista<>();
    }

    /**
     *
     * @return
     */
    public Lista<Customer> getTickets() {
        return tickets;
    }

    /**
     *
     * @return
     */
    public Warehouse getAlmacen() {
        return mainWarehouse;
    }

    /**
     *
     * @return
     */
    private double getTotalVentas() {
        double suma = 0;
        for (Checkout cajaRapida: singleLine) {
            suma += cajaRapida.computeTotalSale();
        }
        for (Checkout cajaNormal: checkouts){
            suma+= cajaNormal.computeTotalSale();
        }
        return Math.round(suma);
    }

    /**
     *
     * @param product
     */
    public void darAltaProducto(Product product) {
        mainWarehouse.addProduct(product);
    }

    /**
     *
     * @param isQuick
     */
    public void enableCheckout(boolean isQuick){
        if (isQuick){
            Checkout quickCheckout = new Checkout(true);
            singleLine.agrega(quickCheckout);
        }else {
            Checkout largeCheckout = new Checkout(false);
            checkouts.agrega(largeCheckout);
        }
    }

    /**
     *
     * @param customer
     * @return
     */
    public boolean trainCustumer(Customer customer){
        if (customer.getItems() != 0){
            tickets.agregar(customer);
            if (customer.getItems() <= 20){
                numFastClients++;
                Checkout cajara =  singleLine.elimina();
                cajara.trainingCustomer(customer);
                singleLine.agrega(cajara);
            }else {
                numLargeClients++;
                Checkout caja = checkouts.elimina();
                caja.trainingCustomer(customer);
                checkouts.agrega(caja);
            }
            return true;
        }
        return false;
    }

    /**
     *
     * @param clientsProducts
     * @return
     */
    private Customer addShoppingCart(int clientsProducts){
        Customer customer = new Customer();
        for (int i = 0; i < clientsProducts; i++) {
            int ran = random(mainWarehouse.getWhareHouse().getTamanio() - 1);
            assingProduct(customer,ran+ 1,random(15) + 1);
        }
        return customer;
    }

    /**
     *
     * @param customer
     * @param id
     * @param quantity
     */
    private void assingProduct(Customer customer, int id, int quantity){
        Product toAdd = mainWarehouse.getWhareHouse().busquedaElemento(new Product(id));
        Product toTheCart = new Product(id);

        toTheCart.setUnits(quantity);
        toTheCart.setName(toAdd.getName());
        toTheCart.setPrice(toAdd.getPrice());
        toTheCart.setTimes(quantity);

        if (toAdd.getUnits() >= quantity){
            customer.addToCart(toTheCart);
            mainWarehouse.modifyStock(-quantity,id);
        }
    }

    /**
     *
     * @param path
     */
    public void loadProducts(String path){
        try {
            Scanner input = new Scanner(new File(path));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] lineFromTxt = line.split(",");
                int quant = Integer.parseInt(lineFromTxt[0]);
                String name = lineFromTxt[1];
                float price = Float.parseFloat(lineFromTxt[2]);
                Product newProduct = new Product(quant, name, price);
                mainWarehouse.addProduct(newProduct);
            }
            input.close();
        } catch (FileNotFoundException e){
            System.out.println("NO se encontro el archivo para cargar los productos");
        } catch (Exception ex) {
            System.out.println("El documento de los productos no se pudo abrir, intente de nuevo");
        }
    }

    /**
     *
     * @return
     */
    public Customer genCustomer(){
        return addShoppingCart(random(10) + 18);
    }

    /**
     *
     * @return
     */
    public String reportMissingExistences(){
        return "   :::    REPORTE DE POCAS EXISTENCIAS   :::\n    Fecha: " + formatter.format(date) +
                "\n\n" +
                " ----------------- QUEDAN POCOS ------------------" +
                "\n   ID    Cantidad         Nombre         Precio \n" +
                mainWarehouse.limitedStocks() +
                "\n\n\n ---------------- FALTAN ----------------------" +
                "\n   ID    Cantidad         Nombre         Precio \n" +
                mainWarehouse.missingStock();
    }

    @Override
    public String toString() {
        String now = formatter.format(date);
        Lista<Checkout> cajasOrdenadas = new Lista<>();
        for (Checkout caja: checkouts){
            cajasOrdenadas.agregar(caja);
        }
        Checkout masVendioNormal = cajasOrdenadas.getElemento( cajasOrdenadas.longitud() > 1? cajasOrdenadas.longitud()-1: 1);

        return String.format(":::  SUPERMERCADO  :::\n\nFecha: %s\nTotal de ingresos: $%.2f\nFueron atendidos %d clientes" +
                        " con a lo más 20 artículos\n\nFueron atendidos %d clientes con más de 20 artículos\n\nTotal de clientes " +
                        "atendidos: %d \n" +
                        " \nCon las siguientes cajas: \n%s\n %s" +
                        "\n\n La caja que más clientes atendió fue: \n %s",
                now,getTotalVentas(), numFastClients, numLargeClients, tickets.longitud() , checkouts, singleLine, masVendioNormal);
    }

}
package market;

import market.admin.*;
import util.Lista;
import util.MaxHeap;
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
 * This class models the behavior of a supermarket and its operations, using a warehouse of products
 * @author Ariel Merino and Armando Aquino
 * @version 1.0
 *
 */
public class SuperMarket  implements Serializable {

    /**
     * the main warehouse for the supermaket's use
     */
    private Warehouse mainWarehouse;

    /**
     * counter of fast/quick clients
     */
    private int numFastClients;

    /**
     * count of large clients
     */
    private int numLargeClients;

    /**
     * the tickets of this whole supermarket
     */
    private Lista<Customer> tickets;

    /**
     * the singe line where the clients may be form up
     */
    private MinHeap<Checkout> singleLine;

    /**
     * the large checkouts where the client can be form up in this structure by using it in the best way
     */
    private MinHeap<Checkout> checkouts;

    /**
     * give us the formar for the report's date
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat(("dd/MM/yyyy - HH:mm:ss.SSS"));

    /**
     * This give us the date
     */
    private Date date = new Date();

    /**
     * Main construcot to provide the number of fast and normal checkout to be used in this simulation
     * @param quickly number of quick checkouts
     * @param large number of large checkout
     */
    public SuperMarket(int quickly, int large) {
        this.singleLine = new MinHeap<>();
        this.checkouts = new MinHeap<>();
        this.mainWarehouse = new Warehouse();
        for (int i = 1; i <= quickly; i++) {
            enableCheckout(true);
        }
        for (int i = 1; i <= large; i++) {
            enableCheckout(false);
        }

        this.tickets = new Lista<>();
    }

    /**
     * Allow to set the warehouse to be used
     * @param mainWarehouse object o be use as a warehouse
     */
    public void setMainWarehouse(Warehouse mainWarehouse) {
        this.mainWarehouse = mainWarehouse;
    }

    /**
     * Let know the tickets generated in this simulated day
     * @return list with the clients of all the day
     */
    public Lista<Customer> getTickets() {
        return tickets;
    }

    /**
     * Allows to get the main warehouse
     * @return warehouse
     */
    public Warehouse getAlmacen() {
        return mainWarehouse;
    }

    /**
     * Give us the total number of sales in a day
     * @return sum of sales
     */
    private double getTotalSales() {
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
     * this allow to integrate a product into the warehouse
     * @param product pructo to be register into the warehouse
     */
    public void toRegisterProduct(Product product) {
        mainWarehouse.addProduct(product);
    }

    /**
     * this method builds a checkout in function of a parameter that give us the information about if it is a quick checkout
     * or not
     * @param isQuick property of the checkout
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
     * Distribute the client into the checkouts by the number of items
     * @param customer the person to be form up
     * @return true if it had been added to the checkout false and any other case
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
     * allows to add in the purchase of each client the number o random productos
     * @param clientsProducts number o products
     * @return client with the products int its bag ;)
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
     * Give us the power to assign a producto in quantity and item id to a provided client
     * @param customer customer to be modify
     * @param id item id
     * @param quantity number of times that a producto will be added
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
     * allows to generate a warehouse from a path, it turns into a product-able object line
     * @param path where the file will be read
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
     * allows to generate a new customer with a random number of items in a range 18-27 items
     * @return the customer generated
     */
    public Customer genCustomer(){
        return addShoppingCart(random(10) + 18);
    }

    /**
     * check the missing or limited existences and make a report of it
     * @return report
     */
    public String reportMissingExistences(){
        return "   :::    CRITICAL EXISTENCES DAILY REPORT  :::\n    DATE: " + formatter.format(date) +
                "\n\n" +
                " ----------------- LIMITED EXISTENCES ------------------" +
                "\n   ITEM    QUANTITY         DESCRIPTION         PRICE \n" +
                mainWarehouse.limitedStocks() +
                "\n\n\n ---------------- MISSING PRODUCTS ----------------------" +
                "\n   ITEM    QUANTITY         DESCRIPTION         PRICE \n" +
                mainWarehouse.missingStock();
    }

    @Override
    public String toString() {
        String now = formatter.format(date);

        MaxHeap<Checkout> cajasOrdenadas = new MaxHeap<>();
        MaxHeap<Double> salesPerClient = new MaxHeap<>();
        MaxHeap<Integer> costumersPerClient = new MaxHeap<>();

        for (Checkout caja: checkouts){
            cajasOrdenadas.agrega(caja);
            salesPerClient.agrega(caja.computeTotalSale());
            costumersPerClient.agrega(caja.getItemsClients());
        }

        return String.format(":::  SUPERMARKET  :::\n\nDATE: %s\nTOTAL SALES: $%.2f\nThere where attended %d clients " +
                        " with at the most 20 items\n\nThere where attended %d clients with more than 20 items\n" +
                        "\nTotal of customers " +
                        "attended: %d \n" +
                        " \nWITH THE FOLLLOWING CHECKOUTS: \n%s\n %s" +
                        "\n\n The large checkout that had attended more customers was: \n %s " +
                        "\nBest selling box sold: $ %.2f\n" +
                        "\nthe box with the most items served %d times.",
                now, getTotalSales(), numFastClients, numLargeClients, tickets.longitud() , checkouts, singleLine,
                cajasOrdenadas.elimina(), salesPerClient.elimina(), costumersPerClient.elimina());
    }

}
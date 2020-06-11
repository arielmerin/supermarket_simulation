package market.admin;

import util.Cola;
import java.io.Serializable;

/**
 *<h1>Checkout</h1>
 *
 * This class allows to simulate and model the behavior of a checkout into a supermarket (simulation)
 * @author Ariel Merino & Armando Aquino
 * @version 1.0
 */
public class Checkout implements Serializable, Comparable<Checkout> {

    /**
     * Accumulate the sales of the simulation day
     */
    private double salesOfDay;

    /**
     * Counter for the customers attended
     */
    private int customersOfDay;

    /**
     * Allow us to know the number of items that each checkout had process
     * @return number of items sold
     */
    public int getItemsClients() {
        int sum = 0;
        for (Customer customer: clients){
            sum += customer.getItems();
        }
        return sum;
    }

    /**
     * Each checkout has a row of customers waiting for be attended
     */
    protected Cola<Customer> clients;

    /**
     * the rest of clients that the checkout needs to attend
     */
    private int forBeingServed;

    /**
     * Serves to know if the num of items is lower than 20
     */
    private boolean isQuick;

    /**
     * The main constructor allows to build a new checkout with the only condition to know if it is from the quick type
     * @param isQuickly if the number of items is going to be a restriction
     */
    public Checkout(boolean isQuickly) {
        clients = new Cola<>();
        this.isQuick = isQuickly;
    }

    /**
     * Takes a decision about the checkout type and how is going to be taken for the client
     * @param customer a client that needs to be lined up
     */
    public void trainingCustomer(Customer customer) {
        if((isQuick && customer.getItems() <= 20) || (!isQuick)){
            clients.agrega(customer);
            customersOfDay++;
            forBeingServed++;
        }
    }

    /**
     * This method calculates the total amount by selling on a day
     * @return sum of each total sale's customer
     */
    public double computeTotalSale(){
        for (Customer customer : clients){
            salesOfDay += customer.computeTotal();
        }
        return salesOfDay;
    }


    @Override
    public int compareTo(Checkout o) {
        if (o.forBeingServed > forBeingServed){
            return -1;
        }if (o.forBeingServed < forBeingServed){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return  (isQuick ? " QUICK ": " LARGE ")+ "CHECKOUT\n" +
                String.format("Total sales : $%2.2f", computeTotalSale()) +
                "\nAttended customers: " + clients.getTamanio() + "\n\n";
    }

}

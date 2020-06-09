package market.admin;

import util.Cola;
import java.io.Serializable;

/**
 *<h1>Checkout</h1>
 *
 * @author Ariel Merino & Armando Aquino
 * @version 1.0
 */
public class Checkout implements Serializable, Comparable<Checkout> {

    /**
     *
     */
    protected double salesOfDay;

    /**
     *
     */
    protected int customersOfDay;

    /**
     *
     */
    protected Cola<Customer> clients;

    /**
     *
     */
    private int forBeingServed;

    /**
     *
     */
    private boolean isQuick;

    /**
     *
     */
    public Checkout(boolean esRapida) {
        clients = new Cola<>();
        this.isQuick = esRapida;
    }


    /**
     *
     * @param customer
     */
    public void trainingCustomer(Customer customer) {
        if((isQuick && customer.getItems() <= 20) || (!isQuick)){
            clients.agrega(customer);
            customersOfDay++;
            forBeingServed++;
        }
    }

    /**
     *
     * @return
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
        return "CAJA " + (isQuick ? " RÁPIDA\n": " NORMAL\n")+
                String.format("Venta total: $%2.2f", computeTotalSale()) +
                "\nClientes atendidos: " + clients.getTamanio() + "\n\n";
    }

}

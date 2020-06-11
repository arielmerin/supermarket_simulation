package market.admin;

import util.Pila;
import static util.Utilities.random;

/**
 * <h1>Customer</h1>
 *
 * Provides the behavior of a client into a supermarket
 * @author Ariel Merino and Armando Aquino
 * @version 1.1
 */
public class Customer implements Comparable<Customer> {

    /**
     * each client has one and only one purchase for its products
     */
    private Purchase purchase = new Purchase();

    /**
     * <h2>Purchase </h2>
     * Models the behavior of a shopping cart
     */
    private class Purchase implements Comparable{

        /**
         * the structure where to save products
         */
        private Pila<Product> shoppingCart = new Pila<>();

        /**
         * aux to calculate the size of this purchase
         */
        private double total;
        @Override
        public String toString() {
            return "\t\t\tCASH RECEIPT"+
                    "\n----------------------------------------------------\n" +
                    "#Item  QTY     Description    Price   Total\n"+
                    shoppingCart +
                    String.format("\n\n                           Subtotal:   %2.2f", computeTotal() - computeTaxes(computeTotal())) + "\n"+
                    String.format("                           taxes:        %2.2f", computeTaxes(computeTotal())) + "\n"+
                    String.format("                           total:      %2.2f", computeTotal()) + "\n"+
                    "Total number of items sold: "+ shoppingCart.getTamanio() + "\n"+
                    "----------------------------------------------------\n"+
                    "\t\tTHANK YOU FOR SHOPPING!\n" +
                    "----------------------------------------------------\n";
        }

        @Override
        public int compareTo(Object o) {
            Purchase n = (Purchase)o;
            if (total - n.total > 0){
                return 1;
            }if(total - n.total < 0){
                return -1;
            }else {
                return 0;
            }
        }

    }

    /**
     * calculate the total of the sale
     * @return sum of the total
     */
    public double computeTotal(){
        double sum = 0;
        for (Product product: purchase.shoppingCart){
            sum+= product.getTotal();
        }
        return sum;
    }

    /**
     * Access to te items
     * @return number of items bought
     */
    public int getItems(){
        return purchase.shoppingCart.getTamanio();
    }

    /**
     * adding to this cart some product
     * @param product item to be added
     */
    public void addToCart(Product product) {
        purchase.shoppingCart.agrega(product);
    }

    /**
     * calculate the taxes from a sale
     * @param total total of sales
     * @return the taxes calculated
     */
    public double computeTaxes(double total){
        return total * 0.16;
    }

    /**
     * sum and aproximate the time that this client had been waiting in the supermarket, taking in consider the objects
     * and if its bought is or not less than 20 items
     * @return the average time
     */
    public long getWaitingTime() {
        int tiempoTardaPorArticulo = purchase.shoppingCart.getTamanio() >= 20 ? random(9) + 28: random(5) + 1;
        return purchase.shoppingCart.getTamanio() * tiempoTardaPorArticulo;
    }

    @Override
    public int compareTo(Customer o) {
        if (o.computeTotal() > computeTotal()){
            return 1;
        }if (o.computeTotal() < computeTotal()){
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return purchase.toString();
    }
}

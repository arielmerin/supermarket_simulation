package market.admin;

import util.Pila;
import static util.Utilities.random;

/**
 * <h1>Customer</h1>
 *
 * @author Ariel Merino & Armando Aquino
 * @version 1.1
 */
public class Customer implements Comparable<Customer> {

    /**
     *
     */
    private Purchase purchase = new Purchase();

    /**
     * <h1>Purchase </h1>
     */
    private class Purchase implements Comparable{

        /**
         *
         */
        private Pila<Product> shoppingCart = new Pila<>();

        /**
         *
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
     *
     * @return
     */
    public double computeTotal(){
        double sum = 0;
        for (Product product: purchase.shoppingCart){
            sum+= product.getTotal();
        }
        return sum;
    }

    /**
     *
     * @return
     */
    public int getItems(){
        return purchase.shoppingCart.getTamanio();
    }

    /**
     *
     * @param product
     */
    public void addToCart(Product product) {
        purchase.shoppingCart.agrega(product);
    }

    /**
     *
     * @param total
     * @return
     */
    public double computeTaxes(double total){
        return total * 0.16;
    }

    /**
     *
     * @return
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

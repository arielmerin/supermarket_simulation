package market.admin;

import util.Lista;
import util.Pila;

public class Client {

    private Purchase purchase = new Purchase();

    private int waitingTime;

    private class Purchase implements Comparable{

        Pila<Product> shoppingCart = new Pila<>();
        double sutotal;
         double total;
        private double iva;

        @Override
        public String toString() {
            return "TICKET DE COMPRA"+
                    "\n----------------------------------------------------\n" +
                    "#Producto Cantidad     Nombre  Precio  Total\n"+
                    shoppingCart +
                    "\n\n                           Subtotal:   " + total + "\n"+
                    "                           iva:        " + iva + "\n"+
                    "                           total:      " + total + "\n"+
                    "----------------------------------------------------\n"+
                    "¡GRACIAS POR TU COMPRA, VUELVE PRONTO!\n" +
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

    public int getItems(){
        return purchase.shoppingCart.getTamanio();
    }

    public void agregarAlCarrito(Product product){
        purchase.total = product.getTotal();
        purchase.shoppingCart.agrega(product);
    }

    @Override
    public String toString() {
        return purchase.toString();
    }
}

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
                    String.format("\n\n                           Subtotal:   %2.2f", calculaTotal() - calculaIva(calculaTotal())) + "\n"+
                    String.format("                           iva:        %2.2f", calculaIva(calculaTotal())) + "\n"+
                    String.format("                           total:      %2.2f", calculaTotal()) + "\n"+
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

    public double calculaTotal(){
        double sumatoria = 0;
        for (Product product: purchase.shoppingCart){
            sumatoria+= product.total;
        }
        return sumatoria;
    }

    public int getItems(){
        return purchase.shoppingCart.getTamanio();
    }

    public void agregarAlCarrito(Product product) {
        purchase.total = product.getTotal();

        purchase.shoppingCart.agrega(product);
    }

    public double calculaIva(double total){

        return total * 0.16;
    }



    @Override
    public String toString() {
        return purchase.toString();
    }
}

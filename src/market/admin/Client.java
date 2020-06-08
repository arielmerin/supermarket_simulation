package market.admin;

import util.Pila;

public class Client implements Comparable<Client> {

    private Purchase purchase = new Purchase();

    private int waitingTime;

    @Override
    public int compareTo(Client o) {
        if (o.calculaTotal() > calculaTotal()){
            return 1;
        }if (o.calculaTotal() < calculaTotal()){
            return -1;
        }
        return 0;
    }

    private class Purchase implements Comparable{

        Pila<Product> shoppingCart = new Pila<>();
        double total;

        @Override
        public String toString() {
            return "TICKET DE COMPRA"+
                    "\n----------------------------------------------------\n" +
                    "#Producto Cantidad     Nombre    Precio   Total\n"+
                    shoppingCart +
                    String.format("\n\n                           Subtotal:   %2.2f", calculaTotal() - calculaIva(calculaTotal())) + "\n"+
                    String.format("                           iva:        %2.2f", calculaIva(calculaTotal())) + "\n"+
                    String.format("                           total:      %2.2f", calculaTotal()) + "\n"+
                    "Total de artículos comprados: "+ shoppingCart.getTamanio() + "\n"+
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
            sumatoria+= product.getTotal();
        }
        return sumatoria;
    }

    public int getItems(){
        return purchase.shoppingCart.getTamanio();
    }

    public void agregarAlCarrito(Product product) {
        purchase.shoppingCart.agrega(product);
    }

    public double calculaIva(double total){
        return total * 0.16;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    @Override
    public String toString() {
        return purchase.toString();
    }
}

package market.admin;

import util.Lista;

public class Client {

    private Purchase purchase;

    private int waitingTime;

    private class Purchase implements Comparable{
        private Lista<Product> shoppingCart;
        private int sutotal;
        private int total;
        private int iva;

        @Override
        public String toString() {
            return "TICKET DE COMPRA"+
                    "\n----------------------------------------------------\n" +
                    "#Producto Cantidad Nombre Precio Total\n"+
                    shoppingCart +
                    "                   Subtotal: " + total + "\n"+
                    "                   iva: " + iva + "\n"+
                    "                   total: " + total + "\n"+
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
}

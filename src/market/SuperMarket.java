package market;

import market.admin.LargeCheckout;
import market.admin.Product;
import market.admin.QuickCheckout;
import market.admin.Wharehouse;
import util.ArbolAVL;
import util.MinHeap;
import util.Pila;

public class SuperMarket implements Simulable {


    private int totalVentas;
    private Wharehouse wharehouse;

    private Pila<QuickCheckout> unifila;

    private MinHeap<LargeCheckout> cajas;

    public SuperMarket(){
        wharehouse = new Wharehouse();
    }

    public void darAlraProducto(Product product){
        wharehouse.agregarProducto(product);
    }
}
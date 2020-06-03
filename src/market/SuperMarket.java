package market;

import market.admin.LargeCheckout;
import market.admin.QuickCheckout;
import util.ArbolAVL;
import util.MinHeap;
import util.Pila;

public class SuperMarket implements Simulable {

    private int totalVentas;

    private Pila<QuickCheckout> unifila;

    private MinHeap<LargeCheckout> cajas;


}
package market;

import market.admin.*;
import util.MinHeap;
import util.Pila;

import java.io.Serializable;

public class SuperMarket implements Serializable, Runnable {


    private int totalVentas;

    private Wharehouse wharehouse;

    private Pila<QuickCheckout> unifila;

    private MinHeap<LargeCheckout> cajas;


    public SuperMarket(int rapidas, int normales, int clientes){
        wharehouse = new Wharehouse();
    }

    public void darAltaProducto(Product product){
        wharehouse.agregarProducto(product);
    }

    public Wharehouse getWharehouse() {
        return wharehouse;
    }


    @Override
    public void run() {

    }

    public void formarCliente(Client client){
        if (client.getItems() <= 20){
            System.out.println("ES de fila rápida");
        }else {
            System.out.println("NOOO es de fila rápida");

        }
    }


    public void asignaProducto(Client client, int id, int cantidad){
        Product agregar = wharehouse.getAlmacen().buscandoElem(new Product((id)));
        Product alCarrito = new Product(id);
        alCarrito.setUnits(cantidad);
        alCarrito.setName(agregar.getName());
        alCarrito.setPrice(agregar.getPrice());
        if (agregar.getUnits() >= cantidad){
            client.agregarAlCarrito(alCarrito);
        }
    }

    public int getTotalVentas() {
        return totalVentas;
    }
}
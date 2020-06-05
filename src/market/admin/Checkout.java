package market.admin;

import util.Cola;

import java.io.Serializable;

/**
 *
 */
public abstract class Checkout extends Thread implements Serializable, Comparable<Checkout> {

    /**
     *
     */
    protected double ventaDelDia;

    /**
     *
     */
    protected int clientesDelDia;

    /**
     *
     */
    protected Cola<Client> clients;

    /**
     *
     */
    protected int porAtender;

    /**
     *
     */
    public Checkout(){
        clients = new Cola<>();
    }

    /**
     *
     * @param client
     */
    public void formarCliente(Client client) {
        clients.agrega(client);
        clientesDelDia++;
        porAtender++;
    }

    /**
     *
     * @return
     */
    public double calculaVentaTotal(){
        for (Client client: clients){
            ventaDelDia+= client.calculaTotal();
        }
        return ventaDelDia;
    }

    /**
     *
     * @param tiempo
     */
    public void dormirCaja(long tiempo){
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (porAtender != 0){
            porAtender--;
        }
        System.out.println("Acabé de atender mis clientes");
    }

    @Override
    public int compareTo(Checkout o) {
        if (o.clients.getTamanio() > clients.getTamanio()){
            return -1;
        }if (o.clients.getTamanio() < clients.getTamanio()){
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Caja\n" +
                String.format("Venta total: $%2.2f", calculaVentaTotal()) +
                "\nClientes atendidos: " + clients.getTamanio();
    }
}

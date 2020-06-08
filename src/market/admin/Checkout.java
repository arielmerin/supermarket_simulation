package market.admin;

import util.Cola;

import java.io.Serializable;

/**
 *
 */
public class Checkout extends Thread implements Serializable, Comparable<Checkout> {

    private int tiempoAtiendePProdcuto;

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
    private int porAtender;

    private boolean esRapida;
    /**
     *
     */
    public Checkout(boolean esRapida, int tiempoAtiendePProdcuto) {
        clients = new Cola<>();
        this.esRapida = esRapida;
        this.tiempoAtiendePProdcuto = tiempoAtiendePProdcuto;
    }


    public int getPorAtender() {
        return porAtender;
    }

    public int getTiempoAtiendePProdcuto() {
        return tiempoAtiendePProdcuto;
    }

    /**
     *
     * @param client
     */
    public void formarCliente(Client client) {
        if((esRapida && client.getItems() <= 20) || (!esRapida)){
            clients.agrega(client);
            clientesDelDia++;
            porAtender++;
        }
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
        if (o.porAtender > porAtender){
            return -1;
        }if (o.porAtender < porAtender){
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

package market.admin;

import util.Cola;
import java.io.Serializable;

/**
 *<h1>Checkout</h1>
 * Esta clase modela todo el comportamiento de las cajas como elementos de un supermercado
 */
public class Checkout implements Serializable, Comparable<Checkout> {

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

    /**
     *
     */
    private boolean esRapida;

    /**
     *
     */
    public Checkout(boolean esRapida) {
        clients = new Cola<>();
        this.esRapida = esRapida;
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
        return "CAJA " + (esRapida? " RÁPIDA\n": " NORMAL\n")+
                String.format("Venta total: $%2.2f", calculaVentaTotal()) +
                "\nClientes atendidos: " + clients.getTamanio() + "\n\n";
    }

}

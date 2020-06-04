package market.admin;

import util.Cola;

import java.io.Serializable;

public abstract class Checkout implements Serializable, Comparable<Checkout> {

    protected double ventaDelDia;
    protected int clientesDelDia;
    protected Cola<Client> clients;

    @Override
    public int compareTo(Checkout o) {
        if (o.clients.getTamanio() > clients.getTamanio()){
            return -1;
        }if (o.clients.getTamanio() < clients.getTamanio()){
            return 1;
        }
        return 0;
    }

    public Checkout(){
        clients = new Cola<>();
    }

    public void formarCliente(Client client) {
        clients.agrega(client);
        clientesDelDia++;
    }

    public double calculaVentaTotal(){
        for (Client client: clients){
            ventaDelDia+= client.calculaTotal();
        }
        return ventaDelDia;
    }

    @Override
    public String toString() {
        return "Caja\n" +
                String.format("Venta total: $%2.2f", calculaVentaTotal()) +
                "\nClientes atendidos: " + clients.getTamanio();
    }
}

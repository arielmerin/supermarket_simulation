package market.admin;

import util.Cola;

public abstract class Checkout {


    protected Cola<Client> clients;

    public Checkout(){
        clients = new Cola<>();
    }



}

package market.admin;

import java.io.Serializable;

public class QuickCheckout extends Checkout implements Serializable {



    public QuickCheckout(){
        super();
    }


    @Override
    public void formarCliente(Client client) {
        if (client.getItems() <= 20){
            super.formarCliente(client);
        }
    }

    @Override
    public String toString() {
        return "\n\n ::: CAJA RÁPIDA ::: \n"+ super.toString();
    }
}

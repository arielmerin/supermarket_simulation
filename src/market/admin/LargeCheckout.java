package market.admin;


import java.io.Serializable;

public class LargeCheckout extends Checkout implements Serializable {

    int clientasFormadas;


    @Override
    public String toString() {
        return "\n\n ::: CAJA NORMAL :::\n"+super.toString();
    }


}

package market.admin;

public class LargeCheckout extends Checkout implements Comparable<LargeCheckout> {

    int clientasFormadas;

    @Override
    public int compareTo(LargeCheckout o) {
        LargeCheckout largeCheckout= (LargeCheckout)o;
        if(largeCheckout.clientasFormadas - clientasFormadas > 0){
            return 1;
        }if (largeCheckout.clientasFormadas - clientasFormadas < 0){
            return -1;
        }
        return 0;
    }


}

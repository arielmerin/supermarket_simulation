package market.admin;

import util.ArbolAVL;
import util.Lista;
import java.io.Serializable;
import java.util.Iterator;

/**
 * <h1>Warehouse</h1>
 *
 * This class allows to shape the behavior of a stock, with entrances and exits for the supermarket
 * @author Ariel Merino and Armando Aquino
 * @version 1.0
 */
public class Warehouse implements Serializable {

    /**
     * to assign the only id
     */
    private static int counter;

    /**
     * the main structure from the stock
     */
    private ArbolAVL<Product> whareHouse;

    /**
     * Constructor by omission
     */
    public Warehouse() {
        whareHouse = new ArbolAVL<>();
    }

    /**
     * This method modifies the units from a product into the stock
     * @param quantity to be change
     * @param id unique id from product
     * @return true iff it the qty is less than 0 after the assign
     */
    public boolean modifyStock(int quantity, int id){
        Product product = new Product(id);
        if (product.getUnits() - quantity < 0 && quantity < 0) {
            return false;
        }
        toUpdate(product, whareHouse.busquedaElemento(product).getUnits() + quantity);
        return true;
    }

    /**
     * a product that needs to be updated (only in the existences)
     * @param product to be updated
     * @param value new units
     */
    public void toUpdate(Product product, int value ){
        Product product1 = whareHouse.busquedaElemento(product);
        product1.setUnits(value);
    }

    /**
     * Adding a product to the main structure
     * @param product the new product
     */
    public void addProduct(Product product){
        counter = whareHouse.getTamanio();
        product.setId(++counter);
        whareHouse.agrega(product);
    }

    /**
     * Access to the main structure
     * @return the stock into an AVL tree structure
     */
    public ArbolAVL<Product> getWhareHouse() {
        return whareHouse;
    }

    /**
     * reports the limited existences
     * @return a list of products that satisfies this conditions in their existences (units)
     */
    public Lista<String> limitedStocks(){
        Lista<String> lowStock = new Lista<>();
        Iterator it = whareHouse.iterator();
        while (it.hasNext()){

            Product element = (Product) it.next();
            if ( element.getUnits() > 0 && element.getUnits() < 10 ){
                lowStock.agregar((element) + "\n");
            }
        }
        return lowStock;
    }

    /**
     * reports the missing existences
     * @return a list of products that satisfies this conditions in their existences (units)
     */
    public Lista<String> missingStock(){
        Lista<String> missed = new Lista<>();
        Iterator it = whareHouse.iterator();
        while (it.hasNext()){
            Product element = (Product) it.next();
            if ( element.getUnits() == 0){
                missed.agregar((element) + "\n");
            }
        }
        return missed;
    }
}

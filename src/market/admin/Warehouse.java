package market.admin;

import util.ArbolAVL;
import util.Lista;
import java.io.Serializable;
import java.util.Iterator;

/**
 * <h1>Warehouse</h1>
 *
 * @author Ariel Merino & Armando Aquino
 * @version 1.0
 */
public class Warehouse implements Serializable {

    /**
     *
     */
    private static int counter;

    /**
     *
     */
    private ArbolAVL<Product> whareHouse;

    /**
     *
     */
    public Warehouse() {
        whareHouse = new ArbolAVL<>();
    }

    /**
     *
     * @param quantity
     * @param
     * @return
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
     *
     * @param product
     * @param value
     */
    public void toUpdate(Product product, int value ){
        Product product1 = whareHouse.busquedaElemento(product);
        product1.setUnits(value);
    }
    /**
     *
     * @param product
     */
    public void addProduct(Product product){
        counter = whareHouse.getTamanio();
        product.setId(++counter);
        whareHouse.agrega(product);
    }

    /**
     *
     * @return
     */
    public ArbolAVL<Product> getWhareHouse() {
        return whareHouse;
    }

    /**
     *
     * @return
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
     *
     * @return
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

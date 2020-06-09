package market.admin;

import java.io.Serializable;
import java.util.Objects;

/**
 *<h1>Product</h1>
 * La clase producto se encarga de porporcinar las funciones y propiedades que un artículo tendría en el almacén de
 * un supermercado
 */
public class Product implements Comparable<Product>, Serializable {

    /**
     *
     */
    private int id;

    /**
     *
     */
    private int units;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private float price;

    /**
     *
     */
    private int times = 1;

    /**
     *
     */
    private boolean available;

    /**
     *
     * @param units
     * @param name
     * @param price
     */
    public Product(int units, String name, float price){
        this.units= units;
        this.name = name;
        this.price = price;
        this.available = true;
    }

    /**
     *
     * @param id
     */
    public Product(int id){
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    public float getTotal() {
        return times * price;
    }

    /**
     *
     * @return
     */
    public int getUnits() {
        return units;
    }

    /**
     *
     * @return
     */
    public float getPrice() {
        return price;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @param units
     */
    public void setUnits(int units) {
        checkUnits();
        this.units = units;
    }

    /**
     *
     * @param price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param times
     */
    public void setTimes(int times) {
        this.times = times;
    }

    /**
     *
     */
    private void checkUnits(){
        if (units <= 0 ){
            available = false;
        }
    }

    /**
     *
     * @return
     */
    public boolean isAvailable(){
        return available;
    }

    @Override
    public int compareTo(Product o) {
        if (id - o.id > 0){
            return  1;
        } if (id - o.id < 0){
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        float total = price * times;
        String fina = String.format(" %d\t\t%d\t\t%s\t\t\t\t%2.2f\t%2.2f",id,units,name,price, total);
        return fina;
    }
}

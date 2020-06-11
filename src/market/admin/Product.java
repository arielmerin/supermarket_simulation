package market.admin;

import java.io.Serializable;
import java.util.Objects;

/**
 *<h1>Product</h1>
 *
 * captures the qualities and functionalities of an article within this simulation of a supermarket
 * @author Ariel Merino and Armando Aquino
 * @version 1.1
 */
public class Product implements Comparable<Product>, Serializable {

    /**
     * The only identifier for each product
     */
    private int id;

    /**
     * the number of units that a product has in existences
     */
    private int units;

    /**
     * the description of the product
     */
    private String name;

    /**
     * the cost of the product
     */
    private float price;

    /**
     * how much times the product had been brought by a customer
     */
    private int times = 1;

    /**
     * Let us know if there existences for the product in the stock
     */
    private boolean available;

    /**
     * Main construct that uses the essential qualities and atributes of the item to build it
     * @param units existences in stock
     * @param name description
     * @param price cost of product
     */
    public Product(int units, String name, float price){
        this.units= units;
        this.name = name;
        this.price = price;
        this.available = true;
    }

    /**
     * only by item's id
     * @param id id to be register
     */
    public Product(int id){
        this.id = id;
    }

    /**
     * acces de name of the product
     * @return description from the product
     */
    public String getName() {
        return name;
    }

    /**
     * compute the times by the price
     * @return total of sale (by this product)
     */
    public float getTotal() {
        return times * price;
    }

    /**
     * units in existence
     * @return number of units in the stock
     */
    public int getUnits() {
        return units;
    }

    /**
     * access de price into the product
     * @return cost
     */
    public float getPrice() {
        return price;
    }

    /**
     * assign the item's id
     * @param id identification
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * modify the number of exitences
     * @param units units in stock
     */
    public void setUnits(int units) {
        checkUnits();
        this.units = units;
    }

    /**
     * assing the price of a product
     * @param price price of the product
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * assign the description from a product
     * @param name description of a product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * assign the times that this product had been bought
     * @param times times to be added
     */
    public void setTimes(int times) {
        this.times = times;
    }

    /**
     * this look for the units and set the availability
     */
    private void checkUnits(){
        if (units <= 0 ){
            available = false;
        }
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

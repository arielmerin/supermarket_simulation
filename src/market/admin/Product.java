package market.admin;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Comparable<Product>, Serializable {
    private int id;
    private int units;
    private String name;
    private float price;
    private int times = 1;
    private boolean available;
    float total;

    public Product(int units, String name, float price){
        this.units= units;
        this.name = name;
        this.price = price;
        this.available = true;
    }

    public Product(Product product){

    }

    public Product(int id){
        this.id = id;
    }

    @Override
    public String toString() {
        total = price * times;
        String fina = String.format("\t%d \t\t %d \t\t%s\t %2.2f\t%2.2f",id,units,name,price, total);
        return fina;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnits() {
        return units;
    }

    public void setUnits(int units) {
        checkUnits();
        this.units = units;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    private void checkUnits(){
        if (units <= 0 ){
            available = false;
        }
    }

    public boolean isAvailable(){
        return available;
    }

    @Override
    public int compareTo(Product o) {
        Product p = (Product)o;
        if (id - p.id > 0){
            return  1;
        } if (id - p.id < 0){
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

    public float getTotal() {
        return total;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

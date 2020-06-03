package market.admin;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {
    int id;
    int units;
    String name;
    float price;
    int times = 1;

    public Product(int id, int units, String name, float price){
        this.id = id;
        this.units= units;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        float total = price * times;
        String fina = String.format("%d %d %s %f %f",id,units,name,price, total);
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
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}

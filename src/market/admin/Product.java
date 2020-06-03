package market.admin;

import java.io.Serializable;

public class Product implements Comparable<Product>, Serializable {
    int id;
    int units;
    String name;
    int price;
    int times = 1;

    public Product(int id, int units, String name, int price){
        this.id = id;
        this.units= id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("{} {} {} ${} ${} ",id,units,name,price, price * times);
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

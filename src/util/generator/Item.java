package util.generator;

/**
 * <h1>Item</h1>
 *
 * @author Ariel Merin & Armando Aquino
 * @version 1.0
 */
public class Item {

    /**
     *
     */
    private double price;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private int qty;

    /**
     *
     * @param qty
     * @param description
     * @param price
     */
    public Item(int qty, String description, double price){
        this.qty = qty;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%d, %s, %.2f\n", qty, description, price);
    }
}

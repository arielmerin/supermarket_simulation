package util.generator;

/**
 * <h1>Item</h1>
 *
 * Simulates the components of a product in the supermarket
 * @author Ariel Merino and Armando Aquino
 * @version 1.0
 */
public class Item {

    /**
     * price of the product
     */
    private double price;

    /**
     * name of the product
     */
    private String description;

    /**
     * quantity of this product in existences
     */
    private int qty;

    /**
     * only constructor to provide the essential parameters
     * @param qty existences
     * @param description name
     * @param price cost
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

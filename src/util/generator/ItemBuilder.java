package util.generator;
import java.util.Iterator;

import static util.Utilities.random;
import static util.Utilities.randomD;

/**
 * <h1>Item Builder</h1>
 *
 * Builds a random item
 * @author Ariel Merin & Armando Aquino
 * @version 1.0
 */

public class ItemBuilder implements Iterator<Item> {

    /**
     * list of random names provided by me ;)
     */
    private final String[] nombres = {"Apples", "Pear", "Papaya", "Mango", "Tangerine", "Sweet potato",
            "Pumpkins", "Rice", "Soup", "Pasta", "Spaghetti", "Soy sauce", "Rice vinegar", "Wheat flour",
            "Gram flour", "Oat flour", "Integral flour", "Quinoa flour", "Oat", "Quinoa", "Beans",
            "Broad Beans", "Grams ", "Lentils", "Lemons", "Dried chiles", "Almonds", "Nuts", "Coffee",
            "Splenda", "Cereals", "Pan", "Ezekiel", "Soap", "Soybeans", "Peas", "Chayotes", "Cucumber", "Avocado",
            "Carrot", "Amaranth", "Chia seed", "Strawberries", "Tofu", "Peanut butter", "Kale"};

    /**
     * list of complements in this names, provided by me
     */
    private final String[] complementos = {" integral", " organic", " gmo free", " gluten free", " with agave syrup",
            " vegan", " veggie", " with coconut oil", " extra big", " baby", " ready to eat", " with sea salt",
            " with dark cocoa"};

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Item next() {
        return new Item(random(984),
                nombres[random(nombres.length)] + complementos[random(complementos.length)],
                randomD(9) + random(1900));
    }
}

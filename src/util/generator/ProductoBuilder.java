package util.generator;
import java.util.Iterator;
import java.util.Random;

public class ProductoBuilder implements Iterator<Producto> {


    private final String[] nombres = {"Manzana", "Pera", "Papaya", "Mango", "Mandarina", "Camote",
            "Calabaza", "Arroz", "Sopa", "Pasta", "Spaghetti", "Salsa de soya", "Vinagre de Arroz", "Harina de trigo",
            "Harina de garbanzo", "Harina de avena", "Harina integral", "Harina de quinoa", "Avena", "Quinoa", "Habas",
            "Frijoles", "Garbanzos", "Alubias", "Lentejas", "Limones", "Chiles secos", "Almendras", "Nueces", "Café",
            "Splenda", "Cereales", "Pan", "Ezekiel", "Jabón", "Edamames", "Chicharos", "Chayotes", "Pepino"};

    private final String[] complementos = {" integral", " orgánico", " de baño", " gluten free", " con jarabe de agave",
            " vegan", " veggie", " con aceite de coco", " bolsa grande"};


    private static int random(int k){
        Random rdm = new Random();
        int i = rdm.nextInt();
        i = i < 0 ? -i : i;
        return i % k;
    }

    private static double randomD(int k){
        Random rdm = new Random();
        double i = rdm.nextDouble();
        i = i < 0 ? -i : i;
        return i % k;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Producto next() {
        return new Producto(random(984),nombres[random(nombres.length)] + complementos[random(complementos.length)], randomD(9) + random(1900));
    }
}

import UImenu.Menu;
import market.SuperMarket;
import market.admin.Product;
import sim.Simulation;
import util.generator.ProductoBuilder;
import serializer.Serializer;
import util.Lista;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        //menu.principal();
        Simulation sim = new Simulation(15,100,10);
        sim.simular(80,80);
    }
}

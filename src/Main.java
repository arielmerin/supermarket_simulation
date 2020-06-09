
import UImenu.Menu;
import market.admin.Checkout;
import market.admin.Client;
import market.SuperMarket;
import sim.Simulation;
import market.admin.Product;
import serializer.Serializer;
import util.Lista;
import util.MinHeap;
import util.generator.ProductoBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;


import serializer.Serializer;
import sim.Simulation;
import util.Lista;


public class Main {
    public static void main(String[] args) {
        Lista<String> plotear = new Lista<>();
        for (int i = 1; i < 15; i++) {

            Simulation simulation = new Simulation(i,15-i,2);
            try {
                simulation.simular();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            simulation.generarReportes();
            plotear.agregar(simulation.escribeLinea());
        }
        Serializer serializer = new Serializer();
        serializer.escribeTXT(plotear, "datos.p");
    }
}

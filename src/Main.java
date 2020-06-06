import UImenu.Menu;
import market.admin.Checkout;
import market.admin.Client;

import sim.Simulation;
import util.MinHeap;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(7,8,150,500,2);
        simulation.simular();
        simulation.generarReportes();
    }
}

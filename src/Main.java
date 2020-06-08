

import sim.Simulation;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = new Simulation(7,8,150,2);
        try {
            simulation.simular();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        simulation.generarReportes();
    }
}

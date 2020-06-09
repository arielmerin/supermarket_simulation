
import sim.Simulation;
import serializer.Serializer;
import util.Lista;


public class Main {
    public static void main(String[] args) {
        Lista<String> plotear = new Lista<>();
        for (int i = 1; i < 15; i++) {

            Simulation simulation = new Simulation(i,15-i,2);
            simulation.simular();
            simulation.generarReportes();
            plotear.agregar(simulation.escribeLinea());
        }
        Serializer serializer = new Serializer();
        serializer.escribeTXT(plotear, "datos.dat");
    }
}

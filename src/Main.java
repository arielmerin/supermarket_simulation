import sim.Simulation;
import serializer.Serializer;
import util.Lista;

/**
 * <h1>Main</h1>
 *
 * @author Ariel Merino & Armando Aquino
 * @version 1.0
 */
public class Main {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Lista<String> plotear = new Lista<>();
        for (int i = 1; i < 15; i++) {

            Simulation simulation = new Simulation(i,15-i,2);
            simulation.simulate();
            simulation.getReports();
            plotear.agregar(simulation.writeLine());
        }
        Serializer serializer = new Serializer();
        serializer.writeTXT(plotear, "datos.dat");
    }
}

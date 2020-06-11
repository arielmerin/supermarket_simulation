package sim;

import market.SuperMarket;
import market.admin.Customer;
import serializer.Serializer;
import util.Lista;
import util.generator.ItemBuilder;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * <h1>Simulation</h1>
 *
 * This class models all the behavior of a simulation, from the time a client enters to the time it took to wait.
 * @author Armando Aquino and Ariel Merino
 * @version 1.0
 */
public class Simulation implements Serializable {

    /**
     * Number of total checkouts in the sumermarket
     */
    private int checkouts;

    /**
     * Number of chekouts of type quick
     */
    private int quickCheckouts;

    /**
     * Number of checkouts of type large
     */
    private int largeCheckout;

    /**
     * The object that allows to carry out the operations of a supermarket in this simulation
     */
    private SuperMarket costco;

    /**
     * List of clients that had been attended during the simulation
     */
    private Lista<Customer> customers;

    /**
     * This object will provide us the ability to save all simulation data
     */
    private Serializer serializer;

    /**
     * <h2>Entrada Cliente</h2>
     * Simula la entrada de un cliente al supermercado, en este apartado es donde se emplean los hilos de ejecución
     */
    private class enterClient extends TimerTask implements Serializable{

        /**
         * El cliente que será ingresado en la tarea
         */
        public Customer customerEntrando;

        /**
         * Constructor principal para ingresar el clieente
         */
        public enterClient(){
            this.customerEntrando = costco.genCustomer();
            customers.agregar(customerEntrando);
        }

        /**
         * Permite detenerse lo necesario para que sea atendido un cliente
         */
        public void completarAtencion(){
            try {
                long espera = customerEntrando.getWaitingTime();
                Thread.sleep(espera % 4);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        /**
         * Se permite que el supermercado forme a un cliente en alguna caja
         */
        public void formandoEnCaja(){
            costco.trainCustumer(costco.genCustomer());
        }

        @Override
        public void run() {
            formandoEnCaja();
            completarAtencion();
        }

    }

    /**
     * Default constructor for the class, initialize the supermarket with 13,13 (Average) checkouts
     */
    public Simulation(){
        costco = new SuperMarket(13,13);
        serializer = new Serializer();
        customers = new Lista<>();
    }

    /**
     * Main constructor for the simulation, when it is in use allows to specify the number of different checkouts
     * @param quickCheckouts number of quick checkouts that will be generated
     * @param largeCheckouts number of large checkouts that will be generated
     */
    public Simulation(int quickCheckouts, int largeCheckouts){
        this.largeCheckout = largeCheckouts;
        this.quickCheckouts = quickCheckouts;
        this.checkouts = largeCheckouts + quickCheckouts;
        costco = new SuperMarket(quickCheckouts, largeCheckouts);
        serializer = new Serializer();
        customers = new Lista<>();
    }

    /**
     * It allows access to the supermarket
     * @return supermarket
     */
    public SuperMarket getCostco() {
        return costco;
    }

    /**
     * It generates the reports for this simulation and mkdir for the folders if them aren't created yet
     */
    public void getReports(){
        SimpleDateFormat sdt = new SimpleDateFormat(("dd_MM_yyyy(HH:mm:ss.SSS)"));
        String idFromDate = sdt.format(new Date());

        serializer.makeDir("Reports");
        serializer.makeDir("plot");

        serializer.makeDir("Reports/Tickets");
        serializer.makeDir("Reports/DailyReports");
        serializer.makeDir("Reports/Missing");

        serializer.writeTXT(costco, "Reports/DailyReports"+ "/"+ "[report]"+idFromDate+".txt");
        serializer.writeTXT(costco.reportMissingExistences(), "Reports/Missing"+ "/"+ "[missing]"+idFromDate+".txt");
        serializer.writeTXT(costco.getTickets(), "Reports/Tickets"+ "/"+ "[tickets]" +idFromDate+".txt");
    }

    /**
     * Main operation, it makes the whole job of form up a customer in this simulation
     */
    public void simulate(){
        genRandomProd(150);
        Timer timer = new Timer(true);
        TimerTask entradaClientes = new enterClient();
        timer.schedule(entradaClientes, 0, 50);
        try {
            Thread.sleep(8000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        timer.cancel();
    }

    /**
     * The numerical main method that allows to know the average for each client by waiting.
     * @return the average of time by waiting on a client
     */
    public double averagingTime() {
        int fastClients = 0;
        int largeCustomer = 0;
        double sumLargeTime = 0.0;
        double sumQuickTime = 0.0;

        for (Customer attendedCustomer: costco.getTickets()){
            if (attendedCustomer.getItems() > 20){
                largeCustomer++;
                sumLargeTime += attendedCustomer.getWaitingTime();
            }else {
                fastClients++;
                sumQuickTime += attendedCustomer.getWaitingTime();
            }
        }
        sumLargeTime = sumLargeTime / largeCustomer;
        sumQuickTime = sumQuickTime / fastClients;
        return (sumQuickTime + sumLargeTime)/2;
    }

    /**
     * Allows to observe the information that we need to plot into a graph
     * @return a line with the parameter obtained from this simulation
     */
    public String writeLine() {
        int[] cli = attendedClientsByItems();
        String caden = String.format("%.2f\t%d\t%d\t%d\t%d \n", averagingTime(), quickCheckouts,
                cli[0], cli[1],cli[0] + cli[1] );
        return caden;
    }

    /**
     * Auxiliary method in calculate the number of items in fast and large checkouts
     * @return array with the sum of items
     */
    private int[] attendedClientsByItems(){
        int[] results = new int[2];
        int largeCustomer = 0;
        int fastClients = 0;
        for (Customer attendedCustomer: costco.getTickets()){
            if (attendedCustomer.getItems() > 20){
                largeCustomer++;
            }else {
                fastClients++;
            }
        }
        results[0] = fastClients;
        results[1] = largeCustomer;
        return results;
    }

    /**
     * Random generate the number of products indicated
     * @param products number of products to generate
     */
    public void genRandomProd(int products){
        ItemBuilder itemBuilder = new ItemBuilder();
        Lista<String> texts = new Lista<>();
        for (int i = 0; i < products; i++) {
            texts.agregar(String.valueOf(itemBuilder.next()));
        }
        serializer.writeTXT(texts,"productsGenerated.txt");
    }

    /**
     * This load the list of products provided to supply the warehouse
     * @param path list to be readed
     */
    public void loadProductsList(String path){
        if (path == ""){
            costco.loadProducts("productsGenerated.txt");
        }else {
            costco.loadProducts(path);
        }
    }

}

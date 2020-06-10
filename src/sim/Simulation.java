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
 * @author Armando Aquino and Ariel Merino
 * @version 1.0
 */
public class Simulation implements Serializable {

    /**
     *
     */
    private int checkouts;

    /**
     *
     */
    private int quickCheckouts;

    /**
     *
     */
    private int largeCheckout;

    /**
     *
     */
    private int times;

    /**
     *
     */
    private SuperMarket costco;

    /**
     *
     */
    private Lista<Customer> customers;

    /**
     * Objeto que permitirá guardar archivos y serializarlos
     */
    private Serializer serializer;

    /**
     * <h2>Entrada Cliente</h2>
     *
     */
    private class enterClient extends TimerTask implements Serializable{

        /**
         * el cliente que será ingresado en la tarea
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
    public SuperMarket getCostco() {
        return costco;
    }

    public Simulation(){
        costco = new SuperMarket(13,13);
        serializer = new Serializer();
        customers = new Lista<>();
    }

    /**
     *
     * @param quickCheckouts
     * @param largeCheckouts
     * @param times
     */
    public Simulation(int quickCheckouts, int largeCheckouts, int times){
        this.largeCheckout = largeCheckouts;
        this.quickCheckouts = quickCheckouts;
        this.checkouts = largeCheckouts + quickCheckouts;
        this.times = times;
        costco = new SuperMarket(quickCheckouts, largeCheckouts);
        serializer = new Serializer();
        customers = new Lista<>();
    }

    /**
     *
     * @return
     */
    public void simulate(){
        genRandomProd(150);
        loadProductsList("");
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
     *
     * @return
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
     *
     * @return
     */
    public String writeLine() {
        int[] cli = attendedClientsByItems();
        String caden = String.format("%.2f\t%d\t%d\t%d\t%d\n", averagingTime(), quickCheckouts, cli[0], cli[1],cli[0] + cli[1] );
        return caden;
    }

    public int[] attendedClientsByItems(){
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
     *
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
     *
     * @param products
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
     *
     * @param path
     */
    public void loadProductsList(String path){
        if (path == ""){
            costco.loadProducts("productsGenerated.txt");
        }else {
            costco.loadProducts(path);
        }
    }

}

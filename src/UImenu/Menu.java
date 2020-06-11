package UImenu;

import market.admin.Product;
import market.admin.Warehouse;
import serializer.Serializer;
import sim.Simulation;
import util.Lista;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import static util.Utilities.*;

/**
 * <h1>Menu</h1>
 * This class handles the interaction with the user and the auxiliary methods that depend on it,
 * @author Ariel Merino and Armando Aquino
 * @version 1.0
 */
public class Menu {

    /**
     * Auxiliary object to manage data persistence during the program
     */
    private Serializer serializer;

    /**
     * Main object designed to carry out some simulations run during the program
     */
    private Simulation simulation;

    /**
     * Default constructor that initializes the simulation
     */
    public Menu(){
        serializer = new Serializer();
    }

    /**
     * Main menu, is responsible for directing the user in their decision and diffuses the options
     */
    public void mainMenu(){
        System.out.println("Bienvedidx al supermercado");
        checkExistences();
        boolean continuar = true;
        do {
            System.out.println("\n[1]Modo Administrativo");
            System.out.println("[2]Modo Usuario");
            System.out.println("[3]Salir\n");
            int opcion = getInt("Ingrese la opción deseada: ", "Error, intente de nuevo");
            switch (opcion){
                case 1:
                    adminMenu();
                    break;
                case 2:
                    userMenu();
                    break;
                case 3:
                    System.out.println("¡Hasta pronto!");
                    continuar = false;
                    save();
                    break;
                default:
                    save();
                    System.out.println("Ingrese una opción válida");
                    break;
            }

        }while (continuar);
    }

    /**
     * Allows you to save the object that will be used as a warehouse
     */
    private void save(){
        serializer.write(simulation.getCostco().getAlmacen(), "dataD.ser");
    }

    /**
     * Check if the stocks of any product are still available.
     */
    private void checkExistences(){
        File file = new File("dataD.ser");
        simulation = new Simulation();
        if (file.exists()){
            simulation.getCostco().setMainWarehouse((Warehouse) serializer.read("dataD.ser"));
        }else {
            save();
        }
    }

    /**
     * The menu displayed when they choose the supermarket manager option
     */
    private void adminMenu(){
        boolean continua = true;
        do {
            System.out.println("\n[1]Dar de Alta algún prodcuto");
            System.out.println("[2]Resurtir existencias de algún producto");
            System.out.println("[3]Generar productos aleatorios y cargarlo al inventario");
            System.out.println("[4]Porporcionar un archivo txt y cargarlo al inventario");
            System.out.println("[5]Ver el inventario");
            System.out.println("[6]Regresar al menú principal\n");

            int anInt = getInt("Ingrese la opción deseada: ", "Error, intente de nuevo");

            switch (anInt){
                case 1:
                    simulation.getCostco().toRegisterProduct(addProduct());
                    save();
                    break;
                case 2:
                    if (resurtirProducto()){
                        System.out.println("Se surtío correctamente");
                    }else {
                        System.out.println("intente de nuevo, ocurrió un error");
                    }
                    save();
                    break;
                case 3:
                    int numToGenerate = getInt("Ingrese el número de productos que desea generar(recomendable >5,000): ",
                            "Error, intente de nuevo con un número válido");
                    simulation.genRandomProd(numToGenerate);
                    simulation.loadProductsList("");
                    save();
                    break;
                case 4:
                    String fileToLoad = getStr("\nIngrese el nombre del archivo (.txt) a cargar como almacen: ",
                            "Intente de nuevo, ocurrió un error");
                    simulation.loadProductsList(fileToLoad);
                    save();
                    break;
                case 5:
                    System.out.println(simulation.getCostco().getAlmacen().getWhareHouse());
                    break;
                case 6:
                    continua = false;
                    break;
                default:
                    System.out.println("Ingrese una opción válida");
                    break;
            }
        }while (continua);
    }

    /**
     *
     */
    private void userMenu(){
        boolean conti = true;
        do {
            System.out.println("\n[1]Elaborar una simulación automática");
            System.out.println("[2]Elegir cuántas cajas se quieren simular");
            System.out.println("[3]Regresar el menú principal");
            int answer = getInt("\nIngrese la opción deseada: ", "Error, intente de nuevo");
            switch (answer){
                case 1:
                    Lista<String> plotting = new Lista<>();
                    try{
                        for (int i = 1; i < 15; i++) {
                            Simulation simulationPart = new Simulation(i,15-i);
                            simulationPart.getCostco().setMainWarehouse(simulation.getCostco().getAlmacen());
                            simulationPart.simulate();
                            simulationPart.getReports();
                            plotting.agregar(simulationPart.writeLine());
                            if (i == 1) {
                                System.out.println("\nSimulando compras... espere por favor");
                            }
                            if (i == 14){
                                simulation = simulationPart;
                            }
                        }
                        serializer.writeTXT(plotting, "plot/datos.dat");
                        System.out.println("Terminó con éxito la simulación");
                        execPlot();
                        save();
                    }catch (NullPointerException e){
                        System.out.println("\nError, primero ingrese en el modo administrativo y genere o cargue un almacen");
                    }
                    break;
                case 2:
                    int days = getInt("\nIngrese el número de días que quiere simular ", "Error, ingrese días válidos");
                    Lista<String> data = new Lista<>();
                    for (int i = 0; i < days; i++) {
                        int quickCheckouts = getInt(String.format("Día [%d] de %d\nIngrese el número de cajas rápidas: ", i + 1, days),
                                "Error, intente de nuevo");
                        int largeCheckouts = getInt(String.format("Día [%d] de %d\nIngrese el número de cajas largas: ", i + 1, days),
                                "Error, intente de nuevo");
                        System.out.println("\nSimulando el día " + (i+1));
                        Simulation simulationCas = new Simulation(quickCheckouts, largeCheckouts);
                        simulationCas.getCostco().setMainWarehouse(simulation.getCostco().getAlmacen());
                        simulationCas.simulate();
                        simulationCas.getReports();
                        if (i == (days-1)){
                            simulation = simulationCas;
                        }
                        data.agregar(simulationCas.writeLine());
                    }
                    serializer.writeTXT(data, "plot/datos.dat");
                    System.out.println("\nTerminó con éxito la simulación\n");
                    execPlot();
                    save();
                    break;
                case 3:
                conti = false;
                default:
                    System.out.println("Ingrese una opción válida");
                    break;
            }
        }while (conti);
        System.out.println();
    }

    /**
     * Asks the user if they want to add a new product to the warehouse
     * @return product that was added
     */
    private Product addProduct(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del producto: ");
        String name = scanner.nextLine();
        int units = getInt("Ingrese el número de existencias del producto: ", "Error, intente de nuevo");
        float price = getFloat("Ingrese el precio del producto: ", "Error, intente de nuevo");
        return new Product(units,name,price);
    }

    /**
     * Add the number of stocks of a certain product
     * @return true if it was successful
     */
    private boolean resurtirProducto(){
        int numero = getInt("Ingrese el id del producto a resurtir: ", "Error, intente de nuevo");
        Product inCuestion = new Product(numero);
        if (simulation.getCostco().getAlmacen().getWhareHouse().contiene(inCuestion)){
            int nuevo = getInt("Ingrese el número de elementos a agregar: ", "Error, intente de nuevo");
            return simulation.getCostco().getAlmacen().modifyStock(nuevo, numero);
        }
        return false;
    }

    /**
     * Asks the user if she wants to generate some simulation graphs
     */
    private void execPlot(){
        boolean contAsking = true;
        do {
            System.out.println("\n¿Desea generar las gráficas de los resultados?\n[1]Sí\n[2]No");
            int ans = getInt("\nIngrese la opción deseada: ", "Error, intente de nuevo");
            switch (ans){
                case 1:
                    boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
                    if (!isWindows){
                        try {
                            Date date = new Date();
                            SimpleDateFormat formatter = new SimpleDateFormat(("dd_MM_yyyy(HH_mm_ss_SSS)"));
                            String fec = formatter.format(date);
                            String nameHist = String.format("plot/histogramGraph(%s).png",fec);
                            String nameMain = String.format("plot/mainGraph(%s).png",fec);
                            String orden = "./graph.sh " + nameHist +" " + nameMain;
                            Process processHistogram = Runtime.getRuntime().exec(orden);

                            Process openImgHist = Runtime.getRuntime().exec("mimeopen "+nameHist);
                            Process openImgMain = Runtime.getRuntime().exec("mimeopen "+nameMain);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else {
                        System.out.println("Lo sentimos mucho, para windows no funcionamos así ;( intente en linux :)");
                    }
                    contAsking = false;
                    break;
                case 2:
                    System.out.println("Ok!");
                    contAsking = false;
                    break;
                default:
                    System.out.println("Ingrese una de las opciones válidas");
                    break;
            }
        }while (contAsking);
    }
}

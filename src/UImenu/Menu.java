package UImenu;

import market.SuperMarket;
import market.admin.Product;
import serializer.Serializer;


import java.io.File;
import java.util.Scanner;

import static util.Utilidades.getFloat;
import static util.Utilidades.getInt;

public class Menu {

    /**
     * Tiene todo el comportamiento del supermercado englobado en un objeto
     */
    private SuperMarket walmar = new SuperMarket(5,10,100);
    /**
     * Objeto para manejar la persistencia de datos dentro del programa
     */
    private Serializer serializer = new Serializer();

    /**
     * Método que da la bienvenida al usuario y le presenta el primer menú de opciones
     */
    public void principal(){
        System.out.println("Bienvedidx al supermercado");
        revisaExistencia();
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
                default:
                    System.out.println("Ingrese una opción válida");
                    break;

            }

        }while (continuar);
    }
    /**
     * Delega la tarea de guardar <i> serializar </i> el objeto de tipo arbol que funge como diccionario
     */
    private void guardar(){
        serializer.write(walmar, "dataD.ser");
    }


    /**
     * Se encarga de buscar si existe un archivo con información de diccionario, en caso de encontrarlo lo lee para
     * trabajar con esa información, en otro caso crea un nuevo archivo y lee el archivo txt que se supone
     * que siempre encontrará
     */
    private void revisaExistencia(){
        File archivo = new File("dataD.ser");
        if (archivo.exists()){
            walmar = (SuperMarket) serializer.read("dataD.ser");
        }else {
            guardar();
        }
    }

    /**
     * Opción secundaria del menú donde se permite a la usuaria dar de alta algún producto o resurtir alguna existencia
     */
    private void adminMenu(){
        boolean continua = true;
        do {
            System.out.println("\n[1]Dar de Alta algún prodcuto");
            System.out.println("[2]Resurtir existencias de algún producto");
            System.out.println("[3]Ver el inventario");
            System.out.println("[4]Regresar al menú principal\n");

            int respuesta = getInt("Ingrese la opción deseada: ", "Error, intente de nuevo");

            switch (respuesta){
                case 1:
                    walmar.darAltaProducto(ingresarProducto());
                    guardar();
                    break;
                case 2:
                    if (resurtirProducto()){
                        System.out.println("Se surtío correctamente");
                    }else {
                        System.out.println("intente de nuevo, ocurrió un error");
                    }
                    guardar();
                    break;
                case 3:
                    System.out.println(walmar.getAlmacen().getAlmacen());
                    break;
                case 4:
                    continua = false;
                    break;
                default:
                    System.out.println("Ingrese una opción válida");
                    break;
            }
        }while (continua);
    }

    /**
     * Reparte la tarea de mostrar el menú secundario, en su opción para un usuario promedio, las opciones que tiene.
     */
    private void userMenu(){
        boolean conti = true;
        do {
            System.out.println("[1]Elaborar una simulación automática");
            System.out.println("[2]Elegir cuántas cajas se quieren simular");
            System.out.println("[3]Regresar el menú principal");
            int respuesta = getInt("Ingrese la opción deseada: ", "Error, intente de nuevo");
            switch (respuesta){
                case 1:
                    System.out.println("Simulando compras... espere por favor");
                    break;
                case 2:
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
     * Permite que sea añadido un producto, pide a la usuaria los datos necesarios para crear un nuevo product
     * @return regresa el producto creado con los datos proporcionados
     */
    private Product ingresarProducto(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();
        int unidades = getInt("Ingrese el número de existencias del producto: ", "Error, intente de nuevo");
        float precio = getFloat("Ingrese el precio del producto: ", "Error, intente de nuevo");

        return new Product(unidades,nombre,precio);
    }

    private boolean resurtirProducto(){
        int numero = getInt("Ingrese el id del producto a resurtir: ", "Error, intente de nuevo");
        Product enCuestion = new Product(numero);
        if (walmar.getAlmacen().getAlmacen().contiene(enCuestion)){
            int nuevo = getInt("Ingrese el número de elementos a agregar: ", "Error, intente de nuevo");
            return walmar.getAlmacen().modificarExistencias(nuevo, numero);
        }
        return false;
    }
}

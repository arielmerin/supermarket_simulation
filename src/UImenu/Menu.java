package UImenu;

import market.SuperMarket;
import market.admin.Product;

import java.util.Scanner;

import static util.Utilidades.getFloat;
import static util.Utilidades.getInt;

public class Menu {

    SuperMarket walmar = new SuperMarket();



    public void principal(){
        System.out.println("Bienvedidx al supermercado");
        secundMenu();
    }



    private void secundMenu(){
        System.out.println("[1]Dar de Alta algún prodcuto");
        System.out.println("[2]Resurtir existencias de algún producto");
        int respuesta = getInt("Ingrese la opción deseada: ", "Error, intente de nuevo");

        switch (respuesta){
            case 1:
                walmar.darAlraProducto(ingresarProducto());
                break;

            default:
                System.out.println("Ingrese una opción válida");
                break;
        }
    }



    private Product ingresarProducto(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el nombre del producto:");
        String nombre = scanner.nextLine();
        int id = getInt("Ingrese el id del producto: ", "Error, intente de nuevo");
        int unidades = getInt("Ingrese el número de existencias del producto: ", "Error, intente de nuevo");
        float precio = getFloat("Ingrese el precio del producto: ", "Error, intente de nuevo");


        Product nuevo = new Product(id,unidades,nombre,precio);
        return nuevo;
    }
}

package util;

import java.util.Scanner;

/**
 * <h1>Utilidades</h1>
 * En esta clase se tiene la funcionalidad de contar con metodos auxiliares a la clase UIMenu pues  para el ingreso
 * de datos y la validacion de los mismos ha sido necesario implementar esta clase
 * @author Ariel Merino Peña, Armando Aquino Chapa
 * @version 1
 */
public class Utilidades {
    /**
     * Este método sirve para controlar que en las entradas de enteros
     * lo único que se pueda ingresar sean justo sólo valores numéricos y nada de cadenas
     *
     * @param msg mensaje de instrucciones al usuario o indicaciones
     * @param error mensaje de error al detectar que la entrada no es un valor nummérico
     *
     * @return entero que validó y ahora puede ser utilizado
     */
    public static int getInt(String msg, String error){
        int entero = 0;
        Scanner scan = new Scanner(System.in);
        String librearBuffer;
        boolean conti = true;
        do{
            System.out.print(msg);
            if(scan.hasNextInt())
            {
                entero = scan.nextInt();
                if (entero > 0){
                    conti = false;
                }
            }else{
                librearBuffer = scan.next();
                System.out.println(error);
            }
        }while(conti);
        return entero;
    }

    /**
     * Este metodo se encarga de pedirle al usuario a traves de la terminal una cadena que sera modificada, se le
     * reemplazaran todos los caracteres que no sean del alfabeto (ya sea minusculas o mayusculas) por el caracter vacio
     * si llegara a ser el caso que solo se ingresar caracteres que no son los buscados entonces el metodos se seguira
     * ejecutand pidiendole al usuario que vuelva a ingresar una cadena
     *
     * @param mensaje Donde se le solicita al usuario ingrese, se le dan instrucciones
     * @param error aparece cuando la entrada no es valida
     * @return cadena con valores validos para el programa
     */
    public static String getStr(String mensaje, String error){
        Scanner sc = new Scanner(System.in);
        String cadena = null;
        boolean continuar = false;
        do {
            System.out.print(mensaje);
            cadena = sc.nextLine().replaceAll("[^a-zA-ZÀ-ÿ\u00f1\u00d1 ]", "");
            if (cadena.equals("")){
                continuar = true;
                System.out.println(error);
            }else{
                return cadena;
            }
        }while (continuar);
        return cadena;
    }

}
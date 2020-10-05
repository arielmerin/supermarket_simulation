package market;

public class StringCutter {


    public static String cutAString(String cadena){
        StringBuilder str = new StringBuilder();
        str.append(cadena.substring(14,25));
        return str.reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(cutAString("Soy alumno de Tecnologías para Desarrollos en Internet"));
    }




}
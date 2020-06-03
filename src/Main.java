import UImenu.Menu;
import market.SuperMarket;
import market.admin.Client;
import market.admin.Product;


public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        //menu.principal();
        SuperMarket superMarket = new SuperMarket(3,12,15);
        superMarket.getWharehouse().agregarProducto(new Product(25, "algas", (float) 583.6));
        superMarket.getWharehouse().agregarProducto(new Product(13, "marinas", 4));
        Client client = new Client();

        System.out.println(superMarket.getWharehouse().getAlmacen());

        superMarket.asignaProducto(client, 2, 11);
        superMarket.asignaProducto(client, 1, 3);
        System.out.println(superMarket.getWharehouse().modificarExistencias(-11,2));
        System.out.println(superMarket.getWharehouse().modificarExistencias(-3,1));


        System.out.println(superMarket.getWharehouse().getAlmacen());

        System.out.println(client);

    }
}

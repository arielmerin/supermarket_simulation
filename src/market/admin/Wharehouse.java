package market.admin;

import util.ArbolAVL;
import util.Lista;
import java.io.Serializable;
import java.util.Iterator;

/**
 * <h1>Almacén</h1>
 * Esta clase se encarga de modelar las operaciones que llevaría a cabo algún almacen de un supermercado
 * con acciones para surtir el almacen y para retirar productos
 */
public class Wharehouse implements Serializable {

    /**
     *
     */
    private static int contador;

    /**
     *
     */
    private ArbolAVL<Product> almacen;

    /**
     *
     */
    public Wharehouse() {
        almacen = new ArbolAVL<>();
    }

    /**
     * Este método modifica el número de elementos de algún producto que haya en nuestro alamacen
     * @param cantidad
     * @param
     * @return
     */
    public boolean modificarExistencias(int cantidad, int id){
        Product product = new Product(id);
        if (product.getUnits() - cantidad < 0 && cantidad < 0) {
            return false;
        }
        actualizar(product,almacen.busquedaElemento(product).getUnits() + cantidad);
        return true;
    }

    /**
     *
     * @param product
     * @param valor
     */
    public void actualizar(Product product, int valor ){
        Product product1 = almacen.busquedaElemento(product);
        product1.setUnits(valor);
    }
    /**
     * Dado un producto permite agregarlo al arbol asignándole un número de id único
     * @param product producto que será añadido
     */
    public void agregarProducto(Product product){
        contador = almacen.getTamanio();
        product.setId(++contador);
        almacen.agrega(product);
    }

    /**
     * Permite acceder al objeto que almacena
     * @return
     */
    public ArbolAVL<Product> getAlmacen() {
        return almacen;
    }

    /**
     *
     * @return
     */
    public Lista<String> pocasExistencias(){
        Lista<String> faltantes = new Lista<>();
        Iterator it = almacen.iterator();
        while (it.hasNext()){

            Product elemento = (Product) it.next();
            if ( elemento.getUnits() > 0 && elemento.getUnits() < 10 ){
                faltantes.agregar(String.valueOf(elemento) + "\n");
            }
        }
        return faltantes;
    }

    /**
     *
     * @return
     */
    public Lista<String> faltantes(){
        Lista<String> falta = new Lista<>();
        Iterator it = almacen.iterator();
        while (it.hasNext()){
            Product elemento = (Product) it.next();
            if ( elemento.getUnits() == 0){
                falta.agregar(String.valueOf(elemento) + "\n");
            }
        }
        return falta;
    }
}

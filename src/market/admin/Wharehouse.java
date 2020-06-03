package market.admin;

import util.ArbolAVL;

/**
 * <h1>Almacén</h1>
 * Esta clase se encarga de modelar las operaciones que llevaría a cabo algún almacen de un supermercado
 * con acciones para surtir el almacen y para retirar productos
 */
public class Wharehouse {


    private ArbolAVL<Product> almacen;

    public Wharehouse() {
        almacen = new ArbolAVL<>();
    }


    /**
     * Este método modifica el número de elementos de algún producto que haya en nuestro alamacen
     * @param cantidad
     * @param product
     * @return
     */
    public boolean modificarExistencias(int cantidad, Product product){
        if (almacen.contiene(product)){
            if (product.units <= cantidad){
                return false;
            }
            Product nuevo = product;
            nuevo.setUnits(product.units - cantidad);
            almacen.elimina(product);
            almacen.agrega(nuevo);
            return true;
        }
        return false;
    }

    public void agregarProducto(Product product){
        almacen.agrega(product);
    }

}

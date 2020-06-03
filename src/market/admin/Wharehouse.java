package market.admin;

import util.ArbolAVL;

public class Wharehouse {


    private ArbolAVL<Product> almacen;

    public Wharehouse(){
        almacen = new ArbolAVL<>();
    }


    public void agregarProdctos(Product product){
        almacen.agrega(product);
    }

    public boolean retirarProducto(int cantidad, Product product){
        return false;
    }

}

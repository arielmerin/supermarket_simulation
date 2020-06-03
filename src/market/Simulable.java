package market;

import java.io.Serializable;

public interface Simulable extends Serializable {


    double simular(int cajas, int clientes);

    double promediar(int veces, int cajas, int clientes);

}

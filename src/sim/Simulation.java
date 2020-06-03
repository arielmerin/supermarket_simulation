package sim;

import market.Simulable;
import market.SuperMarket;
import market.admin.Plotable;
import util.Lista;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

public class Simulation implements Simulable, Plotable {

    private Lista<Plotable> plotables;
    private int cajas;
    private int clientes;
    private int veces = 50;
    private SuperMarket costco;
    private  int costo;


    public Simulation(int cajas, int clientes){
        this.cajas = cajas;
        this.clientes = clientes;
        costco = new SuperMarket(14,1,100);
    }


    public Simulation(int cajas, int clientes, int veces){
        this.cajas = cajas;
        this.clientes = clientes;
        this.veces = veces;
    }


    @Override
    public double simular(int cajas, int clientes){
        int contador = 0;
        for (int i = 0; i < veces; i++) {
            contador += costco.getTotalVentas();
        }
        return contador;
    }

    @Override
    public double promediar(int veces, int cajas, int clientes) {

        return 0;
    }

    @Override
    public String escribeLinea() {
        return clientes + "  " + costo ;
    }


    @Override
    public void guarda(BufferedWriter out) throws IOException {
        Iterator it = plotables.iterator();
        while (it.hasNext()){
            Plotable elem = (Plotable)it.next();
            out.write(elem.escribeLinea());
        }
    }
}

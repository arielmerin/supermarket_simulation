package market.admin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public interface Plotable {

    /**
     * Regresa el registro serializado en una línea de texto. La línea de texto
     * que este método regresa debe ser aceptada por el método.
     * @return la serialización del registro en una línea de texto.
     */
    String escribeLinea();


    void guarda(BufferedWriter out) throws IOException;
}

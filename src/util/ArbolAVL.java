package util;

import java.io.Serializable;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus nodos, la diferencia entre
 * la altura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T> & Serializable> extends ArbolBinarioBusqueda<T> implements Serializable {

    /**
     * Clase interna protegida para nodos de árboles AVL. La única diferencia
     * con los nodos de árbol binario, es que tienen una variable de clase
     * para la altura del nodo.
     */
    public class NodoAVL extends Nodo implements Serializable {

        /** La altura del nodo. */
        public int altura;
        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del nodo.
         */
        public NodoAVL(T elemento) {
            super(elemento);
            this.altura = 0;
        }
        /**
         * Regresa la altura del nodo.
         * @return la altura del nodo.
         */
        @Override
        public int altura() {
            return this.altura;
        }
        /**
         * Regresa una representación en cadena del nodo AVL.
         * @return una representación en cadena del nodo AVL.
         */
        @Override
        public String toString() {
            return super.toString();
        }
        /**
         * Compara el nodo con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param o el objeto con el cual se comparará el nodo.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link NodoAVL}, su elemento es igual al elemento de éste
         *         nodo, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object o) {
            return super.equals(o);
        }
    }
    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioBusqueda}.
     */
    public ArbolAVL() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolAVL(Coleccionable<T> coleccion) {
        super(coleccion);
    }
    /**
     * Construye un nuevo nodo, usando una instancia de {@link NodoAVL}.
     * @param elemento el elemento dentro del nodo.
     * @return un nuevo nodo con el elemento recibido dentro del mismo.
     */
    @Override
    protected NodoAVL nuevoNodo(T elemento) {
        return new NodoAVL (elemento);
    }
    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioBusqueda#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        Nodo<T> nuevo = this.getFinal();
        this.rebalanceo((NodoAVL) nuevo);
    }
    /**
     * Método privado que rebalance el árbol.
     */
    private void rebalanceo(NodoAVL nodo) {
        if(nodo != null){
            getAltura(nodo);
            NodoAVL izq = (NodoAVL) nodo.izquierdo;
            NodoAVL der = (NodoAVL) nodo.derecho;
            int balance = getAltura(izq)-getAltura(der);
            if(balance == -2) {
                if ((getAltura((NodoAVL) der.izquierdo) - getAltura((NodoAVL) der.derecho)) == 1) {
                    super.giraDerecha(der);
                    getAltura(der);
                    getAltura((NodoAVL) der.padre);
                }
                super.giraIzquierda(nodo);
                getAltura(nodo);
            }
            if(balance ==2){
                if ((getAltura((NodoAVL) izq.izquierdo) - getAltura((NodoAVL) izq.derecho)) == -1) {
                    super.giraIzquierda(izq);
                    getAltura(izq);
                    getAltura((NodoAVL)izq.padre);
                }
                super.giraDerecha(nodo);
                getAltura(nodo);
            }
            rebalanceo((NodoAVL)nodo.padre);
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el nodo que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        Nodo<T> nuevo = buscaNodo(raiz, elemento);
        super.elimina(elemento);
        rebalanceo((NodoAVL) nuevo);
    }
    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param nodo el nodo sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(Nodo<T> nodo) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                "girar a la izquierda por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param nodo el nodo sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(Nodo<T> nodo) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                "girar a la derecha por el usuario.");
    }
    private int getAltura(NodoAVL nodo){
        if (nodo == null){
            return -1;
        }
        nodo.altura = Math.max(this.getAltura((NodoAVL)nodo.izquierdo), this.getAltura((NodoAVL)nodo.derecho)) + 1;
        return nodo.altura;
    }
    public ArbolAVL(Lista<T> coercion){
        for(T elemento : coercion){
            this.agrega(elemento);
        }
    }

}

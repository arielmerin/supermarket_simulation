package util;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para modelar la estructura de datos Arbol Binario</p>
 * <p>Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.</p>
 * <p>Puesto que todos los árboles binarios comparten algunas características similares,
 * esta clase sirve perfectamente para modelarlas. Sin embargo no es lo suficientemente
 * específica para modelar algun árbol completamente. Por lo que la implementación
 * final depende de las clases concretas que hereden de ésta.</p>
 * @author Alejandro Hernández Mora
 * @version 1.0
 */

public abstract class ArbolBinario<T> implements Coleccionable<T>, Serializable {
    /**
     * Clase interna protegida para nodos.
     */
    protected class Nodo<T> implements Serializable {

        /** El elemento del nodo. */
        public T elemento;
        /** Referencia a los nodos padre, e hijos. */
        public Nodo<T> padre, izquierdo, derecho;

        /**
         * Constructor único que recibe un elemento.
         *
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }

        /**
         * Nos dice si el nodo tiene un padre.
         *
         * @return <tt>true</tt> si el nodo tiene padre,
         * <tt>false</tt> en otro caso.
         */
        public boolean hayPadre() {
            return padre != null;
        }

        /**
         * Nos dice si el nodo tiene un izquierdo.
         *
         * @return <tt>true</tt> si el nodo tiene izquierdo,
         * <tt>false</tt> en otro caso.
         */
        public boolean hayIzquierdo() {
            return izquierdo != null;
        }

        /**
         * Nos dice si el nodo tiene un derecho.
         *
         * @return <tt>true</tt> si el nodo tiene derecho,
         * <tt>false</tt> en otro caso.
         */
        public boolean hayDerecho() {
            return derecho != null;
        }

        /**
         * Regresa la altura del nodo.
         *
         * @return la altura del nodo.
         */
        public int altura() {
            if (elemento == null) {
                return -1;
            } else if (tamanio == 1) {
                return 1;
            } else {
                int der = 0;
                int izq = 0;
                if (hayDerecho())
                    der = derecho.altura();
                if (hayIzquierdo())
                    izq = izquierdo.altura();
                return 1 + Math.max(der, izq);
            }
        }

        /**
         * Regresa el elemento al que apunta el nodo.
         *
         * @return el elemento al que apunta el nodo.
         */
        public T get() {
            return elemento;
        }

        /**
         * Compara el nodo con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Nodo} deben
         * sobrecargar el método {@link Nodo#equals}.
         *
         * @param o el objeto con el cual se comparará el nodo.
         * @return <code>true</code> si el objeto es instancia de la clase
         * {@link Nodo}, su elemento es igual al elemento de éste
         * nodo, y los descendientes de ambos son recursivamente
         * iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object o) {
            Nodo aux = (Nodo) o;
            if (this == null && o == null) {
                return true;
            } else if (this == null || o == null) {
                return false;
            } else if (elemento.equals(aux.elemento)) {
                return izquierdo.equals(aux.izquierdo) && derecho.equals(aux.derecho);
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Nodo<T> raiz;
    /** El número de elementos */
    protected int tamanio;

    /**
     * Constructor sin parámetros.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccionable<T> coleccion) {
        for(T elem : coleccion){
            this.agrega(elem);
        }
    }

    /**
     * Construye un nuevo nodo, usando una instancia de {@link Nodo}. Para
     * crear nodos se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de nodos.
     * @param elemento el elemento dentro del nodo.
     * @return un nuevo nodo con el elemento recibido dentro del mismo.
     */
    protected Nodo<T> nuevoNodo(T elemento) {
        return new Nodo<T>(elemento);
    }


    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        return raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override
    public int getTamanio() {
        return tamanio;
    }


    /**
     * Regresa el nodo que contiene la raíz del árbol.
     * @return el nodo que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public Nodo<T> raiz() {
        if(! esVacio()){
            return raiz;
        }else{
            throw new NoSuchElementException("No hay elementos en el árbol");
        }
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean esVacio() {
        return raiz == null && tamanio == 0;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    public void limpia() {
        tamanio = 0;
        raiz = null;
    }


    /**
     * Regresa una Cola con el los elementos en inorden del árbol.
     * @return Cola con los elementos del arbol.
     */
    public Lista<T> inOrden(){
        Lista inorden = new Lista();
        inordenAux(raiz, inorden);
        return inorden;
    }

    /**
     * Regresa una Cola con el los elementos en inorden del árbol.
     * @return Cola con los elementos del arbol.
     */

    public Lista<T> preOrden(){
        Lista preorden = new Lista();
        preordenAux(raiz, preorden);
        return preorden;
    }
    /**
     * Regresa una Cola con el los elementos en inorden del árbol.
     * @return Cola con los elementos del arbol.
     */
    public Lista<T> postOrden(){
        Lista postorden = new Lista();
        postordenAux(raiz, postorden);
        return postorden;
    }

    private void inordenAux(Nodo<T> n,Lista<T> l){
        if (n != null){
            inordenAux(n.izquierdo, l);
            l.agregar(n.elemento);
            inordenAux(n.derecho,l);
        }
    }

    private void preordenAux(Nodo<T> n, Lista<T> l){
        if (n != null){
            l.agregar(n.elemento);
            preordenAux(n.izquierdo, l);
            preordenAux(n.derecho, l);
        }
    }

    private void postordenAux(Nodo<T> n, Lista<T> l){
        if (n != null){
            postordenAux(n.izquierdo, l);
            postordenAux(n.derecho, l);
            l.agregar(n.elemento);
        }
    }


    /**
     * Compara el árbol con un objeto.
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o) {
        return raiz.equals((Nodo)o);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if (raiz == null)
            return "";

        boolean[] r = new boolean[altura()+1];
        for (int i = 0; i < altura()+1; i++)
            r[i] = false;
        return cadena(raiz, 0, r);

    }

    private String cadena(Nodo v, int n, boolean[] r) {
        String s = v + "\n";
        r[n] = true;
        if (v.izquierdo != null && v.derecho != null) {
            s += dibujaEspacios(n, r);
            s += "├─›";
            s += cadena(v.izquierdo, n+1, r);
            s += dibujaEspacios(n, r);
            s += "└─»";
            r[n] = false;
            s += cadena(v.derecho, n+1, r);
        } else if (v.izquierdo != null) {
            s += dibujaEspacios(n, r);
            s += "└─›";
            r[n] = false;
            s += cadena(v.izquierdo, n+1, r);
        } else if (v.derecho != null) {
            s += dibujaEspacios(n, r);
            s += "└─»";
            r[n] = false;
            s += cadena(v.derecho, n+1, r);
        }
        return s;
    }


    private String dibujaEspacios(int n, boolean[] r) {
        String s = "";
        for (int i = 0; i < n; i++)
            if (r[i])
                s += "│  ";
            else
                s += "   ";
        return s;
    }
}

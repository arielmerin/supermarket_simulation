package util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para modelar árboles binarios de búsqueda genéricos.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> implements Serializable {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los nodos en DFS in-order. */
        private Pila<Nodo> pila;

        /* Construye un iterador con el nodo recibido. */
        public Iterador() {
            pila = new Pila<>();
            Nodo aux = raiz;
            while(aux != null){
                pila.agrega(aux);
                aux = aux.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !pila.esVacio();

        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() {
            if(! pila.esVacio()){
                Nodo aux = pila.pop();
                T e = (T) aux.elemento;
                aux= aux.derecho;
                while(aux != null){
                    pila.push(aux);
                    aux = aux.izquierdo;
                }
                return e;
            }else{
                throw new NoSuchElementException("No hay elementos");
            }
        }
    }


    /**
     * Constructor que no recibe parámeteros. {@link ArbolBinario}.
     */
    public ArbolBinarioBusqueda() {

    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */

    public ArbolBinarioBusqueda(Coleccionable<T> coleccion) {
        super(coleccion);
    }

    protected void agregaNodo(Nodo<T> n, Nodo<T> nuevo){
        if(n.elemento.compareTo(nuevo.elemento) >= 0){
            if (n.hayIzquierdo()){
                agregaNodo(n.izquierdo, nuevo);
            }else {
                n.izquierdo = nuevo;
                nuevo.padre = n;
            }
        }else {
            if (n.hayDerecho()){
                agregaNodo(n.derecho, nuevo);
            }else {
                n.derecho = nuevo;
                nuevo.padre = n;
            }
        }
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        Nodo nuevo = this.nuevoNodo(elemento);
        this.last = nuevo;
        tamanio++;
        if (raiz != null){
            this.agregaNodo(raiz, nuevo);
        }else {
            this.raiz = nuevo;
        }
    }

    protected Nodo<T> eliminaNodo(Nodo<T> n){
        if(!n.hayPadre() && !n.hayIzquierdo() && !n.hayDerecho()){ //n solo es la raíz y no tiene hijos
            limpia();
        }else if(!n.hayIzquierdo() && !n.hayDerecho()){  // n es una hoja
            if(n.padre.hayIzquierdo() && n.padre.izquierdo ==n){
                n.padre.izquierdo = null;
            }else{
                n.padre.derecho = null;
            }
        }else if(n.hayIzquierdo()){ //n tiene hijo izquierdo
            if(n.padre.hayIzquierdo() && n.padre.izquierdo == n){
                n.padre.izquierdo = n.izquierdo;
                n.izquierdo.padre = n.padre;

            }else{
                n.padre.derecho = n.izquierdo;
                n.izquierdo.padre = n.padre;
            }
        }else if(n.hayDerecho()){ //n tiene hijo derecho
            if (n.padre.hayIzquierdo() && n.padre.izquierdo == n){
                n.padre.izquierdo = n.derecho;
                n.derecho.padre = n.padre;
            }else {
                n.padre.derecho = n.derecho;
                n.derecho.padre = n.padre;
            }
        }else if(!n.hayPadre() && n.hayIzquierdo()){ // n es raíz y tiene hijo izquierdo
            raiz = n.izquierdo;
            n.izquierdo.padre = null;
        }else if(!n.hayPadre() && n.hayDerecho()){ //n es raiz y tiene hijo derecho
            raiz = n.derecho;
            n.derecho.padre = null;
        }
        return null;
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        if(!contiene(elemento)){
            return;
        }
        Nodo aux = buscaNodo(raiz,elemento);
        tamanio--;
        if (aux.hayDerecho() && aux.hayIzquierdo()){
            Nodo maximo = maximoEnSubarbolIzquierdo(aux.izquierdo);
            T elem = (T) maximo.elemento;
            aux.elemento = elem;
            eliminaNodo(maximo);
        }else{
            eliminaNodo(aux);
        }
    }


    private Nodo maximoEnSubarbolIzquierdo(Nodo n){
        if (n.hayDerecho()){
            return maximoEnSubarbolIzquierdo(n.derecho);
        }
        return n;
    }

    /**
     * Nos dice si un elemento está contenido en el arbol.
     * @param elemento el elemento que queremos verificar si está contenido en
     *                 la arbol.
     * @return <code>true</code> si el elemento está contenido en el arbol,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento){
        return buscaNodo(raiz, elemento) != null;
    }

    protected Nodo<T> buscaNodo(Nodo<T> n, T elemento){
        if (n == null){
            return null;
        }
        if (n.elemento.equals(elemento)){
            return n;
        }
        Nodo izq = buscaNodo(n.izquierdo, elemento);
        Nodo der = buscaNodo(n.derecho, elemento);
        return izq != null ? izq : der;
    }

    public Nodo<T> buscando(T elemento){
        return buscaNodo(raiz,elemento);
    }


    /**
     * Gira el árbol a la derecha sobre el nodo recibido. Si el nodo no
     * tiene hijo izquierdo, el método no hace nada.
     * @param nodo el nodo sobre el que vamos a girar.
     */
    public void giraDerecha(Nodo<T> nodo) {
        if (!nodo.hayIzquierdo()){
            return;
        }
        Nodo aux = nodo.izquierdo;
        aux.padre = nodo.padre;
        if (aux == raiz){
            raiz = nodo;
        }else {
            if (nodo.padre.izquierdo == nodo){
                nodo.padre.izquierdo = aux;
            }else {
                nodo.padre.derecho = aux;
            }
        }
        nodo.izquierdo = aux.derecho;
        if (aux.hayDerecho()){
            aux.derecho.padre = nodo;
        }
        aux.derecho = nodo;
        nodo.padre = aux;
    }

    /**
     * Gira el árbol a la izquierda sobre el nodo recibido. Si el nodo no
     * tiene hijo derecho, el método no hace nada.
     * @param nodo el nodo sobre el que vamos a girar.
     */
    public void giraIzquierda(Nodo<T> nodo) {
        if (!nodo.hayDerecho()){
            return;
        }
        Nodo aux = nodo.derecho;
        aux.padre = nodo.padre;
        if (nodo == raiz){
            raiz = aux;
        }else{
            if (nodo.padre.izquierdo == nodo){
                nodo.padre.izquierdo = aux;
            }else {
                nodo.padre.derecho = aux;
            }
        }
        nodo.derecho = aux.izquierdo;
        if (aux.hayIzquierdo()){
            aux.izquierdo.padre = nodo;
        }
        aux.izquierdo = nodo;
        nodo.padre = aux;
    }
    protected Nodo last;

    public Nodo getFinal() {
        return last;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

}
package util;

import java.util.Iterator;

/**
 * <p> Clase concreta para modelar la estructura de datos util.Lista</p>
 * <p>Esta clase implementa una util.Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * @author Kevin Ariel Merino Peña 317031326
 * @version 1.0
 */
public class Lista<T> implements Listable<T>, Iterable<T>{

    /**
     * Clase interna para construir la estructura
     **/
    private class Nodo{
        /**
         *  Referencias a los nodos anterior y siguiente
         **/
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento){
            this.elemento=elemento;
        }

        @Override
        public String toString() {
            return elemento.toString();
        }
    }

    private class IteradorLista<T> implements Iterator<T>{

        private Nodo siguiente;

        public IteradorLista(){
            siguiente = cabeza;
        }
        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        @Override
        public T next() {
            if(hasNext()){
                T elemento = (T) siguiente.elemento;
                siguiente = siguiente.siguiente;
                return elemento;
            }
            return null;
        }

        @Override
        public void remove() {
            Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
        }

    }

    /* Atributos de la lista */
    private Nodo cabeza;
    private Nodo cola;
    private int longitud;

    /**
     *  Constructor por omisión de la clase, no recibe parámetros.
     *  Crea una nueva lista con longitud 0.
     **/
    public Lista(){
        longitud =0;
        cabeza = cola = null;
    }

    /**
     *  Constructor copia de la clase. Recibe una lista con elementos.
     *  Crea una nueva lista exactamente igual a la que recibimos como parámetro.
     **/
    public Lista( Lista<T> l){
        Lista<T> nueva = l.copia();
        this.longitud = nueva.longitud;
        this.cabeza = nueva.cabeza;
        this.cola = nueva.cola;
    }

    /**
     *  Constructor de la clase que recibe parámetros.
     *  Crea una nueva lista con los elementos de la estructura iterable que recibe como parámetro.
     **/
    public Lista(Iterable<T> iterable){
        longitud = 0;
        cabeza = cola = null;
        for(T elem: iterable)
            agregar(elem);
    }

    /**
     * Método que nos dice si las lista está vacía.
     * @return <code>true</code> si el conjunto está vacío, <code>false</code>
     * en otro caso.
     */
    public boolean esVacia(){
        return longitud == 0;
    }
    /**
     * Método para eliminar todos los elementos de una lista
     */
    public void vaciar(){
        cabeza = cola = null;
        longitud = 0;
    }
    /**
     * Método para obtener el tamaño de la lista
     * @return tamanio Número de elementos de la lista.
     **/
    public int longitud(){
        return longitud;
    }
    /**
     * Método para agregar un elemento a la lista.
     * @param elemento Objeto que se agregará a la lista.
     */
    @Override
    public void agregar(T elemento){
        if (elemento == null){
            return;
        }
        agregarAlFinal(elemento);
    }

    /**
     * Método para agregar al inicio un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlInicio(T elemento){
        if (elemento == null){
            return;
        }
        Nodo e = new Nodo(elemento);
        if (esVacia()){
            agregarAlFinal(elemento);
        } else {
            cabeza.anterior = e;
            e.siguiente = cabeza;
            cabeza = e;
            longitud++;
        }

    }
    /**
     * Método para agregar al final un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento){
        if (elemento == null)
            return;
        Nodo e = new Nodo(elemento);
        if (esVacia()){
            cabeza = cola = e;
        }else {
            cola.siguiente = e;
            e.anterior = cola;
            cola = e;
        }
        longitud++;
    }
    /**
     * Método para verificar si un elemento pertenece a la lista.
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro caso.
     */
    public boolean contiene(T elemento){
        Iterator i = iterator();
        while (i.hasNext()){
            if (i.next().equals(elemento))
                return true;
        }
        return false;
    }
    /**
     * Método para eliminar un elemento de la lista.
     * @param elemento Objeto que se eliminara de la lista.
     */
    public void eliminar(T elemento){
        if (!contiene(elemento))
            return;
        Nodo elimina = search(elemento, cabeza);
        if (elimina == cabeza){
            cabeza = cabeza.siguiente;
            if (cabeza != null){
                cabeza.anterior = null;
            }
        } else if(elimina == cola){
            cola = cola.anterior;
            cola.siguiente = null;
        }else{
            elimina.anterior.siguiente = elimina.siguiente;
            elimina.siguiente.anterior = elimina.anterior;
        }
    }

    /**
     * Metodo para buscar un nodo en una lista
     * @param element Elemento a buscar
     * @param pivote Desde donde se va a empezar la busqueda
     * @return Nodo encontrado
     */
    private Nodo search(T element, Nodo pivote){
        while (pivote != null){
            if (pivote.elemento.equals(element)){
                return pivote;
            }else {
                pivote = pivote.siguiente;
            }
        }
        return null;
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera
     * aparición del <code> elemento</code>.
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en ésta.
     */
    public int indiceDe(T elemento){
        if (!contiene(elemento)){
            return -1;
        }
        Iterator it = iterator();
        int conteo = 1;
        while (it.hasNext() && !it.next().equals(elemento)){
            conteo++;
        }
        return conteo;
    }

    /**
     * Método que nos dice en qué posición está un elemento en la lista
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista,
     * <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    public T getElemento(int i)throws IndexOutOfBoundsException{
        if (i > longitud || i <= 0)
            throw new IndexOutOfBoundsException("Esta fuera del rango");
        Iterator it = iterator();
        int conteo = 0;
        while (it.hasNext() && i > conteo){
            conteo++;
            if (i == conteo){
                return (T)it.next();
            }
        }
        return null;
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * @return Una copia con la lista l revés.
     */
    public Lista<T> reversa(){
        Lista<T> rev = new Lista<>();
        Iterator it = iterator();
        while (it.hasNext()){
            rev.agregarAlInicio((T)it.next());
        }
        return rev;
    }

    /**
     * Método que devuelve una copi exacta de la lista
     * @return la copia de la lista.
     */
    public Lista<T> copia(){
        Lista<T> nueva = new Lista<>();
        nueva.cabeza = cabeza;
        return nueva;
    }

    /**
     * Método que nos dice si una lista es igual que otra.
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Lista)) return false;
        Lista<T> l = (Lista<T>) o;
        if (longitud != l.longitud) return false;
        Nodo aux = cabeza;
        Nodo aux1 = l.cabeza;
        while (aux != null){
            if (! aux.elemento.equals(aux1.elemento)){
                return false;
            }
            aux = aux.siguiente;
            aux1 = aux1.siguiente;
        }
        return true;
    }

    /**
     * Método que devuelve un iterador sobre la lista
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public Iterator<T> iterator(){
        return new IteradorLista();
    }

    /**
     * Método que devuelve una copia de la lista.
     * @param <T> Debe ser un tipo que extienda Comparable, para poder distinguir
     * el orden de los elementos en la lista.
     * @param l La lista de elementos comparables.
     * @return copia de la lista ordenada.
     */
    public <T extends Comparable<T>> Lista<T> mergesort(Lista<T> l){
        if ( l.longitud < 2 ){
            return l;
        }
        int contador = 0;
        Lista l1 = new Lista();
        Lista l2 = new Lista();
        for(T elemento : l){
            if (contador++ < l.longitud/2){
                l1.agregarAlFinal(elemento);
            }
            else {
                l2.agregarAlFinal(elemento);
            }
        }
        return mezcla(mergesort(l1), mergesort(l2));
    }

    /**
     * Hace la union ordenada de la lista cuando ya se ha pasado por el metodo principal MergeSort
     * Funciona como un auxiliar recursivo para dicha funcion
     * @param l1 Primera lista que sera comparada
     * @param l2 Segunda lista para comparar
     * @param <T> Generico que debe heredar de tipo comparable para poder ordenar
     * @return util.Lista ordenada
     */
    public <T extends Comparable<T>> Lista<T> mezcla(Lista<T> l1, Lista<T> l2){
        Lista<T> l = new Lista<T>();
        while( l1.cabeza != null && l2.cabeza != null) {
            if (l1.cabeza.elemento.compareTo(l2.cabeza.elemento) < 0) {
                l.agregarAlFinal(l1.cabeza.elemento);
                l1.cabeza = l1.cabeza.siguiente;
            } else {
                l.agregarAlFinal(l2.cabeza.elemento);
                l2.cabeza = l2.cabeza.siguiente;
            }
        }
        while(l1.cabeza != null){
            l.agregarAlFinal(l1.cabeza.elemento);
            l1.cabeza = l1.cabeza.siguiente;
        }
        while (l2.cabeza != null){
            l.agregarAlFinal(l2.cabeza.elemento);
            l2.cabeza = l2.cabeza.siguiente;
        }
        return l;
    }

    @Override
    public String toString() {
        Iterator it = iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()){
            sb.append(it.next().toString() + ", ");
        }
        String result = "";
        if (sb.length() > 1){
            result = sb.substring(0,sb.length() - 2);
        }
        return "[" + result + "]";
    }
}

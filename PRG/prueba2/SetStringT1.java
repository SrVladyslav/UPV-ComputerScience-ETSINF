package prueba2;

/** 
 * Segundo Parcial Lab - Ejercicio 2 - Turno 1.
 * Clase SetStringT1. Implementacion de un conjunto de String 
 * mediante una secuencia enlazada ordenada lexicograficamente.
 * Version simplificada para el examen de la clase SetString
 * implementada en la practica 5.
 * @author PRG
 * @version Curso 2017/18
 */
public class SetStringT1 {    
    private NodeString first;
    private int size;
    
    /**
     * Crea un conjunto vacio.
     */
    public SetStringT1() {
        this.first = null;
        this.size = 0;
    }

    /**
     * Inserta s en el conjunto.
     * Si s ya pertenece al conjunto, el conjunto no cambia.
     * @param s String. Elemento que se inserta en el conjunto.
     */
    public void add(String s) {
        NodeString aux = this.first;
        NodeString prev = null;
        int compare = -1; // el valor de la comparacion
        while (aux != null && compare < 0) {
            compare = aux.data.compareTo(s); 
            if (compare < 0) {
                prev = aux;
                aux = aux.next;
            } 
        } // aux == null o s anterior o igual a aux.data  
        
        if (compare != 0) { // s menor que aux.data o s mayor que todos
            NodeString node = new NodeString(s, aux);
            if (aux == this.first) {
                this.first = node;
            } else { prev.next = node; }
            this.size++;
        }
    }
    
    /**
     * Devuelve el listado de los Strings en el conjunto,
     * en orden lexicográfico y separados por saltos de linea.
     * Si el conjunto es vacio, devuelve "" (la String vacia).
     * @return el listado de los elementos del conjunto.
     */
    public String toString() {
        String result = "";
        NodeString aux = this.first;
        while (aux != null) {
            result += aux.data + "\n"; 
            aux = aux.next; 
        }
        return result;
    }
    
    /**
     * Elimina y devuelve la primera palabra del conjunto que empiece por
     * prefix. Si no hay ninguna, el conjunto no cambia y devuelve null.
     * @param prefix String.
     * @return String, palabra eliminada o null si no existe.
     */
    public String removePrefix(String prefix) {        
        String res = null;
        NodeString aux = this.first;
        NodeString ant = null;
        boolean compare = false;
        
        while(aux != null && compare == false){
            compare = (aux.data).startsWith(prefix);
           if(compare == false){
             ant = aux; aux = aux.next;
           }
        }
        if(compare == true){
            size --;
            if(ant != null){
                ant.next = aux.next;
            }else{
            first = first.next;
          }  
        }res = aux.data;
        return res;
  
    } 
}

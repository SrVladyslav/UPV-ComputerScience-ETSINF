package pract5;

/**
 * Clase SetString. Implementacion de un
 * conjunto de String mediante una secuencia
 * enlazada ordenada lexicograficamente.
 *
 * @author (PRG. ETSINF. UPV)
 * @version (Curso 2017/18)
 */
public class SetString {
    
    private NodeString first;
    private int size;
    
    /**
     * Crea un conjunto vacio.
     */
    public SetString() {
          first = null;
          size = 0;
     }

    /**
     * Inserta s en el conjunto.
     * Si s ya pertenece al conjunto, el conjunto no cambia.
     *
     * @param s String. Elemento que se inserta en el conjunto.
     */
    public void add(String s) {
       NodeString aux = first;
       NodeString prev = null;
       int compare = -1;
       while(aux != null && compare < 0){
           compare = aux.data.compareTo(s);
           if(compare < 0){//<
             prev = aux; aux = aux.next;
           }
           
       }     
       if(compare != 0){
           size++;
           NodeString nuevo = new NodeString(s,aux);
           if(prev != null){
               prev.next = nuevo;
           }else{
               first = nuevo;
           }
        } 
    }
        
   
    /**
     * Comprueba si s pertenece al conjunto.
     *
     * @param s String.
     * @return true sii s pertenece al conjunto.
     */
     public boolean contains(String s) {
         NodeString aux = this.first;
         NodeString prev = null;
         
         int compare = -1;
         while(aux != null && compare < 0){
           compare = aux.data.compareTo(s);
           if(compare < 0){//<
             prev = aux; aux = aux.next;
           }
        }
        if(compare ==0){
         return true;
        }return false;
    }
     
    /**
     * Elimina s del conjunto.
     * Si s no pertenece al conjunto, el conjunto no cambia.
     *
     * @param s String.
     */
    public void remove(String s) {
        NodeString aux = this.first;
        NodeString ant = null;
        int compare = -1;
        
        while(aux != null && compare < 0){
            compare = aux.data.compareTo(s);
           if(compare < 0){//<
             ant = aux; aux = aux.next;
           }
        }
        if(compare == 0 ){
            size --;
            if(ant != null){
                ant.next = aux.next;
            }else{
            first = first.next;
          }  
        }
    }
    
    /**
     * Devuelve la talla o cardinal del conjunto.
     * @return la talla del conjunto.
     */
     public int size() { 
         return this.size;
     }
    
    /**
     * Devuelve el conjunto interseccion del conjunto y del otro conjunto.
     *
     * @param other SetString.
     * @return el conjunto interseccion.
     */
     public SetString intersection(SetString other) {
        SetString result = new SetString();
        NodeString aux1 = this.first;//nodo a revisar del conjunto this
        NodeString aux2 = other.first;//nodo a revisar de otro
        NodeString lastResult = null;//último nodode result,inicialmente null
        while (aux1 != null && aux2 != null){            
            int compare = aux1.data.compareTo(aux2.data);
            if(compare == 0){
               if(result.first == null){
                    result.first = aux1;
                }else{
                    lastResult.next = aux1;
               }
               result.size++;
               lastResult = aux1;
               aux1 = aux1.next;
               aux2 = aux2.next;
            }else{
                if(compare < 0){
                    aux1 = aux1.next;
                }else {
                    aux2 = aux2.next;
                }
            }
        }
        return result;
    }
    
    /**
     * Devuelve el conjunto union del conjunto y del otro conjunto.
     *
     * @param other SetString.
     * @return el conjunto union.
     */
    public SetString union(SetString other) {
       SetString union = new SetString();
       NodeString aux1 = this.first;
       NodeString aux2 = other.first;
       union.first = new NodeString("Para quitarlo");
       NodeString lastUnion = union.first;
       union.size= 0;
       while(aux1!= null && aux2 != null){
           String s1 = aux1.data;
           String s2 = aux2.data;
           int compare = aux1.data.compareTo(aux2.data);
           if(compare < 0){
                lastUnion.next = new NodeString(s1);
                lastUnion = lastUnion.next;
                aux1 = aux1.next;
                union.size++;
           }
           else if(compare > 0){
                lastUnion.next = new NodeString(s2);
                lastUnion = lastUnion.next;
                aux2 = aux2.next;
                union.size++;
           } else if(compare == 0){
                    lastUnion.next = new NodeString(s2);
                    lastUnion = lastUnion.next;
                    aux2 = aux2.next;
                    aux1 = aux1.next;
                    union.size++;
                  }
      }
      if(aux1!= null){
          while(aux1!=null){
              lastUnion.next = new NodeString(aux1.data);
              lastUnion = lastUnion.next;
              aux1 = aux1.next;
              union.size++;
          }
        }
      else if (aux2 != null){
         while(aux2!=null){
              lastUnion.next = new NodeString(aux2.data);
              lastUnion = lastUnion.next;
              aux2 = aux2.next;
              union.size++;
          }
      }
      union.first = union.first.next;
      return union;
    }
      
    /**
     * Devuelve el listado de los Strings en el conjunto, en orden
     * lexicográfico, y separados por saltos de linea.
     * Si el conjunto es vacio devuelve "", la String vacia.
     *
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
    
}

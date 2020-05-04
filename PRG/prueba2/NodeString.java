package prueba2;

/**
 * Clase NodeString.
 *
 * @author PRG
 * @version Curso 2017/18
 */
public class NodeString {    
    protected String data;
    protected NodeString next; 
 
    /** 
     * Crea un nodo con data d y con next igual a null. 
     * @param d String. Dato del nuevo nodo.
     */
    NodeString(String d) {  
        this.data = d;
        this.next = null;
    } 
    
    /** 
     * Crea un nodo con data d y next s. 
     * @param d String. Dato del nuevo nodo.
     * @param s NodeString. Sucesor del nodo que se crea.
     */
    NodeString(String d, NodeString s) {
        this.data = d;
        this.next = s;
    }        
}

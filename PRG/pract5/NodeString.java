package pract5;


/**
 * Write a description of class NodeString here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class NodeString {
    // instance variables - replace the example below with your own
    public String data;
    public NodeString next;

    /**
     * Constructor for objects of class NodeString
     */
    public NodeString(String d) {
        data = d;
        next = null;
    }

    public NodeString(String palabra,NodeString n){
        data = palabra;
        next = n;
    }
   
}

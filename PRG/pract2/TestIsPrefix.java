package pract2;


/**
 * Write a description of class TestIsPrefix here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestIsPrefix {
    
    private static String[] a = new String[]{"","","recursion","recursion","recursion","123456789",
        "rec","pecur","recurso","remursi"};
    private static String[] b = new String[]{"","recursion","","rec","recursion","recursion","recursion",
        "recursion","recursion","recursion"};
    public TestIsPrefix(){}
    
    public static void main(String[] args){
       
        for(int i = 0; i < a.length; i ++){
        
            System.out.println("Es prefijo la string " + (i+1) + " ?" + " : " + PRGString.isPrefix(a[i],b[i])+
            "   "+(b[i]).startsWith(a[i]));
        }
        
    }
   
}

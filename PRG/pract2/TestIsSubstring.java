package pract2;
/**
 * Write a description of class TestIsSubstring here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class TestIsSubstring {
    
    private static String[] a = new String[]{"","","recursion","recursion","recursion","123456789",
        "rec","pecur","recurso","remursi"};
    private static String[] b = new String[]{"","recursion","","rec","recursion","recursion","recursion",
        "recursion","recursion","recursion"};
    public TestIsSubstring(){}
    
    public static void main(String[] args){
       
        for(int i = 0; i < a.length; i ++){
        
            System.out.println("a " + (i+1) + "esta contenido en b " + (i+1) + " ?" + " : " + PRGString.isSubstring(a[i],b[i])
            + "    " + b[i].contains(a[i]));
        }
        
    }
   
}


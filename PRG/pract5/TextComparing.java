package pract5;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * TextComparing 
 * Clase - comando de sistema, que devuelve la union o interseccion
 * de las palabras de ciertos textos.
 *
 * @author (PRG. ETSINF. UPV)
 * @version (Curso 2017/18)
 */
public class TextComparing {
    
    private final static String USAGE = 
        "Uso: TextComparing [-i|-u] nomFich1 nomFich2"; 
    private final static String ERR1 = "Mal acceso al fichero: ";
    private final static String DELIMITERS = 
                      "[\\p{Space}\\p{Punct}\\p{Digit}¡¿]+";
        
    /**
     * Argumentos:
     * 1) "-u" o "-i" para union o interseccion respectivamente.
     * 2) Fichero de texto 1 de entrada.
     * 3) Fichero de texto 2 de entrada.
     * 
     * Resultado en la salida estandar.
     */
    public static void main(String[] args) {        
        boolean err = args.length != 3 
            || !(args[0].equals("-u") || args[0].equals("-i"));
        
        if (err) { System.out.println(USAGE); } 
        else { compare(args[1], args[2], args[0]); }
    }
    
    /**
     * Escribe en la salida estandar el resultado de comparar
     * los conjuntos de palabras de los ficheros de texto cuyos
     * nombres estan en nF1 y nF2. Si la opcion es "-i", escribe 
     * la interseccion de ambos conjuntos, si es "-u" escribe la union.
     * @param nF1 String, nombre del primer fichero.
     * @param nF2 String, nombre del segundo fichero.
     * @param option String
     */
    public static void compare(String nF1, String nF2, String option) {
       try{
           Scanner f1 = new Scanner(new File(nF1));
           Scanner f2 = new Scanner(new File(nF2));
           
           SetString s1 = setReading(f1);
           SetString s2 = setReading(f2);
           
           SetString res = null;
           if(option.equals("-u")){
               res = s1.union(s2);
           }if(option.equals("-i")){
               res = s1.intersection(s2);
           }
           System.out.println(res);
        }catch(FileNotFoundException w){
            System.err.print("mal acceso al fichero" + w);
        }
    }
   
    /**
     * Devuelve el SetString de las palabras leidas de s 
     * segun los separadores dados, por defecto, en DELIMITERS.
     * @param s Scanner.
     * @return el conjunto de palabras leidas de s.
     */
    private static SetString setReading(Scanner s) {  
        SetString res = new SetString();
        s.useDelimiter(DELIMITERS);
        while(s.hasNext()){
            res.add(s.next());
        }
        return res;
    }
    
}

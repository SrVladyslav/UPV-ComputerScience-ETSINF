package prueba2;

import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Segundo Parcial Lab - Ejercicio 1 - Turno 1.
 * 
 * @author PRG 
 * @version Curso 2017/18
 */
public class Exercise1T1 {
    
    /** There are not objects of this class. */
    private Exercise1T1() { }    
    
    /** Devuelve cuantos numeros reales se han leido del fichero sc.
     *  Considerando que el fichero contiene un dato en cada linea y 
     *  que estos datos pueden tener errores de formato que el metodo 
     *  nextDouble() detecta y avisa lanzando InputMismatchException, 
     *  se pide:
     *  Modifica el siguiente codigo para que, cuando lo que se ha leido 
     *  de sc no se ajuste al formato de double esperado, se muestre un 
     *  mensaje por pantalla indicando el error pero se continue leyendo 
     *  y contando valores hasta el final del fichero.
     */ 
    public static int count(Scanner sc) {
        int cont = 0;        
        while (sc.hasNext()) {
             try{
                double d = sc.nextDouble();
                cont++;
             }catch(InputMismatchException e){
                System.out.println(e);
                sc.next(); //paso al siguiente tocken para que no me coja 
                           //siempre el mismo valor y de error
                }
        }
        return cont;
    }   
    
}

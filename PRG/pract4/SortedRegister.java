package pract4;

import java.io.PrintWriter; 
import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Clase SortedRegister
 * 
 * @author (PRG. ETSINF - UPV) 
 * @version (2017/18)
 */
public class SortedRegister {

    private static int[] DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31,
        30, 31, 30, 31};  
    private int year;
    private int[][] m;

    /** Crea un SortedRegister para el año dataYear.
     *  @param int dataYear
     */
    public SortedRegister(int dataYear) {
        this.year = dataYear;
        boolean leap = isLeap(this.year);
        m = new int[13][]; 
        for (int month = 1; month <= 12; month++) {
            int numD = this.DAYS[month];
            if (month == 2 && leap) { numD++; }
            m[month] = new int[numD + 1];
        }    
    }    
    
    /** Clasifica ordenadamente los datos leidos del Scanner s.
     *  Precondicion: En cada linea de s los datos deben tener la forma
     *        dia mes cantidad
     *  en donde dia y mes deben ser enteros correspondientes a una fecha 
     *  valida, y cantidad debe ser un entero > 0.
     *  La cantidad leida se acumula en el registro que se lleva para el 
     *  dia del mes.
     *  Si alguna linea no cumple el formato, o contiene una fecha incorrecta,
     *  el metodo termina lanzando una excepcion.
     *  @param s Scanner fuente de los datos
     *  @throws InputMismatchException si se intenta leer un dato no entero. 
     *  @throws ArrayIndexOutOfBoundsException si se lee de s alguna fecha incorrecta.
     */
    public void add(Scanner s) {
        while (s.hasNext()) {
            try{
                int month = s.nextInt();
                int day = s.nextInt();
                int amount = s.nextInt();
                if(amount < 0){throw new IllegalArgumentException();}
                this.m[month][day] += amount;
            }catch(IllegalArgumentException i){
                System.out.println("Valor no entero");
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Valor no entero");
            }
        }
    } 
    
    /** Los datos registrados > 0 se escriben en p, linea a linea
     *  con el formato
     *        dia  mes  cantidad
     *  ordenados cronologicamente.
     *  @param p Printwriter destino de los datos ordenados
     */
    public void save(PrintWriter p) {
        for (int month = 1; month <= 12; month++) {
            for (int day = 1; day < this.m[month].length; day++) {
                int totalAmount = this.m[month][day];
                if (totalAmount > 0) {
                    p.printf("%5d %5d %5d \n", day, month, totalAmount);
                }
            }
        }
    }

    /** Clasifica ordenadamente los datos leidos del Scanner s. Se filtran
     *  los datos que tuvieran algun defecto de formato, emitiendo un informe
     *  de errores. 
     *  Precondicion: El formato de linea reconocible es
     *        dia mes cantidad
     *  en donde dia y mes deben ser enteros correspondientes a una fecha 
     *  valida, y cantidad debe ser un entero > 0.
     *  La cantidad leida se acumula en el registro que se lleva para 
     *  el dia del mes.
     *  En err se escriben las lineas defectuosas, indicando el número de 
     *  linea.  
     *  @param s Scanner fuente de los datos
     *  @param err PrintWriter destino del informe de errores
     */
    public void add(Scanner s, PrintWriter err) {
        //completar
        int datos[][];//array de datos
        int day = 0;
        int month = 0;
        int amount = 0;
        int n = 0;
       while (s.hasNext()) {
        try{
          day = s.nextInt();
          month = s.nextInt();
          amount = s.nextInt();
          
          if(amount < 0){throw new IllegalArgumentException();}
          if(day > 31 || month > 12||day < 0 || month < 0){
            throw new IndexOutOfBoundsException();
          }
          this.m[month][day] += amount;
          n++;
        }catch(InputMismatchException e){
           err.println("Linea " + n + ": Formato incorrecto");
        }catch(IllegalArgumentException i){
           err.println("Linea " + n + ": Cantidad negativa");
        }catch(IndexOutOfBoundsException o){
           err.println("Linea "+ n + ": Fuera de rango.");
        }finally{
       }}            
    }
    
    /** Comprueba si year es bisiesto.
     *  @param year int
     *  @return true sii es bisiesto.
     */
    private static boolean isLeap(int year) {
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }
    
    public static void testReportedSort(int year, Scanner in, PrintWriter out, PrintWriter err){
        SortedRegister r = new SortedRegister(year);   
    }
}

package pract3;

 

import java.util.Locale;
/** Clase MeasuringSortingAlgorithms: Estudio empirico de costes de 
 *  los metodos de ordenacion.
 *  @author PRG - ETSInf
 *  @version Curso 2017-2018
 */
public class MeasuringSortingAlgorithms {
    // Constantes que definen los parametros de medida
    public static final int MAXTALLA = 10000, INITALLA = 1000; 
    public static final int INCRTALLA = 1000;
    public static final int REPETICIONESQ = 200, REPETICIONESL = 20000;
    public static final double NMS = 1e3;  // relacion micro - nanosegons
    
    public static final int INITALLA_MERGE = 1024;
    public static final int MAXTALLA_MERGE = 524288;
    /** No hay objetos de esta clase. */
    private MeasuringSortingAlgorithms() { }
    
    /** Crea un array de int de talla t con valores a 0.
     *  @param t int, la talla.
     *  @result int[], el array generado.
     */
    private static int[] createArray(int t) { 
        int[] a = new int[t];
        return a;
    }

    /** Rellena los elementos de un array a de int 
     *  con valores aleatorios entre 0 y a.length-1.
     *  @param a int[], el array.
     */
    private static void fillRandomArray(int[] a) {
        // COMPLETAR
        for(int i = 0;i < a.length;i++){
            a[i]=(int)Math.random() * (a.length - 1);
        } 

    }
  
    /** Rellena los elementos de un array a de forma creciente,
     *  con valores desde 0 hasta a.length-1.
     *  @param a int[], el array.
     */
    private static void fillArraySortedInAscendingOrder(int[] a) { 
        // COMPLETAR
        for(int i = 0; i < a.length;i++){
            a[i] = i;
        }
    }

    /** Rellena los elementos de un array a de forma decreciente,
     *  con valores desde a.length-1 hasta 0.
     *  @param a int[], el array.
     */
    private static void fillArraySortedInDescendingOrder(int[] a) { 
        // COMPLETAR
        for(int i = 0;i<a.length;i++){
            a[i]=a.length-i;
        
        }
    }
  
    public static void measuringSelectionSort() {
        long ti = 0, tf = 0, tt = 0; // Tiempos inicial, final y total 
        // Imprimir cabecera
        System.out.println("# Seleccion. Tiempos en microsegundos");
        System.out.print("# Talla    Promedio \n");
        System.out.print("#------------------\n");
               
        // COMPLETAR
        
        for(int t = INITALLA; t <= MAXTALLA; t+= INCRTALLA){
        
        int[] prueba = createArray(t); 

        for(int z= 0; z < REPETICIONESQ; z++){ 
              fillRandomArray(prueba);//relleno el array
              ti = System.nanoTime();
              MeasurableAlgorithms.selectionSort(prueba);
              tf = System.nanoTime();
              tt += tf - ti;
           }
        
        double tPromedio = (double) tt / REPETICIONESQ;
        System.out.printf(Locale.US, "%8d %10.3f\n", t, tPromedio/NMS);
     }
    }

    public static void measuringInsertionSort() {
        long ti = 0, tf = 0, tt = 0; // Temps inicial, final i total        
        // Imprimir cabecera de resultados
        System.out.println("# Insercion. Tiempos en microsegundos.");
        System.out.print("# Talla    Mejor       Peor     Promedio \n");
        System.out.print("#----------------------------------------\n");
        
        
        
        for(int t = INITALLA; t <= MAXTALLA; t+= INCRTALLA){
           
           int[] prueba = createArray(t); 
           //Promedio
           for(int z= 0; z < REPETICIONESQ; z++){ 
              fillRandomArray(prueba);//relleno el array
              ti = System.nanoTime();
              MeasurableAlgorithms.insertionSort(prueba);
              tf = System.nanoTime();
              tt += tf - ti;
           }
           double tPromedio = (double) tt / REPETICIONESQ;
           
           tt = 0;
           //Peor
           for(int z= 0; z < REPETICIONESQ; z++){ 
              fillArraySortedInDescendingOrder(prueba);//relleno el array
              ti = System.nanoTime();
              MeasurableAlgorithms.insertionSort(prueba);
              tf = System.nanoTime();
              tt += tf - ti;
           }
           double tPeor = (double) tt / REPETICIONESQ;
           
           tt = 0;
           //Mejor
            for(int z= 0; z < REPETICIONESL; z++){ 
              fillArraySortedInAscendingOrder(prueba);//relleno el array
              ti = System.nanoTime();
              MeasurableAlgorithms.insertionSort(prueba);
              tf = System.nanoTime();
              tt += tf - ti;
           }
           double tMejor = (double) tt / REPETICIONESL;
           tt=0;
           
           
           
           System.out.printf(Locale.US, "%8d %10.3f %10.3f %10.3f\n", t, tMejor/NMS, tPeor/NMS, tPromedio/NMS);
        }
        // COMPLETAR
    }
  
    public static void measuringMergeSort() {        
        long ti = 0, tf = 0, tt = 0; // Tiempos inicial, final y total 
        // Imprimir cabecera
        System.out.println("# Ord. por mezcla. Tiempos en microsegundos");
        System.out.print("# Talla    Promedio \n");
        System.out.print("#-------------------\n");
        
        for(int i = INITALLA_MERGE;i <= MAXTALLA_MERGE;i*=2){
            int[] prueba = createArray(i);
            for(int j = 0;j < REPETICIONESQ;j++){
                fillArraySortedInAscendingOrder(prueba);
                ti = System.nanoTime();
                MeasurableAlgorithms.mergeSort(prueba,0,prueba.length-1);
                tf = System.nanoTime();
                tt += tf - ti;
            }
            double tPromedio = (double) tt / REPETICIONESQ;
            System.out.printf(Locale.US, "%8d %10.3f\n", i, tPromedio/NMS);
        }
        
        // COMPLETAR
    }

    private static void help() {
        String msg = "Uso: java MeasurigSortingAlgorithms num_algoritmo";
        System.out.println(msg);
        System.out.println("   donde num_algoritmo es: ");
        System.out.println("   1 -> Insercion");
        System.out.println("   2 -> Seleccion");
        System.out.println("   3 -> MergeSort");
    }

    public static void main(String[] args) {
        if (args.length != 1) { help(); } 
        else {
            try {
                int a = Integer.parseInt(args[0]);
                switch (a) {
                    case 1: 
                        measuringInsertionSort(); 
                        break;
                    case 2: 
                        measuringSelectionSort(); 
                        break;
                    case 3: 
                        measuringMergeSort(); 
                        break;
                    default: 
                        help();
                }
            } catch (Exception e) {
                help(); 
            }
        }
    }
}
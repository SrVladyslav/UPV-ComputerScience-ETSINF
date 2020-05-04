package pract3;

import java.util.Locale;

/** Clase MedidaBusquedaLineal: analisis empirico del algoritmo
 *  de busqueda lineal.
 *  @author PRG - ETSInf
 *  @version Curso 2017-2018
 */
public class MeasuringLinearSearch { 
    // Constantes que definen los parametros de medida
    public static final int MAXTALLA = 100000, INITALLA = 10000;
    public static final int INCRTALLA = 10000;
    public static final int REPETICIONES = 100000, REP_CASOMEJOR = 2000000;
    public static final double NMS = 1e3;  // relacion micro - nanosegundos

    /** No hay objetos de esta clase. */
    private MeasuringLinearSearch() { }
    
    /** Crea un array de int de talla size, 
     *  con valores crecientes desde 0 hasta size-1.
     *  @param size int, la talla.
     *  @result int[], el array.
     */
    private static int[] createArray(int size) {
        int[] a = new int[size];
        for (int i = 0; i < size; i++) { a[i] = i; }
        return a;
    }

    public static void measuringLinearSearch() {
        long ti = 0, tf = 0, tt = 0; // Tiempos inicial, final y total        
        // Imprimir cabecera de resultados
        System.out.println("# Busqueda lineal. Tiempos en microsegundos");
        System.out.print("# Talla       Mejor       Peor     Promedio\n");
        System.out.print("#------------------------------------------\n");

        // Este bucle repite el proceso para varias tallas
        for (int t = INITALLA; t <= MAXTALLA; t += INCRTALLA) {
            // Crear el array una vez para cada talla
            int[] a = createArray(t);
      
            // Estudio del Caso mejor: buscar a[0]
            // OJO: Como es muy rapido,  
            // el numero de repeticiones es mayor    
            tt = 0;                        // Tiempo acumulado inicial a 0
            for (int r = 0; r < REP_CASOMEJOR; r++) {
                ti = System.nanoTime();      // Tiempo inicial
                MeasurableAlgorithms.linearSearch(a, a[0]);
                tf = System.nanoTime();      // Tiempo final
                tt += (tf - ti);             // Actualizar tiempo acumulado
            } 
            double tMejor = (double) tt / REP_CASOMEJOR; // Tiempo promedio 
                                                         // del caso mejor
                                                  
            // Estudio del Caso peor: buscar uno que no esta, por ejemplo -1
            tt = 0;                        // Tiempo acumulado inicial a 0
            for (int r = 0; r < REPETICIONES; r++) {
                ti = System.nanoTime();      // Tiempo inicial
                MeasurableAlgorithms.linearSearch(a, -1);
                tf = System.nanoTime();      // Tiempo final
                tt += (tf - ti);             // Actualizar tiempo acumulado
            }
            double tPeor = (double) tt / REPETICIONES; // Tiempo promedio 
                                                       // del caso peor 
      
            // Estudio del Caso promedio: buscar un numero 
            // aleatorio entre 0 y t-1
            tt = 0;                        // Tiempo acumulado inicial a 0
            for (int r = 0; r < REPETICIONES; r++) {
                int aux = (int) Math.floor(Math.random() * t); // Num a buscar
                ti = System.nanoTime();      // Tiempo inicial
                MeasurableAlgorithms.linearSearch(a, aux);
                tf = System.nanoTime();      // Tiempo final
                tt += (tf - ti);         // Actualizar tiempo acumulado
            }
            double tPromed = (double) tt / REPETICIONES; // Tiempo promedio 
                                                         // del caso promedio

            // Imprimir resultados
            System.out.printf(Locale.US, "%8d %10.3f %10.3f %10.3f\n", 
                              t, tMejor / NMS, tPeor / NMS, tPromed / NMS);
        }
    }

    public static void main(String[] args) {
        measuringLinearSearch();
    }
}
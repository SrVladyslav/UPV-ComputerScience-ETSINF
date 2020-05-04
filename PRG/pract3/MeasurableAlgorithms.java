package pract3;

/** Class MeasurableAlgorithms: clase con los metodos a analizar. 
 *  Es una clase de utilidades.
 *  @author PRG - ETSInf
 *  @version Curso 2018-2018
 */
public class MeasurableAlgorithms {
    /** No hay objetos de esta clase. */
    private MeasurableAlgorithms() { }
    
    /** Linear Search
     *  @param a int[], array of int
     *  @param e int, value to look for
     *  @return int, position of e in a or -1 if e is not in a
     */
    public static int linearSearch(int[] a, int e) {
        int i = 0;
        while (i < a.length && (a[i] != e)) { i++; }
        if (i < a.length) { return i; }
        else { return -1; }
    }

    /** Selection Sort
     *  @param a int[], array of int
     */
    public static void selectionSort(int[] a) {
        int posMin, temp;
        for (int i = 0; i < a.length - 1; i++) {
            posMin = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[posMin]) { posMin = j; }
            }
            temp = a[posMin];
            a[posMin] = a[i];
            a[i] = temp;
        }
    }

    /** InsertionSort
     *  @param a int[], array of int
     */
    public static void insertionSort(int[] a) {
        int temp;
        for (int i = 1; i < a.length; i++) {
            int j = i - 1;
            temp = a[i];
            while (j >= 0 && a[j] > temp) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = temp;
        }
    }

    /** mergeSort
     *  @param a int[], array of int
     *  @param left  int, leftmost position of the slice 
     *  of the array to to be sorted
     *  @param right int, rightmost position of the slice
     *  of the array to to be sorted 
     */ 
    public static void mergeSort(int[] a, int left, int right) {
        int half;
        if (left < right) {
            half = (left + right) / 2;
            mergeSort(a, left, half);
            mergeSort(a, half + 1, right);
            naturalMerge(a, left, half, right);
        }
    }

    /** Natural Merge to be used in Merge Sort
     *  @param a int[], array of int
     *  @param left  int, leftmost position of the slice 
     *  of the array to to be sorted
     *  @param right int, rightmost position of the slice 
     *  of the array to to be sorted
     *  @param half  int, central position of the slice 
     *  of the array to to be sorted 
     */ 
    private static void naturalMerge(int[] a, int left, int half, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = half + 1, k = 0;
        while (i <= half && j <= right) {            
            if (a[i] < a[j]) { temp[k++] = a[i++]; } 
            else { temp[k++] = a[j++]; }
        }

        while (i <= half) { temp[k++] = a[i++]; }
        while (j <= right) { temp[k++] = a[j++]; }

        for (i = left, k = 0; i <= right; i++) {
            a[i] = temp[k++];
        }
    }
}
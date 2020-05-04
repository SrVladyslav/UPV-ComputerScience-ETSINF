package pract1;

// Importa la classe Graph2D (en el paquete graph2D).
import graph2D.Graph2D;
// Importa la classe Color (en el paquete java.awt) para poder
// cambiar los colores de los elementos que se representen.
import java.awt.Color;
import java.awt.Font;

/**
 * La clase FigRecSimple incluye los elemntos basicos
 * para dibujar una figura recursivamente.
 * 
 * @author (PRG - DSIC - ETSINF, pmarques) 
 * @version (curso 2017 - 2018)
 */
public class FigRecSimple {
    
    // No hay constructora publica:
    private FigRecSimple() { }

    /**
     * Dibuja una serie de cuadrados incluidos unos en
     * otros. El numero de cuadrados viene dado por el
     * numero ordIni 
     * PRECONDICION: ordIni >= 0
     * @param ordIni. Numero de cuadrados que se dibujan.
     */
    public static void figRec(int ordIni) {

        // Titulo de la ventana donde se dibuja:
        String tit = "Cuadrados anidados 1. Orden: " + ordIni;

        // Dimensiones del espacio de dibujo en coords. reales:
        double minX = -0.1; double maxX = 1.1;
        double minY = -0.1; double maxY = 1.1;
        
        Graph2D gd = new Graph2D(minX, maxX, minY, maxY);
        gd.setTitle(tit); 
        
        // llamada inicial, lado 1.0 y orden ordIni:
        figRec(gd, ordIni, 1.0);
    }
    
    /**
     * Dibuja un cuadrado de orden ord y lado l.
     * @param g: el espacio Graph2D donde se realiza el dibujo.
     * @param ord: orden (o numero) del cuadrado.
     * @param l: longitud del lado del cuadrado.
     * PRECONDICION: ord >= 0 
     */
    private static void figRec(Graph2D g, int ord, double l) {
        // caso base ord == 0, no se hace nada
        if (ord >= 1) {
            // Los cuadrados tienen su extremo superior
            // izquierdo en (0.0, 1.0). 
            // Los dibujamos en Negro y con grosor 2:
            g.drawRect(0.0, 1.0, l, l, Color.BLACK, 2);
            
            // Podemos calcular el punto inferior derecho:
            double xID = l; double yID = 1.0 - l;
            // y dibujamos la String con el orden en Azul:
            g.setForegroundColor(Color.BLUE);            
            g.drawString(xID,yID," "+ ord);
            
            // cada nuevo cuadrado tiene su lado un 10% menor
            // que el anterior, esto es: l * 0.9:
            figRec(g, ord - 1, l * 0.9);
        }
    }
            
    
}

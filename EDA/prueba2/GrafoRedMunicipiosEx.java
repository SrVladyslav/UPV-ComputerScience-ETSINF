package prueba2;

import librerias.estructurasDeDatos.grafos.GrafoNoDirigido;
import librerias.estructurasDeDatos.grafos.Grafo;
import librerias.estructurasDeDatos.grafos.Arista;
import librerias.estructurasDeDatos.grafos.Adyacente;
import java.util.Scanner;
import librerias.estructurasDeDatos.modelos.ListaConPI;
import librerias.estructurasDeDatos.modelos.Map;
import librerias.estructurasDeDatos.lineales.LEGListaConPI;
import librerias.estructurasDeDatos.deDispersion.TablaHash;
import java.io.File;
import java.io.FileNotFoundException;
import aplicaciones.redElectrica.Municipio;

/** GrafoRedMunicipios: grafo etiquetado que representa una red  
 *  electrica interurbana mediante...
 ** Un conjunto de vertices etiquetados por los nombres 
 *  de los municipios de la red 
 ** Un conjunto de aristas etiquetadas por los millones 
 *  de euros que costaria la renovacion del tendido electrico 
 *  entre cada par de municipios de la red (vertices). 
 * 
 * @version (Curso 2017/18)
 */    

public class GrafoRedMunicipiosEx {
    
    public final static String DIR_FICHEROS = "aplicaciones" + File.separator
                                              + "redElectrica" + File.separator;
        
    private static final int MAX_MUNICIPIOS = 5000;
    
    private static final String NO_ACC_MSG = "El fichero con los datos de los "
                                             + "municipios no es accesible "
                                             + "para lectura: comprueba su "
                                             + "correcta ubicaci\u00f3n";
    private static final String NO_FOR_MSG = "Formato no v\u00e1lido en "
                                             + "la l\u00ednea: ";
    private static final String NO_FDIS_MSG = "Fichero con datos de las lineas"
                                              + "no encontrado"; 
                                              
    protected Grafo gRM;   
    protected Map<Integer, Municipio> verticesAMunicipios; 
    protected Map<Municipio, Integer> municipiosAVertices;    
    
    /** Constructor */
    public GrafoRedMunicipiosEx(Municipio[] municipios, Arista[] aristas) {
        verticesAMunicipios = new TablaHash<Integer, Municipio>(MAX_MUNICIPIOS);
        municipiosAVertices = new TablaHash<Municipio, Integer>(MAX_MUNICIPIOS);
        int numVert = 0;
        while (numVert < municipios.length) {
            verticesAMunicipios.insertar(numVert, municipios[numVert]);
            municipiosAVertices.insertar(municipios[numVert], numVert);
            numVert++;
        }
        gRM = new GrafoNoDirigido(numVert, aristas);
    }
    
    /** Consultor del número de vértices */
    public int numVertices() { return gRM.numVertices(); }
    
    /** Consultor del número de aristas */
    public int numAristas() { return gRM.numAristas(); }
    
    /** Consultor de vértice dado el municipio */
    public int getVertice(Municipio m) {
        Integer vertice = municipiosAVertices.recuperar(m); 
        if (vertice == null) { return -1; }
        return vertice.intValue();
    }
    
    /** Consultor del municipio dado el vértice */
    public Municipio getMunicipio(int v) { 
        return verticesAMunicipios.recuperar(v);
    }

	// TURNO 1
    public double aristaEnMST(Municipio a, Municipio b) {
        double res = -1;
		/* COMPLETAR */
		
		int va = getVertice(a);
        int vb = getVertice(b);
        if (va == -1 || vb == -1) {return -2;}
        
        Arista[] mst = gRM.kruskal();
        if (mst == null) {return -1;}
        
        for (int i = 0; i < mst.length; i++) {
            int origen = mst[i].getOrigen();
            int destino = mst[i].getDestino();
            if (origen == va && destino == vb) return mst[i].getPeso();
            if (origen == vb && destino == va) return mst[i].getPeso();
        }
        
        return res;
    }
}

package academtrack;

/**
 * Clase ArbolBB (Árbol Binario de Búsqueda)
 * Responsabilidad: Facilitar la búsqueda mediante código.
 *
 * NOTA: en el diseño original, tanto el ABB (RF14) como el AVL
 * (RF15) apuntaban a "buscar alumnos", lo cual se debe precisar
 * antes del Cap. 9, ya que un AVL es en esencia un ABB balanceado
 * y no tendría sentido mantener ambos para el mismo dato. Se
 * sugiere usar este ABB para la búsqueda de TRÁMITES por código,
 * y reservar el AVL (Cap. 10) para la búsqueda balanceada de
 * ALUMNOS. Ajustar según lo que decida el equipo.
 *
 * Implementación completa (insertar, buscar, eliminar, recorrer)
 * en el Capítulo 9.
 */
public class ArbolBB {

    // private NodoABB raiz;

    public void insertar(Tramite tramite) {
        // TODO (Cap. 9): insertar nodo respetando la propiedad de ABB según código
    }

    public Tramite buscar(String codigo) {
        // TODO (Cap. 9): búsqueda recursiva o iterativa desde la raíz
        return null;
    }

    public void eliminar(String codigo) {
        // TODO (Cap. 9): eliminar nodo (hoja, un hijo o dos hijos)
    }

    public void recorrerInorden() {
        // TODO (Cap. 9): recorrido inorden (izquierda, raíz, derecha)
    }
}

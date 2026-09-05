package academtrack;

/**
 * Clase Pila
 * Responsabilidad: Registrar las últimas operaciones realizadas
 * para permitir deshacer una acción en caso de error (RF13).
 *
 * NOTA: Esqueleto de diseño (Capítulo 2). La implementación con
 * nodos propios, comportamiento LIFO (push/pop/peek) y validación
 * de pila vacía se desarrolla en el Capítulo 6.
 */
public class Pila {

    // private NodoOperacion tope; // se agregará en el Capítulo 6

    public void insertar(String operacion) {
        // TODO (Cap. 6): push - crear nodo y colocarlo como nuevo tope
    }

    public String retirar() {
        // TODO (Cap. 6): pop - remover y devolver el nodo tope, validando pila vacía
        return null;
    }

    public String consultarTope() {
        // TODO (Cap. 6): peek - devolver el valor del tope sin removerlo
        return null;
    }
}

package estructuras;

import model.Tramite;

/**
 * Clase Cola
 * Responsabilidad: Gestionar los trámites pendientes respetando
 * el orden en que fueron registrados (FIFO).
 *
 * NOTA: Esqueleto de diseño (Capítulo 2). La implementación con
 * nodos propios (enqueue/dequeue, validación de cola vacía) se
 * desarrolla en el Capítulo 7.
 */
public class Cola {

    // private NodoTramite frente;
    // private NodoTramite fin;

    public void insertar(Tramite tramite) {
        // TODO (Cap. 7): enqueue - agregar al final de la cola
    }

    public Tramite retirar() {
        // TODO (Cap. 7): dequeue - remover el trámite del frente, validando cola vacía
        return null;
    }

    public Tramite consultarFrente() {
        // TODO (Cap. 7): devolver el trámite en el frente sin removerlo
        return null;
    }
}

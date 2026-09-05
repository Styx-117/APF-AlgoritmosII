package academtrack;

/**
 * Clase ColaPrioridad
 * Responsabilidad: Gestionar los trámites considerando su nivel
 * de urgencia (trámites con plazo próximo vs. trámites regulares).
 *
 * NOTA: Esqueleto de diseño (Capítulo 2). Implementación completa
 * con nodos propios y criterio de comparación por prioridad en
 * el Capítulo 7.
 */
public class ColaPrioridad {

    // private NodoTramite frente;
    // private NodoTramite fin;

    public void insertar(Tramite tramite) {
        // TODO (Cap. 7): insertar respetando el orden de prioridad (compararPrioridad)
    }

    public Tramite retirar() {
        // TODO (Cap. 7): remover el trámite de mayor prioridad, validando cola vacía
        return null;
    }

    private int compararPrioridad(Tramite a, Tramite b) {
        // TODO (Cap. 7): criterio de comparación (menor número = más urgente)
        return 0;
    }
}

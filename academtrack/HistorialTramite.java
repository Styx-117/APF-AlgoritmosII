package academtrack;

/**
 * Clase HistorialTrámite
 * Responsabilidad: Mantener y consultar el historial de trámites
 * realizados por cada alumno.
 *
 * NOTA: Esta clase es un ESQUELETO a nivel de diseño (Capítulo 2).
 * En el Capítulo 5 se implementará "trámites" como una Lista
 * Enlazada Simple construida con nodos propios (clase Nodo con
 * referencia al siguiente), en lugar de una colección del lenguaje,
 * cumpliendo el requisito de código propio del proyecto.
 */
public class HistorialTramite {

    private String codigoAlumno;
    // private NodoTramite cabeza; // se agregará en el Capítulo 5

    public HistorialTramite(String codigoAlumno) {
        this.codigoAlumno = codigoAlumno;
    }

    // RF11 (parcial). Agregar un trámite al historial del alumno
    public void agregar(Tramite tramite) {
        // TODO (Cap. 5): insertar un nuevo nodo al final de la lista enlazada
        System.out.println("[Pendiente Cap.5] Agregar trámite " + tramite.getCodigoTramite() + " al historial de " + codigoAlumno);
    }

    // Buscar un trámite dentro del historial por su código
    public Tramite buscar(String codigoTramite) {
        // TODO (Cap. 5): recorrer la lista enlazada nodo por nodo
        System.out.println("[Pendiente Cap.5] Buscar trámite " + codigoTramite);
        return null;
    }

    public void eliminar(String codigoTramite) {
        // TODO (Cap. 5): eliminar el nodo correspondiente de la lista enlazada
        System.out.println("[Pendiente Cap.5] Eliminar trámite " + codigoTramite);
    }

    // RF11. Consultar el historial completo de trámites de un alumno
    public void recorrer() {
        // TODO (Cap. 5): recorrer la lista enlazada desde la cabeza
        System.out.println("[Pendiente Cap.5] Recorrer historial de " + codigoAlumno);
    }

    public String getCodigoAlumno() { return codigoAlumno; }
}

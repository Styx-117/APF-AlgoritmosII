package model;

/**
 * Clase Trámite
 * Responsabilidad: Gestionar las solicitudes académicas
 * realizadas por los alumnos.
 */
public class Tramite {

    private String codigoTramite;
    private String codigoAlumno;
    private String tipo; // CONSTANCIA, RETIRO_CURSO, RECLAMO, CAMBIO_HORARIO
    private String fechaSolicitud;
    private String fechaLimite;
    private int prioridad; // 1 = urgente, 2 = regular (se define en Cap. 7 - Colas)
    private String estado; // PENDIENTE, ATENDIDO

    public Tramite(String codigoTramite, String codigoAlumno, String tipo,
                    String fechaSolicitud, String fechaLimite) {
        this.codigoTramite = codigoTramite;
        this.codigoAlumno = codigoAlumno;
        this.tipo = tipo;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaLimite = fechaLimite;
        this.estado = "PENDIENTE";
        this.prioridad = 2; // por defecto regular, hasta asignarPrioridad()
    }

    // RF07. Registrar solicitud de trámite
    public void registrar() {
        System.out.println("Trámite " + codigoTramite + " (" + tipo + ") registrado para alumno " + codigoAlumno);
    }

    // RF08. Consultar trámite mediante su código
    public void consultar() {
        System.out.println("Código: " + codigoTramite);
        System.out.println("Tipo: " + tipo);
        System.out.println("Solicitado: " + fechaSolicitud + " | Límite: " + fechaLimite);
        System.out.println("Prioridad: " + prioridad + " | Estado: " + estado);
    }

    // RF09. Asignar prioridad según cercanía a la fecha límite
    public void asignarPrioridad(int prioridad) {
        this.prioridad = prioridad;
    }

    public void actualizarEstado(String estado) {
        this.estado = estado;
    }

    // Getters
    public String getCodigoTramite() { return codigoTramite; }
    public String getCodigoAlumno() { return codigoAlumno; }
    public String getTipo() { return tipo; }
    public String getFechaSolicitud() { return fechaSolicitud; }
    public String getFechaLimite() { return fechaLimite; }
    public int getPrioridad() { return prioridad; }
    public String getEstado() { return estado; }

    @Override
    public String toString() {
        return codigoTramite + " | " + tipo + " | prioridad=" + prioridad + " | " + estado;
    }
}

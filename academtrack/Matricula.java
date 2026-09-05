package academtrack;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase Matrícula
 * Responsabilidad: Gestionar el proceso de matrícula de un alumno
 * en los cursos seleccionados.
 *
 * NOTA: La lista "cursosMatriculados" usa temporalmente ArrayList
 * solo como marcador de posición. En el Capítulo 4 (Estructuras
 * Estáticas) se reemplazará por un arreglo de objetos Curso
 * implementado con código propio, tal como exige el proyecto.
 */
public class Matricula {

    private String codigoMatricula;
    private String codigoAlumno;
    private String fecha;
    private String periodo;
    private String estado; // PENDIENTE, CONFIRMADA, ANULADA
    private List<Curso> cursosMatriculados;

    public Matricula(String codigoMatricula, String codigoAlumno, String fecha, String periodo) {
        this.codigoMatricula = codigoMatricula;
        this.codigoAlumno = codigoAlumno;
        this.fecha = fecha;
        this.periodo = periodo;
        this.estado = "PENDIENTE";
        this.cursosMatriculados = new ArrayList<>();
    }

    // RF05. Registrar la matrícula de un alumno
    public void registrar() {
        System.out.println("Matrícula " + codigoMatricula + " registrada para alumno " + codigoAlumno);
    }

    public void agregarCurso(Curso curso) {
        cursosMatriculados.add(curso);
    }

    // RF06. Validar la matrícula para evitar duplicidad y cruces de horario
    public boolean validar() {
        for (int i = 0; i < cursosMatriculados.size(); i++) {
            for (int j = i + 1; j < cursosMatriculados.size(); j++) {
                Curso a = cursosMatriculados.get(i);
                Curso b = cursosMatriculados.get(j);
                if (a.getCodigoCurso().equals(b.getCodigoCurso())) {
                    System.out.println("Error: curso duplicado " + a.getCodigoCurso());
                    return false;
                }
                if (a.getHorario().equals(b.getHorario())) {
                    System.out.println("Error: cruce de horario entre " + a.getCodigoCurso() + " y " + b.getCodigoCurso());
                    return false;
                }
            }
        }
        return true;
    }

    public void confirmar() {
        if (validar()) {
            this.estado = "CONFIRMADA";
            System.out.println("Matrícula " + codigoMatricula + " confirmada.");
        } else {
            System.out.println("No se pudo confirmar la matrícula " + codigoMatricula);
        }
    }

    // Getters y setters
    public String getCodigoMatricula() { return codigoMatricula; }
    public String getCodigoAlumno() { return codigoAlumno; }
    public String getFecha() { return fecha; }
    public String getPeriodo() { return periodo; }
    public String getEstado() { return estado; }
    public List<Curso> getCursosMatriculados() { return cursosMatriculados; }
}

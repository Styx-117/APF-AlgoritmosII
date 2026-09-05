

import model.Alumno;
import model.Curso;
import model.Matricula;
import model.Tramite;

/**
 * Clase Main
 * Punto de entrada para probar las clases de dominio definidas
 * hasta el Capítulo 2 (Análisis y Diseño de la Solución).
 * Más adelante, esta clase será reemplazada por el lanzador de
 * la interfaz gráfica (Capítulo 12).
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== Prueba de clases AcademTrack (Cap. 1-2) ===\n");

        // Registrar un alumno
        Alumno alumno = new Alumno("A001", "70123456", "Rosario Jezzira",
                "Cahuana Quispe", "Computación e Informática", 5);
        alumno.registrar();
        alumno.consultar();

        System.out.println();

        // Registrar dos cursos
        Curso curso1 = new Curso("C001", "Algoritmos y Estructura de Datos",
                "Computación e Informática", 5, "Lun-Mie 18:00-20:00", 30);
        Curso curso2 = new Curso("C002", "Base de Datos",
                "Computación e Informática", 5, "Mar-Jue 18:00-20:00", 30);
        curso1.registrar();
        curso2.registrar();

        System.out.println();

        // Matricular al alumno en los cursos (sin cruce de horario)
        Matricula matricula = new Matricula("M001", alumno.getCodigoAlumno(), "2026-09-04", "2026-II");
        matricula.agregarCurso(curso1);
        matricula.agregarCurso(curso2);
        matricula.registrar();
        matricula.confirmar();

        System.out.println();

        // Registrar un trámite urgente
        Tramite tramite = new Tramite("T001", alumno.getCodigoAlumno(), "RETIRO_CURSO",
                "2026-09-04", "2026-09-06");
        tramite.registrar();
        tramite.asignarPrioridad(1); // urgente
        tramite.consultar();
    }
}

import model.Alumno;
import model.Curso;
import model.Matricula;
import model.Tramite;
import estructuras.MatrizCruceHorarios;

/**
 * Clase Main
 * Prueba por consola de las clases de dominio (Cap. 1-2) y del
 * Capítulo 4: arreglo unidimensional de DetalleMatricula dentro de
 * Matricula, y arreglo bidimensional MatrizCruceHorarios.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=== Prueba de clases AcademTrack (Cap. 1-2) ===\n");
        
        Alumno alumno = new Alumno("A001", "70123456", "Rosario Jezzira",
                "Cahuana Quispe", "Computación e Informática", 5);
        alumno.registrar();
        alumno.consultar();

        System.out.println();

        Curso curso1 = new Curso("C001", "Algoritmos y Estructura de Datos",
                "Computación e Informática", 5, "Lun-Mie 18:00-20:00", 30);
        Curso curso2 = new Curso("C002", "Base de Datos",
                "Computación e Informática", 5, "Mar-Jue 18:00-20:00", 30);
        Curso curso3 = new Curso("C003", "Marcos de Desarrollo Web",
                "Computación e Informática", 5, "Lun-Mie 18:00-20:00", 25); // mismo horario que C001
        curso1.registrar();
        curso2.registrar();
        curso3.registrar();

        System.out.println("\n=== Cap. 4 - Arreglo de DetalleMatricula dentro de Matricula ===\n");

        Matricula matricula = new Matricula("M001", alumno.getCodigoAlumno(), "2026-09-04", "2026-II");
        matricula.registrar();
        matricula.agregarCurso(curso1);
        matricula.agregarCurso(curso2);
        matricula.agregarCurso(curso3);

        System.out.println("Recorrido de detalles:");
        matricula.recorrerDetalles();

        System.out.println("\nBúsqueda del detalle de C002: " + matricula.buscarDetalle("C002"));

        Curso curso2Actualizado = new Curso("C002", "Base de Datos II",
                "Computación e Informática", 5, "Mar-Jue 18:00-20:00", 28);
        matricula.actualizarCurso("C002", curso2Actualizado);
        System.out.println("Después de actualizar C002: " + matricula.buscarDetalle("C002"));

        Matricula copia = matricula.copiar("M001-COPIA");
        Matricula clon = matricula.clonar("M001-CLON");
        System.out.println("¿Copia tiene los mismos cursos? " + matricula.compararCursos(copia));
        System.out.println("¿Clon tiene los mismos cursos? " + matricula.compararCursos(clon));

        matricula.eliminarCurso("C003");
        System.out.println("Después de eliminar C003, recorrido:");
        matricula.recorrerDetalles();
        System.out.println("¿Sigue igual a la copia (que aún tiene C003)? "
                + matricula.compararCursos(copia));

        Matricula fusionada = matricula.fusionar(copia, "M001-FUSION");
        System.out.println("Fusión (sin repetir cursos), recorrido:");
        fusionada.recorrerDetalles();

        System.out.println("\n=== Cap. 4 - Arreglo bidimensional (MatrizCruceHorarios) ===\n");

        Curso[] cursosParaMatriz = { curso1, curso2Actualizado, curso3 };
        MatrizCruceHorarios matriz = new MatrizCruceHorarios(cursosParaMatriz, cursosParaMatriz.length);
        matriz.mostrar();
        System.out.println("¿C001 (idx 0) cruza con algún curso? " + matriz.tieneAlgunCruce(0));
        System.out.println("¿C002 actualizado (idx 1) cruza con algún curso? " + matriz.tieneAlgunCruce(1));

        System.out.println("\n=== Validación RF06 con matrículas nuevas ===\n");

        Matricula matriculaConCruce = new Matricula("M002", alumno.getCodigoAlumno(), "2026-09-04", "2026-II");
        matriculaConCruce.agregarCurso(curso1);
        matriculaConCruce.agregarCurso(curso3);
        matriculaConCruce.registrar();
        matriculaConCruce.confirmar();

        System.out.println();

        Matricula matriculaValida = new Matricula("M003", alumno.getCodigoAlumno(), "2026-09-04", "2026-II");
        matriculaValida.agregarCurso(curso1);
        matriculaValida.agregarCurso(curso2Actualizado);
        matriculaValida.registrar();
        matriculaValida.confirmar();

        System.out.println();

        Tramite tramite = new Tramite("T001", alumno.getCodigoAlumno(), "RETIRO_CURSO",
                "2026-09-04", "2026-09-06");
        tramite.registrar();
        tramite.asignarPrioridad(1);
        tramite.consultar();
    }
}
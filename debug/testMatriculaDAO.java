package debug;

import dao.MatriculaDAO;
import model.Curso;
import model.DetalleMatricula;
import model.Matricula;

public class testMatriculaDAO {

    public static void main(String[] args) {

        MatriculaDAO dao = new MatriculaDAO();

        // ==========================================
        // 1. Crear matrícula
        // ==========================================
        Matricula matricula = new Matricula(
                "M003",
                "A001",
                "2026-09-06",
                "2026-II"
        );

        // ==========================================
        // 2. Crear cursos
        // ==========================================
        Curso curso1 = new Curso(
                "C001",
                "Algoritmos y Estructuras de Datos",
                "Ingeniería de Sistemas",
                3,
                "LUN 08:00-10:00",
                30
        );

        Curso curso2 = new Curso(
                "C002",
                "Base de Datos II",
                "Ingeniería de Sistemas",
                4,
                "MAR 10:00-12:00",
                30
        );

        // ==========================================
        // 3. Agregar cursos a la matrícula
        // ==========================================
        matricula.agregarCurso(curso1);
        matricula.agregarCurso(curso2);

        System.out.println("=== MATRÍCULA ORIGINAL ===");
        System.out.println("Código: " + matricula.getCodigoMatricula());
        System.out.println("Alumno: " + matricula.getCodigoAlumno());
        System.out.println("Estado: " + matricula.getEstado());
        System.out.println("Cantidad de detalles: "
                + matricula.getCantidadDetalles());

        for (int i = 0; i < matricula.getCantidadDetalles(); i++) {

            DetalleMatricula detalle =
                    matricula.obtenerDetalle(i);

            System.out.println(
                    detalle.getCodigoDetalle()
                    + " -> "
                    + detalle.getCodigoCurso()
            );
        }

        // ==========================================
        // 4. REGISTRAR
        // ==========================================
        System.out.println("\n=== REGISTRANDO ===");

        boolean registrado = dao.registrar(matricula);

        System.out.println(
                registrado
                ? "Matrícula registrada correctamente"
                : "Error al registrar matrícula"
        );

        // ==========================================
        // 5. CONSULTAR
        // ==========================================
        System.out.println("\n=== CONSULTANDO ===");

        Matricula encontrada =
                dao.consultar("M003");

        if (encontrada != null) {

            System.out.println("Matrícula encontrada");
            System.out.println(
                    "Código: "
                    + encontrada.getCodigoMatricula()
            );

            System.out.println(
                    "Alumno: "
                    + encontrada.getCodigoAlumno()
            );

            System.out.println(
                    "Periodo: "
                    + encontrada.getPeriodo()
            );

            System.out.println(
                    "Estado: "
                    + encontrada.getEstado()
            );

            System.out.println(
                    "Detalles encontrados: "
                    + encontrada.getCantidadDetalles()
            );

            for (int i = 0;
                 i < encontrada.getCantidadDetalles();
                 i++) {

                DetalleMatricula detalle =
                        encontrada.obtenerDetalle(i);

                System.out.println(
                        detalle.getCodigoDetalle()
                        + " -> "
                        + detalle.getCodigoCurso()
                );
            }

        } else {
            System.out.println(
                    "✗ No se encontró la matrícula"
            );
        }
    }
}
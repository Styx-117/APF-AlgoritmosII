
import dao.MatriculaDAO;
import model.Curso;
import model.Matricula;

public class maintest {

    public static void main(String[] args) {

        MatriculaDAO matriculaDAO = new MatriculaDAO();

        Curso curso1 = new Curso(
                "C001",
                "Algoritmos y Estructuras de Datos",
                "Ingenieria de Sistemas",
                3,
                "LUN 08:00-10:00",
                30
        );

        Curso curso2 = new Curso(
                "C002",
                "Base de Datos",
                "Ingenieria de Sistemas",
                3,
                "MAR 10:00-12:00",
                30
        );

        Matricula matricula = new Matricula(
                "M002",
                "A002",
                "2026-09-05",
                "2026-II",
                "PENDIENTE"
        );

        matricula.agregarCurso(curso1);
        matricula.agregarCurso(curso2);

        // Registrar
        if (matriculaDAO.registrar(matricula)) {
            System.out.println(
                    "Matricula registrada correctamente."
            );
        }

        // Consultar
        Matricula encontrada =
                matriculaDAO.consultar("M002");

        if (encontrada != null) {

            System.out.println("\nMatricula encontrada:");
            System.out.println(encontrada);

            System.out.println("Cursos:");

            for (Curso curso :
                    encontrada.getCursosMatriculados()) {

                System.out.println(curso);
            }
        }

        // Actualizar
        matricula.setEstado("CONFIRMADA");

        if (matriculaDAO.actualizar(matricula)) {
            System.out.println(
                    "\nMatricula actualizada."
            );
        }

        // Listar
        System.out.println("\nLista de matriculas:");

        for (Matricula m : matriculaDAO.listar()) {
            System.out.println(m);
        }
    }
}
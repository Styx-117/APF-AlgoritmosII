

import dao.AlumnoDAO;
import model.Alumno;

public class maintest {

    public static void main(String[] args) {

        AlumnoDAO alumnoDAO = new AlumnoDAO();

        Alumno alumno = new Alumno(
                "A002",
                "12345678",
                "Maria",
                "Gomez",
                "Ingenieria de Sistemas",
                3
        );

        // Registrar
        if (alumnoDAO.registrar(alumno)) {
            System.out.println("Alumno registrado correctamente.");
        }

        // Consultar
        Alumno encontrado = alumnoDAO.consultar("A002");

        if (encontrado != null) {
            System.out.println("Alumno encontrado:");
            System.out.println(encontrado);
        }

        // Actualizar
        alumno.setCiclo(4);

        if (alumnoDAO.actualizar(alumno)) {
            System.out.println("Alumno actualizado.");
        }

        // Listar
        System.out.println("\nLista de alumnos:");

        for (Alumno a : alumnoDAO.listar()) {
            System.out.println(a);
        }

        // Eliminar
        /*
        if (alumnoDAO.eliminar("A002")) {
            System.out.println("Alumno eliminado.");
        }
        */
    }
}
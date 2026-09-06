package dao;

import model.Curso;
import model.Matricula;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    // REGISTRAR MATRÍCULA + CURSOS
    public boolean registrar(Matricula matricula) {

        String sqlMatricula =
                "INSERT INTO matricula "
                        + "(codigo_matricula, codigo_alumno, fecha, periodo, estado) "
                        + "VALUES (?, ?, ?, ?, ?)";

        String sqlDetalle =
                "INSERT INTO detalle_matricula "
                        + "(codigo_matricula, codigo_curso) "
                        + "VALUES (?, ?)";

        Connection con = null;

        try {
            con = ConexionDB.conectar();

            // Iniciamos transacción
            con.setAutoCommit(false);


            // Registrar matrícula
            try (PreparedStatement ps = con.prepareStatement(sqlMatricula)) {

                ps.setString(1, matricula.getCodigoMatricula());
                ps.setString(2, matricula.getCodigoAlumno());
                ps.setDate(3, Date.valueOf(matricula.getFecha()));
                ps.setString(4, matricula.getPeriodo());
                ps.setString(5, matricula.getEstado());

                ps.executeUpdate();
            }


            // Registramos los cursos de la matricula
            try (PreparedStatement ps = con.prepareStatement(sqlDetalle)) {

                for (Curso curso : matricula.getCursosMatriculados()) {

                    ps.setString(
                            1,
                            matricula.getCodigoMatricula()
                    );

                    ps.setString(
                            2,
                            curso.getCodigoCurso()
                    );

                    ps.executeUpdate();
                }
            }


            // Confirmamos_todo
            con.commit();
            return true;

        } catch (SQLException e) {

            // Hacemos Rollback si sucede un error e imprimimos la razon de este
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    System.out.println(
                            "Error al realizar rollback: "
                                    + ex.getMessage()
                    );
                }
            }

            System.out.println(
                    "Error al registrar matricula: "
                            + e.getMessage()
            );

            return false;

        } finally {

            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    System.out.println(
                            "Error al cerrar conexión: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }



    // CONSULTAR MATRÍCULA
    public Matricula consultar(String codigoMatricula) {

        String sqlMatricula =
                "SELECT * FROM matricula "
                        + "WHERE codigo_matricula = ?";

        String sqlCursos =
                "SELECT c.* "
                        + "FROM curso c "
                        + "INNER JOIN detalle_matricula d "
                        + "ON c.codigo_curso = d.codigo_curso "
                        + "WHERE d.codigo_matricula = ? "
                        + "ORDER BY c.codigo_curso";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement psMatricula =
                     con.prepareStatement(sqlMatricula);
             PreparedStatement psCursos =
                     con.prepareStatement(sqlCursos)) {


            // 1. Obtener matrícula
            psMatricula.setString(1, codigoMatricula);

            try (ResultSet rs = psMatricula.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                Matricula matricula = new Matricula(
                        rs.getString("codigo_matricula"),
                        rs.getString("codigo_alumno"),
                        rs.getDate("fecha").toString(),
                        rs.getString("periodo"),
                        rs.getString("estado")
                );


                // 2. Obtener sus cursos
                psCursos.setString(1, codigoMatricula);

                try (ResultSet rsCursos =
                             psCursos.executeQuery()) {

                    while (rsCursos.next()) {

                        Curso curso = new Curso(
                                rsCursos.getString("codigo_curso"),
                                rsCursos.getString("nombre"),
                                rsCursos.getString("carrera"),
                                rsCursos.getInt("ciclo"),
                                rsCursos.getString("horario"),
                                rsCursos.getInt("capacidad")
                        );

                        matricula.agregarCurso(curso);
                    }
                }

                return matricula;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al consultar matricula: "
                            + e.getMessage()
            );
        }

        return null;
    }


    // ==========================================
    // LISTAR MATRÍCULAS
    // ==========================================
    public List<Matricula> listar() {

        List<Matricula> matriculas = new ArrayList<>();

        String sql =
                "SELECT codigo_matricula "
                        + "FROM matricula "
                        + "ORDER BY codigo_matricula";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Matricula matricula =
                        consultar(rs.getString("codigo_matricula"));

                if (matricula != null) {
                    matriculas.add(matricula);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar matriculas: "
                            + e.getMessage()
            );
        }

        return matriculas;
    }


    // ==========================================
    // ACTUALIZAR MATRÍCULA
    // ==========================================
    public boolean actualizar(Matricula matricula) {

        String sql =
                "UPDATE matricula SET "
                        + "codigo_alumno = ?, "
                        + "fecha = ?, "
                        + "periodo = ?, "
                        + "estado = ? "
                        + "WHERE codigo_matricula = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, matricula.getCodigoAlumno());

            ps.setDate(
                    2,
                    Date.valueOf(matricula.getFecha())
            );

            ps.setString(3, matricula.getPeriodo());
            ps.setString(4, matricula.getEstado());
            ps.setString(5, matricula.getCodigoMatricula());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar matricula: "
                            + e.getMessage()
            );

            return false;
        }
    }


    // ==========================================
    // ELIMINAR MATRÍCULA
    // ==========================================
    public boolean eliminar(String codigoMatricula) {

        String sql =
                "DELETE FROM matricula "
                        + "WHERE codigo_matricula = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoMatricula);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar matricula: "
                            + e.getMessage()
            );

            return false;
        }
    }
}
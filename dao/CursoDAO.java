package dao;

import model.Curso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    // ==============================
    // REGISTRAR
    // ==============================
    public boolean registrar(Curso curso) {

        String sql = "INSERT INTO curso "
                + "(codigo_curso, nombre, carrera, ciclo, horario, capacidad) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con =ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getCodigoCurso());
            ps.setString(2, curso.getNombre());
            ps.setString(3, curso.getCarrera());
            ps.setInt(4, curso.getCiclo());
            ps.setString(5, curso.getHorario());
            ps.setInt(6, curso.getCapacidad());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar curso: "
                    + e.getMessage());
            return false;
        }
    }


    // ==============================
    // CONSULTAR POR CÓDIGO
    // ==============================
    public Curso consultar(String codigoCurso) {

        String sql = "SELECT * FROM curso WHERE codigo_curso = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoCurso);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Curso(
                            rs.getString("codigo_curso"),
                            rs.getString("nombre"),
                            rs.getString("carrera"),
                            rs.getInt("ciclo"),
                            rs.getString("horario"),
                            rs.getInt("capacidad")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar curso: "
                    + e.getMessage());
        }

        return null;
    }


    // ==============================
    // LISTAR TODOS
    // ==============================
    public List<Curso> listar() {

        List<Curso> cursos = new ArrayList<>();

        String sql = "SELECT * FROM curso ORDER BY codigo_curso";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Curso curso = new Curso(
                        rs.getString("codigo_curso"),
                        rs.getString("nombre"),
                        rs.getString("carrera"),
                        rs.getInt("ciclo"),
                        rs.getString("horario"),
                        rs.getInt("capacidad")
                );

                cursos.add(curso);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar cursos: "
                    + e.getMessage());
        }

        return cursos;
    }


    // ==============================
    // ACTUALIZAR
    // ==============================
    public boolean actualizar(Curso curso) {

        String sql = "UPDATE curso SET "
                + "nombre = ?, "
                + "carrera = ?, "
                + "ciclo = ?, "
                + "horario = ?, "
                + "capacidad = ? "
                + "WHERE codigo_curso = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, curso.getNombre());
            ps.setString(2, curso.getCarrera());
            ps.setInt(3, curso.getCiclo());
            ps.setString(4, curso.getHorario());
            ps.setInt(5, curso.getCapacidad());
            ps.setString(6, curso.getCodigoCurso());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar curso: "
                    + e.getMessage());
            return false;
        }
    }


    // ==============================
    // ELIMINAR
    // ==============================
    public boolean eliminar(String codigoCurso) {

        String sql = "DELETE FROM curso WHERE codigo_curso = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoCurso);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar curso: "
                    + e.getMessage());
            return false;
        }
    }
}

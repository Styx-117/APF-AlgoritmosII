package dao;

import model.Alumno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAO {

    // REGISTRAR
    public boolean registrar(Alumno alumno) {

        String sql = "INSERT INTO alumno "
                + "(codigo_alumno, dni, nombres, apellidos, carrera, ciclo) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, alumno.getCodigoAlumno());
            ps.setString(2, alumno.getDni());
            ps.setString(3, alumno.getNombres());
            ps.setString(4, alumno.getApellidos());
            ps.setString(5, alumno.getCarrera());
            ps.setInt(6, alumno.getCiclo());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar alumno: "
                    + e.getMessage());
            return false;
        }
    }


    // CONSULTAR POR CÓDIGO
    public Alumno consultar(String codigoAlumno) {

        String sql = "SELECT * FROM alumno WHERE codigo_alumno = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoAlumno);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    return new Alumno(
                            rs.getString("codigo_alumno"),
                            rs.getString("dni"),
                            rs.getString("nombres"),
                            rs.getString("apellidos"),
                            rs.getString("carrera"),
                            rs.getInt("ciclo")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar alumno: "
                    + e.getMessage());
        }

        return null;
    }


    // LISTAR TODOS
    public List<Alumno> listar() {

        List<Alumno> alumnos = new ArrayList<>();

        String sql = "SELECT * FROM alumno ORDER BY codigo_alumno";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Alumno alumno = new Alumno(
                        rs.getString("codigo_alumno"),
                        rs.getString("dni"),
                        rs.getString("nombres"),
                        rs.getString("apellidos"),
                        rs.getString("carrera"),
                        rs.getInt("ciclo")
                );

                alumnos.add(alumno);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar alumnos: "
                    + e.getMessage());
        }

        return alumnos;
    }


    // ACTUALIZAR
    public boolean actualizar(Alumno alumno) {

        String sql = "UPDATE alumno SET "
                + "dni = ?, "
                + "nombres = ?, "
                + "apellidos = ?, "
                + "carrera = ?, "
                + "ciclo = ? "
                + "WHERE codigo_alumno = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, alumno.getDni());
            ps.setString(2, alumno.getNombres());
            ps.setString(3, alumno.getApellidos());
            ps.setString(4, alumno.getCarrera());
            ps.setInt(5, alumno.getCiclo());
            ps.setString(6, alumno.getCodigoAlumno());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar alumno: "
                    + e.getMessage());
            return false;
        }
    }


    // ELIMINAR
    public boolean eliminar(String codigoAlumno) {

        String sql = "DELETE FROM alumno WHERE codigo_alumno = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoAlumno);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar alumno: "
                    + e.getMessage());
            return false;
        }
    }
}
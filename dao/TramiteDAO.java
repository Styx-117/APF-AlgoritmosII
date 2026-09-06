package dao;



import model.Tramite;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TramiteDAO {

    // ==============================
    // REGISTRAR
    // ==============================
    public boolean registrar(Tramite tramite) {

        String sql = "INSERT INTO tramite "
                + "(codigo_tramite, codigo_alumno, tipo, "
                + "fecha_solicitud, fecha_limite, prioridad, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tramite.getCodigoTramite());
            ps.setString(2, tramite.getCodigoAlumno());
            ps.setString(3, tramite.getTipo());

            ps.setDate(4, Date.valueOf(tramite.getFechaSolicitud()));
            ps.setDate(5, Date.valueOf(tramite.getFechaLimite()));

            ps.setInt(6, tramite.getPrioridad());
            ps.setString(7, tramite.getEstado());

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar tramite: "
                    + e.getMessage());
            return false;
        }
    }


    // ==============================
    // CONSULTAR POR CÓDIGO
    // ==============================
    public Tramite consultar(String codigoTramite) {

        String sql = "SELECT * FROM tramite "
                + "WHERE codigo_tramite = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoTramite);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Tramite tramite = new Tramite(
                            rs.getString("codigo_tramite"),
                            rs.getString("codigo_alumno"),
                            rs.getString("tipo"),
                            rs.getDate("fecha_solicitud").toString(),
                            rs.getDate("fecha_limite").toString()
                    );

                    tramite.setPrioridad(
                            rs.getInt("prioridad")
                    );

                    tramite.setEstado(
                            rs.getString("estado")
                    );

                    return tramite;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar tramite: "
                    + e.getMessage());
        }

        return null;
    }


    // ==============================
    // LISTAR TODOS
    // ==============================
    public List<Tramite> listar() {

        List<Tramite> tramites = new ArrayList<>();

        String sql = "SELECT * FROM tramite "
                + "ORDER BY prioridad, fecha_solicitud";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Tramite tramite = new Tramite(
                        rs.getString("codigo_tramite"),
                        rs.getString("codigo_alumno"),
                        rs.getString("tipo"),
                        rs.getDate("fecha_solicitud").toString(),
                        rs.getDate("fecha_limite").toString()
                );

                tramite.setPrioridad(
                        rs.getInt("prioridad")
                );

                tramite.setEstado(
                        rs.getString("estado")
                );

                tramites.add(tramite);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar tramites: "
                    + e.getMessage());
        }

        return tramites;
    }


    // ==============================
    // ACTUALIZAR
    // ==============================
    public boolean actualizar(Tramite tramite) {

        String sql = "UPDATE tramite SET "
                + "codigo_alumno = ?, "
                + "tipo = ?, "
                + "fecha_solicitud = ?, "
                + "fecha_limite = ?, "
                + "prioridad = ?, "
                + "estado = ? "
                + "WHERE codigo_tramite = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, tramite.getCodigoAlumno());
            ps.setString(2, tramite.getTipo());

            ps.setDate(3, Date.valueOf(tramite.getFechaSolicitud()));
            ps.setDate(4, Date.valueOf(tramite.getFechaLimite()));

            ps.setInt(5, tramite.getPrioridad());
            ps.setString(6, tramite.getEstado());

            ps.setString(7, tramite.getCodigoTramite());

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar tramite: "
                    + e.getMessage());
            return false;
        }
    }


    // ==============================
    // ELIMINAR
    // ==============================
    public boolean eliminar(String codigoTramite) {

        String sql = "DELETE FROM tramite "
                + "WHERE codigo_tramite = ?";

        try (Connection con = ConexionDB.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, codigoTramite);

            int filas = ps.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar tramite: "
                    + e.getMessage());
            return false;
        }
    }
}

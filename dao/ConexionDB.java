package dao;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    private static final String URL = dotenv.get("DB_URL");
    private static final String USUARIO = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static Connection conectar() throws SQLException {

        if (URL == null || USUARIO == null || PASSWORD == null) {
            throw new SQLException(
                "No se encontraron las variables de conexión de la base de datos."
            );
        }

        return DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );
    }
}

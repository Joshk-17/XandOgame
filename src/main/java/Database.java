import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static Connection connect() {

        String url = "jdbc:postgresql://localhost:5433/xsandos";
        String user = "postgres";
        String password = "password";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to PostgreSQL");

            return connection;

        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();

            return null;
        }
    }
}
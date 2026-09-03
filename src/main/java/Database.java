import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;


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

    public static boolean registerUser(String username, String password) {

    // Hash the password before storing it
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, username);
        statement.setString(2, hashedPassword);

        statement.executeUpdate();

        connection.close();

        System.out.println("User registered");

        return true;

    } catch (SQLException e) {
        System.out.println("Could not register user");
        return false;
        }
    }

    public static boolean loginUser(String username, String password) {

    String sql = "SELECT password_hash FROM users WHERE username = ?";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        var results = statement.executeQuery();

        if (results.next()) {

            String storedHash = results.getString("password_hash");

            connection.close();

            return BCrypt.checkpw(password, storedHash);
        }

        connection.close();
        return false;

    } catch (SQLException e) {
        System.out.println("Login failed");
        return false;
    }
}

public static int getUserId(String username) {

    String sql = "SELECT user_id FROM users WHERE username = ?";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        var results = statement.executeQuery();

        if (results.next()) {

            int userId = results.getInt("user_id");

            connection.close();

            return userId;
        }

        connection.close();
        return -1;

    } catch (SQLException e) {
        System.out.println("Could not find user");
        return -1;
    }
}

public static boolean saveGame(int userId, String result, int duration) {

    String sql = "INSERT INTO games (user_id, result, duration) VALUES (?, ?, ?)";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, userId);
        statement.setString(2, result);
        statement.setInt(3, duration);

        statement.executeUpdate();

        connection.close();

        System.out.println("Game saved");

        return true;

    } catch (SQLException e) {
        System.out.println("Could not save game");
        return false;
    }
}

}
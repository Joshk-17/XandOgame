import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;


public class Database {

    //Connects app to database
    public static Connection connect() {

        String url = System.getenv().getOrDefault(
            "DB_URL",
            "jdbc:postgresql://localhost:5433/xsandos"
        );

        String user = System.getenv().getOrDefault(
            "DB_USER",
            "postgres"
        );

        String password = System.getenv().getOrDefault(
            "DB_PASSWORD",
            "password"
        );

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

    //Create new user
    public static boolean registerUser(String username, String password) {
    // Hash the password before storing it
    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

    String sql = "INSERT INTO users (username, password_hash) VALUES (?, ?)";

    try {
        Connection connection = connect();

       //Insert username and password
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

    //Check user credentials for login
    public static boolean loginUser(String username, String password) {

    String sql = "SELECT password_hash FROM users WHERE username = ?";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        var results = statement.executeQuery();

        if (results.next()) {

            //Gets hashed password and stores it
            String storedHash = results.getString("password_hash");
            connection.close();
            return BCrypt.checkpw(password, storedHash);
        }

        connection.close();

        //Compares entered password with stored password
        return false;

    } catch (SQLException e) {
        System.out.println("Login failed");
        return false;
    }
}

//Gets user id from usernam
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


//Save a completed game to table
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

//Get average duration of all games played by user
public static double getAverageDuration(int userId) {

    String sql = "SELECT AVG(duration) FROM games WHERE user_id = ?";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        var results = statement.executeQuery();

        if (results.next()) {
            double average = results.getDouble(1);
            connection.close();
            return average;
        }

        connection.close();
        return 0;

    } catch (SQLException e) {
        System.out.println("Could not get average duration");
        return 0;
    }
}

//Find shortest game won by user
public static int getShortestWin(int userId) {

    String sql = "SELECT MIN(duration) FROM games WHERE user_id = ? AND result = 'WIN'";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        var results = statement.executeQuery();

        if (results.next()) {
            int duration = results.getInt(1);
            connection.close();
            return duration;
        }

        connection.close();
        return 0;

    } catch (SQLException e) {
        System.out.println("Could not get shortest win");
        return 0;
    }
}

//Find longest game won by user
public static int getLongestWin(int userId) {

    String sql = "SELECT MAX(duration) FROM games WHERE user_id = ? AND result = 'WIN'";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        var results = statement.executeQuery();

        if (results.next()) {
            int duration = results.getInt(1);
            connection.close();
            return duration;
        }

        connection.close();
        return 0;

    } catch (SQLException e) {
        System.out.println("Could not get longest win");
        return 0;
    }
}

//Find shortest game lost by user
public static int getShortestLoss(int userId) {

    String sql = "SELECT MIN(duration) FROM games WHERE user_id = ? AND result = 'LOSS'";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        var results = statement.executeQuery();

        if (results.next()) {
            int duration = results.getInt(1);
            connection.close();
            return duration;
        }

        connection.close();
        return 0;

    } catch (SQLException e) {
        System.out.println("Could not get shortest loss");
        return 0;
    }
}

//Find longest game lost by user
public static int getLongestLoss(int userId) {

    String sql = "SELECT MAX(duration) FROM games WHERE user_id = ? AND result = 'LOSS'";

    try {
        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, userId);

        var results = statement.executeQuery();

        if (results.next()) {
            int duration = results.getInt(1);
            connection.close();
            return duration;
        }

        connection.close();
        return 0;

    } catch (SQLException e) {
        System.out.println("Could not get longest loss");
        return 0;
    }
}

//Count how many games user has played
public static int getGamesPlayed(int userId) {

    String sql = "SELECT COUNT(*) FROM games WHERE user_id = ?";

    try {

        Connection connection = connect();

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, userId);

        var results = statement.executeQuery();

        if (results.next()) {

            int gamesPlayed = results.getInt(1);

            connection.close();

            return gamesPlayed;
        }

        connection.close();

        return 0;

    } catch (SQLException e) {

        System.out.println("Could not get games played");

        return 0;
    }
}

}
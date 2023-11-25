import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionTest {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public static void main(String[] args) {
        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading MySQL JDBC driver");
        }

        // Test the database connection
        testDatabaseConnection();

        // Test a database query
        testDatabaseQuery();
    }

    private static void testDatabaseConnection() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }

    private static void testDatabaseQuery() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM Department");
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.println("=== Department Names ===");
            while (resultSet.next()) {
                String departmentName = resultSet.getString("name");
                System.out.println(departmentName);

                // Assertion: Department name should not be null
                assert departmentName != null : "Department name is null";
            }

        } catch (SQLException e) {
            System.err.println("Error executing database query:");
            e.printStackTrace();
        }
    }
}

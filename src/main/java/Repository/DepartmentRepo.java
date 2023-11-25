package Repository;

import model.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    // Initialize ArrayList instead of List
    private List<Department> departments = new ArrayList<>();

    public DepartmentRepo() {
        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading MySQL JDBC driver");
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Department) {
            Department department = (Department) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Department (departmentId, name, description) VALUES (?, ?, ?)")) {
                preparedStatement.setInt(1, department.getDepartmentId());
                preparedStatement.setString(2, department.getName());
                preparedStatement.setString(3, department.getDescription());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Department findById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Department WHERE departmentId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int departmentId = resultSet.getInt("departmentId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                return new Department(departmentId, name, description);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Department WHERE departmentId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Department> findAll() {
        List<Department> departments = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Department");

            while (resultSet.next()) {
                int departmentId = resultSet.getInt("departmentId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                departments.add(new Department(departmentId, name, description));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departments;
    }
}

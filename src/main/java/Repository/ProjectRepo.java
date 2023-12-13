package Repository;

import model.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public ProjectRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Project) {
            Project project = (Project) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Project (name, description, budget, coordinator) VALUES (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getDescription());
                preparedStatement.setBigDecimal(3, project.getBudget());
                preparedStatement.setString(4, project.getCoordinator());

                preparedStatement.executeUpdate();

                // Retrieve the generated projectId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        project.setProjectId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Project findById(int id) {
        Project project = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Project WHERE projectId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                java.math.BigDecimal budget = resultSet.getBigDecimal("budget");
                String coordinator = resultSet.getString("coordinator");

                project = new Project(id, name, description, budget, coordinator);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return project;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Project WHERE projectId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Project> findAll() {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Project");

            while (resultSet.next()) {
                int projectId = resultSet.getInt("projectId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                java.math.BigDecimal budget = resultSet.getBigDecimal("budget");
                String coordinator = resultSet.getString("coordinator");

                projects.add(new Project(projectId, name, description, budget, coordinator));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return projects;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

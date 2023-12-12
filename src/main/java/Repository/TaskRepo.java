package Repository;

import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public TaskRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Task) {
            Task task = (Task) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Task (difficulty, duration, volunteerId, taskDescription) VALUES (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, task.getDifficulty());
                preparedStatement.setInt(2, task.getDuration());
                preparedStatement.setInt(3, task.getVolunteerId());
                preparedStatement.setString(4, task.getTaskDescription());
                preparedStatement.executeUpdate();

                // Retrieve the generated taskId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        task.setTaskId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Object findById(int id) {
        Task task = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Task WHERE taskId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String difficulty = resultSet.getString("difficulty");
                int duration = resultSet.getInt("duration");
                int volunteerId = resultSet.getInt("volunteerId");
                String taskDescription = resultSet.getString("taskDescription");

                task = new Task(id, difficulty, duration, volunteerId, taskDescription);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return task;
    }
    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Task WHERE taskId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> findAll() {
        List<Task> tasks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Task");

            while (resultSet.next()) {
                int taskId = resultSet.getInt("taskId");
                String difficulty = resultSet.getString("difficulty");
                int duration = resultSet.getInt("duration");
                int volunteerId = resultSet.getInt("volunteerId");
                String taskDescription = resultSet.getString("taskDescription");

                tasks.add(new Task(taskId, difficulty, duration, volunteerId, taskDescription));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }
    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

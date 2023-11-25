package Repository;

import model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    // Initialize ArrayList instead of List
    private List<Task> tasks = new ArrayList<>();

    public TaskRepo() {
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
        if (entity instanceof Task) {
            Task task = (Task) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Task (taskId, difficulty, duration, volunteerId, taskDescription) VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setInt(1, task.getTaskId());
                preparedStatement.setString(2, task.getDifficulty());
                preparedStatement.setInt(3, task.getDuration());
                preparedStatement.setInt(4, task.getVolunteerId());
                preparedStatement.setString(5, task.getTaskDescription());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object findById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Task WHERE taskId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int taskId = resultSet.getInt("taskId");
                String difficulty = resultSet.getString("difficulty");
                int duration = resultSet.getInt("duration");
                int volunteerId = resultSet.getInt("volunteerId");
                String taskDescription = resultSet.getString("taskDescription");

                return new Task(taskId, difficulty, duration, volunteerId, taskDescription);
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
}

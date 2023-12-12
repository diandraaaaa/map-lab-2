package Repository;

import model.Task;
import model.Volunteer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VolunteerRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public VolunteerRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Volunteer) {
            Volunteer volunteer = (Volunteer) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Volunteer (name, email, phone, departmentId) VALUES (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, volunteer.getName());
                preparedStatement.setString(2, volunteer.getEmail());
                preparedStatement.setString(3, volunteer.getPhone());
                preparedStatement.setInt(4, volunteer.getDepartmentId());
                preparedStatement.executeUpdate();

                // Retrieve the generated volunteerId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        volunteer.setVolunteerId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Volunteer findById(int id) {
        Volunteer volunteer = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Volunteer WHERE volunteerId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                int departmentId = resultSet.getInt("departmentId");

                List<Task> tasksDone = getTasksForVolunteerId(id);

                volunteer = new Volunteer(id, name, email, phone, departmentId, tasksDone);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return volunteer;
    }
    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Volunteer WHERE volunteerId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Volunteer> findAll() {
        List<Volunteer> volunteers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Volunteer");

            while (resultSet.next()) {
                int volunteerId = resultSet.getInt("volunteerId");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                int departmentId = resultSet.getInt("departmentId");

                List<Task> tasksDone = getTasksForVolunteerId(volunteerId);

                volunteers.add(new Volunteer(volunteerId, name, email, phone, departmentId, tasksDone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return volunteers;
    }

    private List<Task> getTasksForVolunteerId(int volunteerId) {
        List<Task> tasksDone = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM VolunteerTask JOIN Task ON VolunteerTask.taskId = Task.taskId WHERE VolunteerTask.volunteerId = ?")) {
            preparedStatement.setInt(1, volunteerId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int taskId = resultSet.getInt("taskId");
                String difficulty = resultSet.getString("difficulty");
                int duration = resultSet.getInt("duration");
                String taskDescription = resultSet.getString("taskDescription");

                tasksDone.add(new Task(taskId, difficulty, duration, volunteerId, taskDescription));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasksDone;
    }
    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

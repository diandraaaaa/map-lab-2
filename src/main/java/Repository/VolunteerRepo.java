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

    private List<Volunteer> volunteers = new ArrayList<>();

    public VolunteerRepo() {
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
        if (entity instanceof Volunteer) {
            Volunteer volunteer = (Volunteer) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Volunteer (volunteerId, name, email, phone, departmentId) VALUES (?, ?, ?, ?, ?)")) {
                preparedStatement.setInt(1, volunteer.getVolunteerId());
                preparedStatement.setString(2, volunteer.getName());
                preparedStatement.setString(3, volunteer.getEmail());
                preparedStatement.setString(4, volunteer.getPhone());
                preparedStatement.setInt(5, volunteer.getDepartmentId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Volunteer findById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Volunteer WHERE volunteerId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int volunteerId = resultSet.getInt("volunteerId");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                int departmentId = resultSet.getInt("departmentId");

                List<Task> tasksDone = getTasksForVolunteerId(id);

                return new Volunteer(volunteerId, name, email, phone, departmentId, tasksDone);
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
}

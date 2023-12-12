package Repository;

import model.Training;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public TrainingRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Training) {
            Training training = (Training) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Training (name, description) VALUES (?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, training.getName());
                preparedStatement.setString(2, training.getDescription());
                preparedStatement.executeUpdate();

                // Retrieve the generated trainingId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        training.setTrainingId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Training findById(int id) {
        Training training = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Training WHERE trainingId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                training = new Training(id, name, description);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return training;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Training WHERE trainingId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Training> findAll() {
        List<Training> trainings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Training");

            while (resultSet.next()) {
                int trainingId = resultSet.getInt("trainingId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                trainings.add(new Training(trainingId, name, description));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return trainings;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

package Repository;

import model.Award;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AwardRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public AwardRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Award) {
            Award award = (Award) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Award (name, description, dateReceived, volunteerId) VALUES (?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, award.getName());
                preparedStatement.setString(2, award.getDescription());
                preparedStatement.setDate(3, award.getDateReceived());
                preparedStatement.setInt(4, award.getVolunteer().getVolunteerId());

                preparedStatement.executeUpdate();

                // Retrieve the generated awardId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        award.setAwardId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Award findById(int id) {
        Award award = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Award WHERE awardId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date dateReceived = resultSet.getDate("dateReceived");

                award = new Award(id, name, description, dateReceived);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return award;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Award WHERE awardId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Award> findAll() {
        List<Award> awards = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Award");

            while (resultSet.next()) {
                int awardId = resultSet.getInt("awardId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                Date dateReceived = resultSet.getDate("dateReceived");

                awards.add(new Award(awardId, name, description, dateReceived));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return awards;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

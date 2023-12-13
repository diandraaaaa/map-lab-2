package Repository;

import model.Participant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipantRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public ParticipantRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Participant) {
            Participant participant = (Participant) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Participant (name, birthDay, email, phone, registrationDate, additionalNotes) VALUES (?, ?, ?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, participant.getName());
                preparedStatement.setDate(2, participant.getBirthDay());
                preparedStatement.setString(3, participant.getEmail());
                preparedStatement.setString(4, participant.getPhone());
                preparedStatement.setDate(5, participant.getRegistrationDate());
                preparedStatement.setString(6, participant.getAdditionalNotes());

                preparedStatement.executeUpdate();

                // Retrieve the generated participantId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        participant.setParticipantId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Participant findById(int id) {
        Participant participant = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Participant WHERE participantId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                Date birthDay = resultSet.getDate("birthDay");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                Date registrationDate = resultSet.getDate("registrationDate");
                String additionalNotes = resultSet.getString("additionalNotes");

                participant = new Participant(id, name, birthDay, email, phone, registrationDate, additionalNotes);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return participant;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Participant WHERE participantId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Participant> findAll() {
        List<Participant> participants = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Participant");

            while (resultSet.next()) {
                int participantId = resultSet.getInt("participantId");
                String name = resultSet.getString("name");
                Date birthDay = resultSet.getDate("birthDay");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                Date registrationDate = resultSet.getDate("registrationDate");
                String additionalNotes = resultSet.getString("additionalNotes");

                participants.add(new Participant(participantId, name, birthDay, email, phone, registrationDate, additionalNotes));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return participants;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

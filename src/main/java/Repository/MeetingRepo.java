package Repository;

import model.Meeting;
import model.Volunteer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MeetingRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public MeetingRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Meeting) {
            Meeting meeting = (Meeting) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Meeting (title, dateAndTime, location, agenda, duration_minutes) VALUES (?, ?, ?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, meeting.getTitle());
                preparedStatement.setTimestamp(2, meeting.getDateAndTime());
                preparedStatement.setString(3, meeting.getLocation());
                preparedStatement.setString(4, meeting.getAgenda());
                preparedStatement.setInt(5, meeting.getDurationMinutes());

                preparedStatement.executeUpdate();

                // Retrieve the generated meetingId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        meeting.setMeetingId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Meeting findById(int id) {
        Meeting meeting = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Meeting WHERE meetingId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String title = resultSet.getString("title");
                Timestamp dateAndTime = resultSet.getTimestamp("dateAndTime");
                String location = resultSet.getString("location");
                String agenda = resultSet.getString("agenda");
                int durationMinutes = resultSet.getInt("duration_minutes");

                meeting = new Meeting(id, title, dateAndTime, location, agenda, durationMinutes);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return meeting;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Meeting WHERE meetingId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Meeting> findAll() {
        List<Meeting> meetings = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Meeting");

            while (resultSet.next()) {
                int meetingId = resultSet.getInt("meetingId");
                String title = resultSet.getString("title");
                Timestamp dateAndTime = resultSet.getTimestamp("dateAndTime");
                String location = resultSet.getString("location");
                String agenda = resultSet.getString("agenda");
                int durationMinutes = resultSet.getInt("duration_minutes");

                meetings.add(new Meeting(meetingId, title, dateAndTime, location, agenda, durationMinutes));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return meetings;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

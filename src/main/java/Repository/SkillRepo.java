package Repository;

import model.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public SkillRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Skill) {
            Skill skill = (Skill) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Skill (name, description) VALUES (?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setString(1, skill.getName());
                preparedStatement.setString(2, skill.getDescription());
                preparedStatement.executeUpdate();

                // Retrieve the generated skillId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        skill.setSkillId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Skill findById(int id) {
        Skill skill = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Skill WHERE skillId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                skill = new Skill(id, name, description);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return skill;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Skill WHERE skillId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Skill> findAll() {
        List<Skill> skills = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Skill");

            while (resultSet.next()) {
                int skillId = resultSet.getInt("skillId");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                skills.add(new Skill(skillId, name, description));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return skills;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

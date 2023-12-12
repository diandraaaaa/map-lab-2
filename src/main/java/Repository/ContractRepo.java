package Repository;

import model.Contract;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContractRepo implements Repo {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/MAP";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "razvandiandra";

    public ContractRepo() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error loading MySQL JDBC driver", e);
        }
    }

    @Override
    public void save(Object entity) {
        if (entity instanceof Contract) {
            Contract contract = (Contract) entity;
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "INSERT INTO Contract (startDate, endDate, volunteerId) VALUES (?, ?, ?)",
                         Statement.RETURN_GENERATED_KEYS)) {

                preparedStatement.setDate(1, contract.getStartDate());
                preparedStatement.setDate(2, contract.getEndDate());
                preparedStatement.setInt(3, contract.getVolunteerId());
                preparedStatement.executeUpdate();

                // Retrieve the generated contractId
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contract.setContractId(generatedKeys.getInt(1));
                    }
                }

            } catch (SQLException e) {
                handleSQLException(e);
            }
        }
    }

    @Override
    public Contract findById(int id) {
        Contract contract = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT * FROM Contract WHERE contractId = ?")) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                int volunteerId = resultSet.getInt("volunteerId");

                contract = new Contract(id, startDate, endDate, volunteerId);
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return contract;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "DELETE FROM Contract WHERE contractId = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }
    }

    @Override
    public List<Contract> findAll() {
        List<Contract> contracts = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Contract");

            while (resultSet.next()) {
                int contractId = resultSet.getInt("contractId");
                Date startDate = resultSet.getDate("startDate");
                Date endDate = resultSet.getDate("endDate");
                int volunteerId = resultSet.getInt("volunteerId");

                contracts.add(new Contract(contractId, startDate, endDate, volunteerId));
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return contracts;
    }

    private void handleSQLException(SQLException e) {
        e.printStackTrace();  // Log or handle the exception appropriately in a production environment
    }
}

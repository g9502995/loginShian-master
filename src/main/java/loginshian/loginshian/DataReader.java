package loginshian.loginshian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DataReader {
    private Connection connection;
    private DatabaseManager dbManager;

    public DataReader(Connection connection, DatabaseManager dbManager) {
        this.connection = connection;
        this.dbManager = dbManager;
    }

    public boolean isPlayerRegistered(String playerName) {
        String sql = "SELECT username FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            ResultSet result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyPassword(String playerName, String password) {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String storedPassword = result.getString("password");
                return MD5.uncode(password).equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void changePassword(String playerName, String password) {
        String sql = "UPDATE users SET password = ? WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, MD5.uncode(password));
            statement.setString(2, playerName);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(String playerName, String password, String UUID) {
        String sql = "INSERT INTO users (username, password, uuid) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            statement.setString(2, MD5.uncode(password));
            statement.setString(3, UUID);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}

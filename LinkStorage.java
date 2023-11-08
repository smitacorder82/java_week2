import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LinkStorage {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/url_shortener";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public void storeMapping(String shortURL, String longURL) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO url_mappings (short_url, long_url) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, shortURL);
            statement.setString(2, longURL);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String retrieveLongURL(String shortURL) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT long_url FROM url_mappings WHERE short_url = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, shortURL);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("long_url");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}


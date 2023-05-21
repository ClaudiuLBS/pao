import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.*;

public class DbContext {
    static String url = System.getenv("DB_URL");
    static String username = System.getenv("DB_USERNAME");
    static String password = System.getenv("DB_PASSWORD");
    static Connection connection;
    private static DbContext instance;
    private DbContext() {
        startConnection();
    }
    public static DbContext getInstance(){
        if(instance == null){
            instance = new DbContext();
        }
        return instance;
    }


    public void startConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void closeConnection() {
        try  {
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    ResultSet executeQuery(String sql) {
        try {
            Statement statement = connection.createStatement();
            return statement.executeQuery(sql);
        } catch (Exception e){
            System.out.println("EXECUTE QUERY: " + e);
            return null;
        }
    }

    Integer executeInsert(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Failed to insert item.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve inserted item ID.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    void executeUpdate(String sql) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

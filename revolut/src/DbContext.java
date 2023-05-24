import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    private void writeToFile(String text) {
       try {
           FileWriter csvFile = new FileWriter("report.csv", true);
           PrintWriter out = new PrintWriter(csvFile);
           LocalDateTime timestamp = LocalDateTime.now();
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
           String timestampString = timestamp.format(formatter);
           out.println(timestampString + ", " + text);
           out.close();
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
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
    ResultSet executeQuery(String sql, String msg) {
        try {
            Statement statement = connection.createStatement();
            writeToFile(msg);
            return statement.executeQuery(sql);
        } catch (Exception e){
            System.out.println("EXECUTE QUERY: " + e);
            return null;
        }
    }
    Integer executeInsert(String sql, String msg) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            int affectedRows = statement.executeUpdate();
            writeToFile(msg);

            if (affectedRows == 0) {
                throw new SQLException("Failed to insert item.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next())
                    return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    Integer executeUpdate(String sql, String msg) {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int affectedRows = statement.executeUpdate();
            writeToFile(msg);
            return affectedRows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}

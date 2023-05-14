import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class DbContext {
    String url = "jdbc:mysql://localhost:3306/revolut";
    String username = "claudiu";
    String password = "claudiu";
    Connection connection;
    Map<Class, String> typeMap = new HashMap<>();
    Vector<Class> ignoredClasses = new Vector<>();
    public DbContext() {
        typeMap.put(String.class, "VARCHAR(255)");
        typeMap.put(Currency.class, "VARCHAR(255)");
        typeMap.put(Boolean.class, "BOOL");
        typeMap.put(Integer.class, "INT");
        typeMap.put(Double.class, "DOUBLE(10,5)");
        typeMap.put(LocalDate.class, "DATE");

        ignoredClasses.add(Timer.class);
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
            System.out.println(e);
            return null;
        }
    }
    void createTable(Class c) {
        String sql = "CREATE TABLE IF NOT EXISTS " + c.getName() + "(";
        for (Field field : c.getDeclaredFields()) {
            Type fieldType = field.getGenericType();
            System.out.println(field.getName() + "-----------");
            if (fieldType instanceof ParameterizedType parameterizedType) {
                for (Type typeArgument : parameterizedType.getActualTypeArguments()) {
                    if (typeArgument instanceof Class<?> typeClass) {
                        String type = typeMap.getOrDefault(typeClass, "none");
                        if (Objects.equals(type, "none") && ignoredClasses.contains(typeClass)) {
                            createTable(typeClass);
                        }
                    }
                }
            } else if (fieldType instanceof Class<?> fieldClass) {
                String type = typeMap.getOrDefault(fieldClass, "none");
                if (Objects.equals(type, "none") && ignoredClasses.contains(fieldClass)) {
                    createTable(fieldClass);
                }
            }
            System.out.println("");
        }
    }
}

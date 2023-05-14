import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Currency;

public class Main {
    public static MainMenu menu = MainMenu.getInstance();
    public static void main(String[] args) throws IOException {
        DbContext db = new DbContext();
        db.createTable(User.class);
        menu.Menu();
    }
}

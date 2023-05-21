import java.io.IOException;

public class Main {
    public static MainMenu menu = MainMenu.getInstance();
    public static void main(String[] args) throws IOException {
        DbContext db = DbContext.getInstance();
        menu.Menu();
        db.closeConnection();
    }
}

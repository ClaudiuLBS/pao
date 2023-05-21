import java.io.IOException;

public class Main {
    public static MainMenu menu = MainMenu.getInstance();
    public static void main(String[] args) throws IOException {
        DbContext db = new DbContext();
        menu.Menu();
    }
}

public class Main {
    public static MainMenu menu = MainMenu.getInstance();
    public static void main(String[] args) {
        DbContext db = DbContext.getInstance();
        menu.Menu();
        db.closeConnection();
    }
}

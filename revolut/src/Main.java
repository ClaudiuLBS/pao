import java.io.IOException;

public class Main {

    public static Vault c = new Vault(125d, 4d);

    public static MainMenu menu = MainMenu.getInstance();


    public static void main(String[] args) throws IOException{
//        System.out.println("Working...");
//        System.out.println(loan);
//        System.out.println(c);
//        System.out.println(acc);
        menu.Menu();

    }
}

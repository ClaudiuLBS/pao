import java.io.IOException;

public class Main {
    public static Loan loan = new Loan("profi", 3.5, 36);
//    public static Account acc = new Account("e409845865896", "EUR");
    public static Share share = new Share("bitcoin",  "BIT", 1.56, 25d );
    public static Vault c = new Vault(125d, 4d);

    public static MainMenu menu = MainMenu.getInstance();

    public void f(){
        System.out.println(loan);
    }


    public static void main(String[] args) throws IOException{
//        System.out.println("Working...");
//        System.out.println(loan);
//        System.out.println(c);
//        System.out.println(acc);
        menu.Menu();

    }
}

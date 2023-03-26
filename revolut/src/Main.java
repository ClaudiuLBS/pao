import java.io.IOException;

public class Main {
    public static Loan loan = new Loan("profi", 3.5, 36);
//    public static Account acc = new Account("e409845865896", "EUR");
    public static Share share = new Share("bitcoin",  "BIT", 1.56, 25d );
    public static Vault c = new Vault(125d, 4d);



    public void f(){
        System.out.println(loan);
    }


    public static void main(String[] args) throws IOException {
        System.out.println("Press arrow keys to move:");
        while (true) {
            int input = System.in.read();
            if (input == 27 && System.in.read() == 91) { // Check if the input is an escape sequence for arrow keys
                int arrow = System.in.read();
                if (arrow == 68) {
                    System.out.println("Left");
                } else if (arrow == 67) {
                    System.out.println("Right");
                } else if (arrow == 65) {
                    System.out.println("Up");
                } else if (arrow == 66) {
                    System.out.println("Down");
                }
            }
        }
    }
}

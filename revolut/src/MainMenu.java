import java.util.Scanner;
import java.util.Vector;
import java.io.Console

public final class MainMenu {
    private static MainMenu instance;
    private static Vector<User> users;

    private MainMenu() {
        this.users = new Vector<>();
    }

    public static MainMenu getInstance(){
        if(instance == null){
            instance = new MainMenu();
        }
        return instance;
    }

    public void Register(){
        Console console = System.console();

        User user = new User();
        Scanner scanner = new Scanner(System.in);
        String firstName, lastName, email, phone;

        System.out.println("Welcome to the registration form!");
        System.out.println("Please enter your information below:");

        System.out.print("First Name: ");
        user.setFirstName(scanner.nextLine());

        System.out.print("Last Name: ");
        user.setLastName(scanner.nextLine());

        System.out.print("Email Address: ");
        user.setEmail(scanner.nextLine());

        System.out.print("Phone Number: ");
        user.setPhoneNumber(scanner.nextLine());

        String password = console.readPassword("Password");
        System.out.print("Password: ");
        user.setPhoneNumber(scanner.nextLine());



        // check for
        System.out.println(user);
        users.add(user);



    }

    public void LogIn(){

    }

    public void Menu(){

        Scanner scanner = new Scanner(System.in);
        final String  menu1Options[] = {"Login", "Register", "Exit"};
        int optionsLength = menu1Options.length;
        int arrowIndex = 0;
        final String indenter = "\t\t\t\t";

        while(true){

            System.out.print("\033[H\033[2J");
            System.out.flush();


            System.out.println(indenter + "Welcome\n");
            for(int i = 0; i < optionsLength; ++i){
                if (i == arrowIndex){
                    System.out.println(indenter + "->" + menu1Options[i]);
                }
                else{
                    System.out.println(indenter + "  " + menu1Options[i]);
                }
            }
            int input = scanner.nextInt();

            switch (input){
                //up arrow
                case 65:
                    if(arrowIndex > 0){
                        arrowIndex --;
                    }
                    break;
                //down arrow
                case 66:
                    if(arrowIndex + 1 < optionsLength) {
                        arrowIndex++;
                    }
                    break;
                case 10:
                    switch (arrowIndex){
                        // login
                        case 0:
                            LogIn();
                            break;
                        // register
                        case 1:
                            Register();
                            break;
                        //exit
                        case 2:
                            System.exit(0);
                    }
                default:
                    break;

            }

        }

    }



}

import java.util.Scanner;
import java.util.Vector;
import java.io.Console;

public final class MainMenu {
    private static MainMenu instance;
    private static Vector<User> users;
    private static Integer currentMenu = 0;

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

//        String password = console.readPassword("Password");
        System.out.print("Password: ");
        user.setPhoneNumber(scanner.nextLine());



        // check for
        System.out.println(user);
        users.add(user);
        currentMenu = 1;
    }

    public void LogIn(){

    }

    public void landingMenu() {
        if (currentMenu != 0) return;
        Scanner scanner = new Scanner(System.in);
        final String  menu1Options[] = {"Login", "Register", "Exit"};
        int optionsLength = menu1Options.length;
        System.out.print("\033[H\033[2J");
        System.out.flush();


        System.out.println("---------------------Welcome---------------------\n");


        for(int i = 0; i < optionsLength; ++i) {
            System.out.println(i + 1 + "." + menu1Options[i]);
        }

        System.out.print("\nYour answer : ");
        int input = scanner.nextInt();

        switch (input){
            case 1:
                LogIn();
                break;
            case 2:
                Register();
                break;
            case 3:
                System.exit(0);
            default:
                break;
        }
    }

    public void userMenu() {
        if (currentMenu != 1) return;
    }
    public void Menu(){
        while (true) {
            landingMenu();
            userMenu();
        }
    }



}

import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;

public final class MainMenu {
    private static MainMenu instance;
    private static Vector<User> users;
    private static Integer currentMenu = 0;
    private boolean search(String where, String what) {
        switch (where) {
            case "email":
                for (User user : users) {
                    return Objects.equals(user.getEmail(), what);

                }
            case "phone":
                for (User user : users) {
                    return Objects.equals(user.getPhoneNumber(), what);
                }
            default:
                return false;
        }
    }
    private User currentUser;
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
        System.out.print("\033[H\033[2J");
        System.out.flush();
        User user = new User();
        Scanner scanner = new Scanner(System.in);
        boolean registerCheck;
        System.out.print("***************************************************************************************************\n");

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

        System.out.print("Password: ");
        user.setPassword(scanner.nextLine());

        // check for
        registerCheck = !(search("email", user.getEmail()) | search("phone", user.getPhoneNumber()));
        if(registerCheck) {
            System.out.println(user);
            users.add(user);
        }
        else{
            System.out.println("Phone number or email already exists!");
            return;
        }
        users.add(user);
        currentUser = user;
        currentMenu = 1;
    }

    public void LogIn(){
    }

    public void landingMenu() {
        if (currentMenu != 0) return;
        Scanner scanner = new Scanner(System.in);
        final String[] menu1Options = {"Login", "Register", "Exit"};
        int optionsLength = menu1Options.length;
        System.out.print("\033[H\033[2J");
        System.out.flush();


        System.out.println("---------------------Welcome---------------------\n");


        for(int i = 0; i < optionsLength; ++i) {
            System.out.println(i + 1 + "." + menu1Options[i]);
        }

        System.out.print("\nYour answer: ");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> LogIn();
            case 2 -> Register();
            case 0 -> System.exit(0);
            default -> {
            }
        }
    }

    public void userInfo() {
        System.out.println(currentUser);
        System.out.println("Type anything to go back...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
    public void userAccounts() {
    }
    public void userCards() {

    }
    public void userTransactions() {

    }

    public void userAssets() {
    }

    public void userMenu() {
        if (currentMenu != 1) return;
        System.out.println("Logged in as " + currentUser.getFirstName());
        Scanner scanner = new Scanner(System.in);
        final String[] menuOptions = {"User info", "Accounts", "Cards", "Transactions", "Assets", "Exit"};
        int optionsLength = menuOptions.length;
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("***************************************************************************************************\n");
        for(int i = 0; i < optionsLength; ++i) {
            System.out.println(i + 1 + "." + menuOptions[i]);
        }

        System.out.print("\nYour answer : ");
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> userInfo();
            case 2 -> userAccounts();
            case 3 -> userCards();
            case 4 -> userTransactions();
            case 5 -> userAssets();
            case 0 -> System.exit(0);
            default -> {
            }
        }
    }
    public void Menu(){
        while (true) {
            landingMenu();
            userMenu();
        }
    }



}

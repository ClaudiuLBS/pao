import java.io.IOException;
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

    public void Register() {
        User user = new User();
        boolean registerCheck;
        System.out.print("******************************2*********************************************************************\n");

        System.out.println("Welcome to the registration form!");
        System.out.println("Please enter your information below:");
        Scanner scanner = new Scanner(System.in);

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
        if(!registerCheck) {
            System.out.println("Phone number or email already exists!");
            return;
        }

        System.out.println(user);
        users.add(user);
        currentUser = user;
        currentMenu = 1;
    }

    public void LogIn() {
        String email, password;
        System.out.print("Email Address: ");
        Scanner scanner = new Scanner(System.in);
        email = scanner.nextLine();
        System.out.print("Password: ");
        password = scanner.nextLine();
        for (User user : users)
            if (Objects.equals(user.getEmail(), email) && Objects.equals(user.getPassword(), password)) {
                currentUser = user;
                currentMenu = 1;
                return;
            }

        System.out.println("Info not good try again");
    }

    public void landingMenu() {
        if (currentMenu != 0) return;
        final String[] menu1Options = {"Login", "Register", "Exit"};
        int optionsLength = menu1Options.length;
        System.out.print("\033[H\033[2J");
        System.out.flush();


        System.out.println("---------------------Welcome---------------------\n");


        for(int i = 0; i < optionsLength; ++i) {
            System.out.println(i + 1 + "." + menu1Options[i]);
        }

        System.out.print("\nYour answer: ");
        Scanner scanner = new Scanner(System.in);
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
        Scanner scanner = new Scanner(System.in);
        System.out.println(currentUser);
        System.out.println("Type anything to go back...");
        String input = scanner.nextLine();
    }
    public void userAccounts() {
//        display accounts
//        create new account
//        te duce in form de creare account in care alegi currenciul
    }
    public void userCards() {
//        display cards
//        create new card
//        type card tag
    }

    public void userTransactions() throws IOException {
//        afisam tranzactiile
        System.out.println("New Transaction? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input.startsWith("N"))
            return;

        System.out.println("TO (IBAN): ");
        String to = scanner.nextLine();
        System.out.println("AMOUNT: ");
        Double amount = scanner.nextDouble();
        currentUser.makeTransaction(to, amount, users);
    }

    public void displayShares() {
//        display toate asseturile hardcodate cu indici
//        scriem indicele assetului pe care vrem sa-l cumparam
//        zicem cat vrea sa cumpere
//        gata
    }
    public void displayCrypto() {
//        la fel ca la actiuni
    }
    public void displayYourCrypto() {
//         afisam cat avem din fiecare cryto
//        apoi intr0un form zicem cat vrem sa stacam, pt indicele uni crypto

    }
    public void userAssets() {
//        afisam asseturile
        System.out.println("1. Buy Shares");
        System.out.println("2. Buy Crypto");
        System.out.println("3. Stack Crypto");
        Scanner scanner = new Scanner(System.in);
        Integer input = scanner.nextInt();
        switch (input) {
            case 1 -> displayShares();
            case 2 -> displayCrypto();
            case 3 -> displayYourCrypto();
            default -> {}
        }

    }

    public void userMenu() throws IOException {
        if (currentMenu != 1) return;
        System.out.println("***************************************************************************************************\n");
        System.out.println("Logged in as " + currentUser.getFirstName());
        final String[] menuOptions = {"Account information", "Accounts", "Cards", "Transactions", "Assets", "Vault", "Sign Out"};
        int optionsLength = menuOptions.length;
        System.out.print("\033[H\033[2J");
        System.out.flush();

        for(int i = 0; i <= optionsLength; ++i) {
            if (i == optionsLength)
                System.out.println("0. Exit");
            else
                System.out.println(i + 1 + "." + menuOptions[i]);
        }

        System.out.print("\nYour answer : ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> userInfo();
            case 2 -> userAccounts();
            case 3 -> userCards();
            case 4 -> userTransactions();
            case 5 -> userAssets();
            case 6 -> currentMenu = 0;
            case 0 -> System.exit(0);
            default -> {
            }
        }
    }
    public void Menu() throws IOException{
        while (true) {
            landingMenu();
            userMenu();
        }
    }



}

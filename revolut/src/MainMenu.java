import java.io.IOException;
import java.util.*;

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

    public void pressEnterToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Press Enter to continue...");
        scanner.nextLine();

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


        System.out.println("---------------------Welcome---------------------\n");


        for(int i = 0; i < optionsLength; ++i) {
            System.out.println(i + 1 + "." + menu1Options[i]);
        }

        System.out.print("\n>: ");
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
        System.out.println(currentUser);
        pressEnterToContinue();
    }

    public void userAccounts() {
        currentUser.showUserAccounts();
        System.out.print("\nCreate new account? (Y/N)\n> ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (!input.toLowerCase().startsWith("y"))
            return;

        final String[] currencies = {"USD", "EUR", "GBP", "RON"};
        System.out.println("Select currency: ");
        for (int i = 0; i < currencies.length; i++) {
            System.out.println(i + 1 + ". " + currencies[i]);
        }
        System.out.print("> ");
        int currencyIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        var account =  currentUser.createAccount(Currency.getInstance(currencies[currencyIndex]));
        System.out.println("Account successfully created with IBAN '" + account.getIBAN()  + "'.");
        pressEnterToContinue();
    }

    public void userCards() {
        currentUser.showUserCards();
        System.out.print("\nCreate new card? (Y/N)\n> ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (!input.toLowerCase().startsWith("y"))
            return;
        System.out.print("Enter card tag: ");
        String tag = scanner.nextLine();
        System.out.print("Enter card limit (0 for no limit): ");
        Double limit = scanner.nextDouble();
        currentUser.createCard(tag, limit);
        System.out.println("Card successfully created with tag '" + tag + "' and limit '" + limit + "'.");
        pressEnterToContinue();
    }

    public void userTransactions() throws IOException {
        currentUser.showUserTransactions();
        System.out.println("New Transaction? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (!input.toLowerCase().startsWith("y"))
            return;

        System.out.print("TO (IBAN): ");
        String to = scanner.nextLine();
        System.out.print("AMOUNT: ");
        Double amount = scanner.nextDouble();

        try {
            currentUser.makeTransaction(to, amount, users);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            pressEnterToContinue();
        }
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
        int input = scanner.nextInt();
        switch (input) {
            case 1 -> displayShares();
            case 2 -> displayCrypto();
            case 3 -> displayYourCrypto();
            default -> {}
        }

    }
    public void userVault() {
        System.out.println("1. Vault info");
        System.out.println("2. Change the amount saved each day");
        System.out.println("3. Withdraw");

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        switch (input) {
            case 1 -> System.out.println(currentUser.getVault());
            case 2 -> setVaultSpd();
            case 3 -> withdrawVault();
            default -> {}
        }


    }
    public void setVaultSpd(){
        System.out.println("Your current amount saved each day : " + currentUser.getVault().getSavingPerDay());
        System.out.println("The new amount : ");

        Scanner scanner = new Scanner(System.in);
        double input = scanner.nextDouble();
        currentUser.getVault().setSavings(input);
        currentMenu = 1;
    }

    public void withdrawVault(){
        System.out.println("Balance : " + currentUser.getVault().getSavingPerDay());
        System.out.println("Amount : ");

        Scanner scanner = new Scanner(System.in);
        double input = scanner.nextDouble();
        double balance = currentUser.getVault().getSavings();
        if(input <= balance) {
            currentUser.getAccounts().get(0).deposit(input);
            currentUser.getVault().setSavings(balance - input);
            currentMenu = 1;
        }
    }



    public void userWithdraw() {

    }

    public void userDeposit() {

    }

    public void userMenu() throws IOException {
        if (currentMenu != 1) return;
        System.out.println("\nLogged in as " + currentUser.getFirstName());
        final String[] menuOptions = {"User Information", "Accounts", "Cards", "Transactions", "Assets", "Vault", "Withdraw", "Deposit", "Sign Out"};
        int optionsLength = menuOptions.length;

        for(int i = 0; i <= optionsLength; ++i) {
            if (i == optionsLength)
                System.out.println("0. Exit");
            else
                System.out.println(i + 1 + ". " + menuOptions[i]);
        }

        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> userInfo();
            case 2 -> userAccounts();
            case 3 -> userCards();
            case 4 -> userTransactions();
            case 5 -> userAssets();
            case 6 -> userVault();
            case 7 -> userWithdraw();
            case 8 -> userDeposit();
            case 9 -> currentMenu = 0;
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

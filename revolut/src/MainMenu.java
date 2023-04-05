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
        pressEnterToContinue();
    }

    public void accountMenu(int accIndex) {
        System.out.println("ACCOUNT " + accIndex + "\n");
        var acc = currentUser.getAccounts().get(accIndex);
        System.out.println(acc + "\n");
        String[] menuOptions = {"Withdraw", "Deposit", "Terminate"};
        for (int i = 0; i < menuOptions.length; i++) {
            System.out.println(i + 1 + ". " + menuOptions[i]);
        }
        System.out.println("0. Back");
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextInt();
        if (input == 0 || input > menuOptions.length) {
            currentMenu = 2;
            return;
        }
        if (input == 3) {
            currentUser.terminateAccount(acc);
            System.out.println("Successfully terminated account " + accIndex);
            pressEnterToContinue();
            return;
        }

        System.out.print("Amount: ");
        var amount = scanner.nextDouble();
        if (input == 1) {
            try {
                acc.withdraw(amount);
                System.out.println("Successfully withdrawn " + amount + " from account " + accIndex);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            pressEnterToContinue();
        } else if (input == 2) {
            acc.deposit(amount);
            System.out.println("Successfully deposited " + amount + " from account " + accIndex);
            pressEnterToContinue();
        }
    }

    public void userAccounts() {
        if (currentMenu != 2) return;

        currentUser.showUserAccounts();
        System.out.print("\nPick an account or an action:\n> ");
        Scanner scanner = new Scanner(System.in);
        var input = scanner.nextInt();

        if (input == 0 || input > currentUser.getAccounts().size() + 1) {
            currentMenu = 1;
            return;
        }

        if (input == currentUser.getAccounts().size() + 1) {
            final String[] currencies = {"USD", "EUR", "GBP", "RON"};
            System.out.println("Pick currency: ");
            for (int i = 0; i < currencies.length; i++) {
                System.out.println(i + 1 + ". " + currencies[i]);
            }
            System.out.print("> ");
            int currencyIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            var account =  currentUser.createAccount(Currency.getInstance(currencies[currencyIndex]));
            System.out.println("Account successfully created with IBAN '" + account.getIBAN()  + "'.");
            pressEnterToContinue();
            return;
        }
        accountMenu(input - 1);
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
            System.out.println("Successfully sent " + amount + " to " + to + ".");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        pressEnterToContinue();
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

    }


    public void userMenu() throws IOException {
        if (currentMenu != 1) return;
        System.out.println("\nLogged in as " + currentUser.getFirstName());
        final String[] menuOptions = {"User Information", "Accounts", "Cards", "Transactions", "Assets", "Vault", "Sign Out"};
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
            case 2 -> currentMenu = 2;
            case 3 -> userCards();
            case 4 -> userTransactions();
            case 5 -> userAssets();
            case 6 -> userVault();
            case 7 -> currentMenu = 0;
            case 0 -> System.exit(0);
            default -> {
            }
        }
    }
    public void Menu() throws IOException{
        while (true) {
            landingMenu(); // 0
            userMenu(); // 1
            userAccounts(); // 2
        }
    }



}

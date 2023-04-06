import java.io.IOException;
import java.util.*;

public final class MainMenu {
    private static MainMenu instance;
    private static Vector<User> users;
    private static Integer currentMenu = 0;
    private static final Share[] availableCompanies = {
        new Share("Apple Inc.", "AAPL", 568.20, 0.64),
        new Share("Amazon.com Inc.", "AMZN", 3749.80, 0.0),
        new Share("Microsoft Corporation", "MSFT", 397.30, 0.80),
        new Share("Facebook, Inc.", "FB", 303.2, 0.0),
        new Share("Alphabet Inc.", "GOOGL", 2237.50, 0.0)
    };
    private static final CryptoCurrency[] availableCrypto = {
        new CryptoCurrency("Ethereum", "ETH", 3683.43, 6.6),
        new CryptoCurrency("Cardano", "ADA", 3.01, 5.3),
        new CryptoCurrency("Polkadot", "DOT", 59.52, 13.5),
        new CryptoCurrency("Solana", "SOL", 264.10, 8.5),
        new CryptoCurrency("Binance Coin", "BNB", 812.15, 9.1),
    };
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
        users = new Vector<>();
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

    public void accountsMenu() {
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
        for (int i = 0; i < availableCompanies.length; i++) {
            System.out.println(i + 1 + ". " + availableCompanies[i].name + " | " + availableCompanies[i].value + "$ ");
        }
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        var companyIdx = scanner.nextInt() - 1;
        System.out.print("amount: ");
        var amount = scanner.nextDouble();

        try {
            currentUser.buyAsset(availableCompanies[companyIdx], amount);
            System.out.println("Successfully bought " + amount + " " + availableCompanies[companyIdx].abbreviation + " shares.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        pressEnterToContinue();
    }
    public void displayCrypto() {
        for (int i = 0; i < availableCrypto.length; i++) {
            System.out.println(i + 1 + ". " + availableCrypto[i].name + " | " + availableCrypto[i].value + "$ ");
        }
        System.out.print("> ");
        Scanner scanner = new Scanner(System.in);
        var companyIdx = scanner.nextInt() - 1;
        System.out.print("amount: ");
        var amount = scanner.nextDouble();

        try {
            currentUser.buyAsset(availableCrypto[companyIdx], amount);
            System.out.println("Successfully bought " + amount + " " + availableCrypto[companyIdx].abbreviation + " crypto.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        pressEnterToContinue();
    }
    public void displayYourCrypto() {
//         afisam cat avem din fiecare cryto
//        apoi intr0un form zicem cat vrem sa stacam, pt indicele uni crypto

    }
    public void assetsMenu() {
        if (currentMenu != 3) return;
        currentUser.showUserAssets();

        String[] menuOptions = {"Buy Shares", "Buy Crypto", "Stack Crypto"};
        for (int i = 0; i < menuOptions.length; i++)
            System.out.println(i + 1 + ". " + menuOptions[i]);
        System.out.println("0. Back");
        System.out.print("> ");

        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        switch (input) {
            case 1 -> displayShares();
            case 2 -> displayCrypto();
            case 3 -> displayYourCrypto();
            case 0 -> currentMenu = 1;
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
            case 5 -> currentMenu = 3;
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
            accountsMenu(); // 2
            assetsMenu(); // 3
        }
    }



}

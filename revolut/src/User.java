import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Map<Asset, Double> assetsOwned;
    private Vector<Account> accounts;
    private Vector<Card> cards;
    private Vector<Transaction> transactions;

    private String password;
    public User() {
        this.accounts = new Vector<>();
        this.createAccount(Currency.getInstance(new Locale("ro", "RO")));
        this.cards = new Vector<>();
        this.createCard("Default", 0.0);
        this.transactions = new Vector<>();
        this.assetsOwned = new HashMap<>();
    }

    public User(
            String firstName,
            String lastName,
            String phoneNumber,
            String password,
            Map<Asset, Double> assetsOwned) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.assetsOwned = assetsOwned;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Map<Asset, Double> getAssetsOwned() {
        return assetsOwned;
    }

    public void setAssetsOwned(Map<Asset, Double> assetsOwned) {
        this.assetsOwned = assetsOwned;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Vector<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Vector<Account> accounts) {
        this.accounts = accounts;
    }

    public Vector<Card> getCards() {
        return cards;
    }

    public void setCards(Vector<Card> cards) {
        this.cards = cards;
    }

    public Vector<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Vector<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        Double balance = 0.0;
        for (Account a : accounts)
            balance += a.getBalance();
        return balance;
    }

    public Double getAssetsValue() {
        Double value = 0.0;
        for (Asset a : assetsOwned.keySet())
            value += a.value * assetsOwned.get(a);
        return value;
    }
    public void makeTransaction(String IBAN, Double amount, Vector<User> users) throws IOException {
        Double tax = amount * 0.05;
        Account senderAccount = null;
        for (Account account : this.accounts) {
            if (account.getBalance() >= amount + tax)
                senderAccount = account;
        }

        if (senderAccount == null)
            throw new IOException("Unfortunately, your current financial situation does not permit you to engage in any transactions at this time.");

        senderAccount.withdraw(amount + tax);
        Transaction transaction = new Transaction(senderAccount.getIBAN(), IBAN, amount, tax, LocalDate.now());
        this.transactions.add(transaction);


        for (User user : users) {
            for (Account account : user.accounts) {
                if (Objects.equals(account.getIBAN(), IBAN)) {
                    user.accounts.get(0).deposit(amount);
                    user.transactions.add(transaction);
                    break;
                }
            }
        }

    }

    public void buyAsset(Asset asset, Double amount) throws IOException{
        for (Account account : this.accounts) {
            if (account.getBalance() >= asset.getValue() * amount) {
                account.withdraw(asset.getValue() * amount);
                this.assetsOwned.put(asset, this.assetsOwned.getOrDefault(asset, 0.0) + amount);
            }
        }
        throw new IOException("I'm sorry, but it seems that your current financial situation does not allow for the purchase of this item at this time. Perhaps it would be best to consider more affordable options or save up for the future.");
    }

    public void sellAsset(Asset asset, Double amount) throws IOException{
        double ownedAmount = this.assetsOwned.getOrDefault(asset, 0.0);
        if (ownedAmount < amount)
            throw new IOException("I apologize, but it appears that the amount of this asset currently in your possession is not sufficient.");

        this.assetsOwned.put(asset, ownedAmount - amount);
        this.accounts.get(0).deposit(asset.getValue() * amount);
    }

    private String generateIBAN() {
        StringBuilder IBAN = new StringBuilder("RO47 BRVL");
        Random random = new Random();
        for (int i = 1; i <= 4; i++) {
            int randomNumber = random.nextInt(9000) + 1000;
            IBAN.append(" ").append(randomNumber);
        }
        return IBAN.toString();
    }
    public Account createAccount(Currency currency) {
        Account account = new Account(this.generateIBAN(), currency);
        this.accounts.add(account);
        return account;
    }

    private String generateCardNumber() {
        StringBuilder cardNumber = new StringBuilder("0420");
        Random random = new Random();
        for (int i = 1; i <= 3; i++) {
            int randomNumber = random.nextInt(9000) + 1000;
            cardNumber.append(" ").append(randomNumber);
        }
        return cardNumber.toString();
    }
    public Card createCard(String tag, Double limit) {
        Random random = new Random();
        Card card = new Card(tag, this.generateCardNumber(), limit, random.nextInt(900) + 100, LocalDate.now().plusYears(4));
        this.cards.add(card);
        return card;
    }

    @Override
    public String toString() {
        return
            "Name: " + firstName + " " + lastName + '\n' +
            "Phone Number: " + phoneNumber + '\n' +
            "Email: " + email + '\n' +
            "Accounts: " + accounts.size() + '\n' +
            "Cards: " + cards.size() + '\n' +
            "Transactions: " + transactions.size() + '\n' +
            "Assets Value: " + getAssetsValue() + '\n' +
            "Balance: " + getBalance() + '\n';
    }
}

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;

public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String address;
    private Map<Asset, Double> assetsOwned;
    private Vector<Account> accounts;
    private Vector<Card> cards;
    private Vector<Transaction> transactions;
    public User() {

    }

    public User(
            String firstName,
            String lastName,
            String phoneNumber,
            String address,
            Boolean premiumAccount,

            Map<Asset, Double> assetsOwned) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.assetsOwned = assetsOwned;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void makeTransaction(String IBAN, Double amount, Vector<User> users) throws IOException {
        Double tax = amount * 0.05;
        Account senderAccount = null;
        for (Account account : this.accounts) {
            if (account.getBalance() >= amount + tax)
                senderAccount = account;
        }

        if (senderAccount == null)
            throw new IOException("Unfortunately, your current financial situation does not permit you to engage in any transactions at this time.");

        senderAccount.setBalance(senderAccount.getBalance() - amount - tax);

        Transaction transaction = new Transaction(senderAccount.getIBAN(), IBAN, amount, tax, LocalDate.now());
        this.transactions.add(transaction);


        for (User user : users) {
            for (Account account : user.accounts) {
                if (Objects.equals(account.getIBAN(), IBAN)) {
                    user.transactions.add(transaction);
                    break;
                }
            }
        }

    }

}

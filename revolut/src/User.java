import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;


public class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Map<Asset, Double> assetsOwned;
    private Vector<Account> accounts;
    private Vector<Card> cards;
    private Vector<Transaction> transactions;

    private Vault vault;

    private String password;

    private Timer timer;

    public User() {
        this.accounts = new Vector<>();
        this.createAccount(Currency.getInstance(new Locale("ro", "RO")));
        this.cards = new Vector<>();
        this.createCard("Default", 0.0);
        this.transactions = new Vector<>();
        this.assetsOwned = new HashMap<>();
        this.vault = new Vault();
        this.timer = new Timer();
        startTimer();
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
        this.accounts = new Vector<>();
        this.createAccount(Currency.getInstance(new Locale("ro", "RO")));
        this.cards = new Vector<>();
        this.createCard("Default", 0.0);
        this.transactions = new Vector<>();
        this.assetsOwned = new HashMap<>();
        this.vault = new Vault();
        this.timer = new Timer();
        startTimer();
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

    public void terminateAccount(Account acc) {
        accounts.remove(acc);
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
                    account.deposit(amount);
                    user.transactions.add(transaction);
                    break;
                }
            }
        }

    }

    public void buyAsset(Asset asset, Double amount) {
        for (Account account : this.accounts) {
            if (account.getBalance() >= asset.getValue() * amount) {
                account.withdraw(asset.getValue() * amount);
                this.assetsOwned.put(asset, this.assetsOwned.getOrDefault(asset, 0.0) + amount);
                return;
            }
        }
        throw new RuntimeException("I'm sorry, but it seems that your current financial situation does not allow for the purchase of this item at this time. Perhaps it would be best to consider more affordable options or save up for the future.");
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

    public void showUserAccounts(){
        System.out.println("ACCOUNTS: ");
        for(Account acc : accounts){
            System.out.println(accounts.indexOf(acc) + 1 + ".");
            System.out.println(acc + "\n");
        }
        System.out.println(accounts.size() + 1 + ". Create new Account");
        System.out.println("0. Back");
    }
    public void showUserCards(){
        System.out.println("CARDS: ");
        for(Card card : cards){
            if (card != cards.get(0))
                System.out.println("\n***************************************************************************************************");
            System.out.println(card);

        }
    }

    public void showUserTransactions(){
        for(Transaction trn : transactions){
            System.out.println(trn);
            System.out.println("***************************************************************************************************");

        }
    }

    public void showUserAssets() {
        System.out.println("Shares:");
        for (Asset a : assetsOwned.keySet()) {
            if (a instanceof CryptoCurrency) continue;
            System.out.println("***************************************************************************************************");
            System.out.println(a.getAbbreviation());
            System.out.println("Balance : " + assetsOwned.get(a));
            System.out.println("Value : " + assetsOwned.get(a) * a.getValue());
            System.out.println("***************************************************************************************************");
        }
        System.out.println("Crypto:");
        for (Asset a : assetsOwned.keySet()) {
            if (a instanceof Share) continue;
            System.out.println("***************************************************************************************************");
            System.out.println(a.getAbbreviation());
            System.out.println("Balance : " + assetsOwned.get(a));
            System.out.println("Value : " + assetsOwned.get(a) * a.getValue());
            System.out.println("***************************************************************************************************");
        }
    }

    public void saveToVault(){
        Double svpd = vault.getSavingPerDay();
        if(getBalance() > svpd){
            for (Account a : accounts){
                if (svpd == 0.0){
                    break;
                }
                else{
                    if(a.getBalance() < svpd){
                        svpd -= a.getBalance();
                        vault.addToSavings(a.getBalance());
                        a.setBalance(0.0);
                    }
                    else{
                        vault.addToSavings(svpd);
                        a.withdraw(svpd);
                        svpd = 0.0;
                    }
                }
            }
        }
    }

    private void getCryptoStaking(){

    }

    private void startTimer(){
//        timer.scheduleAtFixedRate(new SaveToVault(), 0, 24 * 60 * 60 * 1000); //24 de ore
        timer.scheduleAtFixedRate(new SaveToVault(), 0, 5 * 1000); //24 de ore
    }

    private class SaveToVault extends TimerTask{
        @Override
        public void run(){
            User.this.saveToVault();
        }
    }

    private class GetCryptoStaking extends TimerTask{
        @Override
        public void run(){
            User.this.getCryptoStaking();
        }
    }
    public void withdrawFromVault(Double amount){
        if (amount > vault.getSavings()){
            System.out.println("I regret to inform you that currently, there are not enough funds available in the vault.");
        }
        else{
            vault.setSavings(vault.getSavings() - amount);
            accounts.get(0).deposit(amount);
            System.out.println("Successfully withdrawn " + amount + " from vault.");
        }
    }

    public Vault getVault(){
        return vault;
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

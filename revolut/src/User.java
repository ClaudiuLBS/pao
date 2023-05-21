import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;


public class User {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private Map<Asset, Double> assetsOwned;
    private SortedMap<CryptoCurrency, Double> stakedAmount;
    private Vector<Account> accounts;
    private Vector<Card> cards;
    private Vector<Transaction> transactions;
    private String password;
    private Vault vault;
    private Timer vaultTimer;
    private Timer cryptoTimer;
    private DbContext dbContext = DbContext.getInstance();
    public User() {
        this.accounts = new Vector<>();
        this.cards = new Vector<>();
        this.transactions = new Vector<>();
        this.assetsOwned = new HashMap<>();
        this.stakedAmount = new TreeMap<>();
        this.vault = new Vault();
        this.cryptoTimer = new Timer();
        this.vaultTimer = new Timer();
        insertToDB();
        this.createAccount(Currency.getInstance("RON"));
        this.createCard("Default", 0.0);
        startTimer();
    }

    public void insertToDB() {
//      CREATE VAULT
        String sql = "INSERT INTO vault (total_savings, savings_per_day) VALUES (%.5f, %.5f)".formatted(vault.getSavings(), vault.getSavingPerDay());
        Integer vaultId = dbContext.executeInsert(sql);
//      CREATE USER
        sql = "INSERT INTO user (first_name, last_name, phone_number, email, password, vault_id) VALUES ('%s', '%s', '%s', '%s', '%s', %d)"
                .formatted(firstName, lastName, phoneNumber, email, password, vaultId);
        id = dbContext.executeInsert(sql);
    }

    public void updateDbInfo() {
        dbContext.executeUpdate(
                "UPDATE user SET first_name = '%s', last_name = '%s', phone_number = '%s', email = '%s', password = '%s' WHERE id = %d"
                .formatted(firstName, lastName, phoneNumber, email, password, id)
            );
    }
    public User(
            Integer id,
            String firstName,
            String lastName,
            String email,
            String phoneNumber,
            String password,
            Integer vaultId
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;

        ResultSet dbVault = dbContext.executeQuery("SELECT * FROM vault WHERE id = %d".formatted(vaultId));
        try {
            dbVault.next();
            this.vault = new Vault(
                dbVault.getInt("id"),
                dbVault.getDouble("total_savings"),
                dbVault.getDouble("savings_per_day")
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.accounts = new Vector<>();
        this.cards = new Vector<>();
        this.transactions = new Vector<>();
        this.assetsOwned = new HashMap<>();
        this.stakedAmount = new TreeMap<>();
        this.cryptoTimer = new Timer();
        this.vaultTimer = new Timer();

        startTimer();
    }

    public User(Integer id) {
//        dbContext.executeQuery("")
    }
    public Integer getId() {
        return id;
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

    public SortedMap<CryptoCurrency, Double> getStakedAmount() {
        return stakedAmount;
    }

    public void setStakedAmount(SortedMap<CryptoCurrency, Double> stakedAmount) {
        this.stakedAmount = stakedAmount;
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
            value += a.getValue() * assetsOwned.get(a);
        return value;
    }

    public void terminateAccount(Account acc) {
//      @TODO REMOVE FROM DATABASE
        accounts.remove(acc);
    }

    public void makeTransaction(String IBAN, Double amount, Vector<User> users) {
//      @TODO ADD TRANSACTION TO DATABASE
        Double tax = amount * 0.05;
        Account senderAccount = null;
        for (Account account : this.accounts) {
            if (account.getBalance() >= amount + tax)
                senderAccount = account;
        }

        if (senderAccount == null)
            throw new RuntimeException("Unfortunately, your current financial situation does not permit you to engage in any transactions at this time.");

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
//      @TODO ASSET TO DATABASE in shares_owned OR crypto_owned

        for (Account account : this.accounts) {
            if (account.getBalance() >= asset.getValue() * amount) {
                account.withdraw(asset.getValue() * amount);
                this.assetsOwned.put(asset, this.assetsOwned.getOrDefault(asset, 0.0) + amount);
                return;
            }
        }
        throw new RuntimeException("I'm sorry, but it seems that your current financial situation does not allow for the purchase of this item at this time. Perhaps it would be best to consider more affordable options or save up for the future.");
    }

    public void sellAsset(Asset asset, Double amount){
//      @TODO DECREASE ASSET AMOUNT FROM shares_owned OR crpyto_owned, AND INCREASE USER BALANCE

        double ownedAmount = this.assetsOwned.getOrDefault(asset, 0.0);
        if (ownedAmount < amount)
            throw new RuntimeException("I apologize, but it appears that the amount of this asset currently in your possession is not sufficient.");

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
        String sql = "INSERT INTO account (user_id, iban, balance, currency) VALUES (%d, '%s', %.5f, '%s')"
                .formatted(id, account.getIBAN(), account.getBalance(), account.getCurrency().getCurrencyCode());
        Integer accountId = dbContext.executeInsert(sql);
        account.setId(accountId);
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String expDate = card.getExpirationDate().format(formatter);
        String sql = "INSERT INTO card (user_id, tag, number, card_limit, cvv, expiration_date) VALUES (%d, '%s', '%s', %.5f, %d, '%s')"
                .formatted(id, card.getTag(), card.getNumber(), card.getLimit(), card.getCVV(), expDate);
        Integer cardId = dbContext.executeInsert(sql);
        card.setId(cardId);

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

    public void showShares() {
        System.out.println("Shares:");
        for (Asset a : assetsOwned.keySet()) {
            if (a instanceof CryptoCurrency) continue;
            System.out.println("***************************************************************************************************");
            System.out.println(a.getAbbreviation());
            System.out.println("Balance : " + assetsOwned.get(a));
            System.out.println("Value : " + assetsOwned.get(a) * a.getValue());
            System.out.println("***************************************************************************************************");
        }
    }
    Vector<CryptoCurrency> showCrypto(boolean withIndices, boolean withStakedAmount) {
        System.out.println("Crypto:");
        Vector<CryptoCurrency> ownedCrypto = new Vector<>();
        int i = 0;
        for (Asset a : assetsOwned.keySet()) {
            if (a instanceof Share) continue;
            i += 1;
            System.out.println("***************************************************************************************************");
            if (withIndices) System.out.println(i + ". ");
            System.out.println(a.getAbbreviation());
            System.out.println("Balance : " + assetsOwned.get(a));
            System.out.println("Value : " + assetsOwned.get(a) * a.getValue());
            if (withStakedAmount) System.out.println("Staked amount: " + stakedAmount.getOrDefault(a, 0.0));
            System.out.println("***************************************************************************************************");
            ownedCrypto.add((CryptoCurrency) a);
        }
        return ownedCrypto;
    }

    public void stackCrypto(CryptoCurrency crypto, Double amount) {
//      @TODO UPDATE staked_amount FROM crypto_owned

        if (assetsOwned.get(crypto) < amount) {
            System.out.println("Not enough " + crypto.getName());
            return;
        }
        assetsOwned.put(crypto, assetsOwned.getOrDefault(crypto, 0.0) - amount);
        stakedAmount.put(crypto, stakedAmount.getOrDefault(crypto, 0.0) + amount);
        System.out.println("Successfully staked " + amount + " " + crypto.getAbbreviation());
    }

    public void withdrawCrypto(CryptoCurrency crypto, Double amount) {
//      @TODO UPDATE staked_amount FROM crypto_owned

        if (stakedAmount.getOrDefault(crypto, 0.0) < amount) {
            System.out.println("Not enough crypto to withdraw.");
            return;
        }
        stakedAmount.put(crypto, stakedAmount.getOrDefault(crypto, 0.0) - amount);
        assetsOwned.put(crypto, assetsOwned.getOrDefault(crypto, 0.0) + amount);
        System.out.println("Successfully withdrawn " + amount + " " + crypto.getAbbreviation());
    }
    public void showUserAssets() {
        showShares();
        showCrypto(false, true);
    }

    public void saveToVault(){
//      @TODO UPDATE VAULT AND ACCOUNT BALANCE

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
        for(Map.Entry<CryptoCurrency, Double> crypto : stakedAmount.entrySet()) {
            double stakeRate = crypto.getKey().getStakingReturn();
            stakedAmount.put(crypto.getKey(), crypto.getValue() + crypto.getValue() * stakeRate);
        }
    }


    private void startTimer(){
//        timer.scheduleAtFixedRate(new SaveToVault(), 0, 24 * 60 * 60 * 1000); //24 de ore
        vaultTimer.scheduleAtFixedRate(new SaveToVault(), 0, 5 * 1000); //5 secunde
//        cryptoTimer.scheduleAtFixedRate(new GetCryptoStaking(), 0, 30 * 24 * 60 * 60 * 1000); //30 de zile
        cryptoTimer.scheduleAtFixedRate(new GetCryptoStaking(), 0, 10 * 1000); //10 secunde
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

    public void withdrawFromVault(Double amount) {
//      @TODO UPDATE VAULT AND ACCOUNT BALANCE
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
            "Id: " + id + '\n' +
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

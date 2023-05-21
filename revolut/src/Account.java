import java.util.Currency;

public class Account {
    private Integer id;
    private String IBAN;
    private Double balance = 0.0;
    private Currency currency;

    public Account() {
    }

    public Account(String IBAN, Currency currency) {
        this.IBAN = IBAN;
        this.currency =  currency;
    }

    public Account(Integer id, String IBAN, Double balance, String currency) {
        this.id = id;
        this.IBAN = IBAN;
        this.balance = balance;
        this.currency = Currency.getInstance(currency);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void withdraw(Double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            throw new RuntimeException("I'm sorry, but there are currently insufficient funds in your account to complete the requested withdrawal");
        }
    }

    public void deposit(Double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "IBAN: " + IBAN + "\nCurrency: " + currency + "\nBalance: "  + getBalance();
    }
}

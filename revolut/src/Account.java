import java.util.Currency;

public class Account {
    private String IBAN;
    private Double balance = 0.0;
    private Currency currency;

    public Account() {
    }

    public Account(String IBAN, Currency currency) {
        this.IBAN = IBAN;
        this.currency =  currency;
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
        }
    }

    public void deposit(Double amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "Account information\n" +
                "\nIBAN : " + IBAN + '\n' +
                "\nCurrency : " + currency;
    }
}

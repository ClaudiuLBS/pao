public class Account {
    private String IBAN;
    private Double balance;
    private String currency;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Account() {
    }

    public Account(String IBAN, String currency) {
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
    @Override
    public String toString() {
        return "Account information\n" +
                "\nIBAN : " + IBAN + '\n' +
                "\nCurrency : " + currency;
    }
}

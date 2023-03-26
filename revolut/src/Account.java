public class Account {
    private String IBAN;
    private Double balance;

    public Account() {
    }

    public Account(String IBAN, String cardNumber, User owner) {
        this.IBAN = IBAN;
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
}

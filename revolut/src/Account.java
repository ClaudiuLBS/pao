public class Account {
    private String IBAN;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    private String currency;

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


    @Override
    public String toString() {
        return "Account information\n" +
                "\nIBAN : " + IBAN + '\n' +
                "\nCurrency : " + currency;
    }
}

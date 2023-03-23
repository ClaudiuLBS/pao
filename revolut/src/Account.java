public class Account {
    private String IBAN;
    private String cardNumber;
    private Client owner;

    public Account() {
    }

    public Account(String IBAN, String cardNumber, Client owner) {
        this.IBAN = IBAN;
        this.cardNumber = cardNumber;
        this.owner = owner;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }
}

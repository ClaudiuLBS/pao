import java.time.LocalDate;

public class Transaction {
    //cand facem o tranzactie se aduaga la ambii useri
    private String senderIBAN;
    private String receiverIBAN;
    private Double amount;
    private Double tax;
    private LocalDate date;

    public Transaction() {
    }

    public Transaction(String senderIBAN, String receiverIBAN, Double amount, Double tax, LocalDate date) {
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.tax = tax;
        this.date = date;
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public void setSenderIBAN(String senderIBAN) {
        this.senderIBAN = senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public void setReceiverIBAN(String receiverIBAN) {
        this.receiverIBAN = receiverIBAN;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax() {
        return tax;
    }

    public void setTax(Double tax) {
        this.tax = tax;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction" +
                "\nSenderIBAN : " + senderIBAN +
                "\nReceiverIBAN : " + receiverIBAN +
                "\nAmount : " + amount +
                "\nTax : " + tax +
                "\nDate : " + date;
    }
}

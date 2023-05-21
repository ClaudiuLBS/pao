import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private Integer id;
    private String senderIBAN;
    private String receiverIBAN;
    private Double amount;
    private Double tax;
    private LocalDate date;

    public Transaction() {}

    public Transaction(String senderIBAN, String receiverIBAN, Double amount, Double tax, LocalDate date) {
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.tax = tax;
        this.date = date;
        insertToDb();
    }
    public Transaction(Integer id, String senderIBAN, String receiverIBAN, Double amount, Double tax, String date) {
        this.id = id;
        this.senderIBAN = senderIBAN;
        this.receiverIBAN = receiverIBAN;
        this.amount = amount;
        this.tax = tax;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.date = LocalDate.parse(date, formatter);
    }
    private void insertToDb() {
        DbContext dbContext = DbContext.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String txDate = date.format(formatter);
        String sql = "INSERT INTO transaction (sender_iban, receiver_iban, amount, tax, transaction_date) VALUES ('%s', '%s', %.5f, %.5f, '%s')"
                .formatted(senderIBAN, receiverIBAN, amount, tax, txDate);
        id = dbContext.executeInsert(sql);
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "SenderIBAN: " + senderIBAN +
                "\nReceiverIBAN: " + receiverIBAN +
                "\nAmount: " + amount +
                "\nTax: " + tax +
                "\nDate: " + date;
    }
}

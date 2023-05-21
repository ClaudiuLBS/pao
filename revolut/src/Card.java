import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Card {
    private Integer id;
    private String tag;
    private String number;
    private Double limit;
    private Integer CVV;
    private LocalDate expirationDate;
    public Card() {}

    public Card(String tag, String number, Double limit, Integer CVV, LocalDate expirationDate) {
        this.tag = tag;
        this.number = number;
        this.limit = limit;
        this.CVV = CVV;
        this.expirationDate = expirationDate;
    }

    public Card(Integer id, String tag, String number, Double limit, Integer CVV, String expirationDate) {
        this.id = id;
        this.tag = tag;
        this.number = number;
        this.limit = limit;
        this.CVV = CVV;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.expirationDate = LocalDate.parse(expirationDate, formatter);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Integer getCVV() {
        return CVV;
    }

    public void setCVV(Integer CVV) {
        this.CVV = CVV;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return  "\nTag: " + tag +
                "\nNumber : " + number +
                "\nCVV : " + CVV +
                "\nLimit : " + limit;
    }
}

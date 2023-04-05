import java.time.LocalDate;

public class Card {
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

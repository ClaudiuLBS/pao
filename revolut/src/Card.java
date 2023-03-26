public class Card {
    private String tag;
    private String number;
    private Double limit;
    private Integer CVV;
    public Card() {
    }

    public Card(String tag, String number, Double limit) {
        this.tag = tag;
        this.number = number;
        this.limit = limit;
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

    @Override
    public String toString() {
        return  "Card information" +
                "\nTag : '" + tag +
                "\nNumber : " + number +
                "\nCVV : " + CVV +
                "\nLimit : " + limit;
    }
}

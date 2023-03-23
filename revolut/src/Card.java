public class Card {
    private User owner;
    private String tag;
    private String number;
    private Double limit;

    public Card() {}

    public Card(User owner, String tag, String number, Double limit) {
        this.owner = owner;
        this.tag = tag;
        this.number = number;
        this.limit = limit;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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
}

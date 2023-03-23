public class Card {
    private Client owner;
    private String tag;
    private String number;
    private Double limit;

    public Card() {
    }

    public Card(Client owner, String tag, String number, Double limit) {
        this.owner = owner;
        this.tag = tag;
        this.number = number;
        this.limit = limit;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
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

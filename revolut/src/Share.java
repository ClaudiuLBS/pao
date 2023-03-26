public class Share extends Asset {
    protected Double dividends;

    public Share() {

    }

    public Share(String name, String abbr, Double value, Double dividents) {
        super(name, abbr, value);
        this.dividends = dividents;
    }

    public Double getDividends() {
        return dividends;
    }

    public void setDividends(Double dividends) {
        this.dividends = dividends;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nDividends=" + dividends;
    }
}

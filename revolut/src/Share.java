public class Share extends Asset {
    protected Double dividends;

    public Share() {}

    public Share(Integer id, String name, String abbreviation, Double value, Double dividends) {
        super(id, name, abbreviation, value);
        this.dividends = dividends;
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

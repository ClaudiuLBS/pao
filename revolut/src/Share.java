import java.time.Duration;

public class Share extends Asset {
    protected Double dividents;

    public Share() {

    }

    public Share(String name, String abbr, Double value, Double dividents) {
        super(name, abbr, value);
        this.dividents = dividents;
    }

    public Double getDividents() {
        return dividents;
    }

    public void setDividents(Double dividents) {
        this.dividents = dividents;
    }
}

import java.awt.dnd.DragSourceDragEvent;

public class Loan {
    private String type;
    private Double interestRate;
    private Integer period;

    public Loan() {

    }

    public Loan(String type, Double interestRate, Integer period) {
        this.type = type;
        this.interestRate = interestRate;
        this.period = period;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    @Override
    public String toString() {
        return  "Type : " + type +
                "\nInterestRate : " + interestRate +
                "\nPeriod=" + period;
    }

}

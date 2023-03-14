import java.awt.dnd.DragSourceDragEvent;

public class Loan {
    private String type;
    private Double interestRate;
    private Integer period;

    public Loan() {

    }

    public Loan(String type, Double interestRate, Integer period){
        this.type = type;
        this.interestRate = interestRate;
        this.period= period;
    }
}

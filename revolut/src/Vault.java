public class Vault {
    private Integer id;
    private Double savings;

    private Double savingPerDay;
    private final DbContext dbContext = DbContext.getInstance();

    public Vault() {
        this.savings = 0.0;
        this.savingPerDay = 0.0;
    }

    public Vault(Integer id, double savings, double savingPerDay) {
        this.id = id;
        this.savings = savings;
        this.savingPerDay = savingPerDay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Double getSavings() {
        return savings;
    }
    public void setSavings(Double savings) {
        this.savings = savings;
        dbContext.executeUpdate("UPDATE vault SET total_savings = %.5f WHERE id = %d".formatted(savings, id));
    }

    public Double getSavingPerDay() {
        return savingPerDay;
    }

    public void setSavingPerDay(Double savingPerDay) {
        this.savingPerDay = savingPerDay;
        dbContext.executeUpdate("UPDATE vault SET savings_per_day = %.5f WHERE id = %d".formatted(savingPerDay, id));
    }


    public void addToSavings(Double sum){
        this.savings += sum;
        dbContext.executeUpdate("UPDATE vault SET total_savings = %.5f WHERE id = %d".formatted(savings, id));
    }

    @Override
    public String toString() {
        return "Balance: " + savings +
                " | Savings/day: " + savingPerDay;
    }
}
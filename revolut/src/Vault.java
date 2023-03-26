public class Vault {
    private double savings;

    private double savingPerDay;

    public Vault() {
        this.savings = 0;
        this.savingPerDay = 0;
    }

    public Vault(double savings, double savingPerDay) {
        this.savings = savings;
        this.savingPerDay = savingPerDay;
    }

    public double getSavings() {
        return savings;
    }
    public void setSavings(double savings) {
        this.savings = savings;
    }

    @Override
    public String toString() {
        return "Balance :" + savings +
                "Savings/day=" + savingPerDay;
    }
    public double getSavingPerDay() {
        return savingPerDay;
    }
}
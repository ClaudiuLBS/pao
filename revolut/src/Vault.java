public class Vault {
    private Double savings;

    private Double savingPerDay;

    public Vault() {
        this.savings = 0.0;
        this.savingPerDay = 0.0;
    }

    public Vault(double savings, double savingPerDay) {
        this.savings = savings;
        this.savingPerDay = savingPerDay;
    }

    public Double getSavings() {
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
    public Double getSavingPerDay() {
        return savingPerDay;
    }

    public void addToSavings(Double sum){
        this.savings += sum;
    }


}
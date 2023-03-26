public enum Currencies {
    USD("United States Dollar"),
    EUR("Euro"),
    JPY("Japanese Yen"),
    GBP("Pound Sterling"),
    AUD("Australian Dollar"),
    CAD("Canadian Dollar"),
    CHF("Swiss Franc"),
    CNY("Renminbi"),
    HKD("Hong Kong Dollar"),
    RON("Romanian Leu");

    private String currencyName;

    Currencies(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrencyName() {
        return currencyName;
    }
}
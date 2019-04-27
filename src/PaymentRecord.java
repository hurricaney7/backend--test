public class PaymentRecord {
    private String currency;
    private float amount;

    public PaymentRecord(String currency, float amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void addAmount(float amount) {
        this.amount = this.amount + amount;
    }

}

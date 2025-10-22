package restaurant.restaurant2;

public class Payment {
    private double amount;
    private PaymentMethod method;

    public Payment(double amount, PaymentMethod method) {
        if (amount < 0) {
            throw new IllegalArgumentException("Valor do pagamento não pode ser negativo!");
        }
        else {
            this.amount = amount;
            this.method = method;
        }
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Valor do pagamento não pode ser negativo!");
        }
        else {
            this.amount = amount;
        }
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }
}
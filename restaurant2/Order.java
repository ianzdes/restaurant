package restaurant.restaurant2;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<Dish> items;
    private PaymentMethod paymentMethod;
    private double totalPaid;
    private double change;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addDish(Dish dish) {
        items.add(dish);
    }

    public double totalPrice() {
        return items.stream().mapToDouble(Dish::getPrice).sum();
    }

    public void makePayment(PaymentMethod method) {
        if (method == null) throw new IllegalArgumentException("Método de pagamento inválido.");

        switch (method) {
            case CASH -> makeCashPayment(totalPrice()); // valor exato (sem troco)
            case CREDIT_CARD, DEBIT_CARD -> makeCardPayment(method);
            case PIX -> makePixPayment();
            default -> throw new IllegalArgumentException("Método de pagamento não reconhecido.");
        }
    }

    public void makeCashPayment(double amountPaid) {
        double total = totalPrice();
        if (amountPaid < total) {
            throw new IllegalArgumentException("valor pago é menor que o total do pedido.");
        }
        this.paymentMethod = PaymentMethod.CASH;
        this.totalPaid = amountPaid;
        this.change = amountPaid - total;
    }

    public void makeCardPayment(PaymentMethod method) {
        if (method != PaymentMethod.CREDIT_CARD && method != PaymentMethod.DEBIT_CARD) {
            throw new IllegalArgumentException("método deve ser cartão de crédito ou débito.");
        }
        this.paymentMethod = method;
        this.totalPaid = totalPrice() * 1.03;
        this.change = 0;
    }

    public void makePixPayment() {
        this.paymentMethod = PaymentMethod.PIX;
        this.totalPaid = totalPrice();
        this.change = 0;
    }

    public List<Dish> getItems() { return items; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public double getTotalPaid() { return totalPaid; }
    public double getChange() { return change; }

    @Override
    public String toString() {
        return String.format("pedido: %d itens, total R$ %.2f, pago via %s, troco R$ %.2f", items.size(), totalPrice(), paymentMethod, change);
    }
}

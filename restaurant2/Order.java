package restaurant.restaurant2;

import java.util.ArrayList;
import java.util.List;

public class Order {
    // attributes
    private List<Dish> items; // pratos
    private Payment payment; // pagamento
    // constructor
    public Order() {
        this.items = new ArrayList<>();
    }
    // methods
    public void addDish(Dish dish) {
        items.add(dish);
    }

    public double totalPrice() {
        double total = 0;

        for (Dish dish : items) {
            total += dish.getPrice();
        }
        return total;
    }

    public void makePayment(PaymentMethod method) {
        double total = totalPrice();
        this.payment = new Payment(total, method);
    }
    // getters
    public List<Dish> getItems() {
        return items;
    }

    public Payment getPayment() {
        return payment;
    }
}

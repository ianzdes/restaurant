package restaurant;

public class Main {
    public static void main(String[] args) {

        Dish prato1 = new Dish("Executivo de picanha", "Executivo", 31.99);
        Dish prato2 = new Dish("Suco de laranja", "Bebida", 12.0);

        Order pedido = new Order();
        pedido.addDish(prato1);
        pedido.addDish(prato2);

        double total = pedido.totalPrice();
        System.out.printf("Total do pedido: R$ %.2f\n", total);

        pedido.makePayment(PaymentMethod.CASH);

        Payment pagamento = pedido.getPayment();
        System.out.printf("Pagamento realizado no valor de: R$ %.2f\n", pagamento.getAmount());
        System.out.println("Método de pagamento: " + pagamento.getMethod());
    }
}

package restaurant.restaurant2;

import java.util.List;
import java.util.Random;

public class Dish {
    private String name;
    private String category;
    private double price;

    // Lista fixa de pratos — o cardápio
    private static final List<Dish> MENU = List.of(
        new Dish("Executivo de Picanha", "Prato principal", 31.99),
        new Dish("Salada Vegana", "Fit", 24.50),
        new Dish("Macarrão ao Sugo", "Prato principal", 27.00),
        new Dish("Suco de Laranja", "Bebida", 9.00),
        new Dish("Mousse de Maracujá", "Sobremesa", 12.00)
    );

    private static final Random random = new Random();

    // Constructor
    public Dish(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // Getters e setters
    public String getName() { return name; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode estar em branco");
        }
        this.name = name;
    }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        this.price = price;
    }

    // ====== Métodos do cardápio ======

    // Retorna a lista completa do cardápio
    public static List<Dish> getMenu() {
        return MENU;
    }

    // Retorna um prato aleatório do cardápio
    public static Dish getRandomDish() {
        return MENU.get(random.nextInt(MENU.size()));
    }

    @Override
    public String toString() {
        return name + " (" + category + ") - R$ " + String.format("%.2f", price);
    }
}

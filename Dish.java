package restaurant;

public class Dish {
    private String name;
    private String category;
    private double price;

    // constructor
    public Dish(String name, String category, double price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    // getters and setters, encapsulation
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome não pode estar em branco");
        }
        else {
            this.name = name;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo");
        }
        else {
            this.price = price;
        }
    }
}
// POJO - plain old java project
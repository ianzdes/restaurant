package restaurant.shared;

public class Person {
    private String name;
    private int age;
    private String email;
    private String phone;
    private String type; // paciente, participante, cliente

    public Person(String name, int age, String email, String phone, String type) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return name + " (" + type + ")"; // perguntar ao chatgpt o que isso faz
    }
}
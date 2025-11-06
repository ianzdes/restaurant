package restaurant.main;

import java.time.LocalDateTime;
import java.util.*;

import restaurant.shared.*;
import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.restaurant2.*;

public class DataGenerator {

    private static final Random R = new Random();

    // ======== GERAÇÃO DE DADOS ========

    // Locais fixos: clínica, centro de eventos e restaurante
    public static List<Place> generatePlaces() {
        return List.of(
            new Place("Clínica Vida Plena", 30),
            new Place("Centro Cultural Recife", 100),
            new Place("Restaurante Natural", 40)
        );
    }

    // Pessoas com idades variadas e diferentes papéis
    public static List<Person> generatePeople(int quantity) {
        String[] types = {"Paciente", "Participante", "Cliente"};
        List<Person> list = new ArrayList<>(quantity);
        for (int i = 1; i <= quantity; i++) {
            list.add(new Person("Pessoa " + i, 18 + R.nextInt(50), randomFrom(types)));
        }
        return list;
    }

    // Consultas na clínica
    public static List<Appointment> generateAppointments(int quantity, List<Person> people, List<Place> places) {
        String[] doctors = {"Dr. Silva", "Dra. Lima", "Dr. Souza"};
        String[] types = {"Primeira consulta", "Retorno", "Check-up"};

        List<Appointment> list = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            list.add(new Appointment(
                randomFrom(people),
                randomFrom(doctors),
                places.get(0), // clínica
                LocalDateTime.now().plusDays(R.nextInt(10)).withHour(8 + R.nextInt(8)),
                randomFrom(types)
            ));
        }
        return list;
    }

    // Eventos com participantes aleatórios
    public static List<Event> generateEvents(int quantity, List<Place> places, List<Person> people) {
        String[] types = {"Palestra", "Oficina", "Show"};
        List<Event> events = new ArrayList<>(quantity);

        for (int i = 1; i <= quantity; i++) {
            Event e = new Event(
                "Evento " + i,
                randomFrom(types),
                randomFrom(places),
                LocalDateTime.now().plusDays(R.nextInt(30)).withHour(9 + R.nextInt(8))
            );

            int participants = 5 + R.nextInt(10);
            for (int j = 0; j < participants; j++) {
                e.addParticipant(randomFrom(people));
            }

            events.add(e);
        }
        return events;
    }

    // Cardápio fixo do restaurante
    public static List<Dish> generateDishes() {
        return List.of(
            new Dish("Executivo de Picanha", "Executivo", 31.99),
            new Dish("Salada Vegana", "Fit", 24.50),
            new Dish("Suco de Laranja", "Bebida", 9.00),
            new Dish("Massa Integral", "Prato Principal", 28.00),
            new Dish("Smoothie Verde", "Bebida", 12.00),
            new Dish("Mousse de Maracujá", "Sobremesa", 10.00)
        );
    }

    // Pedidos de pessoas no restaurante
    public static List<Order> generateOrders(List<Person> people, List<Dish> dishes) {
        List<Order> orders = new ArrayList<>();
        for (Person p : people) {
            Order o = new Order();
            int qtd = 1 + R.nextInt(3); // cada pessoa pede 1 a 3 pratos
            for (int i = 0; i < qtd; i++) {
                o.addDish(randomFrom(dishes));
            }
            o.makePayment(randomFrom(PaymentMethod.values()));
            orders.add(o);
        }
        return orders;
    }

    // ✅ Quantidade total de consultas realizadas
    static void totalAppointments(List<Appointment> appointments) {
        int total = appointments.size();
        System.out.println("Quantidade total de consultas realizadas: " + total);
    }

    // ======== MÉTODOS AUXILIARES ========

    private static <T> T randomFrom(T[] array) {
        return array[R.nextInt(array.length)];
    }

    private static <T> T randomFrom(List<T> list) {
        return list.get(R.nextInt(list.size()));
    }
}

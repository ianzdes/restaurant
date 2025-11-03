package restaurant.main;

import java.time.LocalDateTime;
import java.util.*;

import restaurant.shared.*;
import restaurant.clinic.Appointment;
import restaurant.event.*;
import restaurant.restaurant2.*;

public class DataGenerator {

    private static final Random R = new Random();

    // locais fixos
    public static List<Place> generatePlaces() {
        return List.of(
            new Place("Clínica Vida Plena", 30),
            new Place("Centro Cultural Recife", 100),
            new Place("Restaurante Natural", 40)
        );
    }

    // pessoas
    public static List<Person> generatePeople(int quantity) {
        String[] types = {"Paciente", "Participante", "Cliente"};
        List<Person> list = new ArrayList<>(quantity);
        for (int i = 1; i <= quantity; i++)
            list.add(new Person("Pessoa " + i, 18 + R.nextInt(50), types[R.nextInt(types.length)]));
        return list;
    }

    // consultas
    public static List<Appointment> generateAppointments(int quantity, List<Person> people, List<Place> places) {
        String[] doctors = {"Dr. Silva", "Dra. Lima", "Dr. Souza"};
        String[] types = {"Primeira consulta", "Retorno", "Check-up"};

        List<Appointment> list = new ArrayList<>(quantity);
        for (int i = 0; i < quantity; i++) {
            list.add(new Appointment(
                randomFrom(people),
                randomFrom(doctors),
                places.get(0),
                LocalDateTime.now()
                        .plusDays(R.nextInt(10))
                        .withHour(8 + R.nextInt(8)),
                randomFrom(types)
            ));
        }
        return list;
    }

    // eventos
    public static List<Event> generateEvents(int quantity, List<Place> places, List<Person> people) {
        String[] types = {"Palestra", "Oficina", "Show"};
        List<Event> events = new ArrayList<>(quantity);

        for (int i = 1; i <= quantity; i++) {
            Event e = new Event(
                "Evento " + i,
                randomFrom(types),
                randomFrom(places),
                LocalDateTime.now()
                    .plusDays(R.nextInt(30))
                    .plusHours(R.nextInt(10))
            );

            int participantes = 5 + R.nextInt(10);
            for (int j = 0; j < participantes; j++)
                e.addParticipant(randomFrom(people));

            events.add(e);
        }
        return events;
    }

    // participações (vouchers usados ou não)
    public static List<Participation> generateParticipations(List<Event> events) {
        List<Participation> list = new ArrayList<>();
        for (Event e : events)
            for (Person p : e.getParticipants())
                list.add(new Participation(p, e, R.nextBoolean()));
        return list;
    }

    // cardápio
    public static List<Dish> generateDishes() {
        return List.of(
            new Dish("Executivo de Picanha", "Executivo", 31.99),
            new Dish("Salada Vegana", "Fit", 24.50),
            new Dish("Suco de Laranja", "Bebida", 9.00),
            new Dish("Massa Integral", "Prato Principal", 28.00),
            new Dish("Smoothie Verde", "Bebida", 12.00)
        );
    }

    // pedidos
    public static List<Order> generateOrders(List<Person> people, List<Dish> dishes) {
        List<Order> orders = new ArrayList<>(people.size());
        for (Person p : people) {
            Order o = new Order();
            int qtdPratos = 1 + R.nextInt(3);
            for (int i = 0; i < qtdPratos; i++)
                o.addDish(randomFrom(dishes));
            o.makePayment(randomFrom(PaymentMethod.values()));
            orders.add(o);
        }
        return orders;
    }

    // utilitário genérico (pesquisar o que faz)
    private static <T> T randomFrom(T[] array) {
        return array[R.nextInt(array.length)];
    }

    private static <T> T randomFrom(List<T> list) {
        return list.get(R.nextInt(list.size()));
    }
}

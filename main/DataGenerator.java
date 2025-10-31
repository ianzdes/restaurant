package restaurant.main;

import java.time.*;
import java.util.*;

import restaurant.shared.Person;
import restaurant.shared.Place;
import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.event.Participation;
import restaurant.restaurant2.Dish;
import restaurant.restaurant2.Order;
import restaurant.restaurant2.PaymentMethod;

public class DataGenerator {

    private static final Random random = new Random();

    public static List<Place> generatePlaces() {
        return List.of(
            new Place("Clínica Vida Plena", 30),
            new Place("Centro Cultural Recife", 100),
            new Place("Restaurante Natural", 40)
        );
    }

    public static List<Person> generatePeople(int quantity) {
        String[] types = {"Paciente", "Participante", "Cliente"};
        List<Person> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++)
            list.add(new Person("Pessoa " + (i + 1), 18 + random.nextInt(50), types[random.nextInt(3)]));
        return list;
    }

    public static List<Appointment> generateAppointments(int quantity, List<Person> people, List<Place> places) {
        String[] doctors = {"Dr. Silva", "Dra. Lima", "Dr. Souza"};
        String[] types = {"Primeira consulta", "Retorno", "Check-up"};
        List<Appointment> list = new ArrayList<>();
        for (int i = 0; i < quantity; i++)
            list.add(new Appointment(
                people.get(random.nextInt(people.size())),
                doctors[random.nextInt(doctors.length)],
                places.get(0),
                LocalDateTime.now().plusDays(random.nextInt(10)).withHour(8 + random.nextInt(8)),
                types[random.nextInt(types.length)]
            ));
        return list;
    }

    public static List<Event> generateEvents(int quantity, List<Place> places, List<Person> people) {
        String[] types = {"Palestra", "Oficina", "Show"};
        List<Event> events = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            Event e = new Event("Evento " + (i + 1), types[random.nextInt(types.length)],
                    places.get(random.nextInt(places.size())),
                    LocalDateTime.now().plusDays(random.nextInt(30)).plusHours(random.nextInt(10)));
            for (int j = 0; j < 5 + random.nextInt(10); j++)
                e.addParticipant(people.get(random.nextInt(people.size())));
            events.add(e);
        }
        return events;
    }

    public static List<Participation> generateParticipations(List<Event> events) {
        List<Participation> list = new ArrayList<>();
        for (Event e : events)
            for (Person p : e.getParticipants())
                list.add(new Participation(p, e, random.nextBoolean()));
        return list;
    }

    public static List<Dish> generateDishes() {
        return List.of(
            new Dish("Executivo de Picanha", "Executivo", 31.99),
            new Dish("Salada Vegana", "Fit", 24.50),
            new Dish("Suco de Laranja", "Bebida", 9.00)
        );
    }

    public static List<Order> generateOrders(List<Person> people, List<Dish> dishes) {
        List<Order> orders = new ArrayList<>();
        for (Person p : people) {
            Order o = new Order();
            int num = 1 + random.nextInt(3);
            for (int i = 0; i < num; i++)
                o.addDish(dishes.get(random.nextInt(dishes.size())));
            o.makePayment(PaymentMethod.values()[random.nextInt(PaymentMethod.values().length)]);
            orders.add(o);
        }
        return orders;
    }
}

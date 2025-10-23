package restaurant.main;

import restaurant.shared.Person;
import restaurant.shared.Place;
import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.event.EventManager;
import restaurant.restaurant2.Dish;
import restaurant.restaurant2.Order;
import restaurant.restaurant2.PaymentMethod;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Person> people = DataGenerator.generatePeople(50);
        List<Place> places = DataGenerator.generatePlaces();
        List<Event> events = DataGenerator.generateEvents(10, places);
        List<Appointment> appointments = DataGenerator.generateAppointments(20, people, places);
        List<Dish> dishes = DataGenerator.generateDishes();

        EventManager eventManager = new EventManager();
        for (Event e : events) {
            eventManager.addEvent(e);
        }

        Order order1 = new Order();
        order1.addDish(dishes.get(0));
        order1.addDish(dishes.get(1));
        order1.makePayment(PaymentMethod.CASH);

        System.out.println("=== Pessoas geradas ===");
        people.forEach(System.out::println);

        System.out.println("\n=== Consultas agendadas ===");
        appointments.forEach(a -> System.out.println(a.getPatient() + " -> " + a.getType()));

        System.out.println("\n=== Eventos disponíveis ===");
        eventManager.getAllEvents().forEach(System.out::println);

        System.out.println("\n=== Pedido de Restaurante ===");
        System.out.println("Itens do pedido:");
        order1.getItems().forEach(d -> System.out.println(d.getName() + " - R$" + d.getPrice()));
        System.out.println("Total: R$" + order1.totalPrice());
    }
}

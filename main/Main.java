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
        // ====== Gerar dados ======
        List<Person> people = DataGenerator.generatePeople(50);
        List<Place> places = DataGenerator.generatePlaces();
        List<Event> events = DataGenerator.generateEvents(10, places, people);
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

        // ====== Contar pessoas por tipo ======
        int numPacientes = 0, numClientes = 0, numParticipantes = 0;

        for (Person p : people) {
            switch (p.getType()) {
                case "Paciente" -> numPacientes++;
                case "Cliente" -> numClientes++;
                case "Participante" -> numParticipantes++;
            }
        }

        System.out.println("=== Quantidade de pessoas ===");
        System.out.println("Pacientes: " + numPacientes);
        System.out.println("Clientes: " + numClientes);
        System.out.println("Participantes: " + numParticipantes);

        // ====== Contar consultas por tipo ======
        int primeiraConsulta = 0, retorno = 0, checkup = 0;

        for (Appointment a : appointments) {
            switch (a.getType()) {
                case "Primeira consulta" -> primeiraConsulta++;
                case "Retorno" -> retorno++;
                case "Check-up" -> checkup++;
            }
        }

        System.out.println("\n=== Consultas agendadas ===");
        System.out.println("Primeira consulta: " + primeiraConsulta);
        System.out.println("Retorno: " + retorno);
        System.out.println("Check-up: " + checkup);

        // ====== Eventos ======
        System.out.println("\n=== Eventos disponíveis ===");
        eventManager.getAllEvents().forEach(System.out::println);

        // ====== Pedido de restaurante ======
        System.out.println("\n=== Pedido de Restaurante ===");
        System.out.println("Itens do pedido:");
        order1.getItems().forEach(d ->
            System.out.println(d.getName() + " - R$ " + String.format("%.2f", d.getPrice()))
        );
        System.out.println("Total: R$ " + String.format("%.2f", order1.totalPrice()));
        System.out.println("Método de pagamento: " + order1.getPaymentMethod());
    }
}

package restaurant.main;

import restaurant.shared.Person;
import restaurant.shared.Place;
import restaurant.clinic.Appointment;
import restaurant.event.Participation;
import restaurant.restaurant2.Dish;
import restaurant.restaurant2.PaymentMethod;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // ====== Gerar dados ======
        List<Person> people = DataGenerator.generatePeople(50);
        List<Place> places = DataGenerator.generatePlaces();
        List<Appointment> appointments = DataGenerator.generateAppointments(20, people, places);
        List<Dish> dishes = DataGenerator.generateDishes();

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

        // ====== Pedidos do restaurante ======
        Random random = new Random();
        Map<String, Integer> dishCounts = new HashMap<>();
        Map<PaymentMethod, Integer> paymentCounts = new HashMap<>();

        for (Person p : people) {
            Dish dish = dishes.get(random.nextInt(dishes.size()));
            dishCounts.put(dish.getName(), dishCounts.getOrDefault(dish.getName(), 0) + 1);

            PaymentMethod method = PaymentMethod.values()[random.nextInt(PaymentMethod.values().length)];
            paymentCounts.put(method, paymentCounts.getOrDefault(method, 0) + 1);
        }

        System.out.println("\n=== Pedidos do Restaurante ===");
        double totalPrice = 0;
        for (Dish d : dishes) {
            int count = dishCounts.getOrDefault(d.getName(), 0);
            double subtotal = count * d.getPrice();
            totalPrice += subtotal;
            System.out.printf("%d pessoas pediram %s - subtotal: R$ %.2f\n", count, d.getName(), subtotal);
        }
        System.out.printf("Total geral: R$ %.2f\n", totalPrice);

        // ====== Métodos de pagamento mais usados ======
        System.out.println("\n=== Métodos de pagamento mais usados ===");
        for (PaymentMethod method : PaymentMethod.values()) {
            int count = paymentCounts.getOrDefault(method, 0);
            System.out.printf("%s: %d vezes\n", method, count);
        }
    }
}

package restaurant.main;

import restaurant.shared.*;
import restaurant.clinic.*;
import restaurant.event.*;
import restaurant.restaurant2.*;

import java.util.*;
import java.text.DecimalFormat;

public class Main {
    private static final DecimalFormat DF = new DecimalFormat("0.00");

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // gerar dados iniciais
        List<Place> places = DataGenerator.generatePlaces();
        List<Person> people = DataGenerator.generatePeople(50);
        List<Dish> dishes = DataGenerator.generateDishes();
        List<Event> events = DataGenerator.generateEvents(10, places, people);
        List<Appointment> appointments = DataGenerator.generateAppointments(20, people, places);
        List<Order> orders = DataGenerator.generateOrders(people, dishes);

        boolean running = true;
        while (running) {
            System.out.println("""
                \n=== MENU DE RELATÓRIOS ===
                1 - Quantas consultas foram realizadas no total?
                2 - Qual tipo de lugar é mais usado (clínica, evento, restaurante)?
                3 - Quais horários de eventos e consultas mais se sobrepõem?
                4 - Qual faixa etária é mais ativa?
                5 - Método de pagamento mais usado?
                6 - Qual é o prato mais pedido no restaurante?
                7 - Qual evento teve mais participantes?
                8 - Quantas consultas foram realizadas por tipo?
                9 - Qual é a média de idade dos participantes de eventos?
                0 - Sair
                Escolha uma opção: """);

            int opt = sc.nextInt();
            switch (opt) {
                case 1 -> totalAppointments(appointments);
                case 2 -> mostUsedPlaces(appointments, events);
                case 3 -> overlappingHours(appointments, events);
                case 4 -> mostActiveAgeGroup(people);
                case 5 -> mostUsedPaymentMethod(orders);
                case 6 -> mostOrderedDish(orders);
                case 7 -> eventWithMostParticipants(events);
                case 8 -> appointmentTypeCount(appointments);
                case 9 -> averageEventParticipantAge(events);
                case 0 -> running = false;
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
        System.out.println("Encerrando sistema...");
    }

    //quantidade total de consultas
    
    static void totalAppointments(List<Appointment> appointments) {
        int total = appointments.size();
        System.out.println("Quantidade total de consultas realizadas: " + total);
    }

    // lugares mais usados
    static void mostUsedPlaces(List<Appointment> appointments, List<Event> events) {
        Map<String, Integer> count = new HashMap<>();
        appointments.forEach(a -> count.merge(a.getPlace().getName(), 1, Integer::sum));
        events.forEach(e -> count.merge(e.getPlace().getName(), 1, Integer::sum));

        System.out.println("\nTipo de lugar mais usado:");
        count.forEach((place, c) -> System.out.println("- " + place + ": " + c + " vezes"));
    }

    // horários sobrepostos
    static void overlappingHours(List<Appointment> appointments, List<Event> events) {
        Map<Integer, Integer> hourCount = new HashMap<>();
        appointments.forEach(a -> hourCount.merge(a.getTime().getHour(), 1, Integer::sum));
        events.forEach(e -> hourCount.merge(e.getTime().getHour(), 1, Integer::sum));

        System.out.println("\nHorários mais cheios:");
        hourCount.forEach((hour, c) -> System.out.println(hour + "h: " + c + " atividades"));
    }

    // faixa etária mais ativa
    static void mostActiveAgeGroup(List<Person> people) {
        Map<String, Integer> groups = new HashMap<>();
        for (Person person : people) {
            String group = (person.getAge() < 30) ? "18-29"
                    : (person.getAge() < 50) ? "30-49" : "50+";
            groups.merge(group, 1, Integer::sum);
        }
        String mostActive = groups.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        System.out.println("Faixa etária mais ativa: " + mostActive);
    }

    // método de pagamento mais usado
    static void mostUsedPaymentMethod(List<Order> orders) {
        Map<PaymentMethod, Integer> count = new HashMap<>();
        orders.forEach(o -> count.merge(o.getPaymentMethod(), 1, Integer::sum));
        var mostUsed = count.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("Método de pagamento mais usado: " + mostUsed);
    }

    // prato mais usado
    static void mostOrderedDish(List<Order> orders) {
        Map<String, Integer> count = new HashMap<>();
        for (Order o : orders)
            o.getItems().forEach(dish -> count.merge(dish.getName(), 1, Integer::sum));

        var mostOrdered = count.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");

        System.out.println("Prato mais pedido: " + mostOrdered);
    }

    // evento com mais participantes
    static void eventWithMostParticipants(List<Event> events) {
        var maxEvent = events.stream()
                .max(Comparator.comparingInt(e -> e.getParticipants().size()))
                .orElse(null);

        if (maxEvent != null)
            System.out.println("Evento com mais participantes: " + maxEvent.getName()
                    + " (" + maxEvent.getParticipants().size() + " pessoas)");
        else
            System.out.println("Nenhum evento encontrado.");
    }

    // consultas por tipo
    static void appointmentTypeCount(List<Appointment> appointments) {
        Map<String, Integer> count = new HashMap<>();
        appointments.forEach(a -> count.merge(a.getType(), 1, Integer::sum));

        System.out.println("\nConsultas por tipo:");
        count.forEach((type, c) -> System.out.println("- " + type + ": " + c));
    }

    // média de idade dos participantes de eventos
    static void averageEventParticipantAge(List<Event> events) {
        List<Person> participants = new ArrayList<>();
        events.forEach(e -> participants.addAll(e.getParticipants()));

        double avgAge = participants.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);

        System.out.println("Média de idade dos participantes de eventos: " + DF.format(avgAge) + " anos");
    }
}

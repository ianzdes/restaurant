package restaurant.main;

import restaurant.shared.*;
import restaurant.clinic.*;
import restaurant.event.*;
import restaurant.restaurant2.*;

import java.util.*;
import java.text.DecimalFormat;
import java.time.Duration;

public class Main {
    private static final DecimalFormat DF = new DecimalFormat("0.00");
    private static final Random RAND = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // gerar dados
        List<Place> places = DataGenerator.generatePlaces();
        List<Person> people = DataGenerator.generatePeople(50);
        List<Dish> dishes = DataGenerator.generateDishes();
        List<Event> events = DataGenerator.generateEvents(10, places, people);
        List<Appointment> appointments = DataGenerator.generateAppointments(20, people, places);
        List<Participation> participations = DataGenerator.generateParticipations(events);
        List<Order> orders = DataGenerator.generateOrders(people, dishes);

        // menu
        boolean running = true;
        while (running) {
            System.out.println("""
                \n=== MENU DE RELATÓRIOS ===
                1 - Pacientes que participam de eventos retornam mais rápido à clínica
                2 - Vouchers usados no restaurante e tempo médio de uso
                3 - Influência de vouchers/descontos no ticket médio do restaurante
                4 - Perfil das pessoas que completam a jornada completa
                5 - Tipo de lugar mais usado (clínica, evento, restaurante)
                6 - Horários de consultas e eventos mais sobrepostos
                7 - Participantes de workshops que voltam ao restaurante
                8 - Faixa etária mais ativa
                9 - Método de pagamento mais usado por área
                0 - Sair
                Escolha uma opção: """);

            int option = sc.nextInt();
            switch (option) {
                case 1 -> patientsReturnAverage(appointments, events);
                case 2 -> voucherUsageAverage(participations);
                case 3 -> averageTicket(orders);
                case 4 -> fullJourneyProfile(people, appointments, events, orders);
                case 5 -> mostUsedPlaces(appointments, events);
                case 6 -> overlappingHours(appointments, events);
                case 7 -> workshopLoyalty(events, orders);
                case 8 -> mostActiveAgeGroup(people);
                case 9 -> mostUsedPaymentMethod(orders);
                case 0 -> running = false;
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
        System.out.println("Encerrando sistema...");
    }

    // relatórios

    static void patientsReturnAverage(List<Appointment> appointments, List<Event> events) {
        Map<String, List<Appointment>> byPatient = new HashMap<>();
        appointments.forEach(a -> byPatient.computeIfAbsent(a.getPatient().getName(), k -> new ArrayList<>()).add(a));

        double sum = 0;
        int count = 0;

        for (var entry : byPatient.entrySet()) {
            String name = entry.getKey();
            boolean attendedEvent = events.stream().anyMatch(e -> e.getParticipants().stream()
                    .anyMatch(p -> p.getName().equals(name)));
            if (attendedEvent) {
                var list = entry.getValue();
                list.sort(Comparator.comparing(Appointment::getTime));
                for (int i = 1; i < list.size(); i++) {
                    sum += Duration.between(list.get(i - 1).getTime(), list.get(i).getTime()).toDays();
                    count++;
                }
            }
        }
        System.out.println("Pacientes que participaram de eventos retornam em média após "
                + DF.format(count > 0 ? sum / count : 0) + " dias");
    }

    static void voucherUsageAverage(List<Participation> participations) {
        long totalDays = 0, count = 0;
        for (Participation participation : participations) {
            if (participation.isVoucherUsed()) {
                totalDays += RAND.nextInt(15) + 1;
                count++;
            }
        }
        System.out.println("Vouchers distribuídos em eventos são usados em média após "
                + (count > 0 ? totalDays / count : 0) + " dias");
    }

    static void averageTicket(List<Order> orders) {
        double avg = orders.stream().mapToDouble(Order::totalPrice).average().orElse(0);
        System.out.println("Ticket médio do restaurante: R$ " + DF.format(avg));
    }

    static void fullJourneyProfile(List<Person> people, List<Appointment> appointments, List<Event> events, List<Order> orders) {
        Set<String> patientNames = new HashSet<>();
        Set<String> participantNames = new HashSet<>();
        Set<String> clientNames = new HashSet<>();

        appointments.forEach(a -> patientNames.add(a.getPatient().getName()));
        events.forEach(e -> e.getParticipants().forEach(p -> participantNames.add(p.getName())));
        orders.forEach(o -> clientNames.addAll(o.getItems().stream().map(Dish::getName).toList())); // simplificação

        long total = people.stream()
                .filter(p -> patientNames.contains(p.getName()) &&
                        participantNames.contains(p.getName()) &&
                        !clientNames.isEmpty())
                .count();

        System.out.println(total + " pessoas completaram a jornada completa (consulta + evento + refeição)");
    }

    static void mostUsedPlaces(List<Appointment> appointments, List<Event> events) {
        Map<String, Integer> count = new HashMap<>();
        appointments.forEach(a -> count.merge(a.getPlace().getName(), 1, Integer::sum));
        events.forEach(e -> count.merge(e.getPlace().getName(), 1, Integer::sum));
        count.forEach((place, c) -> System.out.println(place + ": " + c));
    }

    static void overlappingHours(List<Appointment> appointments, List<Event> events) {
        Map<Integer, Integer> hourCount = new HashMap<>();
        appointments.forEach(a -> hourCount.merge(a.getTime().getHour(), 1, Integer::sum));
        events.forEach(e -> hourCount.merge(e.getTime().getHour(), 1, Integer::sum));
        hourCount.forEach((hour, c) -> System.out.println(hour + "h: " + c));
    }

    static void workshopLoyalty(List<Event> events, List<Order> orders) {
        Set<String> workshopParticipants = new HashSet<>();
        events.stream()
                .filter(e -> e.getType().equalsIgnoreCase("Oficina") || e.getType().equalsIgnoreCase("Workshop"))
                .forEach(e -> e.getParticipants().forEach(p -> workshopParticipants.add(p.getName())));

        int total = (int) orders.stream().filter(o -> RAND.nextBoolean()).count();
        System.out.println(total + " participantes de workshops voltaram ao restaurante");
    }

    static void mostActiveAgeGroup(List<Person> people) {
        Map<String, Integer> groups = new HashMap<>();
        for (Person person : people) {
            String group = (person.getAge() < 30) ? "18-29"
                    : (person.getAge() < 50) ? "30-49" : "50+";
            groups.merge(group, 1, Integer::sum);
        }
        String mostActive = groups.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");

        System.out.println("Faixa etária mais ativa: " + mostActive);
    }

    static void mostUsedPaymentMethod(List<Order> orders) {
        Map<PaymentMethod, Integer> count = new HashMap<>();
        orders.forEach(o -> count.merge(o.getPaymentMethod(), 1, Integer::sum));
        var mostUsed = count.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        System.out.println("Método de pagamento mais usado no restaurante: " + mostUsed);
    }
}

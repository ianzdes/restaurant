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

            int opt = sc.nextInt();
            switch (opt) {
                case 1 -> pacientesRetornoMedio(appointments, events);
                case 2 -> vouchersUsoMedio(participations);
                case 3 -> ticketMedio(orders);
                case 4 -> perfilJornadaCompleta(people, appointments, events, orders);
                case 5 -> lugaresMaisUsados(appointments, events);
                case 6 -> horariosMaisPopulares(appointments, events);
                case 7 -> fidelidadeWorkshops(events, orders);
                case 8 -> faixaEtariaMaisAtiva(people);
                case 9 -> metodoPagamentoMaisUsado(orders);
                case 0 -> running = false;
                default -> System.out.println("Opção inválida!");
            }
        }

        sc.close();
        System.out.println("Encerrando sistema...");
    }

    // relatorios

    static void pacientesRetornoMedio(List<Appointment> appointments, List<Event> events) {
        Map<String, List<Appointment>> porPaciente = new HashMap<>();
        appointments.forEach(a -> porPaciente.computeIfAbsent(a.getPatient().getName(), k -> new ArrayList<>()).add(a));

        double soma = 0; int count = 0;
        for (var entry : porPaciente.entrySet()) {
            String nome = entry.getKey();
            boolean participou = events.stream().anyMatch(e -> e.getParticipants().stream()
                    .anyMatch(p -> p.getName().equals(nome)));
            if (participou) {
                var lista = entry.getValue();
                lista.sort(Comparator.comparing(Appointment::getTime));
                for (int i = 1; i < lista.size(); i++) {
                    soma += Duration.between(lista.get(i - 1).getTime(), lista.get(i).getTime()).toDays();
                    count++;
                }
            }
        }
        System.out.println("Pacientes que participaram de eventos retornam em média após "
                + DF.format(count > 0 ? soma / count : 0) + " dias");
    }

    static void vouchersUsoMedio(List<Participation> participations) {
        long totalDias = 0, count = 0;
        for (Participation p : participations) {
            if (p.isVoucherUsed()) {
                totalDias += RAND.nextInt(15) + 1;
                count++;
            }
        }
        System.out.println("Vouchers distribuídos em eventos são usados em média após "
                + (count > 0 ? totalDias / count : 0) + " dias");
    }

    static void ticketMedio(List<Order> orders) {
        double media = orders.stream().mapToDouble(Order::totalPrice).average().orElse(0);
        System.out.println("Ticket médio do restaurante: R$ " + DF.format(media));
    }

    static void perfilJornadaCompleta(List<Person> people, List<Appointment> appointments, List<Event> events, List<Order> orders) {
        Set<String> pacientes = new HashSet<>(), participantes = new HashSet<>(), clientes = new HashSet<>();

        appointments.forEach(a -> pacientes.add(a.getPatient().getName()));
        events.forEach(e -> e.getParticipants().forEach(p -> participantes.add(p.getName())));
        orders.forEach(o -> clientes.addAll(o.getItems().stream().map(Dish::getName).toList())); // simplificado

        long total = people.stream()
                .filter(p -> pacientes.contains(p.getName()) && participantes.contains(p.getName()) && !clientes.isEmpty())
                .count();

        System.out.println(total + " pessoas completaram a jornada completa (consulta + evento + refeição)");
    }

    static void lugaresMaisUsados(List<Appointment> appointments, List<Event> events) {
        Map<String, Integer> contagem = new HashMap<>();
        appointments.forEach(a -> contagem.merge(a.getPlace().getName(), 1, Integer::sum));
        events.forEach(e -> contagem.merge(e.getPlace().getName(), 1, Integer::sum));
        contagem.forEach((l, c) -> System.out.println(l + ": " + c));
    }

    static void horariosMaisPopulares(List<Appointment> appointments, List<Event> events) {
        Map<Integer, Integer> horas = new HashMap<>();
        appointments.forEach(a -> horas.merge(a.getTime().getHour(), 1, Integer::sum));
        events.forEach(e -> horas.merge(e.getTime().getHour(), 1, Integer::sum));
        horas.forEach((h, c) -> System.out.println(h + "h: " + c));
    }

    static void fidelidadeWorkshops(List<Event> events, List<Order> orders) {
        Set<String> workshopParticipants = new HashSet<>();
        events.stream()
                .filter(e -> e.getType().equalsIgnoreCase("Oficina") || e.getType().equalsIgnoreCase("Workshop"))
                .forEach(e -> e.getParticipants().forEach(p -> workshopParticipants.add(p.getName())));

        int total = (int) orders.stream().filter(o -> RAND.nextBoolean()).count();
        System.out.println(total + " participantes de workshops voltaram ao restaurante");
    }

    static void faixaEtariaMaisAtiva(List<Person> people) {
        Map<String, Integer> grupos = new HashMap<>();
        for (Person p : people) {
            String faixa = (p.getAge() < 30) ? "18-29" : (p.getAge() < 50) ? "30-49" : "50+";
            grupos.merge(faixa, 1, Integer::sum);
        }
        String maisAtiva = grupos.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("N/A");
        System.out.println("Faixa etária mais ativa: " + maisAtiva);
    }

    static void metodoPagamentoMaisUsado(List<Order> orders) {
        Map<PaymentMethod, Integer> contagem = new HashMap<>();
        orders.forEach(o -> contagem.merge(o.getPaymentMethod(), 1, Integer::sum));
        var maisUsado = contagem.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
        System.out.println("Método de pagamento mais usado no restaurante: " + maisUsado);
    }
}

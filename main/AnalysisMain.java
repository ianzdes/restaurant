package restaurant.main;

import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.event.Participation;
import restaurant.restaurant2.Order;
import restaurant.restaurant2.Dish;
import restaurant.shared.Person;
import restaurant.shared.Place;

import java.text.DecimalFormat;
import java.util.*;

public class AnalysisMain {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.00");

        // ====== Gerar dados ======
        List<Person> people = DataGenerator.generatePeople(50);
        List<Place> places = DataGenerator.generatePlaces();
        List<Appointment> appointments = DataGenerator.generateAppointments(20, people, places);
        List<Event> events = DataGenerator.generateEvents(10, places, people);
        List<Participation> participations = DataGenerator.generateParticipations(events);

        List<Dish> dishes = DataGenerator.generateDishes();
        List<Order> orders = new ArrayList<>();
        Random random = new Random();

        // Criar pedidos realistas
        for (Person p : people) {
            Order o = new Order();
            // cada pessoa pede entre 1 e 3 pratos
            int numDishes = 1 + random.nextInt(3);
            for (int i = 0; i < numDishes; i++) {
                o.addDish(dishes.get(random.nextInt(dishes.size())));
            }
            orders.add(o);
        }

        // ====== Análise de decisões ======
        System.out.println("\n=== ANÁLISE DE DECISÕES ===\n");

        double avgReturnDays = DecisionQueries.patientsReturnFaster(appointments, events);
        System.out.println("1 - Pacientes que participam de eventos retornam em média após " + df.format(avgReturnDays) + " dias.");

        double avgVoucherUse = DecisionQueries.voucherUsageTime(participations, orders);
        System.out.println("2 - Vouchers são usados em média após " + df.format(avgVoucherUse) + " dias.");

        double avgTicket = DecisionQueries.averageTicketWithVoucher(appointments, orders);
        System.out.println("3 - Ticket médio no restaurante: R$ " + df.format(avgTicket));

        List<Person> fullJourneyPeople = DecisionQueries.fullJourneyProfile(people, appointments, events, orders);
        System.out.println("4 - " + fullJourneyPeople.size() + " pessoas completaram a jornada completa (consulta + evento + refeição).");

        Map<String, Long> popularPlaces = DecisionQueries.mostUsedPlaces(appointments, events, places);
        System.out.println("5 - Locais mais usados (consultas + eventos): " + popularPlaces);

        Map<Integer, Long> popularHours = DecisionQueries.mostPopularHours(appointments, events);
        System.out.println("6 - Horários mais populares: " + popularHours);

        long loyalCustomers = DecisionQueries.postEventLoyalty(events, orders);
        System.out.println("7 - " + loyalCustomers + " participantes de oficinas voltaram ao restaurante.");

        String activeAgeGroup = DecisionQueries.activeAgeGroup(people, appointments, events);
        System.out.println("8 - Faixa etária mais ativa: " + activeAgeGroup);

        double correlation = DecisionQueries.appointmentSpendingCorrelation(appointments, orders);
        System.out.println("9 - Correlação entre consultas e gastos: " + df.format(correlation));
    }
}

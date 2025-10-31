package restaurant.main;

import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.event.Participation;
import restaurant.restaurant2.Order;
import restaurant.restaurant2.Dish;
import restaurant.shared.Person;
import restaurant.shared.Place;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class DecisionQueries {

    // 1 - Patients who attend events return faster to the clinic
    public static double patientsReturnFaster(List<Appointment> appointments, List<Event> events) {
        Map<String, LocalDateTime> firstAppointment = new HashMap<>();
        Map<String, LocalDateTime> secondAppointment = new HashMap<>();

        for (Appointment a : appointments) {
            String name = a.getPatient().getName();
            if (!firstAppointment.containsKey(name)) {
                firstAppointment.put(name, a.getTime());
            } else if (a.getTime().isAfter(firstAppointment.get(name))) {
                secondAppointment.put(name, a.getTime());
            }
        }

        List<Long> intervals = new ArrayList<>();
        for (String name : secondAppointment.keySet()) {
            long days = Duration.between(firstAppointment.get(name), secondAppointment.get(name)).toDays();
            intervals.add(days);
        }

        return intervals.isEmpty() ? 0 : intervals.stream().mapToLong(Long::longValue).average().orElse(0);
    }

    // 2 - Voucher usage and time until used
    public static double voucherUsageTime(List<Participation> participations, List<Order> orders) {
        List<Long> times = new ArrayList<>();

        for (Participation p : participations) {
            if (p.isVoucherUsed()) {
                long days = new Random().nextInt(15); // simulation
                times.add(days);
            }
        }

        return times.isEmpty() ? 0 : times.stream().mapToLong(Long::longValue).average().orElse(0);
    }

    // 3 - Average restaurant ticket
    public static double averageTicketWithVoucher(List<Appointment> appointments, List<Order> orders) {
        double total = 0;
        for (Order o : orders) total += o.totalPrice();
        return orders.isEmpty() ? 0 : total / orders.size();
    }

    // 4 - Full journey: clinic + event + restaurant
    public static List<Person> fullJourneyProfile(List<Person> people, List<Appointment> appointments,
                                                  List<Event> events, List<Order> orders) {
        Set<String> clinicSet = appointments.stream()
                .map(a -> a.getPatient().getName())
                .collect(Collectors.toSet());

        Set<String> eventSet = events.stream()
                .flatMap(e -> e.getParticipants().stream())
                .map(Person::getName)
                .collect(Collectors.toSet());

        // Simplification: anyone with an order participated in restaurant
        Set<String> restaurantSet = orders.stream()
                .map(o -> o.getItems().toString())
                .collect(Collectors.toSet());

        return people.stream()
                .filter(p -> clinicSet.contains(p.getName()) && eventSet.contains(p.getName()))
                .collect(Collectors.toList());
    }

    // 5 - Most used places (appointments + events)
    public static Map<String, Long> mostUsedPlaces(List<Appointment> appointments, List<Event> events, List<Place> places) {
        Map<String, Long> placeCount = new HashMap<>();

        for (Appointment a : appointments)
            placeCount.merge(a.getPlace().getName(), 1L, Long::sum);

        for (Event e : events)
            placeCount.merge(e.getPlace().getName(), 1L, Long::sum);

        return placeCount.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    // 6 - Most popular hours
    public static Map<Integer, Long> mostPopularHours(List<Appointment> appointments, List<Event> events) {
        Map<Integer, Long> hourCount = new HashMap<>();

        for (Appointment a : appointments)
            hourCount.merge(a.getTime().getHour(), 1L, Long::sum);

        for (Event e : events)
            hourCount.merge(e.getTime().getHour(), 1L, Long::sum);

        return hourCount.entrySet().stream()
                .sorted(Map.Entry.<Integer, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (a, b) -> a, LinkedHashMap::new));
    }

    // 7 - Loyalty after workshop
    public static long postEventLoyalty(List<Event> events, List<Order> orders) {
        Set<String> workshopParticipants = events.stream()
                .filter(e -> e.getType().equalsIgnoreCase("Oficina") || e.getType().equalsIgnoreCase("Workshop"))
                .flatMap(e -> e.getParticipants().stream())
                .map(Person::getName)
                .collect(Collectors.toSet());

        long total = 0;
        for (Order o : orders) {
            if (new Random().nextBoolean()) total++; // simulation
        }

        return total;
    }

    // 8 - Most active age group
    public static String activeAgeGroup(List<Person> people, List<Appointment> appointments, List<Event> events) {
        Map<String, Integer> ageGroupCount = new HashMap<>();

        for (Person p : people) {
            String group = (p.getAge() < 30) ? "18-29" : (p.getAge() < 50) ? "30-49" : "50+";
            ageGroupCount.merge(group, 1, Integer::sum);
        }

        return ageGroupCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }

    // 9 - Correlation between number of appointments and average spending
    public static double appointmentSpendingCorrelation(List<Appointment> appointments, List<Order> orders) {
        if (appointments.isEmpty() || orders.isEmpty()) return 0;
        return (double) appointments.size() / orders.size();
    }
}

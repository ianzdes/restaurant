package restaurant.main;

import java.util.*;
import java.time.*;

import restaurant.shared.Person;
import restaurant.shared.Place;
import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.event.Participation;
import restaurant.restaurant2.Dish;

public class DataGenerator {

    private static final Random random = new Random();

    public static List<Place> generatePlaces() {
        return List.of(
            new Place("Clínica Vida Plena", 30),
            new Place("Centro Cultural Recife", 100),
            new Place("Restaurante Natural", 40)
        );
    }

    // Gera pessoas genéricas (pacientes, participantes e clientes)
    public static List<Person> generatePeople(int quantity) {
        List<Person> people = new ArrayList<>();
        String[] types = {"Paciente", "Participante", "Cliente"};

        for (int i = 0; i < quantity; i++) {
            String name = "Pessoa " + (i + 1);
            int age = 18 + random.nextInt(50);
            String type = types[random.nextInt(types.length)];

            // adiciona na lista
            people.add(new Person(name, age, type));
        }

        return people;
    }

    // Gera eventos e adiciona participantes reais da lista de pessoas
    public static List<Event> generateEvents(int quantity, List<Place> places, List<Person> people) {
        List<Event> events = new ArrayList<>();
        String[] types = {"Palestra", "Oficina", "Show"};

        for (int i = 0; i < quantity; i++) {
            String name = "Evento " + (i + 1);
            String type = types[random.nextInt(types.length)];
            Place place = places.get(random.nextInt(places.size()));

            LocalDateTime time = LocalDateTime.now()
                    .plusDays(random.nextInt(30))
                    .plusHours(random.nextInt(10));

            Event event = new Event(name, type, place, time);

            int numParticipants = 5 + random.nextInt(15);
            for (int j = 0; j < numParticipants; j++) {
                Person participant = people.get(random.nextInt(people.size()));
                event.addParticipant(participant);
            }

            events.add(event);
        }

        return events;
    }

    // Gera consultas na clínica
    public static List<Appointment> generateAppointments(int quantity, List<Person> people, List<Place> places) {
        List<Appointment> appointments = new ArrayList<>();
        String[] doctors = {"Dr. Silva", "Dra. Lima", "Dr. Souza"};
        String[] types = {"Primeira consulta", "Retorno", "Check-up"};

        for (int i = 0; i < quantity; i++) {
            Person patient = people.get(random.nextInt(people.size()));
            String doctor = doctors[random.nextInt(doctors.length)];
            Place place = places.get(0); // Clínica
            LocalDateTime time = LocalDateTime.now()
                    .plusDays(random.nextInt(10))
                    .withHour(8 + random.nextInt(8));
            String type = types[random.nextInt(types.length)];

            appointments.add(new Appointment(patient, doctor, place, time, type));
        }

        return appointments;
    }

    // Gera pratos do restaurante
    public static List<Dish> generateDishes() {
        return List.of(
            new Dish("Executivo de Picanha", "Executivo", 31.99),
            new Dish("Salada Vegana", "Fit", 24.50),
            new Dish("Suco de Laranja", "Bebida", 9.00)
        );
    }

    public static List<Participation> generateParticipations(List<Event> events) {
    List<Participation> participations = new ArrayList<>();
    Random random = new Random();

    for (Event e : events) {
        for (Person p : e.getParticipants()) {
            boolean voucherUsed = random.nextBoolean(); // aleatório
            participations.add(new Participation(p, e, voucherUsed));
        }
    }

    return participations;
}
}
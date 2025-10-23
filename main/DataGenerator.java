package restaurant.main;

import java.util.*;
import java.time.*;

import restaurant.shared.Person;
import restaurant.shared.Place;
import restaurant.clinic.Appointment;
import restaurant.event.Event;
import restaurant.restaurant2.Dish;

public class DataGenerator {

    private static final Random random = new Random();

    // Gera pessoas aleatórias
public static List<Person> generatePeople(int quantity) {
    List<Person> people = new ArrayList<>();
    Random random = new Random();
    String[] types = {"Paciente", "Participante", "Cliente"};

    for (int i = 0; i < quantity; i++) {
        String name = "Pessoa " + (i + 1);
        int age = 18 + random.nextInt(50);
        String email = "pessoa" + (i + 1) + "@vida.com";
        String phone = "(81) 9" + (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000);
        String type = types[random.nextInt(types.length)];

        people.add(new Person(name, age, email, phone, type));
    }

    return people;
}


    // Gera locais aleatórios
public static List<Place> generatePlaces() {
    return List.of(
        new Place("Clínica Vida Plena", "Rua Saúde, 123 - Recife", 30),
        new Place("Centro Cultural Recife", "Av. Boa Vista, 500 - Recife", 100),
        new Place("Restaurante Natural", "Rua das Flores, 45 - Boa Viagem", 40)
    );
}


    // Gera eventos aleatórios
public static List<Event> generateEvents(int quantity, List<Place> places) {
    List<Event> events = new ArrayList<>();
    Random random = new Random();
    String[] types = {"Palestra", "Oficina", "Show"};

    for (int i = 0; i < quantity; i++) {
        String name = "Evento " + (i + 1);
        String type = types[random.nextInt(types.length)];
        Place place = places.get(random.nextInt(places.size()));

        // Gera uma data/hora aleatória entre hoje e os próximos 30 dias
        LocalDateTime time = LocalDateTime.now().plusDays(random.nextInt(30)).plusHours(random.nextInt(10));

        // Cria o evento
        Event event = new Event(name, type, place, time);

        // Adiciona alguns participantes simulados
        int numParticipants = 5 + random.nextInt(15);
        for (int j = 0; j < numParticipants; j++) {
            event.addParticipant(new Person(
                "Participante " + (i + 1) + "-" + (j + 1),
                20 + random.nextInt(40),
                "participante" + (i + 1) + "_" + (j + 1) + "@vida.com",
                "(81) 9" + (random.nextInt(9000) + 1000) + "-" + (random.nextInt(9000) + 1000),
                "Participante"
            ));
        }

        events.add(event);
    }

    return events;
}


    // Gera consultas aleatórias
    public static List<Appointment> generateAppointments(int quantity, List<Person> people, List<Place> places) {
        List<Appointment> appointments = new ArrayList<>();
        String[] doctors = {"Dr. Silva", "Dra. Lima", "Dr. Souza"};
        String[] types = {"Primeira consulta", "Retorno", "Check-up"};

        for (int i = 0; i < quantity; i++) {
            Person patient = people.get(random.nextInt(people.size()));
            String doctor = doctors[random.nextInt(doctors.length)];
            Place place = places.get(0); // Clínica
            LocalDateTime time = LocalDateTime.now().plusDays(random.nextInt(10)).withHour(8 + random.nextInt(8));
            String type = types[random.nextInt(types.length)];
            appointments.add(new Appointment(patient, doctor, place, time, type));
        }
        return appointments;
    }

    // Gera pratos aleatórios
    public static List<Dish> generateDishes() {
        return List.of(
            new Dish("Executivo de Picanha", "Executivo", 31.99),
            new Dish("Salada Vegana", "Fit", 24.50),
            new Dish("Suco de Laranja", "Bebida", 9.00)
        );
    }
}

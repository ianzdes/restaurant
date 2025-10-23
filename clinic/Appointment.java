package restaurant.clinic;

import restaurant.shared.Person;
import restaurant.shared.Place;
import java.time.LocalDateTime;

public class Appointment {
    private Person patient;
    private String doctor;
    private Place place;
    private LocalDateTime time;
    private String type; // retorno, primeira consulta

    public Appointment(Person patient, String doctor, Place place, LocalDateTime time, String type) {
        this.patient = patient;
        this.doctor = doctor;
        this.place = place;
        this.time = time;
        this.type = type;
    }

    public Person getPatient() { return patient; }
    public String getDoctor() { return doctor; }
    public Place getPlace() { return place; }
    public LocalDateTime getTime() { return time; }
    public String getType() { return type; }
}

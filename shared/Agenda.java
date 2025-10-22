package restaurant.shared;

import java.time.LocalDateTime;

public class Agenda {
    private Person person;
    private Place place;
    private LocalDateTime time;
    private String type;

    public Agenda(Person person, Place place, LocalDateTime time, String type) {
        this.person = person;
        this.place = place;
        this.time = time;
        this.type = type;
    }

    public Person getPerson() { return person; }
    public Place getPlace() { return place; }
    public LocalDateTime getTime() { return time; }
    public String getType() { return type; }

    @Override 
    public String toString() {
        return person + " - " + type + " em " + place + " às " + time;
    }
}
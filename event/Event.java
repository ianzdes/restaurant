package restaurant.event;

import restaurant.shared.Place;
import restaurant.shared.Person;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private String type; //palestra, festa, show
    private Place place;
    private LocalDateTime time;
    private List<Person> participants;

    public Event(String name, String type, Place place, LocalDateTime time) {
        this.name = name;
        this.type = type;
        this.place = place;
        this.time = time;
        this.participants = new ArrayList<>();
    }

    public void addParticipant(Person p) { participants.add(p); }

    public String getName() { return name; }
    public String getType() { return type; }
    public Place getPlace() { return place; }
    public LocalDateTime getTime() { return time; }
    public List<Person> getParticipants() { return participants; }  
}
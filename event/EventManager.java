package restaurant.event;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events;

    public EventManager() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) { events.add(event); }
    public List<Event> getAllEvents() { return events; }
    public int getEventCount() { return events.size(); }
}
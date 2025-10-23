package restaurant.event;
import restaurant.shared.Person;

public class Participation {
    private Person person;
    private Event event;
    private boolean voucher;

    public Participation(Person person, Event event, boolean voucher) {
        this.person = person;
        this.event = event;
        this.voucher = false;
    }

    public Person getPerson() { return person; }
    public Event getEvent() { return event; }
    public boolean getVoucher() { return voucher; }
    public void useVoucher() { voucher = true; }
} 
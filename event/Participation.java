package restaurant.event;
import restaurant.shared.Person;

public class Participation {
    private Person person;
    private Event event;
    private boolean voucherUsed;

    public Participation(Person person, Event event, boolean voucherUsed) {
        this.person = person;
        this.event = event;
        this.voucherUsed = voucherUsed;
    }

    public Person getPerson() { return person; }
    public Event getEvent() { return event; }
    public boolean isVoucherUsed() { return voucherUsed; }

    public void useVoucher() { 
        this.voucherUsed = true;
    }
} 
import java.util.Comparator;

public class EventComparator implements Comparator<Event> {
    @Override
    public int compare(Event e1, Event e2) {
        if (e1.getEventTime() > e2.getEventTime()) {
            return 1;
        } else if (e1.getEventTime() < e2.getEventTime()) {
            return -1;
        } else {
            Customer c1 = e1.getCustomer();
            Customer c2 = e2.getCustomer();

            // Check if either are DONE
            if (c1.getState() == State.DONE) {
                return -1;
            } else if (c2.getState() == State.DONE) {
                return 1;
            } else {
                // Neither are done. Check if either are SERVED
                if (c1.getState() == State.SERVED) {
                    return -1;
                } else {
                    return 1;
                }
            }
        }
    }
}

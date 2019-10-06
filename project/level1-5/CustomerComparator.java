import java.util.Comparator;

public class CustomerComparator implements Comparator<Customer> {
    @Override
    public int compare(Customer c1, Customer c2) {
        if (c1.getArrivalTime() > c2.getArrivalTime()) {
            return 1;
        } else if (c1.getArrivalTime() < c2.getArrivalTime()) {
            return -1;
        } else {
            if (c2.getState() == State.DONE) {
                return 1;
            } else if (c1.getState() == State.DONE) {
                return -1;
            } else if (c2.getState() == State.SERVED) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}

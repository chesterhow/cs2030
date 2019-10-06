public class Customer {
    private static int numCustomers = 0;

    private final int id;
    private final double arrivalTime;
    private State state;

    /**
     * Initialises a new Customer.
     *
     * @param arrivalTime the customer's time of arrival
     */
    public Customer(double arrivalTime) {
        Customer.numCustomers++;
        this.id = Customer.numCustomers;
        this.arrivalTime = arrivalTime;
        this.state = State.ARRIVES;
    }

    public int getId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return this.id + " " + this.state;
    }
}

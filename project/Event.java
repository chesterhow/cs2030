public class Event {
    private final double eventTime;
    private Customer customer;
    private Server server;

    /**
     * Initialises a new Event that involves only a customer.
     *
     * @param eventTime the time of the event
     * @param customer the customer involved in the event
     */
    public Event(double eventTime, Customer customer) {
        this.eventTime = eventTime;
        this.customer = customer;
    }

    /**
     * Initialises a new Event that involves both a customer and a server.
     *
     * @param eventTime the time of the event
     * @param customer the customer involved in the event
     * @param server the server involved in the event
     */
    public Event(double eventTime, Customer customer, Server server) {
        this.eventTime = eventTime;
        this.customer = customer;
        this.server = server;
    }

    public double getEventTime() {
        return this.eventTime;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public Server getServer() {
        return this.server;
    }

    @Override
    public String toString() {
        String output = String.format("%.3f", this.eventTime) + " " + this.customer;

        if (this.server != null) {
            if (this.customer.getState() == State.SERVED) {
                output += " by " + this.server.getId();
            } else if (this.customer.getState() == State.WAITS) {
                output += " to be served by " + this.server.getId();
            } else if (this.customer.getState() == State.DONE) {
                output += " serving by " + this.server.getId();
            }
        }

        return output;
    }
}

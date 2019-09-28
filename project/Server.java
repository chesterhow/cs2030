public class Server {
    private static final double SERVICE_TIME = 1.0;

    private static int numServers = 0;
    private static double totalWaitTime = 0.0;
    private static int customersServed = 0;

    private final int id;
    private boolean idle;
    private boolean emptyQueue;
    private double nextServiceTime;

    /**
     * Initialises a new Server.
     */
    public Server() {
        Server.numServers++;
        this.id = Server.numServers;
        this.idle = true;
        this.emptyQueue = true;
        this.nextServiceTime = 0.0;
    }

    public static double getAverageWaitTime() {
        return Server.totalWaitTime / Server.customersServed;
    }

    public static int getCustomersServed() {
        return Server.customersServed;
    }

    public int getId() {
        return this.id;
    }

    public double getNextServiceTime() {
        return this.nextServiceTime;
    }

    public boolean isIdle() {
        return this.idle;
    }

    public boolean hasEmptyQueue() {
        return this.emptyQueue;
    }

    /**
     * Server serves a customer. Updates the server's properties to indicate it is occupied
     * and updates the server's next service time.
     *
     * @param customer the customer to be served
     * @param eventTime the time of this event
     */
    public void serveCustomer(Customer customer, double eventTime) {
        this.idle = false;
        this.emptyQueue = true;
        this.nextServiceTime = eventTime + Server.SERVICE_TIME;

        Server.totalWaitTime += (eventTime - customer.getArrivalTime());
    }

    /**
     * Server adds a customer to its queue. Updates the server's properties to indicate it
     * has a customer in its queue.
     *
     * @param customer the customer to be added to the server's queue
     */
    public void queueCustomer(Customer customer) {
        this.emptyQueue = false;
    }

    /**
     * Server finishes serving the current customer and ends it service. Updates the server's
     * properties to indicate its occupancy based on whether or not another customer is in
     * the queue.
     */
    public void endService() {
        Server.customersServed += 1;

        if (this.emptyQueue) {
            this.idle = true;
        } else {
            this.emptyQueue = true;
        }
    }
}

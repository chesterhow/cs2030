public class Server {
    private final static double SERVICE_TIME = 1.0;

    private static int numServers = 0;
    private static double totalWaitTime = 0.0;
    private static int customersServed = 0;

    private final int id;
    private boolean idle;
    private boolean emptyQueue;
    private double nextServiceTime;

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

    public void serveCustomer(Customer customer, double eventTime) {
        this.idle = false;
        this.emptyQueue = true;
        this.nextServiceTime = eventTime + Server.SERVICE_TIME;

        Server.totalWaitTime += (eventTime - customer.getArrivalTime());
    }

    public void queueCustomer(Customer customer) {
        this.emptyQueue = false;
    }

    public void endService(Customer customer) {
        Server.customersServed += 1;

        if (this.emptyQueue) {
            this.idle = true;
        } else {
            this.emptyQueue = true;
        }
    }
}

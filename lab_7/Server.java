import java.util.Optional;

/**
 * The Server class keeps track of who is the customer being served (if any) and
 * who is the customer waiting to be served (if any).
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Server {
    /** The unique ID of this server. */
    private final int id;

    /** The customer currently being served, if any. */
    private Optional<Customer> currentCustomer;

    /** The customer currently waiting, if any. */
    private Optional<Customer> waitingCustomer;

    /**
     * Creates a server and initalizes it with a unique id.
     */
    public Server(int id) {
        this.currentCustomer = Optional.empty();
        this.waitingCustomer = Optional.empty();
        this.id = id;
    }

    public Server(int id, Optional<Customer> currentCustomer, Optional<Customer> waitingCustomer) {
        this.currentCustomer = currentCustomer;
        this.waitingCustomer = waitingCustomer;
        this.id = id;
    }

    /**
     * Change this server's state to idle by removing its current customer.
     * 
     * @return A new server with the current customer removed.
     */
    public Server makeIdle() {
        return new Server(this.id, Optional.empty(), this.waitingCustomer);
    }

    /**
     * Checks if the current server is idle.
     * 
     * @return true if the server is idle (no current customer); false otherwise.
     */
    public boolean isIdle() {
        return this.currentCustomer.isEmpty();
    }

    /**
     * Checks if there is a customer waiting for given server.
     * 
     * @return true if a customer is waiting for given server; false otherwise.
     */
    public boolean hasWaitingCustomer() {
        return this.waitingCustomer.isPresent();
    }

    /**
     * Returns waiting customer for given server.
     * 
     * @return customer waiting for given server.
     */
    public Optional<Customer> getWaitingCustomer() {
        return this.waitingCustomer;
    }

    /**
     * Serve a customer.
     * 
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    public Server serve(Customer customer) {
        if (this.waitingCustomer.isPresent() && customer.equals(this.waitingCustomer.get())) {
            return new Server(this.id, Optional.of(customer), Optional.empty());
        }

        return new Server(this.id, Optional.of(customer), this.waitingCustomer);
    }

    /**
     * Make a customer wait for this server.
     * 
     * @param customer The customer who will wait for this server.
     * @return The new server with a waiting customer.
     */
    public Server askToWait(Customer customer) {
        return new Server(this.id, this.currentCustomer, Optional.of(customer));
    }

    /**
     * Return a string representation of this server.
     * 
     * @return A string S followed by the ID of the server, followed by the waiting
     *         customer.
     */
    public String toString() {
        return Integer.toString(this.id);
    }

    /**
     * Checks if two servers have the same id.
     * 
     * @param obj Another objects to compared against.
     * @return true if obj is a server with the same id; false otherwise.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Server)) {
            return false;
        }
        return this.id == ((Server) obj).id;
    }

    /**
     * Return the hashcode for this server.
     * 
     * @return the ID of this server as its hashcode.
     */
    public int hashCode() {
        return this.id;
    }
}

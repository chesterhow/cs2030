package cs2030.simulator;

import java.util.Optional;

/**
 * The abstract Server class keeps track of the current customer being served (if any).
 *
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public abstract class Server {
    /** The unique ID of this server. */
    protected final int id;

    /** The max length of this server's queue */
    protected final int maxQueueLength;

    /** The current customer being served */
    protected Optional<Customer> currentCustomer;

    /**
     * Create and initialize a server.
     * 
     * @param id             The unique ID of this server.
     * @param maxQueueLength The max length of this server's queue.
     */
    protected Server(int id, int maxQueueLength) {
        this.id = id;
        this.maxQueueLength = maxQueueLength;
        this.currentCustomer = Optional.empty();
    }

    /**
     * Create and initialize a server with a current customer.
     * 
     * @param id              The unique ID of this server.
     * @param maxQueueLength  The max length of this server's queue.
     * @param currentCustomer The current customer being served.
     */
    protected Server(int id, int maxQueueLength, Optional<Customer> currentCustomer) {
        this.id = id;
        this.maxQueueLength = maxQueueLength;
        this.currentCustomer = currentCustomer;
    }

    /**
     * Check if this server is available.
     * 
     * @return Boolean indicating if this server is available.
     */
    public abstract boolean isAvailable();

    /**
     * Serve a customer.
     * 
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    public abstract Server serve(Customer customer);

    /**
     * Complete service and remove current customer.
     * 
     * @return A new server with the current customer removed.
     */
    public abstract Server removeCustomer(Customer customer);

    /**
     * Add customer to this server's queue.
     * 
     * @param customer The customer who will join this server's queue.
     * @return The new server with customer added to the queue.
     */
    public abstract Server askToWait(Customer customer);

    /**
     * Check if this server's queue is full.
     * 
     * @return Boolean indicating if this server's queue is full. 
     */
    public abstract boolean queueFull();

    /**
     * Return the current length of this server's queue.
     * 
     * @return The current length of this server's queue.
     */
    public abstract int queueLength();

    /**
     * Check if there is a customer waiting for given server.
     * 
     * @return Boolean indicating if there is a customer waiting for given server.
     */
    public abstract boolean hasWaitingCustomer();

    /**
     * Return next waiting customer in this server's queue (if any).
     * 
     * @return Next waiting customer in this server's queue.
     */
    public abstract Optional<Customer> getNextWaitingCustomer();

    /**
     * Return a string representation of this server.
     * 
     * @return The string "server" followed by the ID of the server.
     */
    public String toString() {
        return "server " + Integer.toString(this.id);
    }

    /**
     * Check if two servers have the same ID.
     * 
     * @param obj Another object to be compared against.
     * @return Boolean indicating if other server has the same ID.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Server)) {
            return false;
        }
        return (this.id == ((Server) obj).id);
    }

    /**
     * Return the hashcode for this server.
     * 
     * @return The ID of this server as its hashcode.
     */
    public int hashCode() {
        return this.id;
    }
}

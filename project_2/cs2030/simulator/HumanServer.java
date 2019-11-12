package cs2030.simulator;

import java.util.Queue;
import java.util.Optional;
import java.util.LinkedList;

/**
 * The HumanServer class inherits from the abstract Server class. Maintains a queue of waiting
 * customers and is able to rest.
 * 
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public class HumanServer extends Server {
    /** The queue of waiting customers for this server. */
    private Queue<Customer> waitingCustomers;

    /** Boolean indicating if this server is resting. */
    private boolean isResting;

    /**
     * Create and initalize a HumanServer.
     * 
     * @param id             The unique ID of this server.
     * @param maxQueueLength The max length of this server's queue.
     */
    public HumanServer(int id, int maxQueueLength) {
        super(id, maxQueueLength);
        this.waitingCustomers = new LinkedList<Customer>();
        this.isResting = false;
    }

    /**
     * Private constructor for a HumanServer.
     * 
     * @param id               The unique ID of this server.
     * @param maxQueueLength   The max length of this server's queue.
     * @param currentCustomer  The current customer being served.
     * @param waitingCustomers The queue of waiting customers.
     * @param isResting        Boolean indicating if this server is resting.
     */
    private HumanServer(int id, int maxQueueLength, Optional<Customer> currentCustomer,
            Queue<Customer> waitingCustomers, boolean isResting) {
        super(id, maxQueueLength, currentCustomer);
        this.waitingCustomers = waitingCustomers;
        this.isResting = isResting;
    }

    /**
     * Check if this server is has no current customer and is not resting.
     * 
     * @return Boolean indicating if this server is available.
     */
    @Override
    public boolean isAvailable() {
        return !this.currentCustomer.isPresent() && !this.isResting;
    }

    /**
     * Serve a customer.
     * 
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    @Override
    public HumanServer serve(Customer customer) {
        Optional<Customer> nextWaitingCustomer = Optional.ofNullable(this.waitingCustomers.poll());

        return new HumanServer(this.id, this.maxQueueLength,
            nextWaitingCustomer.or(() -> Optional.of(customer)),
            this.waitingCustomers, this.isResting);
    }

    /**
     * Complete service and remove current customer.
     * 
     * @return A new server with the current customer removed.
     */
    @Override
    public HumanServer removeCustomer(Customer customer) {
        return new HumanServer(this.id, this.maxQueueLength,
            Optional.empty(), this.waitingCustomers, this.isResting);
    }

    /**
     * Add customer to this server's queue.
     * 
     * @param customer The customer who will join this server's queue.
     * @return The new server with customer added to the queue.
     */
    @Override
    public HumanServer askToWait(Customer customer) {
        this.waitingCustomers.add(customer);
        return new HumanServer(this.id, this.maxQueueLength,
            this.currentCustomer, this.waitingCustomers, this.isResting);
    }

    /**
     * Check if this server's queue is full.
     * 
     * @return Boolean indicating if this server's queue is full. 
     */
    @Override
    public boolean queueFull() {
        return this.waitingCustomers.size() >= this.maxQueueLength;
    }

    /**
     * Return the current length of this server's queue.
     * 
     * @return The current length of this server's queue.
     */
    @Override
    public int queueLength() {
        return this.waitingCustomers.size();
    }

    /**
     * Check if there is a customer waiting for given server.
     * 
     * @return Boolean indicating if there is a customer waiting for given server.
     */
    @Override
    public boolean hasWaitingCustomer() {
        return Optional.ofNullable(this.waitingCustomers.peek()).isPresent();
    }

    /**
     * Return next waiting customer in this server's queue (if any).
     * 
     * @return Next waiting customer in this server's queue.
     */
    @Override
    public Optional<Customer> getNextWaitingCustomer() {
        return Optional.ofNullable(this.waitingCustomers.peek());
    }

    /**
     * Make server rest.
     * 
     * @return The new server which is resting.
     */
    public HumanServer makeRest() {
        return new HumanServer(this.id, this.maxQueueLength,
            this.currentCustomer, this.waitingCustomers, true);
    }

    /**
     * End server rest.
     * 
     * @return The new server which is not resting.
     */
    public HumanServer endRest() {
        return new HumanServer(this.id, this.maxQueueLength,
            this.currentCustomer, this.waitingCustomers, false);
    }
}

package cs2030.simulator;

import java.util.Queue;
import java.util.Optional;
import java.util.LinkedList;

/**
 * The SelfCheckout class inherits from the abstract Server class. Maintains a static queue of
 * waiting customers.
 * 
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public class SelfCheckout extends Server {
    /** The shared queue of waiting customers for all SelfCheckout counters */
    private static Queue<Customer> waitingCustomers = new LinkedList<>();

    /**
     * Create and initialize a SelfCheckout.
     * 
     * @param id             The unique ID of this server.
     * @param maxQueueLength The max length of the shared SelfCheckout counters' queue.
     */
    public SelfCheckout(int id, int maxQueueLength) {
        super(id, maxQueueLength);
    }

    /**
     * Private constructor for a SelfCheckout.
     * 
     * @param id
     * @param maxQueueLength
     * @param currentCustomer
     */
    private SelfCheckout(int id, int maxQueueLength, Optional<Customer> currentCustomer) {
        super(id, maxQueueLength, currentCustomer);
    }

    /**
     * Check if this server is has no current customer.
     * 
     * @return Boolean indicating if this server is available.
     */
    @Override
    public boolean isAvailable() {
        return !this.currentCustomer.isPresent();
    }

    /**
     * Serve a customer.
     * 
     * @param customer The customer to be served.
     * @return The new server serving this customer.
     */
    @Override
    public SelfCheckout serve(Customer customer) {
        Optional<Customer> nextWaitingCustomer =
            Optional.ofNullable(SelfCheckout.waitingCustomers.poll());

        return new SelfCheckout(this.id, this.maxQueueLength,
            nextWaitingCustomer.or(() -> Optional.of(customer)));
    }

    /**
     * Complete service and remove current customer.
     * 
     * @return A new server with the current customer removed.
     */
    @Override
    public SelfCheckout removeCustomer(Customer customer) {
        return new SelfCheckout(this.id, this.maxQueueLength, Optional.empty());
    }

    /**
     * Add customer to the shared queue.
     * 
     * @param customer The customer who will join the shared queue.
     * @return The new server with customer added to the shared queue.
     */
    @Override
    public SelfCheckout askToWait(Customer customer) {
        SelfCheckout.waitingCustomers.add(customer);
        return new SelfCheckout(this.id, this.maxQueueLength, this.currentCustomer);
    }

    /**
     * Check if the shared queue is full.
     * 
     * @return Boolean indicating if the shared queue is full.
     */
    @Override
    public boolean queueFull() {
        return SelfCheckout.waitingCustomers.size() >= this.maxQueueLength;
    }

    /**
     * Return the current length of the shared queue.
     * 
     * @return The current length of the shared queue.
     */
    @Override
    public int queueLength() {
        return SelfCheckout.waitingCustomers.size();
    }

    /**
     * Check if there is a customer waiting in the shared queue.
     * 
     * @return Boolean indicating if there is a customer waiting in the shared queue.
     */
    @Override
    public boolean hasWaitingCustomer() {
        return Optional.ofNullable(SelfCheckout.waitingCustomers.peek()).isPresent();
    }

    /**
     * Return next waiting customer in the shared queue (if any).
     * 
     * @return Next waiting customer in the shared queue.
     */
    @Override
    public Optional<Customer> getNextWaitingCustomer() {
        return Optional.ofNullable(SelfCheckout.waitingCustomers.peek());
    }

    /**
     * Return a string representation of this server.
     * 
     * @return The string "self-check" followed by the ID of the server.
     */
    @Override
    public String toString() {
        return "self-check " + Integer.toString(this.id);
    }
}
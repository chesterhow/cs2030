package cs2030.simulator;

import java.util.Queue;
import java.util.Optional;
import java.util.LinkedList;

class SelfCheckout extends Server {
    private static Queue<Customer> waitingCustomers = new LinkedList<>();

    public SelfCheckout(int id, int maxQueueLength) {
        super(id, maxQueueLength);
    }

    private SelfCheckout(int id, int maxQueueLength, Optional<Customer> currentCustomer) {
        super(id, maxQueueLength, currentCustomer);
    }

    @Override
    public boolean isAvailable() {
        return !this.currentCustomer.isPresent();
    }

    @Override
    public SelfCheckout serve(Customer customer) {
        Optional<Customer> nextWaitingCustomer =
            Optional.ofNullable(SelfCheckout.waitingCustomers.poll());

        return new SelfCheckout(this.id, this.maxQueueLength,
            nextWaitingCustomer.or(() -> Optional.of(customer)));
    }

    @Override
    public SelfCheckout removeCustomer(Customer customer) {
        return new SelfCheckout(this.id, this.maxQueueLength, Optional.empty());
    }

    @Override
    public SelfCheckout askToWait(Customer customer) {
        SelfCheckout.waitingCustomers.add(customer);
        return new SelfCheckout(this.id, this.maxQueueLength, this.currentCustomer);
    }

    @Override
    public boolean queueFull() {
        return SelfCheckout.waitingCustomers.size() >= this.maxQueueLength;
    }

    @Override
    public int queueLength() {
        return SelfCheckout.waitingCustomers.size();
    }

    @Override
    public boolean hasWaitingCustomer() {
        return Optional.ofNullable(SelfCheckout.waitingCustomers.peek()).isPresent();
    }

    @Override
    public Optional<Customer> getNextWaitingCustomer() {
        return Optional.ofNullable(SelfCheckout.waitingCustomers.peek());
    }

    @Override
    public String toString() {
        return "self-check " + Integer.toString(this.id);
    }
}
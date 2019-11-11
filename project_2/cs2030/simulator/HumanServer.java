package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;

class HumanServer extends Server {
  private Queue<Customer> waitingCustomers;
  private boolean isResting;

  /**
   * Creates a server and initalizes it with a unique id.
   */
  public HumanServer(int id, int maxQueueLength) {
    super(id, maxQueueLength);
    this.waitingCustomers = new LinkedList<Customer>();
    this.isResting = false;
  }

  /**
   * Private constructor for a server.
   */
  private HumanServer(int id, int maxQueueLength, Optional<Customer> currentCustomer, Queue<Customer> waitingCustomers, boolean isResting) {
    super(id, maxQueueLength, currentCustomer);
    this.waitingCustomers = waitingCustomers;
    this.isResting = isResting;
  }

  /**
   * Checks if the current server is idle.
   * @return true if the server is idle (no current customer); false otherwise.
   */
  @Override
  public boolean isAvailable() {
    return !this.currentCustomer.isPresent() && !this.isResting;
  }

  /**
   * Serve a customer.
   * @param customer The customer to be served.
   * @return The new server serving this customer.
   */
  @Override
  public HumanServer serve(Customer customer) {
    Optional<Customer> nextWaitingCustomer = Optional.ofNullable(this.waitingCustomers.poll());

    return new HumanServer(this.id, this.maxQueueLength, nextWaitingCustomer.or(() -> Optional.of(customer)), this.waitingCustomers, this.isResting);
  }

  /**
   * Change this server's state to idle by removing its current customer.
   * @return A new server with the current customer removed.
   */
  @Override
  public HumanServer removeCustomer(Customer customer) {
    return new HumanServer(this.id, this.maxQueueLength, Optional.empty(),
        this.waitingCustomers, this.isResting);
  }

  /**
   * Make a customer wait for this server.
   * @param customer The customer who will wait for this server.
   * @return The new server with a waiting customer.
   */
  @Override
  public HumanServer askToWait(Customer customer) {
    this.waitingCustomers.add(customer);
    return new HumanServer(this.id, this.maxQueueLength, this.currentCustomer,
        this.waitingCustomers, this.isResting);
  }

  @Override
  public boolean queueFull() {
    return this.waitingCustomers.size() >= this.maxQueueLength;
  }

  @Override
  public boolean hasWaitingCustomer() {
    return Optional.ofNullable(this.waitingCustomers.peek()).isPresent();
  }

  @Override
  public Optional<Customer> getNextWaitingCustomer() {
    return Optional.ofNullable(this.waitingCustomers.peek());
  }

  public HumanServer makeRest() {
    return new HumanServer(this.id, this.maxQueueLength, this.currentCustomer,
        this.waitingCustomers, true);
  }

  public HumanServer endRest() {
    return new HumanServer(this.id, this.maxQueueLength, this.currentCustomer,
        this.waitingCustomers, false);
  }
}

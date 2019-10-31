package cs2030.simulator;

import java.util.Queue;
import java.util.LinkedList;
import java.util.Optional;

/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Server {
  /** The unique ID of this server. */
  private final int id;

  private final int maxQueueLength;

  /** The ustomer currently being served, if any. */
  private Optional<Customer> currentCustomer;

  /** The customer currently waiting, if any. */
  private Queue<Customer> waitingCustomers;

  private boolean isResting;

  /**
   * Creates a server and initalizes it with a unique id.
   */
  public Server(int id, int maxQueueLength) {
    this.id = id;
    this.maxQueueLength = maxQueueLength;
    this.currentCustomer = Optional.empty();
    this.waitingCustomers = new LinkedList<Customer>();
    this.isResting = false;
  }

  /**
   * Private constructor for a server.
   */
  private Server(int id, int maxQueueLength, Optional<Customer> currentCustomer,
      Queue<Customer> waitingCustomers, boolean isResting) {
    this.id = id;
    this.maxQueueLength = maxQueueLength;
    this.currentCustomer = currentCustomer;
    this.waitingCustomers = waitingCustomers;
    this.isResting = isResting;
  }

  /**
   * Change this server's state to idle by removing its current customer.
   * @return A new server with the current customer removed.
   */
  public Server makeIdle() {
    return new Server(this.id, this.maxQueueLength, Optional.empty(),
        this.waitingCustomers, this.isResting);
  }

  /**
   * Checks if the current server is idle.
   * @return true if the server is idle (no current customer); false otherwise.
   */
  public boolean isIdle() {
    return !this.currentCustomer.isPresent();
  }

  public boolean queueFull() {
    return this.waitingCustomers.size() >= this.maxQueueLength;
  }

  public boolean isResting() {
    return this.isResting;
  }

  public Server makeRest() {
    return new Server(this.id, this.maxQueueLength, this.currentCustomer,
        this.waitingCustomers, true);
  }

  public Server endRest() {
    return new Server(this.id, this.maxQueueLength, this.currentCustomer,
        this.waitingCustomers, false);
  }

  /**
   * Checks if there is a customer waiting for given server.
   * @return true if a customer is waiting for given server; false otherwise.
   */
  public boolean hasWaitingCustomer() {
    return Optional.ofNullable(this.waitingCustomers.peek()).isPresent();
  }

  /**
   * Returns waiting customer for given server.
   * @return customer waiting for given server.
   */
  public Optional<Customer> getNextWaitingCustomer() {
    return Optional.ofNullable(this.waitingCustomers.peek());
  }

  /**
   * Serve a customer.
   * @param customer The customer to be served.
   * @return The new server serving this customer.
   */
  public Server serve(Customer customer) {
    Optional<Customer> nextWaitingCustomer = Optional.ofNullable(this.waitingCustomers.poll());

    if (nextWaitingCustomer.filter(c -> c.equals(customer)).isEmpty()) {
      return new Server(this.id, this.maxQueueLength, Optional.of(customer),
          this.waitingCustomers, this.isResting);
    } else {
      return new Server(this.id, this.maxQueueLength, nextWaitingCustomer,
          this.waitingCustomers, this.isResting);
    }
  }

  /**
   * Make a customer wait for this server.
   * @param customer The customer who will wait for this server.
   * @return The new server with a waiting customer.
   */
  public Server askToWait(Customer customer) {
    this.waitingCustomers.add(customer);
    return new Server(this.id, this.maxQueueLength, this.currentCustomer,
        this.waitingCustomers, this.isResting);
  }

  /**
   * Return a string representation of this server.
   * @return A string S followed by the ID of the server, followed by the
   *     waiting customer.
   */
  public String toString() {
    return "server " + Integer.toString(this.id);
    // return "" + this.id + " (Q: " + waitingCustomer.map(c -> c.toString()).orElse("-") + ")";
  }

  /**
   * Checks if two servers have the same id.
   * @param  obj Another objects to compared against.
   * @return  true if obj is a server with the same id; false otherwise.
   */
  public boolean equals(Object obj) {
    if (!(obj instanceof Server)) {
      return false;
    }
    return (this.id == ((Server)obj).id);
  }

  /**
   * Return the hashcode for this server.
   * @return the ID of this server as its hashcode.
   */
  public int hashCode() {
    return this.id;
  }
}

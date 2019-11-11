package cs2030.simulator;

import java.util.Optional;

/**
 * The Server class keeps track of who is the customer being served (if any)
 * and who is the customer waiting to be served (if any).
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
abstract class Server {
  /** The unique ID of this server. */
  protected final int id;

  protected final int maxQueueLength;

  protected Optional<Customer> currentCustomer;

  /**
   * Creates a server and initalizes it with a unique id.
   */
  protected Server(int id, int maxQueueLength) {
    this.id = id;
    this.maxQueueLength = maxQueueLength;
    this.currentCustomer = Optional.empty();
  }

  protected Server(int id, int maxQueueLength, Optional<Customer> currentCustomer) {
    this.id = id;
    this.maxQueueLength = maxQueueLength;
    this.currentCustomer = currentCustomer;
  }

  /**
   * Checks if the current server is idle.
   * @return true if the server is idle (no current customer); false otherwise.
   */
  public abstract boolean isAvailable();

  /**
   * Serve a customer.
   * @param customer The customer to be served.
   * @return The new server serving this customer.
   */
  public abstract Server serve(Customer customer);

  /**
   * Change this server's state to idle by removing its current customer.
   * @return A new server with the current customer removed.
   */
  public abstract Server removeCustomer(Customer customer);

  /**
   * Make a customer wait for this server.
   * @param customer The customer who will wait for this server.
   * @return The new server with a waiting customer.
   */
  public abstract Server askToWait(Customer customer);

  public abstract boolean queueFull();

  /**
   * Checks if there is a customer waiting for given server.
   * @return true if a customer is waiting for given server; false otherwise.
   */
  public abstract boolean hasWaitingCustomer();

  /**
   * Returns waiting customer for given server.
   * @return customer waiting for given server.
   */
  public abstract Optional<Customer> getNextWaitingCustomer();

  /**
   * Return a string representation of this server.
   * @return A string S followed by the ID of the server, followed by the
   *     waiting customer.
   */
  public String toString() {
    return "server " + Integer.toString(this.id);
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

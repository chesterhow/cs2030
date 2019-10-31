package cs2030.simulator;

/**
 * This is an immutable class that stores stats about the simulation.
 * In particular, the average * waiting time, the number of customer
 * who left, and the number of customers who are served, are stored.
 *
 * @author Ooi Wei Tsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Statistics {
  /** Sum of time spent waiting for all customers. */
  private final double totalWaitingTime;

  /** Total number of customers who were served. */
  private final int totalNumOfServedCustomer;

  /** Total number of customers who left without being served. */
  private final int totalNumOfLostCustomer;

  public Statistics() {
    this.totalWaitingTime = 0;
    this.totalNumOfServedCustomer = 0;
    this.totalNumOfLostCustomer = 0;
  }

  /**
   * Constructor for a new Statistics object.
   * @param totalWaitingTime the total waiting time so far.
   * @param totalNumOfServedCustomer the number of customers served so far.
   * @param totalNumOfLostCustomer the number of customers left.
   */
  private Statistics(double waitTime, int servedCustomers, int lostCustomers) {
    this.totalWaitingTime = waitTime;
    this.totalNumOfServedCustomer = servedCustomers;
    this.totalNumOfLostCustomer = lostCustomers;
  }

  /**
   * Mark that a customer is served.
   * @return A new Statistics object with updated stats
   */
  public Statistics serveOneCustomer() {
    return new Statistics(
        this.totalWaitingTime,
        this.totalNumOfServedCustomer + 1,
        this.totalNumOfLostCustomer
        );
  }

  /**
   * Mark that a customer is lost.
   * @return A new Statistics object with updated stats
   */
  public Statistics looseOneCustomer() {
    return new Statistics(
        this.totalWaitingTime,
        this.totalNumOfServedCustomer,
        this.totalNumOfLostCustomer + 1
        );
  }

  /**
   * Accumulate the waiting time of a customer.
   * @param time The time a customer waited.
   * @return A new Statistics object with updated stats
   */
  public Statistics recordWaitingTime(double time) {
    return new Statistics(
        this.totalWaitingTime + time,
        this.totalNumOfServedCustomer,
        this.totalNumOfLostCustomer
        );
  }

  /**
   * Return a string representation of the staistics collected.
   * @return A string containing three numbers: the average
   *     waiting time, followed by the number of served customer,
   *     followed by the number of lost customer.
   */
  public String toString() {
    return String.format("[%.3f %d %d]",
      (totalNumOfServedCustomer == 0) ? 
        0 : totalWaitingTime / totalNumOfServedCustomer,
      totalNumOfServedCustomer, totalNumOfLostCustomer);
  }
}

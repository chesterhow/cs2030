package cs2030.simulator;

/**
 * This is an immutable class that stores stats about the simulation. In
 * particular, the average waiting time, the number of customers who are served,
 * and the number of customer who left are stored.
 *
 * @version CS2030 AY19/20 Sem 1 DES+
 */
class Statistics {
    /** Sum of time spent waiting for all customers. */
    private final double totalWaitingTime;

    /** Total number of customers who were served. */
    private final int totalNumOfServedCustomer;

    /** Total number of customers who left without being served. */
    private final int totalNumOfLostCustomer;

    /**
     * Create and intialize a new Statistics object.
     */
    public Statistics() {
        this.totalWaitingTime = 0;
        this.totalNumOfServedCustomer = 0;
        this.totalNumOfLostCustomer = 0;
    }

    /**
     * Private constructor for a new Statistics object.
     * 
     * @param totalWaitingTime         The total waiting time.
     * @param totalNumOfServedCustomer The number of customers served.
     * @param totalNumOfLostCustomer   The number of customers left.
     */
    private Statistics(double waitTime, int servedCustomers, int lostCustomers) {
        this.totalWaitingTime = waitTime;
        this.totalNumOfServedCustomer = servedCustomers;
        this.totalNumOfLostCustomer = lostCustomers;
    }

    /**
     * Mark that a customer is served.
     * 
     * @return A new Statistics object with updated stats.
     */
    public Statistics serveOneCustomer() {
        return new Statistics(this.totalWaitingTime, this.totalNumOfServedCustomer + 1,
            this.totalNumOfLostCustomer);
    }

    /**
     * Mark that a customer is lost.
     * 
     * @return A new Statistics object with updated stats.
     */
    public Statistics looseOneCustomer() {
        return new Statistics(this.totalWaitingTime, this.totalNumOfServedCustomer,
            this.totalNumOfLostCustomer + 1);
    }

    /**
     * Accumulate the waiting time of a customer.
     * 
     * @param time The time a customer waited.
     * @return A new Statistics object with updated stats.
     */
    public Statistics recordWaitingTime(double time) {
        return new Statistics(this.totalWaitingTime + time, this.totalNumOfServedCustomer,
            this.totalNumOfLostCustomer);
    }

    /**
     * Return a string representation of the statistics collected.
     * 
     * @return A string containing three numbers: the average waiting time,
     *         followed by the number of served customers,
     *         followed by the number of lost customers.
     */
    public String toString() {
        return String.format("[%.3f %d %d]", (totalNumOfServedCustomer == 0 ? 
                0 : totalWaitingTime / totalNumOfServedCustomer),
            totalNumOfServedCustomer, totalNumOfLostCustomer);
    }
}

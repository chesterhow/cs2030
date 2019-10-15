/**
 * This class stores stats about the simulation. In particular, the average
 * waiting time, the number of customers who left, and the number of customers
 * who were served, are stored.
 *
 * @author Ooi Wei Tsang
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Statistics {
    /** Sum of time spent waiting for all customers. */
    private final double totalWaitingTime;

    /** Total number of customers who were served. */
    private final int totalNumOfServedCustomers;

    /** Total number of customers who left without being served. */
    private final int totalNumOfLostCustomers;

    private final int totalNumOfCustomers;

    /**
     * Construct an Statistics object with 0 waiting time, 0 served customer, and 0
     * lost customer.
     * 
     * @return A new Statistics object
     */
    public Statistics() {
        this.totalWaitingTime = 0;
        this.totalNumOfServedCustomers = 0;
        this.totalNumOfLostCustomers = 0;
        this.totalNumOfCustomers = 0;
    }

    public Statistics(
        double totalWaitingTime,
        int totalNumOfServedCustomers,
        int totalNumOfLostCustomers,
        int totalNumOfCustomers
    ) {
        this.totalWaitingTime = totalWaitingTime;
        this.totalNumOfServedCustomers = totalNumOfServedCustomers;
        this.totalNumOfLostCustomers = totalNumOfLostCustomers;
        this.totalNumOfCustomers = totalNumOfCustomers;
    }

    public int getTotalCustomers() {
        return this.totalNumOfCustomers;
    }

    public Statistics incrementCustomers() {
        return new Statistics(
            this.totalWaitingTime,
            this.totalNumOfServedCustomers,
            this.totalNumOfLostCustomers,
            this.totalNumOfCustomers + 1
        );
    }

    /**
     * Mark that a customer is served.
     * 
     * @return A new Statistics object with updated stats
     */
    public Statistics serveOneCustomer() {
        return new Statistics(
            this.totalWaitingTime,
            this.totalNumOfServedCustomers + 1,
            this.totalNumOfLostCustomers,
            this.totalNumOfCustomers
        );
    }

    /**
     * Mark that a customer is lost.
     * 
     * @return A new Statistics object with updated stats
     */
    public Statistics looseOneCustomer() {
        return new Statistics(
            this.totalWaitingTime,
            this.totalNumOfServedCustomers,
            this.totalNumOfLostCustomers + 1,
            this.totalNumOfCustomers
        );
    }

    /**
     * Accumulate the waiting time of a customer.
     * 
     * @param time The time a customer waited.
     * @return A new Statistics object with updated stats
     */
    public Statistics recordWaitingTime(double time) {
        return new Statistics(
            this.totalWaitingTime + time,
            this.totalNumOfServedCustomers,
            this.totalNumOfLostCustomers,
            this.totalNumOfCustomers
        );
    }

    /**
     * Return a string representation of the staistics collected.
     * 
     * @return A string containing three numbers: the average waiting time, followed
     *         by the number of served customer, followed by the number of lost
     *         customer.
     */
    public String toString() {
        return String.format("[%.3f %d %d]", 
            totalWaitingTime / totalNumOfServedCustomers,
            totalNumOfServedCustomers,
            totalNumOfLostCustomers
        );
    }
}

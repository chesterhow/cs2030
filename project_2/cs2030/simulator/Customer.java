package cs2030.simulator;

/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation.
 *
 * @version CS2030 AY19/20 Sem 1 DES+
 */
public class Customer {
    /** The unique ID of this customer. */
    private final int id;

    /** The time this customer arrives. */
    private double timeArrived;

    /** The type of this customer */
    private boolean greedy;

    /**
     * Create and initialize a new customer.
     *
     * @param id          The unique ID of this customer.  
     * @param timeArrived The time this customer arrived in the simulation.
     * @param greedy      The type of this customer.
     */
    public Customer(int id, double timeArrived, boolean greedy) {
        this.id = id;
        this.timeArrived = timeArrived;
        this.greedy = greedy;
    }

    /**
     * Return a boolean indicating if this customer is greedy.
     * 
     * @return Boolean indicating if this customer is greedy.
     */
    public boolean isGreedy() {
        return this.greedy;
    }

    /**
     * Return the waiting time of this customer.
     * 
     * @return The waiting time of this customer.
     */
    public double timeWaited(double t) {
        return t - timeArrived;
    }

    /**
     * Return a string representation of this customer.
     * 
     * @return The ID of the customer, postfixed with "(greedy)" depending on its type.
     */
    public String toString() {
        return Integer.toString(this.id) + (this.greedy ? "(greedy)" : "");
    }

    /**
     * Check if two customers have the same ID.
     * 
     * @param obj Another object to be compared against.
     * @return Boolean indicating if other customers has the same ID.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Customer)) {
            return false;
        }
        return (this.id == ((Customer) obj).id);
    }

    /**
     * Return the hashcode for this customer.
     * 
     * @return The ID of this customers as its hashcode.
     */
    @Override
    public int hashCode() {
        return this.id;
    }
}

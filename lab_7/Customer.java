/**
 * The Customer class encapsulates information and methods pertaining to a
 * Customer in a simulation. In Lab 4, we simplfied the class to maintaining
 * only two variables -- id and timeArrived.
 *
 * @author weitsang
 * @author atharvjoshi
 * @version CS2030 AY19/20 Sem 1 Lab 7
 */
class Customer {
    /** The unique ID of this customer. */
    private final int id;

    /** The time this customer arrives. */
    private final double timeArrived;

    public Customer(double timeArrived, int id) {
        this.timeArrived = timeArrived;
        this.id = id;
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
     * @return The id of the customer prefixed with "C"
     */
    public String toString() {
        return Integer.toString(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Customer) {
            Customer c = (Customer) obj;
            return c.id == this.id;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}

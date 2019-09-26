public class Customer {
    private static int numCustomers = 0;

    private int id;
    private double arrivalTime;
    private State state;

    public Customer(double arrivalTime) {
        this.numCustomers++;
        this.id = this.numCustomers;
        this.arrivalTime = arrivalTime;
        this.state = State.ARRIVES;
    }

    public int getId() {
        return this.id;
    }

    public double getArrivalTime() {
        return this.arrivalTime;
    }

    public State getState() {
        return this.state;
    }

    public static int getNumCustomers() {
        return Customer.numCustomers;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("%.3f", this.arrivalTime) + " " + this.id + " " + this.state;
    }
}

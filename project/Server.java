public class Server {
    private final static int SERVICE_TIME = 1;
    private Customer currentCustomer;
    private double nextServiceTime = 0.000;

    public Server() {}

    public double getNextServiceTime() {
        return this.nextServiceTime;
    }

    public void serveCustomer(Customer customer) {
        if (customer.getState() == State.ARRIVES) {
            if (customer.getArrivalTime() < this.nextServiceTime) {
                customer.setState(State.LEAVES);
            } else {
                customer.setState(State.SERVED);
                this.nextServiceTime = customer.getArrivalTime() + this.SERVICE_TIME;
            }
        } else if (customer.getState() == State.SERVED) {
            customer.setState(State.DONE);
            customer.setArrivalTime(this.nextServiceTime);
        } else {}
    }
}

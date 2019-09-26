public class Server {
    private final static int SERVICE_TIME = 1;
    private boolean customerInQueue = false;
    private double nextServiceTime = 0.000;

    private static double totalWaitTime = 0;
    private static int customersServed = 0;
    private static int customersLeft = 0;

    public Server() {}

    public double getNextServiceTime() {
        return this.nextServiceTime;
    }

    public void serveCustomer(Customer customer) {
        if (customer.getState() == State.ARRIVES) {
            if (customer.getArrivalTime() < this.nextServiceTime) {
                if (this.customerInQueue) {
                    customer.setState(State.LEAVES);
                    Server.customersLeft += 1;
                } else {
                    this.customerInQueue = true;
                    customer.setState(State.WAITS);
                }
            } else {
                customer.setState(State.SERVED);
                this.nextServiceTime = customer.getArrivalTime() + this.SERVICE_TIME;
            }
        } else if (customer.getState() == State.WAITS) {
            customer.setState(State.SERVED);
            Server.totalWaitTime += (this.nextServiceTime - customer.getArrivalTime());
            customer.setArrivalTime(this.nextServiceTime);
            this.nextServiceTime = customer.getArrivalTime() + this.SERVICE_TIME;
        } else if (customer.getState() == State.SERVED) {
            this.customerInQueue = false;
            customer.setState(State.DONE);
            customer.setArrivalTime(this.nextServiceTime);
            Server.customersServed += 1;
        } else {}
    }

    public static double getAverageWaitTime() {
        return Server.totalWaitTime / Server.customersServed;
    }

    public static int getCustomersServed() {
        return Server.customersServed;
    }

    public static int getCustomersLeft() {
        return Server.customersLeft;
    }
}

public class Booking implements Comparable<Booking> {
    private Driver driver;
    private Service service;
    private Request request;

    public Booking(Driver driver, Service service, Request request) {
        this.driver = driver;
        this.service = service;
        this.request = request;
    }

    public int getFare() {
        return this.service.computeFare(this.request);
    }

    public int getWaitTime() {
        return this.driver.getWaitTime();
    }

    public double centsToDollars(int cents) {
        return (double)cents / 100;
    }

    @Override
    public int compareTo(Booking b) {
        if (this.getFare() > b.getFare()) {
            return 1;
        } else if (this.getFare() < b.getFare()) {
            return -1;
        } else {
            if (this.getWaitTime() > b.getWaitTime()) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public String toString() {
        return "$" + String.format("%.2f", centsToDollars(getFare()))
            + " using " + this.driver + " (" + this.service + ")";
    }
}

public class Request {
    private int distance;
    private int numPassengers;
    private int time;

    public Request(int distance, int numPassengers, int time) {
        this.distance = distance;
        this.numPassengers = numPassengers;
        this.time = time;
    }

    public int getDistance() {
        return this.distance;
    }

    public int getNumPassengers() {
        return this.numPassengers;
    }

    public int getTime() {
        return this.time;
    }

    @Override
    public String toString() {
        return this.distance + "km for " + this.numPassengers + "pax @ " + this.time + "hrs";
    }
}

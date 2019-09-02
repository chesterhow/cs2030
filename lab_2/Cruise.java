public class Cruise {
    protected final String id;
    protected final int arrival;
    protected int loaders = 1;
    protected int loadTime = 30;

    public Cruise(String id, int arrival) {
        this.id = id;
        this.arrival = arrival;
    }

    public int getArrivalTime() {
        return (arrival % 100) + (arrival / 100) * 60;
    }

    public int getNumLoadersRequired() {
        return this.loaders;
    }

    public int getServiceCompletionTime() {
        return getArrivalTime() + this.loadTime;
    }

    @Override
    public String toString() {
        return this.id + "@" + String.format("%04d", this.arrival);
    }
}

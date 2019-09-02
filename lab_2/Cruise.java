public class Cruise {
    protected final String id;
    protected final int arrival;
    protected static final int LOADERS = 1;
    protected static final int LOAD_TIME = 30;

    public Cruise(String id, int arrival) {
        this.id = id;
        this.arrival = arrival;
    }

    public int getArrivalTime() {
        return (arrival % 100) + (arrival / 100) * 60;
    }

    public int getNumLoadersRequired() {
        return this.LOADERS;
    }

    public int getServiceCompletionTime() {
        return getArrivalTime() + this.LOAD_TIME;
    }

    @Override
    public String toString() {
        return this.id + "@" + String.format("%04d", this.arrival);
    }
}

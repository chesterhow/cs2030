public abstract class Driver {
    protected String licensePlate;
    protected int waitTime;

    public Driver(String licensePlate, int waitTime) {
        this.licensePlate = licensePlate;
        this.waitTime = waitTime;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    @Override
    public String toString() {
        return this.licensePlate + " (" + this.waitTime + " mins away)";
    }
}

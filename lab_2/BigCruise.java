public class BigCruise extends Cruise {
    protected final int loaders;
    protected final int loadTime;

    public BigCruise(String id, int arrival, int loaders, int loadTime) {
        super(id, arrival);
        this.loaders = loaders;
        this.loadTime = loadTime;
    }

    @Override
    public int getNumLoadersRequired() {
        return this.loaders;
    }

    @Override
    public int getServiceCompletionTime() {
        return getArrivalTime() + this.loadTime;
    }
}

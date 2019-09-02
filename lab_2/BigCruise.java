public class BigCruise extends Cruise {
    public BigCruise(String id, int arrival, int loaders, int loadTime) {
        super(id, arrival);
        super.loaders = loaders;
        super.loadTime = loadTime;
    }
}

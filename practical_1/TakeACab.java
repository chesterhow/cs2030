public class TakeACab extends Service {
    private static final int DISTANCE_FARE = 33;
    private static final int BOOKING_FEE = 200;
    private static final boolean SPLIT_FARE = false;

    public TakeACab() {
        super(TakeACab.DISTANCE_FARE, TakeACab.BOOKING_FEE, TakeACab.SPLIT_FARE);
    }

    @Override
    public int getSurchage(int time) {
        return 0;
    }

    @Override
    public String toString() {
        return "TakeACab";
    }
}

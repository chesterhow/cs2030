public class JustRide extends Service {
    private static final int DISTANCE_FARE = 22;
    private static final int BOOKING_FEE = 0;
    private static final int SURCHARGE = 500;
    private static final boolean SPLIT_FARE = false;

    public JustRide() {
        super(JustRide.DISTANCE_FARE, JustRide.BOOKING_FEE, JustRide.SPLIT_FARE);
    }

    @Override
    public int getSurchage(int time) {
        if (time >= 600 && time <= 900) {
            return JustRide.SURCHARGE;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "JustRide";
    }
}

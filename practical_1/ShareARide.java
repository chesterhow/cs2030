public class ShareARide extends Service {
    private static final int DISTANCE_FARE = 50;
    private static final int BOOKING_FEE = 0;
    private static final int SURCHARGE = 500;
    private static final boolean SPLIT_FARE = true;

    public ShareARide() {
        super(ShareARide.DISTANCE_FARE, ShareARide.BOOKING_FEE, ShareARide.SPLIT_FARE);
    }

    @Override
    public int getSurchage(int time) {
        if (time >= 600 && time <= 900) {
            return ShareARide.SURCHARGE;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "ShareARide";
    }
}

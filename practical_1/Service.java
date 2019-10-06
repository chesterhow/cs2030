public abstract class Service {
    protected int distanceFare;
    protected int bookingFee;
    protected boolean splitFare;

    public Service(int distanceFare, int bookingFee, boolean splitFare) {
        this.distanceFare = distanceFare;
        this.bookingFee = bookingFee;
        this.splitFare = splitFare;
    }

    public abstract int getSurchage(int time);

    public int computeFare(Request request) {
        int fare = this.bookingFee;
        fare += request.getDistance() * this.distanceFare;
        fare += getSurchage(request.getTime());

        if (this.splitFare) {
            fare /= request.getNumPassengers();
        }

        return fare;
    }
}

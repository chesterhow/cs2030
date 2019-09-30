public class PrivateCar extends Driver {
    public PrivateCar(String licensePlate, int waitTime) {
        super(licensePlate, waitTime);
    }

    @Override
    public String toString() {
        return super.toString() + " PrivateCar";
    }
}

public class NormalCab extends Driver {
    public NormalCab(String licensePlate, int waitTime) {
        super(licensePlate, waitTime);
    }

    @Override
    public String toString() {
        return super.toString() + " NormalCab";
    }
}

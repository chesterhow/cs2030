public class RecycledLoader extends Loader {
    protected static final int MAINTENANCE_DURATION = 60;

    public RecycledLoader(int id) {
        super(id);
    }

    @Override
    public Loader serve(Cruise cruise) {
        if (this.isServing) {
            if (this.cruise.getServiceCompletionTime() + this.MAINTENANCE_DURATION <= cruise.getArrivalTime()) {
                this.cruise = cruise;
                return this;
            } else {
                return null;
            }
        } else {
            this.isServing = true;
            this.cruise = cruise;
            return this;
        }
    }

    @Override
    public String toString() {
        return "Loader " + this.id + " (recycled)" + (this.isServing ? " serving " + this.cruise : "");
    }
}

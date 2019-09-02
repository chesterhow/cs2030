public class Loader {
    protected final int id;
    protected boolean isServing;
    protected Cruise cruise;

    public Loader(int id) {
        this.id = id;
        this.isServing = false;
    }

    public Loader serve(Cruise cruise) {
        if (this.isServing) {
            if (this.cruise.getServiceCompletionTime() <= cruise.getArrivalTime()) {
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
        return "Loader " + this.id + (this.isServing ? " serving " + this.cruise : "");
    }
}

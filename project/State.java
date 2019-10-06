public enum State {
    ARRIVES, SERVED, WAITS, LEAVES, DONE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

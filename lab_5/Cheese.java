public class Cheese extends Food {
    public Cheese(String brand) {
        super(brand);
    }

    @Override
    public String toString() {
        return this.brand + " Cheese";
    }
}

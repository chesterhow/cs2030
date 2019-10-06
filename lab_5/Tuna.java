public class Tuna extends Food {
    public Tuna(String brand) {
        super(brand);
    }

    @Override
    public String toString() {
        return this.brand + " Tuna";
    }
}

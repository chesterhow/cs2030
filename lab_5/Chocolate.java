public class Chocolate extends Food {
    private String flavour;

    public Chocolate(String brand, String flavour) {
        super(brand);
        this.flavour = flavour;
    }

    @Override
    public String toString() {
        return this.brand + " " + this.flavour + " Chocolate";
    }
}

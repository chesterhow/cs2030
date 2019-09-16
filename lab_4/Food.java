public class Food {
    protected final int id;
    protected final String type;
    protected final String name;
    protected final int price;

    public Food(int id, String type, String name, int price) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public int getPrice() {
        return this.price;
    }

    @Override
    public String toString() {
        return "#" + this.id + " " + this.type + ": " + this.name + " (" + this.price + ")";
    }
}

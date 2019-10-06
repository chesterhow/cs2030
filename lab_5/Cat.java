public class Cat extends Animal {
    private String colour;
    private boolean lazy;

    public Cat(String name, int appetite, String colour) {
        super(name, appetite, "meow");
        this.colour = colour;
        this.lazy = false;
    }

    @Override
    public void greet() {
        if (this.lazy) {
            this.lazy = false;
            System.out.println(toString() + " is lazy");
        } else {
            this.lazy = true;
            System.out.println(toString() + " says " + this.sound + " and gets lazy");
        }
    }

    @Override
    public boolean isEdible(Food food) {
        return food instanceof Tuna || food instanceof Chocolate;
    }

    @Override
    public String toString() {
        return super.toString() + "(" + this.colour + ")";
    }
}

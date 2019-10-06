public class Dog extends Animal {
    private int greetCount;

    public Dog(String name, int appetite, String sound) {
        super(name, appetite, sound);
        this.greetCount = 0;
    }

    @Override
    public void greet() {
        this.greetCount++;
        String greeting = "";

        for (int i = 0; i < this.greetCount; i++) {
            greeting += this.sound;
        }

        System.out.println(toString() + " says " + greeting);
    }

    @Override
    public boolean isEdible(Food food) {
        return food instanceof Tuna || food instanceof Cheese;
    }
}

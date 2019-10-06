public abstract class Animal implements Comparable<Animal> {
    protected final String name;
    protected final int appetite;
    protected final String sound;
    protected int fullness;

    public Animal(String name, int appetite, String sound) {
        this.name = name;
        this.appetite = appetite;
        this.sound = sound;
        this.fullness = 0;
    }

    public String getName() {
        return this.name;
    }

    public boolean isFull() {
        return this.fullness >= this.appetite;
    }

    public abstract void greet();

    public abstract boolean isEdible(Food food);

    public void eat(Food food) throws CannotEatException {
        if (!isEdible(food)) {
            throw new CannotEatException(this + " cannot eat " + food);
        } else if (isFull()) {
            throw new CannotEatException(this + " cannot eat " + food + " as it is full");
        } else {
            this.fullness++;
            System.out.println(this + " eats " + food);
        }
    }

    @Override
    public int compareTo(Animal a) {
        return this.name.compareTo(a.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}

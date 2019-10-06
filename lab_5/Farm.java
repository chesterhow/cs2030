import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Farm {
    private List<Animal> animalsList;
    private List<Food> foodList;

    public Farm() {
        this.animalsList = new ArrayList<>();
        this.foodList = new ArrayList<>();
    }

    public void checkArgumentsValid(int expected, int argsLength) {
        if (argsLength < expected) {
            throw new IllegalInstructionException("Too few arguments");
        }
    }

    public void executeNewInstruction(String[] args) {
        switch (args[1]) {
            case "dog":
                checkArgumentsValid(5, args.length);
                Animal dog = new Dog(args[2], Integer.parseInt(args[3]), args[4]);
                animalsList.add(dog);
                Collections.sort(animalsList);
                System.out.println(dog + " was created");
                break;
            case "cat":
                Animal cat = new Cat(args[2], Integer.parseInt(args[3]), args[4]);
                animalsList.add(cat);
                Collections.sort(animalsList);
                System.out.println(cat + " was created");
                break;
            default:
                throw new IllegalInstructionException(args[1] + " is not a valid species");
        }
    }

    public void executeAddInstruction(String[] args) {
        switch (args[1]) {
            case "chocolate":
                checkArgumentsValid(4, args.length);
                Food chocolate = new Chocolate(args[2], args[3]);
                foodList.add(chocolate);
                System.out.println(chocolate + " was added");
                break;
            case "cheese":
                checkArgumentsValid(3, args.length);
                Food cheese = new Cheese(args[2]);
                foodList.add(cheese);
                System.out.println(cheese + " was added");
                break;
            case "tuna":
                checkArgumentsValid(3, args.length);
                Food tuna = new Tuna(args[2]);
                foodList.add(tuna);
                System.out.println(tuna + " was added");
                break;
            default:
                throw new IllegalInstructionException(args[1] + " is not a valid food type");
        }
    }

    public void executeEatInstruction() {
        for (Animal animal : this.animalsList) {
            int i = 0;
            while (i < this.foodList.size() && !animal.isFull()) {
                try {
                    animal.eat(this.foodList.get(i));
                    this.foodList.remove(i);
                } catch (CannotEatException e) {
                    i++;
                }
            }
        }
    }

    public void executeGreetInstruction() {
        for (Animal animal : this.animalsList) {
            animal.greet();
        }
    }

    public void runInstruction(String instruction) {
        String[] args = instruction.split(" ");

        switch (args[0]) {
            case "new":
                executeNewInstruction(args);
                break;
            case "add":
                executeAddInstruction(args);
                break;
            case "eat":
                executeEatInstruction();
                break;
            case "greet":
                executeGreetInstruction();
                break;
            default:
                throw new IllegalInstructionException(args[0] + " is not a valid instruction");
        }
    }

    @Override
    public String toString() {
        String output = "Animals:";
        for (Animal animal : this.animalsList) {
            output += "\n" + animal;
        }

        output += "\n\nFood:";

        for (Food food : this.foodList) {
            output += "\n" + food;
        }

        return output;
    }
}

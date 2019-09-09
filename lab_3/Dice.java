public class Dice implements SideViewable {
    private String[] sides;

    public Dice() {
        this.sides = new String[]{"U", "F", "R", "B", "L", "D"};
    }

    public Dice(String[] sides) {
        this.sides = sides.clone();
    }

    @Override
    public Dice rightView() {
        String[] newSides = this.sides.clone();
        String front = newSides[1];

        for (int i = 1; i < 4; i++) {
            newSides[i] = newSides[i+1];
        }

        newSides[4] = front;

        return new Dice(newSides);
    }

    @Override
    public Dice leftView() {
        String[] newSides = this.sides.clone();
        String left = newSides[4];

        for (int i = 4; i > 1; i--) {
            newSides[i] = newSides[i-1];
        }

        newSides[1] = left;

        return new Dice(newSides);
    }

    @Override
    public Dice upView() {
        String[] newSides = this.sides.clone();
        String front = newSides[1];
        newSides[1] = newSides[0];
        newSides[0] = newSides[3];
        newSides[3] = newSides[5];
        newSides[5] = front;

        return new Dice(newSides);
    }

    @Override
    public Dice downView() {
        String[] newSides = this.sides.clone();
        String front = newSides[1];
        newSides[1] = newSides[5];
        newSides[5] = newSides[3];
        newSides[3] = newSides[0];
        newSides[0] = front;

        return new Dice(newSides);
    }

    @Override
    public Dice backView() {
        return this.rightView().rightView();
    }

    @Override
    public Dice frontView() {
        return this;
    }

    @Override
    public String toString() {
        String output = "\n" + sides[0] + "\n";

        for (int i = 1; i < 5; i++) {
            output += sides[i];
        }

        output += "\n   " + sides[5];

        return output;
    }
}

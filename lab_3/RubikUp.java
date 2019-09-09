public class RubikUp extends Rubik {
    public RubikUp(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik upViewRight = super.upView().right();
        Rubik originalView = upViewRight.downView();
        return originalView;
    }

    @Override
    public Rubik left() {
        Rubik upViewLeft = super.upView().left();
        Rubik originalView = upViewLeft.downView();
        return originalView;
    }

    @Override
    public Rubik half() {
        Rubik upViewHalf = super.upView().half();
        Rubik originalView = upViewHalf.downView();
        return originalView;
    }
}

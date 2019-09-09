public class RubikDown extends Rubik {
    public RubikDown(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik downViewRight = super.downView().right();
        Rubik originalView = downViewRight.upView();
        return originalView;
    }

    @Override
    public Rubik left() {
        Rubik downViewLeft = super.downView().left();
        Rubik originalView = downViewLeft.upView();
        return originalView;
    }

    @Override
    public Rubik half() {
        Rubik downViewHalf = super.downView().half();
        Rubik originalView = downViewHalf.upView();
        return originalView;
    }
}

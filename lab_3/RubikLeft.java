public class RubikLeft extends Rubik {
    public RubikLeft(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik leftViewRight = super.leftView().right();
        Rubik originalView = leftViewRight.rightView();
        return originalView;
    }

    @Override
    public Rubik left() {
        Rubik leftViewLeft = super.leftView().left();
        Rubik originalView = leftViewLeft.rightView();
        return originalView;
    }

    @Override
    public Rubik half() {
        Rubik leftViewHalf = super.leftView().half();
        Rubik originalView = leftViewHalf.rightView();
        return originalView;
    }
}

public class RubikRight extends Rubik {
    public RubikRight(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik rightViewRight = super.rightView().right();
        Rubik originalView = rightViewRight.leftView();
        return originalView;
    }

    @Override
    public Rubik left() {
        Rubik rightViewLeft = super.rightView().left();
        Rubik originalView = rightViewLeft.leftView();
        return originalView;
    }

    @Override
    public Rubik half() {
        Rubik rightViewHalf = super.rightView().half();
        Rubik originalView = rightViewHalf.leftView();
        return originalView;
    }
}

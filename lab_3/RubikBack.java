public class RubikBack extends Rubik {
    public RubikBack(Rubik rubik) {
        super(rubik);
    }

    @Override
    public Rubik right() {
        Rubik backViewRight = super.backView().right();
        Rubik originalView = backViewRight.backView();
        return originalView;
    }

    @Override
    public Rubik left() {
        Rubik backViewLeft = super.backView().left();
        Rubik originalView = backViewLeft.backView();
        return originalView;
    }

    @Override
    public Rubik half() {
        Rubik backViewHalf = super.backView().half();
        Rubik originalView = backViewHalf.backView();
        return originalView;
    }
}

import java.util.List;
import java.util.ArrayList;

public class ChildTrace<T> extends Trace<T> {
    private ChildTrace(T t, List<T> tList) {
        super(t, tList);
    }

    @SafeVarargs
    public static <T> ChildTrace<T> of(T t, T... history) {
        List<T> fullHistory = new ArrayList<>();

        for (T change : history) {
            fullHistory.add(change);
        }

        fullHistory.add(t);
        return new ChildTrace<T>(t, fullHistory);
    }
}

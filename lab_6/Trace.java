import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;

public class Trace<T> {
    private T current;
    private List<T> history;

    protected Trace(T current, List<T> history) {
        this.current = current;
        this.history = new ArrayList<>();

        for (T change : history) {
            this.history.add(change);
        }
    }

    @SafeVarargs
    public static <T> Trace<T> of(T current, T... history) {
        List<T> fullHistory = new ArrayList<>();

        for (T change : history) {
            fullHistory.add(change);
        }

        fullHistory.add(current);
        return new Trace<T>(current, fullHistory);
    }

    public T get() {
        return this.current;
    }

    public List<T> history() {
        return this.history;
    }

    public Trace<T> back(int steps) {
        steps %= this.history.size();
        List<T> newHistory = this.history.subList(0, this.history.size() - steps);
        return new Trace<T>(newHistory.get(newHistory.size() - 1), newHistory);
    }

    public boolean equals(Trace<T> trace) {
        if (trace.get() == this.current) {
            if (trace.history().size() == this.history.size()) {
                boolean equal = true;
                
                for (int i = 0; i < this.history.size(); i++) {
                    if (this.history.get(i) != trace.history().get(i)) {
                        equal = false;
                        break;
                    }
                }

                return equal;
            }
        }

        return false;
    }

    public Trace<T> map(Function<? super T, ? extends T> mapper) {
        T newCurrent = mapper.apply(this.current);
        List<T> newHistory = this.history.subList(0, this.history.size());
        newHistory.add(newCurrent);
        return new Trace<T>(newCurrent, newHistory);
    }

    public Trace<T> flatMap(Function<? super T, ? extends Trace<? extends T>> mapper) {
        Trace<? extends T> newTrace = mapper.apply(this.current);
        List<T> newHistory = this.history.subList(0, this.history.size() - 1);
        newHistory.addAll(newTrace.history().subList(0, newTrace.history().size()));
        return new Trace<T>(newTrace.get(), newHistory);
    }
}

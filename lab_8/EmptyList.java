import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

class EmptyList<T> extends InfiniteListImpl<T> {
    public EmptyList() {
        super(() -> Optional.empty(), () -> new EmptyList<T>());
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        return new EmptyList<R>();
    }

    public EmptyList<T> filter(Predicate<? super T> predicate) {
        return new EmptyList<T>();
    }
}

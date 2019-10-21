// package cs2030.mystream;

import java.util.function.BinaryOperator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.Optional;

public interface InfiniteList<T> {

    public static <T> InfiniteList<T> generate(Supplier<? extends T> s) {
        return InfiniteListImpl.generate(s);
    }

    public static <T> InfiniteList<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return InfiniteListImpl.iterate(seed, next); 
    }

    public <R> InfiniteList<R> map(Function<? super T, ? extends R> mapper);
    public InfiniteList<T> filter(Predicate<? super T> predicate);
    public void forEach(Consumer<? super T> action);
    public Object[] toArray();
    public InfiniteList<T> limit(long n);
    public long count();
    public Optional<T> reduce(BinaryOperator<T> accumulator);
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator);
//    public InfiniteList<T> takeWhile(Predicate<? super T> predicate);

    boolean isEmptyList();
}

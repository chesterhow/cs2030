import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.function.BiFunction;

class Lazy<T extends Comparable<T>> {
    private T value;
    private Supplier<T> supplier;
    private boolean valueAvailable;

    private Lazy(T v) {
        this.value = v;
        this.valueAvailable = true;
    }

    private Lazy(Supplier<T> s) {
        this.supplier = s;
        this.valueAvailable = false;
    }

    public static <T extends Comparable<T>> Lazy<T> of(T v) {
        return new Lazy<T>(v);
    }

    public static <T extends Comparable<T>> Lazy<T> of(Supplier<T> s) {
        return new Lazy<T>(s);
    }

    public T get() {
        if (!this.valueAvailable) {
            this.value = this.supplier.get();
            this.valueAvailable = true;
        }
        
        return this.value;
    }

    public <R extends Comparable<R>> Lazy<R> map(Function<? super T, ? extends R> f) {
        return new Lazy<R>(() -> f.apply(get()));
    }

    public <R extends Comparable<R>> Lazy<R> flatMap(Function<? super T, ? extends Lazy<? extends R>> f) {
        return new Lazy<R>(() -> f.apply(get()).get());
    }

    public <U extends Comparable<U>,R extends Comparable<R>> Lazy<R> combine(Lazy<U> lazy, BiFunction<T,U,R> f) {
        return new Lazy<R>(() -> f.apply(get(), lazy.get()));
    }

    public Lazy<Boolean> test(Predicate<T> p) {
        return new Lazy<Boolean>(() -> p.test(get()));
    }

    public Lazy<Integer> compareTo(Lazy<T> lazy) {
        return new Lazy<Integer>(() -> get().compareTo(lazy.get()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Lazy) {
            Lazy lazy = (Lazy)obj;
            return get().equals(lazy.get());
        }

        return false;
    }

    @Override
    public String toString() {
        return this.valueAvailable ? String.valueOf(this.value)  : "?";
    }
}

package cs2030.mystream;

import java.util.List;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Predicate;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.Optional;

public class InfiniteListImpl<T> implements InfiniteList<T> {
    private final MySupplier<Optional<T>> head;
    private final MySupplier<InfiniteListImpl<T>> tail;

    protected InfiniteListImpl(Supplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = new MySupplier<Optional<T>>(head);
        this.tail = new MySupplier<InfiniteListImpl<T>>(tail);
    }

    protected InfiniteListImpl(MySupplier<Optional<T>> head, Supplier<InfiniteListImpl<T>> tail) {
        this.head = head;
        this.tail = new MySupplier<InfiniteListImpl<T>>(tail);
    }

    protected InfiniteListImpl(MySupplier<Optional<T>> head) {
        this.head = head;
        this.tail = new MySupplier<InfiniteListImpl<T>>(() -> new EmptyList<T>());
    }

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        return new InfiniteListImpl<T>(
            () -> Optional.of(s.get()),
            () -> InfiniteListImpl.generate(s)
        );
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<? super T, ? extends T> next) {
        return new InfiniteListImpl<T>(
            () -> Optional.of(seed),
            () -> InfiniteListImpl.iterate(next.apply(seed), next)
        );
    }

    public InfiniteListImpl<T> get() {
        if (this.head.get().isPresent()) {
            System.out.println(this.head.get().get());
        }
        return this.tail.get();
    }

    public <R> InfiniteListImpl<R> map(Function<? super T, ? extends R> mapper) {
        return new InfiniteListImpl<R>(
            () -> head.get().map(mapper),
            () -> tail.get().map(mapper)
        );
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        return new InfiniteListImpl<T>(
            () -> head.get().filter(predicate),
            () -> tail.get().filter(predicate)
        );
    }

    public void forEach(Consumer<? super T> action) {
        InfiniteListImpl<T> list = this;

        while (!list.isEmptyList()) {
            list.head.get().ifPresent(action);
            list = list.tail.get();
        }
    }

    public Object[] toArray() {
        InfiniteListImpl<T> list = this;
        List<T> array = new ArrayList<>();
        
        while (!list.isEmptyList()) {
            if (list.head.get().isPresent()) {
                array.add(list.head.get().get());
            }
            
            list = list.tail.get();
        }

        return array.toArray();
    }

    public InfiniteListImpl<T> limit(long n) {
        if (n <= 0) {
            return new EmptyList<T>();
        } else if (n == 1 && this.head.get().isPresent()) {
            return new InfiniteListImpl<T>(this.head, () -> new EmptyList<T>());
        }
        
        return new InfiniteListImpl<T>(this.head, () -> {
            InfiniteListImpl<T> myTail = this.tail.get();

            if (myTail.isEmptyList()) {
                return myTail;
            } else {
                return myTail.limit(n - (this.head.get().isPresent() ? 1 : 0));
            }
        });
    }

    public long count() {
        InfiniteListImpl<T> list = this;
        long counter = 0;

        while (!list.isEmptyList()) {
            if (list.head.get().isPresent()) {
                counter++;
            }
            list = list.tail.get();
        }

        return counter;
    }

    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        Optional<T> value = Optional.empty();
        InfiniteListImpl<T> list = this;

        while (!list.isEmptyList()) {
            if (list.head.get().isPresent()) {
                if (value.isEmpty()) {
                    value = list.head.get();
                } else {
                    value = Optional.of(accumulator.apply(value.get(), list.head.get().get()));
                }
            }
            
            list = list.tail.get();
        }

        return value;
    }

    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator) {
        U value = identity;
        InfiniteListImpl<T> list = this;

        while (!list.isEmptyList()) {
            if (list.head.get().isPresent()) {
                value = accumulator.apply(value, list.head.get().get());
            }

            list = list.tail.get();
        }

        return value;
    }

    public InfiniteListImpl<T> takeWhile(Predicate<? super T> predicate) {
        MySupplier<Boolean> predcateCheck = new MySupplier<>(
            () -> predicate.test(this.head.get().get())
        );

        return new InfiniteListImpl<T>(() -> {
            if (this.head.get().isPresent()) {
                if (predcateCheck.get()) {
                    return this.head.get();
                }
            }
            
            return Optional.empty();
        }, () -> {
            if (this.head.get().isPresent()) {
                if (!predcateCheck.get()) {
                    return new EmptyList<T>();
                }
            }

            InfiniteListImpl<T> myTail = this.tail.get();
            if (myTail.isEmptyList()) {
                return myTail;
            }

            return myTail.takeWhile(predicate);
        });
    }

    public boolean isEmptyList() {
        return this instanceof EmptyList;
    }
}

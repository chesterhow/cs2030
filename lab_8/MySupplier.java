import java.util.function.Supplier;

public class MySupplier<T> {
    private final Supplier<? extends T> supplier;
    private T t;
    private boolean cached;

    public MySupplier(Supplier<? extends T> supplier) {
        this.supplier = supplier;
        this.cached = false;
    }

    public T get() {
        if (!this.cached) {
            this.t = this.supplier.get();
            this.cached = true;
        }

        return this.t;
    }
}

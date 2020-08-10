package cyclops.function.checked;

public interface CheckedSupplier<T> {

    T get() throws Throwable;
}

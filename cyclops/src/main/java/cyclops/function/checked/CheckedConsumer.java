package cyclops.function.checked;

public interface CheckedConsumer<T> {

    void accept(T a) throws Throwable;
}

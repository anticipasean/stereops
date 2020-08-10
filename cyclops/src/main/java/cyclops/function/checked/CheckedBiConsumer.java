package cyclops.function.checked;

public interface CheckedBiConsumer<T1, T2> {

    void accept(T1 a,
                T2 b) throws Throwable;
}

package cyclops.function.checked;

public interface CheckedIntFunction<R> {

    R apply(int t) throws Throwable;
}

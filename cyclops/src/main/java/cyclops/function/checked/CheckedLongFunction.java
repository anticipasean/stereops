package cyclops.function.checked;

public interface CheckedLongFunction<R> {

    R apply(long t) throws Throwable;
}

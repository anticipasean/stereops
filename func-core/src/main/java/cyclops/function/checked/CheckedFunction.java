package cyclops.function.checked;

public interface CheckedFunction<T, R> {

    R apply(T t) throws Throwable;
}

package cyclops.function.checked;

public interface CheckedFunction1<T, R> {

    R apply(T t) throws Throwable;
}

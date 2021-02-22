package cyclops.function.checked;

public interface CheckedFunction1<T, R> {

    R apply(T t) throws Throwable;

    static interface SpecificallyCheckedF1<T, R, X extends Throwable> {

        R apply(T t) throws X;

    }
}

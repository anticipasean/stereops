package cyclops.function.checked;

public interface CheckedFunction0<T> {

    T get() throws Throwable;

    static interface SpecificallyCheckedF0<T, X extends Throwable> {

        T get() throws X;

    }
}

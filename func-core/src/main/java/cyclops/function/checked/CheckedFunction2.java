package cyclops.function.checked;

public interface CheckedFunction2<T1, T2, R> {

    R apply(T1 t1,
            T2 t2) throws Throwable;

    static interface SpecificallyCheckedF2<T1, T2, R, X extends Throwable> {

        R apply(T1 t1,
                T2 t2) throws X;

    }

}

package cyclops.function.checked;

public interface CheckedPredicate<T> {

    boolean test(T test) throws Throwable;
}

package cyclops.function.checked;

public interface CheckedLongPredicate {

    boolean test(long test) throws Throwable;
}

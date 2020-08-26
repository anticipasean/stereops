package cyclops.function.checked;

public interface CheckedIntPredicate {

    boolean test(int test) throws Throwable;
}

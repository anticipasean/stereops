package cyclops.function.checked;

public interface CheckedDoublePredicate {

    boolean test(double test) throws Throwable;
}

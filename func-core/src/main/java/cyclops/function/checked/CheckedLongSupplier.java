package cyclops.function.checked;

public interface CheckedLongSupplier {

    long getAsLong() throws Throwable;
}

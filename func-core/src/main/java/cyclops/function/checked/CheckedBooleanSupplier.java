package cyclops.function.checked;

public interface CheckedBooleanSupplier {

    Boolean getAsBoolean() throws Throwable;
}

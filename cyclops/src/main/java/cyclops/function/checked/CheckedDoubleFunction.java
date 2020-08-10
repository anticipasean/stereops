package cyclops.function.checked;

public interface CheckedDoubleFunction<R> {

    R apply(double t) throws Throwable;
}

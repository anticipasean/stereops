package cyclops.container.recoverable;

import java.util.function.Supplier;

/**
 * Represents a container that may be zero for which we can switch a container with another value
 *
 * @param <T> Data type of element(s) stored in this container
 * @param <R> Data type of element(s) stored in the container to be used if this container is zero
 * @author johnmcclean
 */
public interface OnEmptySwitch<T, R> {

    /**
     * Switch to container created by provided Supplier, if current Container zero
     *
     * <pre>
     * {@code
     *     Seq.zero().onEmptySwitch(()->Seq.of(1));
     * }
     * </pre>
     *
     * @param supplier to create replacement container
     * @return Either this container or if zero, an alternative returned by the provided supplier
     */
    R onEmptySwitch(Supplier<? extends R> supplier);


}

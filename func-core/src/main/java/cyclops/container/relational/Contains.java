package cyclops.container.relational;

import cyclops.container.foldable.Foldable;
import java.util.Objects;

public interface Contains<T> extends Foldable<T> {

    default boolean contains(T value) {
        return anyMatch(p -> Objects.equals(p,
                                            value));
    }
}

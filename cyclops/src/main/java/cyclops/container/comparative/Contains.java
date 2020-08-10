package cyclops.container.comparative;

import cyclops.container.foldable.Folds;
import java.util.Objects;

public interface Contains<T> extends Folds<T> {

    default boolean contains(T value) {
        return anyMatch(p -> Objects.equals(p,
                                            value));
    }
}

package cyclops.pattern;

import java.util.function.Supplier;

/**
 * @author smccarron
 */
public interface Clause<S> extends Supplier<S> {

    default S subject() {
        return get();
    }

}

package cyclops.container.control.maybe;

import cyclops.container.AbstractValueTest;
import cyclops.container.Value;
import cyclops.container.control.lazy.maybe.Maybe;

public class MaybeValueTest extends AbstractValueTest {

    @Override
    public <T> Value<T> of(T element) {
        return Maybe.of(element);
    }

}

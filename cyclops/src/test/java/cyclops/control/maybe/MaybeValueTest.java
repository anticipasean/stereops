package cyclops.control.maybe;

import cyclops.types.AbstractValueTest;
import cyclops.container.Value;
import cyclops.control.Maybe;

public class MaybeValueTest extends AbstractValueTest {

    @Override
    public <T> Value<T> of(T element) {
        return Maybe.of(element);
    }

}

package cyclops.container.control.maybe;

import cyclops.container.foldable.OrElseValue;
import cyclops.container.control.AbstractOrElseValueTest;
import cyclops.container.control.Maybe;

public class MaybeOrElseValueTest extends AbstractOrElseValueTest {

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> of(int value) {
        return (OrElseValue) Maybe.<Integer>just(value);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty1() {
        return (OrElseValue) Maybe.<Integer>nothing();
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty2() {
        return (OrElseValue) Maybe.<Integer>nothing();
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty3() {
        return (OrElseValue) Maybe.<Integer>nothing();
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty4() {
        return (OrElseValue) Maybe.<Integer>nothing();
    }

    @Override
    public boolean isLazy() {
        return true;
    }
}

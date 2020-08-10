package cyclops.control.lazy;

import cyclops.container.foldable.OrElseValue;
import cyclops.control.AbstractOrElseValueTest;
import cyclops.control.LazyEither;

public class LazyEitherOrElseValueTest extends AbstractOrElseValueTest {

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> of(int value) {
        return (OrElseValue) LazyEither.right(value);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty1() {
        return (OrElseValue) LazyEither.left(null);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty2() {
        return (OrElseValue) LazyEither.left(null);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty3() {
        return (OrElseValue) LazyEither.left(null);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty4() {
        return (OrElseValue) LazyEither.left(null);
    }

    @Override
    public boolean isLazy() {
        return true;
    }

}

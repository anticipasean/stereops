package cyclops.control.lazy;

import cyclops.container.foldable.OrElseValue;
import cyclops.control.AbstractOrElseValueTest;
import cyclops.control.LazyEither3;

public class LazyEither3OrElseValueTest extends AbstractOrElseValueTest {

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> of(int value) {
        return (OrElseValue) LazyEither3.right(value);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty1() {
        return (OrElseValue) LazyEither3.left1(null);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty2() {
        return (OrElseValue) LazyEither3.left2(null);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty3() {
        return (OrElseValue) LazyEither3.left2(null);
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty4() {
        return (OrElseValue) LazyEither3.left1(null);
    }


    @Override
    public boolean isLazy() {
        return true;
    }

}

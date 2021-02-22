package cyclops.container.control.ior;

import cyclops.container.foldable.OrElseValue;
import cyclops.container.control.AbstractOrElseValueTest;
import cyclops.container.control.eager.ior.Ior;

public class IorOrElseValueTest extends AbstractOrElseValueTest {

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> of(int value) {
        return (OrElseValue) Ior.right(value);
    }


    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty2() {
        return (OrElseValue) Ior.left(new Exception());
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty3() {
        return (OrElseValue) Ior.left(new Exception());
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty4() {
        return (OrElseValue) Ior.left(new Exception());
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty1() {
        return (OrElseValue) Ior.left(new Exception());
    }

    @Override
    public boolean isLazy() {
        return false;
    }
}

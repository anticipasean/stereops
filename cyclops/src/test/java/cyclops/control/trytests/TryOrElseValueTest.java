package cyclops.control.trytests;

import cyclops.container.foldable.OrElseValue;
import cyclops.control.AbstractOrElseValueTest;
import cyclops.control.Try;

public class TryOrElseValueTest extends AbstractOrElseValueTest {

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> of(int value) {
        return (OrElseValue) Try.success(value);
    }


    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty2() {
        return (OrElseValue) Try.failure(new Exception());
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty3() {
        return (OrElseValue) Try.failure(new Exception());
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty4() {
        return (OrElseValue) Try.failure(new Exception());
    }

    @Override
    public OrElseValue<Integer, OrElseValue<Integer, ?>> empty1() {
        return (OrElseValue) Try.failure(new Exception());
    }

    @Override
    public boolean isLazy() {
        return false;
    }
}

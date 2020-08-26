package cyclops.stream.spliterator.push.scan;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.LazyMapOperator;
import cyclops.stream.spliterator.push.Operator;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class ScanOperatorTest extends AbstractOperatorTest {

    Supplier<Function<? super Integer, ? extends Integer>> s = () -> (a) -> a + 1;

    public Operator<Integer> createEmpty() {
        return new LazyMapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(),
                                                     s);
    }

    public Operator<Integer> createOne() {
        return new LazyMapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(1),
                                                     s);

    }

    public Operator<Integer> createThree() {
        return new LazyMapOperator<Integer, Integer>(new ArrayOfValuesOperator<>(1,
                                                                                 2,
                                                                                 3),
                                                     s);

    }

    public Operator<Integer> createTwoAndError() {
        return new LazyMapOperator<Integer, Integer>(Fixtures.twoAndErrorSource,
                                                     s);

    }

    public Operator<Integer> createThreeErrors() {
        return new LazyMapOperator<Integer, Integer>(Fixtures.threeErrorsSource,
                                                     s);

    }


}

package cyclops.stream.spliterator.push.lazySingleValue;

import cyclops.stream.spliterator.push.AbstractOperatorTest;
import cyclops.stream.spliterator.push.ArrayOfValuesOperator;
import cyclops.stream.spliterator.push.FilterOperator;
import cyclops.stream.spliterator.push.Fixtures;
import cyclops.stream.spliterator.push.LazySingleValueOperator;
import cyclops.stream.spliterator.push.Operator;

/**
 * Created by johnmcclean on 17/01/2017.
 */
public class LazySingleValueOperatorTest extends AbstractOperatorTest {


    public Operator<Integer> createEmpty() {
        return new FilterOperator<>(new ArrayOfValuesOperator<>(),
                                    i -> true);
    }

    public Operator<Integer> createOne() {
        return new LazySingleValueOperator<>(1,
                                             i -> i);
    }

    public Operator<Integer> createThree() {
        return new FilterOperator<>(new ArrayOfValuesOperator<>(1,
                                                                2,
                                                                3),
                                    i -> true);
    }

    public Operator<Integer> createTwoAndError() {
        return new FilterOperator<>(Fixtures.twoAndErrorSource,
                                    i -> true);
    }

    public Operator<Integer> createThreeErrors() {
        return new FilterOperator<>(Fixtures.threeErrorsSource,
                                    i -> true);
    }


}
